/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.terrain;

import zelone.engine.Loader;
import zelone.models.RawModel;
import zelone.texture.TerrainTexture;
import zelone.texture.TerrainTexturePack;

/**
 *
 * @author Jhawar
 */
public class Terrain
{

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;
    private TerrainTexturePack texture;
    private TerrainTexture blendMap;
    private final float x;
    private final float z;
    private final RawModel model;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texture,TerrainTexture blendMap)
    {
        this.blendMap=blendMap;
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader);
    }

    public TerrainTexturePack getTexture()
    {
        return texture;
    }

    public TerrainTexture getBlendMap()
    {
        return blendMap;
    }

    public float getX()
    {
        return x;
    }

    public float getZ()
    {
        return z;
    }

    public RawModel getModel()
    {
        return model;
    }

    private RawModel generateTerrain(Loader loader)
    {
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
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
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
}
