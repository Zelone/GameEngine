/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.render;

import com.zelone.models.RawModel;
import com.zelone.shader.TerrainShader;
import com.zelone.terrain.Terrain;
import com.zelone.texture.ModelTexture;
import com.zelone.toolBox.Maths;
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
public class TerrainRenderer
{

    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix)
    {
        this.shader = shader;
        
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<Terrain> terrains)
    {
        for (Terrain terrain : terrains) {

            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

            unBindTerrain();
        }
    }

    private void prepareTerrain(Terrain terrain)
    {
        RawModel model = terrain.getModel();
        ModelTexture modelTexture = terrain.getTexture();

        //binding VAO of the current model
        GL30.glBindVertexArray(model.getVaoID());

        //enabling levels of VAO needed
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());

        //activating and binding textures to triangles
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getID());

    }

    private void loadModelMatrix(Terrain terrain)
    {
        //getting Transformation matrix of the current entity
        Matrix4f transformationMatrix = getTransformationMatrix(terrain);
        //loading transformations, shine to shader
        shader.loadTransformationMatrix(transformationMatrix);
        //drawing each triangle

    }

    private void unBindTerrain()
    {
        //disabling levels of VAO 
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        //unbinding VAO of the current model
        GL30.glBindVertexArray(0);

    }

    private Matrix4f getTransformationMatrix(Terrain terrain)
    {
        return Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
    }
}
