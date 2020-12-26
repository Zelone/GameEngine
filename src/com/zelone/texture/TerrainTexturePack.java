/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.texture;

/**
 *
 * @author Jhawar
 */
public class TerrainTexturePack
{
    TerrainTexture rSampler,gSampler,bSampler,backgroundSampler;

    public TerrainTexturePack( TerrainTexture rSampler, TerrainTexture gSampler, TerrainTexture bSampler, TerrainTexture backgroundSampler)
    {
        this.rSampler = rSampler;
        this.gSampler = gSampler;
        this.bSampler = bSampler;
        this.backgroundSampler = backgroundSampler;
    }

    public TerrainTexture getBackgroundSampler()
    {
        return backgroundSampler;
    }

    public TerrainTexture getbSampler()
    {
        return bSampler;
    }

    public TerrainTexture getgSampler()
    {
        return gSampler;
    }

    public TerrainTexture getrSampler()
    {
        return rSampler;
    }
    
}
