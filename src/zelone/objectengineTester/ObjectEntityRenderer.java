/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.rawengineTester;

import zelone.engine.Engine;
import zelone.render.MasterRenderer;
import zelone.shader.StaticShader;
import zelone.toolBox.Maths;
import java.util.List;
import java.util.Map;
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
public class RawEntityRenderer {

    private StaticShader shader;
// entity = float{texturedModel{vaoid,indiceslength,textureVaoID,shineDampner,reflectivity,hastransparency,usefakelighting},position{x,y,z},entityRotationScale{rotx, roty, rotz, scale},rotate{l,m,n},translate{x,y,z}}

    public RawEntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        this.shader.start();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.stop();
    }

    public void render(Map<float[], List<float[][]>> entities) {
        for (float[] model : entities.keySet()) {
            int VaoID = (int) model[0];
            int indiceslength = (int) model[1];
            int textureVaoID = (int) model[2];
            boolean hasTransperancy = ((int) model[5] == 1) ? true : false;
            boolean useFakeLighting = ((int) model[6] == 1) ? true : false;

            //binding VAO of the current model
            Engine.bindVertexArray(VaoID); 

            //enabling levels of VAO needed
            Engine.enableVaArray(0,1,2);
            
            if (hasTransperancy) {
                MasterRenderer.disableCulling();
            }
            shader.useFakeLighting(useFakeLighting);
            shader.loadShineVariables(model[3], model[4]);

            //activating and binding textures to triangles
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureVaoID);

            List<float[][]> batch = entities.get(model);

            for (float[][] entity : batch) {
                //getting Transformation matrix of the current entity
                float[] position = entity[1];
                float[] entityRotationScale = entity[2];
                Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(position[0], position[1], position[2]), entityRotationScale[0], entityRotationScale[1], entityRotationScale[2], entityRotationScale[3]);
                //loading transformations, shine to shader
                shader.loadTransformationMatrix(transformationMatrix);
                //drawing each triangle

                GL11.glDrawElements(GL11.GL_TRIANGLES, indiceslength, GL11.GL_UNSIGNED_INT, 0);
            }

            MasterRenderer.enableCulling();
            //disabling levels of VAO 
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);

            //unbinding VAO of the current model
            GL30.glBindVertexArray(0);
        }
    }

    @Deprecated
    public void render(float[][] entity, StaticShader shader) {
        //binding VAO of the current model
        GL30.glBindVertexArray((int) (entity[0][0]));

        //enabling levels of VAO needed
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        //getting Transformation matrix of the current entity
        float[] position = entity[1];
        float[] entityRotationScale = entity[2];
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(position[0], position[1], position[2]), entityRotationScale[0], entityRotationScale[1], entityRotationScale[2], entityRotationScale[3]);
        //loading transformations, shine to shader
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadShineVariables(entity[0][3], entity[0][4]);

        //activating and binding textures to triangles
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, (int) (entity[0][2]));

        //drawing each triangle
        GL11.glDrawElements(GL11.GL_TRIANGLES, (int) (entity[0][1]), GL11.GL_UNSIGNED_INT, 0);

        //disabling levels of VAO 
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        //unbinding VAO of the current model
        GL30.glBindVertexArray(0);
    }

    @Deprecated
    public void render(float[] texturedModel) {
        int VaoID = (int) texturedModel[0];
        int indiceslength = (int) texturedModel[1];
        int textureVaoID = (int) texturedModel[2];
        
        GL30.glBindVertexArray(VaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureVaoID);
        GL11.glDrawElements(GL11.GL_TRIANGLES, indiceslength, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

}
