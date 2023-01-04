/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zelone.config;

import java.lang.reflect.Field;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Zelone
 */
public class CameraData {

    public final float pitch;
    public final float changesOnKeyPressed;
    public final float yaw;
    public final float roll;
    public final Vector3f position;

    public CameraData(float pitch, float changesOnKeyPressed, float yaw, float roll, Vector3f position) {
        this.pitch = pitch;
        this.changesOnKeyPressed = changesOnKeyPressed;
        this.yaw = yaw;
        this.roll = roll;
        this.position = position;
    }

    public void rrrun() {
        Field[] f = CameraData.class.getFields();
        System.out.println(f.toString());
    }

}
