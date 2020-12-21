/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.engine;

import com.sun.prism.impl.BufferUtil;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Jhawar
 */
public class Loader
{
private List<Integer> vaos = new ArrayList<Integer>();
private List<Integer> vbos = new ArrayList<Integer>();

    public RawModel loadoVAO(float[] postions)
    {
        int vaoID = createVAO();
        storeDataInAttributeList(0, postions);
        unbindVAO();
        return new RawModel(vaoID, postions.length / 3);
    }

    private int createVAO()
    {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data)
    {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer=storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO()
    {

        GL30.glBindVertexArray(0);

    }
    
    private FloatBuffer storeDataInFloatBuffer(float[] data){
    
        FloatBuffer buffer= BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    
    public void cleanUp(){
        for (Integer vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (Integer vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }
}

