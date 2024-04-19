/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.objectengineTester;

import zelone.shader.TerrainShader;
import zelone.toolBox.Maths;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class ObjectTerrainRenderer {

    private TerrainShader shader;
//terrain = float[]{x,z,vaoID,indicesLength,rtexture,gtexture,btexture,backgroundTexture,blendMap}

    public ObjectTerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        this.shader.connectTextureSampler();
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    public boolean render(List<Terrain> terrains) {
        
        return false;

    }

    public void render(List<float[]> terrains) {

        for (float[] terrain : terrains) {
            //  terrain = float[]{x,z,vaoID,indicesLength,rtexture,gtexture,btexture,backgroundTexture,blendMap}

            int vaoID = (int) terrain[2];
            //binding VAO of the current model
            GL30.glBindVertexArray(vaoID);

            //enabling levels of VAO needed
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, (int) terrain[7]);

            GL13.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, (int) terrain[4]);

            GL13.glActiveTexture(GL13.GL_TEXTURE2);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, (int) terrain[5]);

            GL13.glActiveTexture(GL13.GL_TEXTURE3);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, (int) terrain[6]);

            GL13.glActiveTexture(GL13.GL_TEXTURE4);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, (int) terrain[8]);
            shader.loadShineVariables(1, 0);
            //activating and binding textures to triangles
            //getting Transformation matrix of the current entity
            Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain[0], 0, terrain[1]), 0, 0, 0, 1);
            //loading transformations, shine to shader
            shader.loadTransformationMatrix(transformationMatrix);
            //drawing each triangle
            GL11.glDrawElements(GL11.GL_TRIANGLES, (int) terrain[3], GL11.GL_UNSIGNED_INT, 0);

            //disabling levels of VAO 
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);

            //unbinding VAO of the current model
            GL30.glBindVertexArray(0);
        }
    }
}
