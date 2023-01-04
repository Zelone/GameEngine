/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zelone.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Zelone
 */
public class ConfigLoader {

    TerrainsData[] terrainsDatas;
    EntityData[] entityDatas;
    CameraData cameraData;

    public ConfigLoader() {

        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileInputStream(new File("config/config.json"))));
             new CameraData(0, 0, 0, 0, new Vector3f(0, 0, 0)).rrrun();
            System.exit(0);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                System.out.println("" + key + ":" + json.getString(key) + "");
                if (key.contains("camera") || key.contains("Camera")) {
                    JSONObject cameraJSON = json.getJSONObject(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TerrainsData[] getTerrains() {
        return new TerrainsData[]{new TerrainsData(0, 0, "dirt", "pinkFlowers", "path", "grassy", "blendMap")};
    }

    public EntityData[] getEntities() {
        return new EntityData[]{new EntityData("stall", "stallTexture").setReflectivity(1).setShineDamper(10).setPosition(new Vector3f(0, 0, -25)).setRotate(new Vector3f(0, 1, 0))};
    }

    public CameraData getCameraData() {
        return new CameraData(0, 0, 0, 0, new Vector3f(0, 0, 0));
    }

}
