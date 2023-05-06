/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.entities;

import zelone.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class Entity {

    private TexturedModel model;
    
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    private Vector3f rotate;
    private Vector3f translate;
    private float changeScale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void incresePostion(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    private void increseScale(float dScale) {
        this.scale += dScale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Entity move(Vector3f rotate, Vector3f translate) {
        return move(rotate, translate, 0);
    }

    public Entity move(Vector3f rotate, Vector3f translate, float changeScale) {
        this.rotate = rotate;
        this.translate = translate;
        this.changeScale = changeScale;
        return this;
    }

    public void run() {
        this.incresePostion(translate.getX(), translate.getY(), translate.getZ());
        this.increseRotation(rotate.getX(), rotate.getY(), rotate.getZ());
        this.increseScale(changeScale);
    }

}
