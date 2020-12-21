/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.shader;

/**
 *
 * @author Jhawar
 */
public class StaticShader extends ShaderProgram
{

    private static final String VERTIX_FILE = "src/com/zelone/shader/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/com/zelone/shader/fragmentShader.glsl";

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

}
