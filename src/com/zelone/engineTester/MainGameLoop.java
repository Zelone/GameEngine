/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.engineTester;

import com.zelone.OBJConverter.ModelData;
import com.zelone.OBJConverter.OBJFileLoader;
import com.zelone.engine.DisplayManager;
import com.zelone.engine.Loader;
import com.zelone.models.RawModel;
import com.zelone.entities.Camera;
import com.zelone.entities.Entity;
import com.zelone.entities.Light;
import com.zelone.models.TexturedModel;
import com.zelone.render.MasterRenderer;
import com.zelone.render.OBJLoader;
import com.zelone.terrain.Terrain;
import com.zelone.texture.ModelTexture;
import com.zelone.texture.TerrainTexture;
import com.zelone.texture.TerrainTexturePack;
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
        // EntityRenderer renderer = new EntityRenderer(shader);

        List<Terrain> terrains = new ArrayList<Terrain>();
        //ModelTexture modelTerrainTexture = new ModelTexture(loader.loadTexture("grassy"));

        // Terrain terrain = new Terrain(0, 0, loader, modelTerrainTexture);
        //modelTerrainTexture.setReflectivity(1);
        //modelTerrainTexture.setShineDamper(10);
        //terrains.add(terrain);
        Terrain terrain1 = new Terrain(0, 0, loader, new TerrainTexturePack(
                new TerrainTexture(loader.loadTexture("dirt")),
                new TerrainTexture(loader.loadTexture("pinkFlowers")),
                new TerrainTexture(loader.loadTexture("path")),
                new TerrainTexture(loader.loadTexture("grassy"))),
                new TerrainTexture(loader.loadTexture("blendMap")));

        terrains.add(terrain1);
        List<Entity> entities = new ArrayList<Entity>();
        ModelData data = OBJFileLoader.loadOBJ("stall");

        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());//OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        texture.setReflectivity(1);
        texture.setShineDamper(10);
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
        entity.move(new Vector3f(0, 1, 0), new Vector3f(0, 0, 0));

        entities.add(entity);

        entities.add(new Entity(new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")).setHasTransperancy(true).setUseFakeLighting(true)), new Vector3f(1, 0, 3), 0, 0, 0, 1).move(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0)));

        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        Camera camera = new Camera();

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
            for (Entity entity1 : entities) {
                entity1.run();
                masterRenderer.processEntity(entity1);
            }
            for (Terrain terrain2 : terrains) {
                masterRenderer.processTerrain(terrain2);
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
