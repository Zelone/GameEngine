/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.shader;

import com.zelone.entities.Camera;
import com.zelone.toolBox.Maths;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Jhawar
 */
public class StaticShader extends ShaderProgram
{

    private static final String VERTIX_FILE = "src/com/zelone/shader/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/com/zelone/shader/fragmentShader.glsl";
    private int location_transformationMatrix;
    private int loction_projectionMatrix;
    private int location_viewMatrix;

    public StaticShader()
    {
        super(VERTIX_FILE, FRAGMENT_FILE);

    }

    @Override
    protected void bindAttributes()
    {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getUniformLocations()
    {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        loction_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
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
}
