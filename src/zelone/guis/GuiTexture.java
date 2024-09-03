/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.guis;

import org.lwjgl.util.vector.Vector2f;

/**
 *
 * @author Zelone
 */
public class GuiTexture {
    private int texture;
    private Vector2f position; //scale
    private Vector2f scale; // size of screen needs to be added

    public GuiTexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public Vector2f getScale() {
        return scale;
    }

    public Vector2f getPosition() {
        return position;
    }

    public int getTexture() {
        return texture;
    }
    
}
