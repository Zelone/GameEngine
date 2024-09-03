/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.engineTester;

import zelone.config.CameraData;
import zelone.config.ConfigLoader;
import zelone.config.EntityData;
import zelone.config.TerrainsData;
import zelone.engine.DisplayManager;
import zelone.engine.Loader;
import zelone.entities.Camera;
import zelone.entities.Entity;
import zelone.entities.Light;
import zelone.render.MasterRenderer;
import zelone.terrain.Terrain;
import java.util.List;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zelone.render.GuiRenderer;
import zelone.guis.GuiTexture;
import zelone.render.EntityRenderer;
import zelone.shader.StaticShader;
import zelone.texture.ModelTexture;

/**
 *
 * @author Jhawar
 */
public class MainGameLoop {

    public static void main(String[] args) {

        //Runtime rt = Runtime.getRuntime();
        try {
            //rt.exec(new String[]{"cmd.exe","/c","start","/D","D:\\ZProgram\\Python\\Flask\\DailyMonitor","env\\Scripts\\python" ,"-m","runinhouse.cmd"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        //try {
        //    Mouse.create();
        //} catch (LWJGLException ex) {
        //    ex.printStackTrace();
        //}

        StaticShader shader = new StaticShader();
        //EntityRenderer renderer = new EntityRenderer(shader);
        List<Terrain> terrains = new ArrayList<Terrain>();
        //ModelTexture modelTerrainTexture = new ModelTexture(loader.loadTexture("grassy"));
        
        //Terrain terrain = new Terrain(0, 0, loader, modelTerrainTexture);
        //modelTerrainTexture.setReflectivity(1);
        //modelTerrainTexture.setShineDamper(10);
        //terrains.add(terrain);
        ConfigLoader config = new ConfigLoader();
        TerrainsData[] terrainDatas = config.getTerrains();
        for (TerrainsData terrainData : terrainDatas) {
            //Let
            Terrain terrain1 = terrainData.toTerrain(loader);

            terrains.add(terrain1);
        }
        List<Entity> entities = new ArrayList<Entity>();
        EntityData[] entityDatas = config.getEntities();

        for (EntityData entityData : entityDatas) {
            Entity e = entityData.toEntity(loader);
            entities.add(e);
        }

        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        CameraData camdata = config.getCameraData();
        Camera camera = camdata.toCamera();

        MasterRenderer masterRenderer = new MasterRenderer();
        System.out.println("Starts move");
        Mouse.setGrabbed(true);

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("socwan"), new Vector2f(0.5f,0.5f), new Vector2f(0.25f,0.25f));
        guis.add(gui);
        
        GuiRenderer guiRenderer= new GuiRenderer(loader);
        
        
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
            guiRenderer.render(guis);
            masterRenderer.render(light, camera);

            DisplayManager.updateDisplay();
        }
        //shader.cleanUp();
        guiRenderer.cleanUp();
        masterRenderer.cleanUp();
        loader.cleanUp();
        Mouse.destroy();
        DisplayManager.closeDisplay();

    }

}
