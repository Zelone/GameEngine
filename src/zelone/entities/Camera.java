/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Zelone
 */
public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;
    private float changesOnKeyPressed = 0.02f;

    public Camera() {
    }

    public void ResetCamera() {
        this.position = new Vector3f(0, 0, 0);
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
        this.changesOnKeyPressed = 0.02f;
    }

    public void move() {

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.y += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_CAPITAL)) {
            position.y -= changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
            yaw += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
            yaw -= changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
            roll += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            roll -= changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            pitch += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
            pitch -= changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            ResetCamera();
            //System.exit(0);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            changesOnKeyPressed += 0.01;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            changesOnKeyPressed -= 0.01;
        }

    }

    public float getPitch() {
        return pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }

    public float getChangesOnKeyPressed() {
        return this.changesOnKeyPressed;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setChangesOnKeyPressed(float changesOnKeyPressed) {
        this.changesOnKeyPressed = changesOnKeyPressed;
    }

}
