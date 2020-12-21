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
import com.zelone.entities.Camera;
import com.zelone.entities.Entity;
import com.zelone.models.TexturedModel;
import com.zelone.shader.StaticShader;
import com.zelone.texture.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class MainGameLoop
{

    public static void main(String[] args)
    {
        
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        //RECTANGLE MODEL
        float[] vertices = {
            -0.5f, 0.5f, 0f, //v0
            -0.5f, -0.5f, 0f, //v1
            0.5f, -0.5f, 0f, //v2
            0.5f, 0.5f, 0f //v3
        };

        int[] indices = {
            0, 1, 3,
            3, 1, 2
        };

        float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
        };
        Entity cube = getCube(loader);
        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);
        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            //entity.incresePostion(0, 0, -0.002f);
            cube.increseRotation(1, 1, 0);
            camera.move();
            renderer.prepare();
            //gamelogic
            //render
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(cube, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static Entity getCube(Loader loader)
    {
        float[] vertices = {
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f

        };

        float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0

        };

        int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22

        };
        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        return new Entity(texturedModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);
    }

}
