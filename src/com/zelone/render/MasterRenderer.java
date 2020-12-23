/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.render;

import com.zelone.basic.Basic;
import com.zelone.entities.Camera;
import com.zelone.entities.Entity;
import com.zelone.entities.Light;
import com.zelone.models.TexturedModel;
import com.zelone.shader.StaticShader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jhawar
 */
public class MasterRenderer extends Basic
{

    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    public void render(Light sun, Camera camera)
    {
        renderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

    @Override
    public void cleanUp()
    {
        clean = true;
        shader.cleanUp();
    }

    public void processEntity(Entity entity)
    {

        TexturedModel model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(model, batch);
        }
    }

    
}
