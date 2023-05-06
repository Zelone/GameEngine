/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.render;

import zelone.entities.Camera;
import zelone.entities.Entity;
import zelone.entities.Light;
import zelone.models.TexturedModel;
import zelone.shader.StaticShader;
import zelone.shader.TerrainShader;
import zelone.terrain.Terrain;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Jhawar
 */
public class MasterRenderer 
{

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    public static final float SKY_RED = 0.5f;
    public static final float SKY_GREEN = 0.5f;
    public static final float SKY_BLUE = 0.5f;
    public static final float FOG_DENSITY = 0.007f;
    public static final float FOG_GRADIENT = 1.5f;

    private Matrix4f projectionMatrix;
    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();
    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;

    public MasterRenderer()
    {
        super();
        enableCulling();
        createProjectionMatrix();
        this.renderer = new EntityRenderer(shader, projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public static void enableCulling()
    {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling()
    {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void render(Light sun, Camera camera)
    {
        prepare();

        terrainShader.start();

        terrainShader.loadLight(sun);
        terrainShader.loadFog(FOG_DENSITY, FOG_GRADIENT, SKY_RED, SKY_GREEN, SKY_BLUE);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();

        shader.start();
        shader.loadFog(FOG_DENSITY, FOG_GRADIENT, SKY_RED, SKY_GREEN, SKY_BLUE);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

   
    public void cleanUp()
    {
        
        shader.cleanUp();
        terrainShader.cleanUp();
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
            entities.put(model, newBatch);
        }
    }

    public void processTerrain(Terrain terrain)
    {
        terrains.add(terrain);
    }

    public void prepare()
    {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, 1);
    }

    private Matrix4f createProjectionMatrix()
    {
        float aspectRatio = ((float) Display.getWidth()) / ((float) Display.getHeight());
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -(FAR_PLANE + NEAR_PLANE) / frustum_length;
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
        return projectionMatrix;
    }
}
