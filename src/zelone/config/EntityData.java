/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.config;

import org.lwjgl.util.vector.Vector3f;
import zelone.OBJConverter.ModelData;
import zelone.OBJConverter.OBJFileLoader;
import zelone.engine.Loader;
import zelone.entities.Entity;
import zelone.models.RawModel;
import zelone.models.TexturedModel;
import zelone.render.OBJLoader;
import zelone.texture.ModelTexture;

/**
 *
 * @author Zelone
 */
public class EntityData {

    public final String model;
    public final String modelTexture;
    public float shineDamper = 1;
    public float reflectivity = 0;
    public boolean hasTransperancy = false;
    public boolean useFakeLighting = false;
    public Vector3f position = new Vector3f(0, 0, 0);
    public float rotX = 0;
    public float rotY = 0;
    public float rotZ = 0;
    public float scale = 1;
    public Vector3f rotate = new Vector3f(0, 0, 0);
    public Vector3f translate = new Vector3f(0, 0, 0);
    public float changeScale = 0;
    public boolean TypeSetting = true;

    public EntityData(String model, String modelTexture) {
        this.model = model;
        this.modelTexture = modelTexture;
    }
/*
    public EntityData(boolean internal, Object... ask) {
        Field[] f = CameraData.class.getFields();
        try {
            for (int i = 0; i < f.length; i++) {
                switch (f[i].getName()) {
                    case "model":
                        this.model = ((String) ask[i]);
                        break;
                    case "modelTexture":
                        this.modelTexture = ((String) ask[i]);
                        break;
                    case "rotate":
                        this.rotate = ((Vector3f) ask[i]);
                        break;
                    case "hasTransperancy":
                        this.hasTransperancy = ((Boolean) ask[i]);
                        break;
                    case "scale":
                        this.scale = ((Float) ask[i]);
                        break;
                    case "useFakeLighting":
                        this.useFakeLighting = ((Boolean) ask[i]);
                        break;
                    case "changeScale":
                        this.changeScale = ((Float) ask[i]);
                        break;
                    case "reflectivity":
                        this.reflectivity = ((Float) ask[i]);
                        break;
                    case "translate":
                        this.translate = ((Vector3f) ask[i]);
                        break;
                    case "rotX":
                        this.rotX = ((Float) ask[i]);
                        break;
                    case "shineDamper":
                        this.shineDamper = ((Float) ask[i]);
                        break;
                    case "rotZ":
                        this.rotZ = ((Float) ask[i]);
                        break;
                    case "TypeSetting":
                        this.TypeSetting = ((Boolean) ask[i]);
                        break;
                    case "rotY":
                        this.rotY = ((Float) ask[i]);
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
*/
    public void setTypeSetting(boolean TypeSetting) {
        this.TypeSetting = TypeSetting;
    }

    public EntityData setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
        return this;
    }

    public EntityData setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
        return this;
    }

    public EntityData setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
        return this;
    }

    public EntityData setHasTransperancy(boolean hasTransperancy) {
        this.hasTransperancy = hasTransperancy;
        return this;
    }

    public EntityData setPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public EntityData setRotX(float rotX) {
        this.rotX = rotX;
        return this;
    }

    public EntityData setRotY(float rotY) {
        this.rotY = rotY;
        return this;
    }

    public EntityData setRotZ(float rotZ) {
        this.rotZ = rotZ;
        return this;
    }

    public EntityData setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public EntityData setRotate(Vector3f rotate) {
        this.rotate = rotate;
        return this;
    }

    public EntityData setTranslate(Vector3f translate) {
        this.translate = translate;
        return this;
    }

    public EntityData setChangeScale(float changeScale) {
        this.changeScale = changeScale;
        return this;
    }
    
    public Entity toEntity( Loader loader) {
        if (this.TypeSetting) {
            
            ModelData data = OBJFileLoader.loadOBJ(this.model);
            
            RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());//OBJLoader.loadObjModel("stall", loader);
            ModelTexture texture = new ModelTexture(loader.loadTexture(this.modelTexture));
            texture.setData(this);
            TexturedModel texturedModel = new TexturedModel(model, texture);
            
            Entity entity = new Entity(texturedModel, this.position, this.rotX, this.rotY, this.rotZ, this.scale);
            entity.move(this.rotate, this.translate);
            
            return entity;
        } else {
            return new Entity(new TexturedModel(OBJLoader.loadObjModel(this.model, loader), new ModelTexture(loader.loadTexture(this.modelTexture)).setData(this)), this.position, this.rotX, this.rotY, this.rotZ, this.scale).move(this.rotate, this.translate);
        }
    }

}
