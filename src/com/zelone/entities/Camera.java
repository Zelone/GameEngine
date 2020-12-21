/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class Camera
{

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;
    private float changesOnKeyPressed = 0.02f;

    public Camera()
    {
    }

    public void move()
    {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= changesOnKeyPressed;
        }

    }

    public float getPitch()
    {
        return pitch;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getRoll()
    {
        return roll;
    }

    public float getYaw()
    {
        return yaw;
    }

    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public void setRoll(float roll)
    {
        this.roll = roll;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

}
