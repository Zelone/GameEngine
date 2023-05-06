/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class Light
{
    private Vector3f position; 
    private Vector3f color;

    public Light(Vector3f position, Vector3f color)
    {
        this.position = position;
        this.color = color;
    }

    public Vector3f getColor()
    {
        return color;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setColor(Vector3f color)
    {
        this.color = color;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }
    
    
    
}
