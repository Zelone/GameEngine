/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.engineTester;

import zelone.OBJConverter.ModelData;
import zelone.OBJConverter.OBJFileLoader;
import zelone.config.CameraData;
import zelone.config.ConfigLoader;
import zelone.config.EntityData;
import zelone.config.TerrainsData;
import zelone.engine.DisplayManager;
import zelone.engine.Loader;
import zelone.models.RawModel;
import zelone.entities.Camera;
import zelone.entities.Entity;
import zelone.entities.Light;
import zelone.models.TexturedModel;
import zelone.render.MasterRenderer;
import zelone.render.OBJLoader;
import zelone.terrain.Terrain;
import zelone.texture.ModelTexture;
import zelone.texture.TerrainTexture;
import zelone.texture.TerrainTexturePack;
import java.util.List;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        try {
            Mouse.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }

        // StaticShader shader = new StaticShader();
        // EntityRenderer renderer = new EntityRenderer(shader);
        List<Terrain> terrains = new ArrayList<Terrain>();
        //ModelTexture modelTerrainTexture = new ModelTexture(loader.loadTexture("grassy"));

        // Terrain terrain = new Terrain(0, 0, loader, modelTerrainTexture);
        //modelTerrainTexture.setReflectivity(1);
        //modelTerrainTexture.setShineDamper(10);
        //terrains.add(terrain);
        ConfigLoader config = new ConfigLoader();
        TerrainsData[] terrainDatas = config.getTerrains();
        for (TerrainsData terrainData : terrainDatas) {
            //Let
            Terrain terrain1 = new Terrain(terrainData.gridX, terrainData.gridZ, loader, new TerrainTexturePack(
                    new TerrainTexture(loader.loadTexture(terrainData.rSampler)),
                    new TerrainTexture(loader.loadTexture(terrainData.gSampler)),
                    new TerrainTexture(loader.loadTexture(terrainData.bSampler)),
                    new TerrainTexture(loader.loadTexture(terrainData.backgroundSampler))),
                    new TerrainTexture(loader.loadTexture(terrainData.blendMap)));

            terrains.add(terrain1);
        }
        List<Entity> entities = new ArrayList<Entity>();
        EntityData[] entityDatas = config.getEntities();

        for (EntityData entityData : entityDatas) {
            if (entityData.TypeSetting) {

                ModelData data = OBJFileLoader.loadOBJ(entityData.model);

                RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());//OBJLoader.loadObjModel("stall", loader);
                ModelTexture texture = new ModelTexture(loader.loadTexture(entityData.modelTexture));
                texture.setData(entityData);
                TexturedModel texturedModel = new TexturedModel(model, texture);

                Entity entity = new Entity(texturedModel, entityData.position, entityData.rotX, entityData.rotY, entityData.rotZ, entityData.scale);
                entity.move(entityData.rotate, entityData.translate);

                entities.add(entity);
            } else {
                entities.add(new Entity(new TexturedModel(OBJLoader.loadObjModel(entityData.model, loader), new ModelTexture(loader.loadTexture(entityData.modelTexture)).setData(entityData)), entityData.position, entityData.rotX, entityData.rotY, entityData.rotZ, entityData.scale).move(entityData.rotate, entityData.translate));
            }
        }

        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        Camera camera = new Camera();
        CameraData camdata = config.getCameraData();
        camera.setPitch(camdata.pitch);
        camera.setPosition(camdata.position);
        camera.setRoll(camdata.roll);
        camera.setYaw(camdata.yaw);
        camera.setChangesOnKeyPressed(camdata.changesOnKeyPressed);

        MasterRenderer masterRenderer = new MasterRenderer();
        System.out.println("Starts move");
        Mouse.setGrabbed(true);

        while (!Display.isCloseRequested()) {
            //entity.incresePostion(0, 0, -0.002f);
            //entity.increseRotation(0, 1, 0);
            camera.move();
            // renderer.prepare();
            // gamelogic
            // render
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
        Mouse.destroy();
        DisplayManager.closeDisplay();

    }

}
