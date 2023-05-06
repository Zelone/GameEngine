/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zelone.config;

import java.lang.reflect.Field;

/**
 *
 * @author Zelone
 */
public class TerrainsData {

    public final String rSampler;
    public final String gSampler;
    public final String bSampler;
    public final String backgroundSampler;
    public final String blendMap;
    public final int gridX;
    public final int gridZ;

    public TerrainsData(int gridX, int gridZ, String rSampler, String gSampler, String bSampler, String backgroundSampler, String blendMap) {
        this.gridX = gridX;
        this.gridZ = gridZ;
        this.rSampler = rSampler;
        this.gSampler = gSampler;
        this.bSampler = bSampler;
        this.backgroundSampler = backgroundSampler;
        this.blendMap = blendMap;
    }

    public TerrainsData(boolean internal, Object... ask) {
        Field[] f = CameraData.class.getFields();
        try {
            for (int i = 0; i < f.length; i++) {
                switch (f[i].getName()) {
                    case "rSampler":
                        this.rSampler = ((String) ask[i]);
                        break;
                    case "gSampler":
                        this.gSampler = ((String) ask[i]);
                        break;
                    case "bSampler":
                        this.bSampler = ((String) ask[i]);
                        break;
                    case "backgroundSampler":
                        this.backgroundSampler = ((String) ask[i]);
                        break;
                    case "blendMap":
                        this.blendMap = ((String) ask[i]);
                        break;
                    case "gridX":
                        this.gridX = ((Integer) ask[i]);
                        break;
                    case "gridZ":
                        this.gridZ = ((Integer) ask[i]);
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
