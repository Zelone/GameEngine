/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Zelone
 */
public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;
    private float changesOnKeyPressed = 0.02f;
    private float changesOnMouseMovement = 4f;

    public Camera() {
    }

    public void ResetCamera() {
        this.position = new Vector3f(0, 0, 0);
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
        this.changesOnKeyPressed = 0.02f;
        this.changesOnMouseMovement = 4.0f;
    }

    public void move() {
        if(Mouse.isGrabbed()){
        yaw += Mouse.getDX() / changesOnMouseMovement;
        pitch -= Mouse.getDY() / changesOnMouseMovement;
        if (pitch >= 90) {
            pitch = 90;
        } else if (pitch <= -90) {
            pitch = -90;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z += -(float) Math.cos(Math.toRadians(yaw)) * changesOnKeyPressed;
            position.x += (float) Math.sin(Math.toRadians(yaw)) * changesOnKeyPressed;
            position.y -= (float) Math.sin(Math.toRadians(pitch)) * changesOnKeyPressed;

//position.z -= changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z -= -(float) Math.cos(Math.toRadians(yaw)) * changesOnKeyPressed;
            position.x -= (float) Math.sin(Math.toRadians(yaw)) * changesOnKeyPressed;
            position.y += (float) Math.sin(Math.toRadians(pitch)) * changesOnKeyPressed;
//position.z += changesOnKeyPressed;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.z += (float) Math.sin(Math.toRadians(yaw)) * changesOnKeyPressed;
            position.x += (float) Math.cos(Math.toRadians(yaw)) * changesOnKeyPressed;
            //position.x += changesOnKeyPressed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.z -= (float) Math.sin(Math.toRadians(yaw)) * changesOnKeyPressed;
            position.x -= (float) Math.cos(Math.toRadians(yaw)) * changesOnKeyPressed;
            //position.x -= changesOnKeyPressed;
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
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            changesOnKeyPressed += 0.01;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            changesOnKeyPressed -= 0.01;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            changesOnMouseMovement += 0.01;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            changesOnMouseMovement -= 0.01;
            System.err.println("Mouse:" + changesOnMouseMovement);
        }
    }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(!Mouse.isGrabbed());
            //System.exit(0);
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
