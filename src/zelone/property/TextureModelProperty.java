/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.property;

import zelone.OBJConverter.ModelData;
import zelone.OBJConverter.OBJFileLoader;
import zelone.engine.Engine;
import org.json.JSONException;

/**
 *
 * @author Zelone
 */
public class TextureModelProperty implements Property {

    public final String modelname;
    public int indicesLength;
    public int vaoID;

    public TextureModelProperty(String modelname) throws JSONException {
        this.modelname = modelname;
    }

    @Override
    public void init() {
        try {
            ModelData model = OBJFileLoader.loadOBJ(this.modelname);
            this.indicesLength = model.getIndices().length;
            this.vaoID = Engine.loadToVAO(model.getVertices(), model.getTextureCoords(), model.getNormals(), model.getIndices());
        } catch (Exception e) {
        }
    }

    @Override
    public void update() {
        return;
    }

    @Override
    public void randomupdate() {
        return;
    }

    @Override
    public void close() {
        try {
            Engine.removeVao(this.vaoID);
        } catch (Exception ex) {
        }
    }

}
