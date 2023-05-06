/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.property;

/**
 *
 * @author Zelone
 */
public class MovementProperty implements Component {

    @Override
    public void init() {
        
        for (Property p : properties) {
            p.init();
        }
    }

    @Override
    public void update() {
        for (Property p : properties) {
            p.update();
        }

    }

    @Override
    public void randomupdate() {
        for (Property p : properties) {
            p.randomupdate();
        }

    }

    @Override
    public void close() {
        for (Property p : properties) {
            p.close();
        }

    }

    @Override
    public boolean addProperty(Property p) {
        return properties.add(p);
    }

    @Override
    public boolean removeProperty(Property p) {
        return properties.remove(p);
    }

}
