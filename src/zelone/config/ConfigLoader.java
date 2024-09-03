/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.config;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Zelone
 */
public class ConfigLoader {

    TerrainsData[] terrainsDatas;
    EntityData[] entityDatas;
    CameraData cameraData;

    private Object[] getObjectOfField(Field[] fields, JSONObject jsonObj) {
        try {
            Object[] fieldData = new Object[fields.length];
            int i = 0;
            for (Field field : fields) {
                Object data = null;
                Type type = field.getGenericType();
                if (type.getTypeName().contains(".")) {
                    if (type.getTypeName().contains("String")) {
                        data = jsonObj.getString(field.getName());
                    } else if (type.getTypeName().contains("vector")) {
                        JSONObject jsonvalues = jsonObj.getJSONObject(field.getName());
                        data = new Vector3f(Float.parseFloat(jsonvalues.getString("X")), Float.parseFloat(jsonvalues.getString("Y")), Float.parseFloat(jsonvalues.getString("Z")));
                    }

                } else {
                    String s = jsonObj.getString(field.getName());
                    switch (type.getTypeName()) {
                        case "float":
                            data = Float.parseFloat(s);
                            break;
                        case "boolean":
                            data = Boolean.parseBoolean(s);
                            break;
                        case "int":
                            data = Integer.parseInt(s);
                            break;
                    }

                }
                fieldData[i++] = data;
            }
            return fieldData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ConfigLoader() {
        /*
        try {
            JSONObject json = new JSONObject(new JSONTokener(new FileInputStream(new File("config/config.json"))));

            JSONObject jsonCameraObj = json.getJSONObject("camera");
            Field[] fields = CameraData.class.getFields();
            cameraData = new CameraData(true, getObjectOfField(fields, jsonCameraObj));

            JSONArray jsonArrayObj = json.getJSONArray("terrains");
            int JSONlength = jsonArrayObj.length();
            terrainsDatas = new TerrainsData[JSONlength];
            fields = TerrainsData.class.getFields();
            for (int i = 0; i < JSONlength; i++) {
                JSONObject jsonTerrainObj = jsonArrayObj.getJSONObject(i);
                terrainsDatas[i] = new TerrainsData(true, getObjectOfField(fields, jsonTerrainObj));
            }
            jsonArrayObj = json.getJSONArray("entities");
            JSONlength = jsonArrayObj.length();
            entityDatas = new EntityData[JSONlength];
            fields = EntityData.class.getFields();
            for (int i = 0; i < JSONlength; i++) {
                JSONObject jsonEntityObj = jsonArrayObj.getJSONObject(i);
                entityDatas[i] = new EntityData(true, getObjectOfField(fields, jsonEntityObj));
            }

        } catch (Exception e) {
            File f = new File("config/");
            if ((f.exists() && f.isDirectory()) == false) {
                try {
                    f.createNewFile();
                } catch (Exception ex) {
                }
            }
            f = new File("config/config.json");
            if (f.exists()) {
                System.out.println("Some error in config file.");
                f.delete();
                System.exit(0);
            } else {//TO be EDITED
                System.out.println("Creating in config file at location /config/config.json with basic data");
                try {
                    f.createNewFile();
                    JSONObject jsonobj = new JSONObject();
                    JSONObject jsonInternalCameraObj = new JSONObject();
                    jsonobj.put("camera", jsonInternalCameraObj);
                    Field[] fildsInClass = CameraData.class.getFields();
                    for (Field field : fildsInClass) {
                        jsonInternalCameraObj.put(field.getName(), field.getGenericType().getTypeName());
                    }

                    JSONObject jsonInternalTerrainObj = new JSONObject();
                    jsonobj.put("terrains", new JSONArray(new Object[]{jsonInternalTerrainObj}));

                    //
                    fildsInClass = TerrainsData.class.getFields();
                    for (Field field : fildsInClass) {
                        jsonInternalTerrainObj.put(field.getName(), field.getGenericType().getTypeName());
                    }

                    JSONObject jsonInternalEntityObj = new JSONObject();
                    jsonobj.put("entities", new JSONArray(new Object[]{jsonInternalEntityObj}));

                    fildsInClass = EntityData.class.getFields();
                    for (Field field : fildsInClass) {
                        jsonInternalEntityObj.put(field.getName(), field.getGenericType().getTypeName());
                    }

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(jsonobj.toString().getBytes());
                    fos.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
         */
        cameraData = getCameraData();
        terrainsDatas = getTerrains();
        entityDatas = getEntities();

    }

    public TerrainsData[] getTerrains() {
        return new TerrainsData[]{new TerrainsData(0, 0, "dirt", "pinkFlowers", "path", "grassy", "blendMap")};
    }

    public EntityData[] getEntities() {
        return new EntityData[]{
            new EntityData("lowPolyTree", "lowPolyTree").setReflectivity(1).setShineDamper(10).setPosition(new Vector3f(0, 0, 0)).setRotate(new Vector3f(0, 0, 0)),
            new EntityData("stall", "stallTexture").setReflectivity(1).setShineDamper(10).setPosition(new Vector3f(0, 0, 15)).setRotate(new Vector3f(0, 0, 0)),
            new EntityData("Town", "Town").setReflectivity(1).setShineDamper(10).setScale(3.0f).setPosition(new Vector3f(0, 0, 30)).setRotate(new Vector3f(0, 0.04f, 0))};
    }

    public CameraData getCameraData() {
        return new CameraData(0, 0.5f, 0, new Vector3f(50, 5, 50), 0.05f);
    }

}
