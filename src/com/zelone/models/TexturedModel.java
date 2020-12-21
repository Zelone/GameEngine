/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.models;

import com.zelone.texture.ModelTexture;

/**
 *
 * @author Jhawar
 */
public class TexturedModel
{
    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture)
    {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel()
    {
        return rawModel;
    }

    public ModelTexture getTexture()
    {
        return texture;
    }
    
    
}
