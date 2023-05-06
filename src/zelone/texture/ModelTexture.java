/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.texture;

import zelone.config.EntityData;

/**
 *
 * @author Jhawar
 */
public class ModelTexture {

    private int textureID;
    private EntityData data;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public void setShineDamper(float shineDamper) {
        data.shineDamper = shineDamper;
    }

    public EntityData getEntityData() {
        return data;
    }

    public void setReflectivity(float reflectivity) {
        data.reflectivity = reflectivity;
    }

    public boolean hasTransperancy() {
        return data.hasTransperancy;
    }

    public boolean useFakeLighting() {
        return data.useFakeLighting;
    }

    public ModelTexture setUseFakeLighting(boolean useFakeLighting) {
        data.useFakeLighting = useFakeLighting;
        return this;
    }

    public ModelTexture setData(EntityData data) {
        this.data = data;
        return this;
    }

    public ModelTexture setHasTransperancy(boolean hasTransperancy) {
        data.hasTransperancy = hasTransperancy;
        return this;
    }

    public float getShineDamper() {
        return data.shineDamper;
    }

    public float getReflectivity() {
        return data.reflectivity;
    }

    public int getID() {
        return textureID;
    }

}
