/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.rawengineTester;

import zelone.OBJConverter.ModelData;
import zelone.OBJConverter.OBJFileLoader;
import zelone.engine.Engine;
import zelone.entities.Camera;
import zelone.entities.Light;
import zelone.shader.StaticShader;
import zelone.shader.TerrainShader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Zelone
 */
public class RawExecutable {

    //opeining every thing to one file 
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static final int FPS_CAP = 120;

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    public static final float SKY_RED = 27 / 255f;
    public static final float SKY_GREEN = 190 / 255f;
    public static final float SKY_BLUE = 255 / 255f;
    public static final float FOG_DENSITY = 0.007f;
    public static final float FOG_GRADIENT = 1.5f;

    private Matrix4f projectionMatrix;
    private Map<float[], List<float[][]>> entities = new HashMap<float[], List<float[][]>>();
    private List<float[]> terrains = new ArrayList<float[]>();
    private final StaticShader shader;
    private final RawEntityRenderer renderer;

    private final TerrainShader terrainShader;
    private final RawTerrainRenderer terrainRenderer;

    public RawExecutable() {
        {
            ContextAttribs attribs = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            try {
                Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
                Display.create(new PixelFormat(), attribs);
                Display.setTitle("Display1");
            } catch (LWJGLException ex) {
                ex.printStackTrace();
            }
            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        }
        this.shader = new StaticShader();
        this.terrainShader = new TerrainShader();

        List<float[]> terrains = new ArrayList<float[]>();
        {
            float x = 0;
            float z = 0;
            String TerrainTextures[]
                    = new String[]{"dirt", "pinkFlowers", "path", "grassy", "blendMap"};

            int vaoID;
            int indiceslength = 6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1);
            //{rtexture,gtexture,btexture,backgroundTexture,blendMap}
            int[] TerrainTextureID = new int[TerrainTextures.length];
            {
                int i = 0;
                for (String fileName : TerrainTextures) {
                    TerrainTextureID[i++] = Engine.loadTexture(fileName);
                }

                {
                    int count = VERTEX_COUNT * VERTEX_COUNT;
                    float[] postions = new float[count * 3];
                    float[] normals = new float[count * 3];
                    float[] textureCoords = new float[count * 2];
                    int[] indices = new int[indiceslength];
                    int vertexPointer = 0;
                    for (i = 0; i < VERTEX_COUNT; i++) {
                        for (int j = 0; j < VERTEX_COUNT; j++) {
                            postions[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                            postions[vertexPointer * 3 + 1] = 0;
                            postions[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
                            normals[vertexPointer * 3] = 0;
                            normals[vertexPointer * 3 + 1] = 1;
                            normals[vertexPointer * 3 + 2] = 0;
                            textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
                            textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
                            vertexPointer++;
                        }
                    }
                    int pointer = 0;
                    for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
                        for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                            int topLeft = (gz * VERTEX_COUNT) + gx;
                            int topRight = topLeft + 1;
                            int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                            int bottomRight = bottomLeft + 1;

                            indices[pointer++] = topLeft;
                            indices[pointer++] = bottomLeft;
                            indices[pointer++] = topRight;
                            indices[pointer++] = topRight;
                            indices[pointer++] = bottomLeft;
                            indices[pointer++] = bottomRight;
                        }
                    }
                    vaoID = Engine.loadToVAO(postions, textureCoords, normals, indices);
                }
                //TerrainTextureID = int[]{rtexture,gtexture,btexture,backgroundTexture,blendMap}

            }
            float[] terrain1 = new float[]{x * SIZE, z * SIZE, (float) vaoID, (float) indiceslength, (float) TerrainTextureID[0], (float) TerrainTextureID[1], (float) TerrainTextureID[2], (float) TerrainTextureID[3], (float) TerrainTextureID[4]};
            //  terrain = float[]{x,z,vaoID,indicesLength,rtexture,gtexture,btexture,backgroundTexture,blendMap}
            terrains.add(terrain1);
        }
        List<float[][]> entities = new ArrayList<float[][]>();
        {
            String modelName = "stall";
            String modelTextureName = "stallTexture";
            float shineDamper = 10;// 1;
            float reflectivity = 1;// 0;
            boolean hasTransperancy = false;
            boolean useFakeLighting = false;
            float rotx = 0;
            float roty = 0;
            float rotz = 0;
            float scale = 1;
            float[] position = new float[]{0, 0, -25};
            float[] rotate = new float[]{0, 1, 0};
            float[] translate = new float[]{0, 0, 0};
            float entityRotationScale[] = new float[]{rotx, roty, rotz, scale};

            // entity = float{texturedModel{vaoid,indiceslength,textureVaoID,shineDampner,reflectivity,hastransparency,usefakelighting},position{x,y,z},entityRotationScale{rotx, roty, rotz, scale},rotate{l,m,n},translate{x,y,z}}
            entities.add(loadEntity(modelName, modelTextureName, position, entityRotationScale, rotate, translate, shineDamper, reflectivity, hasTransperancy, useFakeLighting));
        }
        entities.add(loadEntity("grassModel", "grassTexture", new float[]{1, 0, 3}, new float[]{0, 0, 0, 1}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 0, true, true));

        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        Camera camera = new Camera();

