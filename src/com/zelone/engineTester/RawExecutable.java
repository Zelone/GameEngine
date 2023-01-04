/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zelone.engineTester;

/**
 *
 * @author Zelone
 */
public class RawExecutable {
    /*
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static final int FPS_CAP = 120;
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;
    private static final String RES_LOC = "res/";

    public RawExecutable() {

//        DisplayManager.createDisplay();
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

        Loader loader = new Loader();
        // StaticShader shader = new StaticShader();
        // EntityRenderer renderer = new EntityRenderer(shader);

//        List<Terrain> terrains = new ArrayList<Terrain>();
//        //ModelTexture modelTerrainTexture = new ModelTexture(loader.loadTexture("grassy"));
        List<float[]> terrains = new ArrayList<float[]>();

//        // Terrain terrain = new Terrain(0, 0, loader, modelTerrainTexture);
//        //modelTerrainTexture.setReflectivity(1);
//        //modelTerrainTexture.setShineDamper(10);
//        //terrains.add(terrain);
//        Terrain terrain1 = new Terrain(0, 0, loader, new TerrainTexturePack(
//                new TerrainTexture(loader.loadTexture("dirt")),
//                new TerrainTexture(loader.loadTexture("pinkFlowers")),
//                new TerrainTexture(loader.loadTexture("path")),
//                new TerrainTexture(loader.loadTexture("grassy"))),
//                new TerrainTexture(loader.loadTexture("blendMap")));
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
                TerrainTextureID[i++] = loadTexture(fileName);
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
                vaoID = loadToVAO(postions, textureCoords, normals, indices);
            }
            //{rtexture,gtexture,btexture,backgroundTexture,blendMap}
            //int[] TerrainTextureID 
        }
        float[] terrain1 = new float[]{x * SIZE, z * SIZE, (float) vaoID, (float) indiceslength, (float) TerrainTextureID[0], (float) TerrainTextureID[1], (float) TerrainTextureID[2], (float) TerrainTextureID[3], (float) TerrainTextureID[4]};

        terrains.add(terrain1);

        List<float[][]> entities = new ArrayList<float[][]>();

//        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());//OBJLoader.loadObjModel("stall", loader);
//        ModelTexture texture = new ModelTexture(loadTexture("stallTexture"));
//
//        texture.setReflectivity(
//                1);
//        texture.setShineDamper(
//                10);
//        entity.move(
//                new Vector3f(0, 1, 0), new Vector3f(0, 0, 0));
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

        entities.add(loadEntity(modelName, modelTextureName, position, entityRotationScale, rotate, translate, shineDamper, reflectivity, hasTransperancy, useFakeLighting));
        entities.add(loadEntity("grassModel", "grassTexture", new float[]{1, 0, 3}, new float[]{0, 0, 0, 1}, new float[]{0, 0, 0}, new float[]{0, 0, 0}, 1, 0, true, true));
//        Light light = new Light(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1));
        Color lightColor = new Color(1, 1, 1);
        float[] lightPosition = new float[]{0, 0, -1};

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
            modelVAOID = loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), indices);//OBJLoader.loadObjModel("stall", loader);
        }
        //{modelVAOID,indiceslength};
        //        TexturedModel texturedModel = new TexturedModel(model, texture);
        int modelTextureVAOID = loadTexture(modelTextureName);
        float texturedModel[] = new float[]{(float) modelVAOID, (float) indiceslength, (float) modelTextureVAOID, (float) shineDamper, (float) reflectivity, (float) ((hasTransperancy) ? 1 : 0), (float) ((useFakeLighting) ? 1 : 0)};
        float[] entityMovement[] = new float[][]{position, rotate, translate};
        return new float[][]{texturedModel, entityMovement[0], entityRotationScale, entityMovement[1], entityMovement[2]};

    }
    private int EntityPOSITION = 1;
    private int EntityROTATIONSCALE = 2;
    private int EntityTRANLATE = 3;
    private int EntityROTATE = 4;

    private float[][] runEntity(float[][] entity) {
//        position, entityRotationScale+= translate,rotate 

    }

    private int loadToVAO(float[] postions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);

        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        storeDataInAttributeList(0, 3, postions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);

        GL30.glBindVertexArray(0);
        return vaoID;
    }

    private int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_LINEAR);

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
     */
}
