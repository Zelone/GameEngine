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
public class EntityMovement implements Runnable
{
    private Entity entity;
    private Vector3f rotate;
    private Vector3f translate;

    public EntityMovement(Entity entity, Vector3f rotate, Vector3f translate)
    {
        this.entity = entity;
        this.rotate = rotate;
        this.translate = translate;
    }
    
    
    
    public Entity getEntity()
    {
        
        return entity;
    }
    public void move(){
    this.run();
    }

    @Override
    public void run()
    {
        entity.incresePostion(translate.getX(), translate.getY(), translate.getZ());
        entity.increseRotation(rotate.getX(), rotate.getY(), rotate.getZ());
    }
    
}
