/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.objectengineTester;

import zelone.OBJConverter.ModelData;
import zelone.OBJConverter.OBJFileLoader;
import zelone.engine.Engine;
import zelone.entities.Camera;
import zelone.entities.Light;
import zelone.shader.StaticShader;
import zelone.shader.TerrainShader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
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
public class ObjectExecutable {

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
    private Map<float[], List<float[][]>> entitiess = new HashMap<float[], List<float[][]>>();
    private List<float[]> terrainss = new ArrayList<float[]>();
    //private final StaticShader shader;
    private final ObjectEntityRenderer renderer;

    //private final TerrainShader terrainShader;
    private final ObjectTerrainRenderer terrainRenderer;
    private List<Entity> entities;
    private List<Terrain> terrains;

    private Entity entity;
    private Terrain terrain;

    public ObjectExecutable() throws Exception {
        {
            //onetime
            ContextAttribs attribs = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            try {
                //onetime
                Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
                Display.create(new PixelFormat(), attribs);
                Display.setTitle("Display1");
            } catch (LWJGLException ex) {
                ex.printStackTrace();
            }
            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        }

        //List<float[]> terrains = new ArrayList<float[]>();
        this.terrains = new ArrayList<Terrain>();
        System.out.println("Terrain init");
        terrain = new Terrain();
        {
            //one terrain
            //onetime
            float x = 0;
            float z = 0;
            String TerrainTextures[]
                    = new String[]{"dirt", "pinkFlowers", "path", "grassy", "blendMap"};
            //  terrain = float[]{x,z,vaoID,indicesLength,rtexture,gtexture,btexture,backgroundTexture,blendMap}
            terrains.add(loadTerrain(x, z, TerrainTextures[0], TerrainTextures[1], TerrainTextures[2], TerrainTextures[4], TerrainTextures[3]));
        }
        System.out.println("Terrain out");

        //onetime
        //------------////this.shader = new StaticShader();
        //List<float[][]> entities = new ArrayList<float[][]>();
        this.entities = new ArrayList<Entity>();
        System.out.println("Entity init");

        {
            //one entity
            //onetime
            String modelName = "dragon";
            String modelTextureName = "stallTexture";
            //base value
            float shineDamper = 10;// 1;
            float reflectivity = 1;// 0;
            boolean hasTransperancy = false;
            boolean useFakeLighting = false;
            //extra data 
            float rotx = 0;
            float roty = 0;
            float rotz = 0;
            float scale = 1;
            //translate
            float[] position = new float[]{0, 0, -25};
            float[] rotate = new float[]{0, 0, 0};
            float[] translate = new float[]{0, 0, 0};
            //movement per tick
            float entityRotationScale[] = new float[]{rotx, roty, rotz, scale};

            // entity = float{texturedModel{vaoid,indiceslength,textureVaoID,shineDampner,reflectivity,hastransparency,usefakelighting},position{x,y,z},entityRotationScale{rotx, roty, rotz, scale},rotate{l,m,n},translate{x,y,z}}
            entities.add(loadEntity(modelName, modelTextureName, position, entityRotationScale, rotate, translate, shineDamper, reflectivity, hasTransperancy, useFakeLighting));

        }
        System.out.println("Entity out");

        //onetime
        //one entity
        entities.add(loadEntity("grassModel", "grassTexture", new float[]{1, 0, 3}, new float[]{0, 0, 0, 1}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 0, true, true));

        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        Camera camera = new Camera();

        {
            //onetime
            Engine.enableCulling();
            createProjectionMatrix();
//            float aspectRatio = ((float) Display.getWidth()) / ((float) Display.getHeight());
//            float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
//            float x_scale = y_scale / aspectRatio;
//            float frustum_length = FAR_PLANE - NEAR_PLANE;
//
//            projectionMatrix = new Matrix4f();
//            projectionMatrix.m00 = x_scale;
//            projectionMatrix.m11 = y_scale;
//            projectionMatrix.m22 = -(FAR_PLANE + NEAR_PLANE) / frustum_length;
//            projectionMatrix.m23 = -1;
//            projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
//            projectionMatrix.m33 = 0;

            //onetime
            this.renderer = new ObjectEntityRenderer(Entity.shader, projectionMatrix);
            this.terrainRenderer = new ObjectTerrainRenderer(Terrain.shader, projectionMatrix);
        }
        //onetime
        while (!Display.isCloseRequested()) {

            //ticktime
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
                //entityticktime
                entity1.tick();

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
                List<A> entities = ((ModelObject)entity1.getObjectByID(ModelObject.createdummyInstance())).modelUserObjects;
                float[] model = entity1.getObjectID(new ModelObject());
                List<float[][]> batch = this.entities.get(0);

                if (batch != null) {
                    batch.add(entity1);
                } else {
                    List<float[][]> newBatch = new ArrayList<float[][]>();
                    newBatch.add(entity1);

                    this.entities.put(model, newBatch);
                }

            }
            //ticktime
            for (Terrain terrain2 : terrains) {
                //terrainticktime
                terrain2.tick();
                this.terrains.add(terrain2);
            }
            //ticktime
            render(light, camera);

            //ticktime
            Display.sync(FPS_CAP);
            Display.update();
        }

