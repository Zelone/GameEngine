/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Jhawar
 */
public class Engine {

    private static List<Integer> vaos = new ArrayList<Integer>();
    private static List<Integer> vbos = new ArrayList<Integer>();
    private static List<Integer> textures = new ArrayList<Integer>();

    public Engine() {

    }

    public static int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_LINEAR);

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    public static int loadToVAO(float[] postions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);

        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        storeDataInAttributeList(0, 3, postions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);

        GL30.glBindVertexArray(0);
        return vaoID;
    }

    private static void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

    }

    public static void removeAllVao() {
        for (Integer vao : vaos) {
            removeVAO(vao);
        }
        vaos.clear();
    }

    private static void removeVAO(int vao) {
        GL30.glDeleteVertexArrays(vao);
    }

    public static void removeVao(int vao) {
        vaos.remove(vao);
        removeVAO(vao);
    }

    public static void removeAllVbo() {
        for (Integer vbo : vbos) {
            removeVBO(vbo);
        }
        vbos.clear();
    }

    private static void removeVBO(int vbo) {
        GL15.glDeleteBuffers(vbo);
    }

    public static void removeVbo(int vbo) {
        vbos.remove(vbo);
        removeVBO(vbo);
    }

    public static void removeAllTextures() {
        for (Integer texture : textures) {
            removeTEXTURE(texture);
        }
        textures.clear();
    }

    private static void removeTEXTURE(int texture) {
        GL11.glDeleteTextures(texture);
    }

    public static void removeTexture(int texture) {
        textures.remove(texture);
        removeTEXTURE(texture);
    }

    private static void enableVA_ARRAY(int i) {
        GL20.glEnableVertexAttribArray(i);
    }

    public static void enableVaArray(int... i) {
        for (int ii : i) {
            enableVA_ARRAY(ii);
        }
    }

    public static void bindVERTEX_ARRAY(int VaoID) {
        GL30.glBindVertexArray(VaoID);
    }

    public static void bindVertexArray(int VaoID) {
        bindVERTEX_ARRAY(VaoID);
    }

}
