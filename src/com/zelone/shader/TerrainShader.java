/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.shader;

import com.zelone.entities.Camera;
import com.zelone.entities.Light;
import com.zelone.toolBox.Maths;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Jhawar
 */
public class TerrainShader extends ShaderProgram 

{

    private static final String VERTIX_FILE = "src/com/zelone/shader/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/com/zelone/shader/terrainFragmentShader.glsl";
    private int location_transformationMatrix;
    private int loction_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightColor;
    private int locaiton_lightPosition;
    private int location_reflectivity;
    private int location_shineDamper;

    public TerrainShader()
    {
        super(VERTIX_FILE, FRAGMENT_FILE);

    }

    @Override
    protected void bindAttributes()
    {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getUniformLocations()
    {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        loction_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        locaiton_lightPosition = super.getUniformLocation("lightPositon");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");

    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix)
    {
        super.loadMatrix(loction_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadShineVariables(float shineDamper, float reflectivity)
    {
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(this.location_shineDamper, shineDamper);
        
    }


    public void loadLight(Light light)
    {
        super.loadVector(locaiton_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
        
    }

}
