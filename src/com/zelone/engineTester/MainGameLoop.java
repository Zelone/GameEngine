/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.engineTester;

import com.zelone.engine.DisplayManager;
import com.zelone.engine.Loader;
import com.zelone.models.RawModel;
import com.zelone.engine.Renderer;
import com.zelone.models.TexturedModel;
import com.zelone.shader.StaticShader;
import com.zelone.texture.ModelTexture;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Jhawar
 */
public class MainGameLoop
{
    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader =new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader=new StaticShader();
        //RECTANGLE MODEL
        float[] vertices = {
            -0.5f,0.5f ,0f,  //v0
            -0.5f,-0.5f,0f,  //v1
            0.5f ,-0.5f,0f,  //v2
            0.5f ,0.5f ,0f   //v3
        };
        
        int[] indices={
            0,1,3,
            3,1,2
        };
        
        float[] textureCoords = {
            0,0,
            0,1,
            1,1,
            1,0
        };
        RawModel model=loader.loadToVAO(vertices,textureCoords,indices);
        ModelTexture texture=new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturedModel= new TexturedModel(model, texture);
        
        while(!Display.isCloseRequested()){
            renderer.prepare();
            //gamelogic
            //render
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
    
}
