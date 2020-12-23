/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.engineTester;

import com.zelone.engine.DisplayManager;
import com.zelone.engine.Loader;
import com.zelone.models.RawModel;
import com.zelone.render.Renderer;
import com.zelone.entities.Camera;
import com.zelone.entities.Entity;
import com.zelone.entities.EntityMovement;
import com.zelone.entities.Light;
import com.zelone.models.TexturedModel;
import com.zelone.render.MasterRenderer;
import com.zelone.render.OBJLoader;
import com.zelone.shader.StaticShader;
import com.zelone.texture.ModelTexture;
import java.util.List;
import java.util.ArrayList;
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
        // StaticShader shader = new StaticShader();
        // Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("dragon", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        texture.setReflectivity(1);
        texture.setShineDamper(10);

        TexturedModel texturedModel = new TexturedModel(model, texture);
        List<EntityMovement> entities=new ArrayList<EntityMovement>();

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        Camera camera = new Camera();
        
        entities.add(new EntityMovement(entity,new Vector3f(0, 0, 0),new Vector3f(0, 0, 0)) );
        MasterRenderer masterRenderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            //entity.incresePostion(0, 0, -0.002f);
            //entity.increseRotation(0, 1, 0);
            camera.move();
            // renderer.prepare();
            //gamelogic
            //render
            // shader.start();
            // shader.loadLight(light);
            // shader.loadViewMatrix(camera);
            // renderer.render(entity, shader);
            // shader.stop();
            for (EntityMovement entity1 : entities) {
                entity1.run();
                masterRenderer.processEntity(entity1.getEntity());
            }

            masterRenderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        //shader.cleanUp();
        masterRenderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