        //close()
    }


    /*
    Entity  = float[][]
    Terrain = float[]
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     */
    public void close() {
        //shader.cleanUp();
        entity.close();
        terrain.close();
        //onetime
        //onetime
        Engine.removeAllVao();
        Engine.removeAllVbo();
        Engine.removeAllTextures();

        //onetime
        Display.destroy();
    }

    public Terrain loadTerrain(float x, float z, String rTexture, String gTexture, String bTexture, String blendMap, String backgroundTexture) throws Exception {
        Terrain terrain = new Terrain();
        terrain.addObject(new TextureObject(backgroundTexture));
        terrain.addObject(new BlendObject(rTexture, gTexture, bTexture, blendMap));
        terrain.addObject(new TranslateObject(new float[]{x, 1, z}, new float[]{0, 0, 0}, 1));
        return terrain;
    }

    public float[] loadTerrainn(float x, float z, String rTexture, String gTexture, String bTexture, String blendMap, String backgroundTexture) throws Exception {
        return loadTerrainn(x, z, new String[]{rTexture, gTexture, bTexture, backgroundTexture, blendMap});
    }

    public float[] loadTerrainn(float x, float z, String[] TerrainTextures) throws Exception {

        terrain.addObject(new TextureObject("grassy"));

        terrain.addObject(new BlendObject("dirt", "pinkFlowers", "path", "blendMap"));
        int vaoID;
        int indiceslength = 6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1);
        //{rtexture,gtexture,btexture,backgroundTexture,blendMap}
        int[] TerrainTextureID = new int[TerrainTextures.length];
        {
            //onetime
            int i = 0;
            for (String fileName : TerrainTextures) {
                //texturetime
                TerrainTextureID[i++] = Engine.loadTexture(fileName);
            }

            {
                //onetime
                int count = VERTEX_COUNT * VERTEX_COUNT;
                float[] postions = new float[count * 3];
                float[] normals = new float[count * 3];
                float[] textureCoords = new float[count * 2];
                int[] indices = new int[indiceslength];

                int vertexPointer = 0;
                //onetime
                for (i = 0; i < VERTEX_COUNT; i++) {
                    //VERTEX_COUNTtime
                    for (int j = 0; j < VERTEX_COUNT; j++) {
                        //VERTEX_COUNT^2time

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
                //onetime
                for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
                    //Vertex_counttime
                    for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                        //Vertex_count^2time
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
                //onetime
                vaoID = Engine.loadToVAO(postions, textureCoords, normals, indices);
            }
            //TerrainTextureID = int[]{rtexture,gtexture,btexture,backgroundTexture,blendMap}

        }
        //onetime
        return new float[]{x * SIZE, z * SIZE, (float) vaoID, (float) indiceslength, (float) TerrainTextureID[0], (float) TerrainTextureID[1], (float) TerrainTextureID[2], (float) TerrainTextureID[3], (float) TerrainTextureID[4]};
    }

    private float[][] loadEntityy(String modelName, String modelTextureName, float[] position, float[] entityRotationScale, float[] rotate, float[] translate) throws Exception {
        return loadEntityy(modelName, modelTextureName, position, entityRotationScale, rotate, translate, 1, 0, false, false);
    }

    private Entity loadEntity(String modelName, String modelTextureName, float[] position, float[] entityRotationScale, float[] rotate, float[] translate, float shineDamper, float reflectivity, boolean hasTransperancy, boolean useFakeLighting) throws Exception {
        Entity entity = new Entity();

        entity.addObject(new TextureObject(modelTextureName));
        entity.addObject(ModelObject.createModelObject(modelName));
        entity.addObject(new TexturedModelObject(shineDamper, reflectivity, hasTransperancy, useFakeLighting));
        entity.addObject(new TranslateObject(position, entityRotationScale, entityRotationScale[3]).addMovement(translate, rotate));
        return entity;
    }

    private float[][] loadEntityy(String modelName, String modelTextureName, float[] position, float[] entityRotationScale, float[] rotate, float[] translate, float shineDamper, float reflectivity, boolean hasTransperancy, boolean useFakeLighting) throws Exception {
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

        terrain.render();
        Terrain.shader.start();

        Terrain.shader.loadLight(sun);
        Terrain.shader.loadFog(FOG_DENSITY, FOG_GRADIENT, SKY_RED, SKY_GREEN, SKY_BLUE);
        Terrain.shader.loadViewMatrix(camera);
        terrainRenderer.render(terrains); // to be  edited
        Terrain.shader.stop();
        terrains.clear();

        Entity.shader.start();
        Entity.shader.loadFog(FOG_DENSITY, FOG_GRADIENT, SKY_RED, SKY_GREEN, SKY_BLUE);
        Entity.shader.loadLight(sun);
        Entity.shader.loadViewMatrix(camera);
        entity.render();
        renderer.render(entities); //To be edited
        Entity.shader.stop();
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

        try {
            ObjectExecutable trying = new ObjectExecutable();
            trying.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

interface BB extends Comparable<String> {

    public abstract void checkRequirement() throws Exception;

    public void tick();

}

interface AA {

    public boolean addObject(B obj) throws Exception;

    public boolean hasTypeObject(String objTypeName);

    public boolean removeObject(B obj);

    public void runForProperty(Consumer<? super B> action);

    public B getObject(int i);

    public <G extends B> B getObjectByID(G type);

    public void render();

    public void close();

    public void tick();
}

abstract class A implements AA {

    ArrayList<B> property = new ArrayList<>();
    ArrayList<String> propertyList = new ArrayList<>();

    static {
        System.out.println("INIT : A");
    }

    public A() {
        System.out.println("IN : A");
    }

    @Override
    public void close() {
        System.out.println("OUT : A");
    }

    @Override
    public void render() {

    }

    @Override
    public boolean addObject(B obj) throws Exception {
        try {
            obj.setObject(this);
        } catch (Exception ex) {
            System.err.println("ILLEGAL OBJECT ADDITION DONE :  " + ex);
        }
        if (!propertyList.contains(obj.getClass().getName())) {
            propertyList.add(obj.getClass().getName());
        } else {
            System.out.println("ProperyList contains " + obj.getClass().getName());
        }
        if (!propertyList.contains(obj.getName())) {
            propertyList.add(obj.getName());
        } else {
            throw new Exception("Already Contains this Object");
        }
        System.out.println("zelone.objectengineTester.A.addObject()" + obj.getName() + ":" + obj.getClass().getName());
        return property.add(obj);
    }

    @Override
    public boolean removeObject(B obj) {
        return property.remove(obj);
    }

    @Override
    public <G extends B> B getObjectByID(G type) {
        return property.stream().filter((t) -> t.getClass().isInstance(type)).toList().get(0);
    }

    @Override
    public B getObject(int i) {
        return property.get(0);
    }

    @Override
    public void runForProperty(Consumer<? super B> action) {
        property.forEach(action);
    }

    @Override
    public boolean hasTypeObject(String objTypeName) {
        System.out.println("zelone.objectengineTester.A.hasTypeObject()" + objTypeName);
        return propertyList.contains(objTypeName);

    }

}

class ERROR {

    final static String ERRORstr = "EEEE";
    final static Random rand = new Random();

    public static String generateString(int len) {
        String out = "";
        for (int i = 0; i < len; i++) {
            out += (char) (((int) ('A')) + rand.nextInt(26));
        }
        return out;
    }

    public static Vector3f addVector3f(Vector3f A, Vector3f B) {
        return new Vector3f(A.getX() + B.getX(), A.getY() + B.getY(), A.getZ() + B.getZ());
    }
}

abstract class B implements BB {

    public A object;
    String name = ERROR.ERRORstr;
    private boolean objectBoolean = true;

    public B() throws Exception {
        if (!objectBoolean) {
            checkRequirement();
        }
    }

    public void setObject(A object) throws Exception {
        if (objectBoolean) {
            objectBoolean = !objectBoolean;
            this.object = object;
        } else {
            throw new Exception("Object Change Not Allowed with out removing");
        }
        checkRequirement();
    }

    public String getName() {
        if (this.name.equals(ERROR.ERRORstr)) {
            this.name = generateName();
            System.out.println("zelone.objectengineTester.B.getName()" + name);
        }
        return this.name;
    }

    private static String generateName() {
        return ERROR.generateString(8);
    }

    @Override
    public int compareTo(String o) {
        return this.name.compareTo(o);
    }

}

class TextureObject extends B {

    int TextureVAOID;

    public TextureObject(String textureName) throws Exception {
        super();
        this.TextureVAOID = Engine.loadTexture(textureName);
    }

    @Override
    public void checkRequirement() throws Exception {

    }

    @Override
    public void tick() {
    }

}

class BlendObject extends B {

    int rTexture, gTexture, bTexture, blendMap;

    public BlendObject(String rTexture, String gTexture, String bTexture, String blendMap) throws Exception {
        this.rTexture = Engine.loadTexture(rTexture);
        this.gTexture = Engine.loadTexture(gTexture);
        this.bTexture = Engine.loadTexture(bTexture);
        this.blendMap = Engine.loadTexture(blendMap);
        setupBlend();
    }

    @Override
    public void checkRequirement() throws Exception {
        boolean err = true;
        err = err && object.hasTypeObject(TextureObject.class.getName());
        if (!err) {
            throw new Error("Requirement not met" + "");
        }
    }

    public void setupBlend() {

    }

    @Override
    public void tick() {
    }

}

class ModelObject extends B {

    int modelVAOID;
    int indiceslength = 0;
    static Map<String, ModelObject> map;
    List< A> modelUserObjects;

    static {
        map = new HashMap<String, ModelObject>();
    }

    public static ModelObject createdummyInstance() {
        return new ModelObject();
    }

    public static ModelObject createModelObject(String modelName) throws Exception {
        if (map.containsKey(modelName)) {
            return map.get(modelName);
        } else {
            return new ModelObject(modelName);
        }
    }

    @Override
    public void setObject(A object) throws Exception {
        if (modelUserObjects == null) {
            modelUserObjects = new LinkedList<>();
        }
        modelUserObjects.add(object);
    }

    private ModelObject() {
    }

    private ModelObject(String modelName) throws Exception {
        super();
        ModelData data = OBJFileLoader.loadOBJ(modelName);
        int[] indices = data.getIndices();
        indiceslength = indices.length;
        modelVAOID = Engine.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), indices);//OBJLoader.loadObjModel("stall", loader);

    }

    @Override
    public void checkRequirement() throws Exception {

    }

    @Override
    public void tick() {

    }
}

class TexturedModelObject extends B {

    float shineDamper;
    float reflectivity;
    boolean hasTransperancy;
    boolean useFakeLighting;

    public TexturedModelObject(float shineDamper, float reflectivity, boolean hasTransperancy, boolean useFakeLighting) throws Exception {
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.hasTransperancy = hasTransperancy;
        this.useFakeLighting = useFakeLighting;
    }

    @Override
    public void checkRequirement() throws Exception {
        boolean err = true;
        err = err && object.hasTypeObject(TextureObject.class.getName());
        err = err && object.hasTypeObject(ModelObject.class.getName());
        if (!err) {
            throw new Error("Requirement not met" + "");
        }
    }

    @Override
    public void tick() {
    }
}

class TranslateObject extends B {

    Vector3f position;
    Vector3f rotation;
    float scale;

    public TranslateObject(float[] position, float[] rotation, float scale) throws Exception {
        this.position = new Vector3f(position[0], position[1], position[2]);
        this.rotation = new Vector3f(rotation[0], rotation[1], rotation[2]);
        this.scale = scale;
    }

    @Override
    public void checkRequirement() throws Exception {

    }
    public Vector3f translate = new Vector3f();
    public Vector3f rotate = new Vector3f();

    public TranslateObject addMovement(float[] translate, float[] rotate) {
        this.rotate = new Vector3f(rotate[0], rotate[1], rotate[2]);
        this.translate = new Vector3f(translate[0], translate[1], translate[2]);
        return this;
    }

    @Override
    public void tick() {

        //        position, entityRotationScale+= translate,rotate 
        //        texturedModel{vaoid,indiceslength,textureVaoID,shineDampner,reflectivity,hastransparency,usefakelighting},position{x,y,z},entityRotationScale{rotx, roty, rotz, scale},rotate{l,m,n},translate{x,y,z}
        position = ERROR.addVector3f(position, translate);
        rotation = ERROR.addVector3f(rotation, rotate);
        //        entity[1][0] += entity[4][0];
        //        entity[1][1] += entity[4][1];
        //        entity[1][2] += entity[4][2];
        //        entity[2][0] += entity[3][0];
        //        entity[2][1] += entity[3][1];
        //        entity[2][2] += entity[3][2];
    }

}

class Entity extends A {

    public static StaticShader shader;

    public Entity() {
        shader = new StaticShader();
    }

    @Override
    public void tick() {
        property.forEach(new Consumer<B>() {
            @Override
            public void accept(B t) {
                t.tick();
            }
        });

    }

    @Override
    public void close() {
        super.close();
        shader.cleanUp();
    }

}

class Terrain extends A {

    public static TerrainShader shader;

    public Terrain() {
        shader = new TerrainShader();
    }

    @Override
    public void tick() {
        property.forEach(new Consumer<B>() {
            @Override
            public void accept(B t) {
                t.tick();
            }
        });
    }

    @Override
    public void close() {
        super.close();
        shader.cleanUp();
    }

}
