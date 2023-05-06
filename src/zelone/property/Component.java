/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.property;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Zelone
 */
public interface Component extends Property {

    ArrayList<Property> properties = new ArrayList<>();

    public boolean addProperty(Property p);

    public boolean removeProperty(Property p);

}
