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

    public CameraData(boolean internal , Object... ask) {
        Field[] f = CameraData.class.getFields();
        try {
            for (int i = 0; i < f.length; i++) {
                switch (f[i].getName()) {
                    case "pitch":
                        this.pitch = ((Float) ask[i]);
                        break;
                    case "changesOnKeyPressed":
                        this.changesOnKeyPressed = ((Float) ask[i]);
                        break;
                    case "yaw":
                        this.yaw = ((Float) ask[i]);
                        break;
                    case "roll":
                        this.roll = ((Float) ask[i]);
                        break;
                    case "position":
                        this.position = ((Vector3f) ask[i]);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rrrun() {
        Field[] ff = CameraData.class.getFields();
        for (Field f : ff) {
            System.out.println(f.getGenericType().getTypeName() + ":" + f.getName());
        }
    }

}
