/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.render;

import com.zelone.entities.Entity;
import com.zelone.models.RawModel;
import com.zelone.models.TexturedModel;
import com.zelone.shader.StaticShader;
import com.zelone.texture.ModelTexture;
import com.zelone.toolBox.Maths;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Jhawar
 */
public class EntityRenderer
{

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix)
    {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities)
    {
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);

            for (Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            unBindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel texturedModel)
    {
        RawModel model = texturedModel.getRawModel();
        ModelTexture modelTexture = texturedModel.getTexture();

        //binding VAO of the current model
        GL30.glBindVertexArray(model.getVaoID());

        //enabling levels of VAO needed
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        if (modelTexture.hasTransperancy()) {
            MasterRenderer.disableCulling();
        }
shader.useFakeLighting(modelTexture.useFakeLighting());
        shader.loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());

        //activating and binding textures to triangles
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getID());

    }

    private void prepareInstance(Entity entity)
    {
        //getting Transformation matrix of the current entity
        Matrix4f transformationMatrix = getTransformationMatrix(entity);
        //loading transformations, shine to shader
        shader.loadTransformationMatrix(transformationMatrix);
        //drawing each triangle

    }

    private void unBindTexturedModel()
    {
        MasterRenderer.enableCulling();
        //disabling levels of VAO 
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        //unbinding VAO of the current model
        GL30.glBindVertexArray(0);

    }

    private Matrix4f getTransformationMatrix(Entity entity)
    {
        return Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
    }

    @Deprecated
    public void render(Entity entity, StaticShader shader)
    {
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getRawModel();

        ModelTexture modelTexture = texturedModel.getTexture();

        //binding VAO of the current model
        GL30.glBindVertexArray(model.getVaoID());

        //enabling levels of VAO needed
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        //getting Transformation matrix of the current entity
        Matrix4f transformationMatrix = getTransformationMatrix(entity);
        //loading transformations, shine to shader
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());

        //activating and binding textures to triangles
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());

        //drawing each triangle
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        //disabling levels of VAO 
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        //unbinding VAO of the current model
        GL30.glBindVertexArray(0);
    }

    @Deprecated
    public void render(TexturedModel texturedModel)
    {
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

}
