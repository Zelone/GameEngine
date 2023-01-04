/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zelone.config;

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

}
