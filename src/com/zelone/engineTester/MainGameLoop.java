/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.engineTester;

import com.zelone.engine.DisplayManager;
import com.zelone.engine.Loader;
import com.zelone.engine.RawModel;
import com.zelone.engine.Renderer;
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
        
        //RECTANGLE MODEL
        float[] vertices = {
            -0.5f,0.5f ,0f,  //v0
            -0.5f,-0.5f,0f,  //v1
            0.5f ,-0.5f,0f,  //v2
            0.5f ,-0.5f,0f   //v3
        };
        
        int[] indices={
        0,1,3,
        3,1,2
        };
        
        RawModel model=loader.loadoVAO(vertices,indices);
        
        
        while(!Display.isCloseRequested()){
            renderer.prepare();
            //gamelogic
            //render
            renderer.render(model);
            DisplayManager.updateDisplay();
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
    
}
