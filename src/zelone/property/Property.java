/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.property;

/**
 *
 * @author Zelone
 */
public interface Property extends Cloneable {


    public void init();

    public void update();

    public void randomupdate();

    public void close();

}
/**
 * Property: Traslate can move can rotate can change size 
 *           Texture can store texture info
 *           shader can use texture and translate data to shade the area 
 */