        {
            Engine.enableCulling();

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

            this.renderer = new RawEntityRenderer(shader, projectionMatrix);
            this.terrainRenderer = new RawTerrainRenderer(terrainShader, projectionMatrix);
        }
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
            for (float[][] entity1 : entities) {
                entity1 = runEntity(entity1);

                //
                //
                //
                //
                //
                //
                //
                //
                //
                //
                //
                //CANNOT RENDER FLOATS use Objects as in original FILE
                float[] model = entity1[0];
                List<float[][]> batch = this.entities.get(model);

                if (batch != null) {

                    batch.add(entity1);
                } else {
                    List<float[][]> newBatch = new ArrayList<float[][]>();
                    newBatch.add(entity1);

                    this.entities.put(model, newBatch);
                }

            }
            for (float[] terrain2 : terrains) {
                this.terrains.add(terrain2);
            }
            render(light, camera);

            Display.sync(FPS_CAP);
            Display.update();
        }
        //shader.cleanUp();

        shader.cleanUp();
        terrainShader.cleanUp();

        Engine.removeAllVao();
        Engine.removeAllVbo();
        Engine.removeAllTextures();

        Display.destroy();
    }

    /*
    Entity  = float[][]
    Terrain = float[]
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     */
    private float[][] loadEntity(String modelName, String modelTextureName, float[] position, float[] entityRotationScale, float[] rotate, float[] translate) {
        return loadEntity(modelName, modelTextureName, position, entityRotationScale, rotate, translate, 1, 0, false, false);
    }

    private float[][] loadEntity(String modelName, String modelTextureName, float[] position, float[] entityRotationScale, float[] rotate, float[] translate, float shineDamper, float reflectivity, boolean hasTransperancy, boolean useFakeLighting) {
        int indiceslength = 0;
        int modelVAOID;
        {
            ModelData data = OBJFileLoader.loadOBJ(modelName);
            int[] indices = data.getIndices();
            indiceslength = indices.length;
            modelVAOID = Engine.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), indices);//OBJLoader.loadObjModel("stall", loader);
        }
        //{modelVAOID,indiceslength};
        //        TexturedModel texturedModel = new TexturedModel(model, texture);
        int modelTextureVAOID = Engine.loadTexture(modelTextureName);
        float texturedModel[] = new float[]{(float) modelVAOID, (float) indiceslength, (float) modelTextureVAOID, (float) shineDamper, (float) reflectivity, (float) ((hasTransperancy) ? 1 : 0), (float) ((useFakeLighting) ? 1 : 0)};
        float[] entityMovement[] = new float[][]{position, rotate, translate};
        // texturedModel{vaoid,indiceslength,textureVaoID,shineDampner,reflectivity,hastransparency,usefakelighting},position{x,y,z},entityRotationScale{rotx, roty, rotz, scale},rotate{l,m,n},translate{x,y,z}
        return new float[][]{texturedModel, entityMovement[0], entityRotationScale, entityMovement[1], entityMovement[2]};

    }

    private float[][] runEntity(float[][] entity) {
//        position, entityRotationScale+= translate,rotate 
        // texturedModel{vaoid,indiceslength,textureVaoID,shineDampner,reflectivity,hastransparency,usefakelighting},position{x,y,z},entityRotationScale{rotx, roty, rotz, scale},rotate{l,m,n},translate{x,y,z}
        entity[1][0] += entity[4][0];
        entity[1][1] += entity[4][1];
        entity[1][2] += entity[4][2];
        entity[2][0] += entity[3][0];
        entity[2][1] += entity[3][1];
        entity[2][2] += entity[3][2];
        return entity;
    }

    
    private void render(Light sun, Camera camera) {
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

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, 1);
    }

    private Matrix4f createProjectionMatrix() {
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

    public static void main(String[] args) {
        RawExecutable trying = new RawExecutable();
    }
}
