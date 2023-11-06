/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.Object;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Zelone
 */
public class GameObject implements MonoBehaviour {

    public Transform transform;
    private ArrayList<? extends Component> components;
    public static ArrayList<GameObject> goList;

    static {
        goList = new ArrayList<>();
    }

    public GameObject() {
        goList.add(this);
        components = new ArrayList<>();
        transform = new Transform(this);
    }

    public GameObject(GameObject parent) {
        this();
        transform.parent = parent.transform;
    }

    public <T extends Component> T GetComponent() {
        for (Component component : components) {
            if (component instanceof T) {
                return (T) component;
            }
        }
        return null;
    }

    public <T extends Component> void AddComponent(T component) {
        try {
            components.add((Component) component);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Start() {
        for (Component component : components) {
            component.Start();
        }
    }

    @Override
    public void Update() {
        for (Component component : components) {
            component.Update();
        }
    }

    public static void main(String[] args) {
        new GameObject();
    }
}

class Transform {

    public GameObject go;
    public Vector3 position, localScale;
    public Quanternium rotation;
    public Transform parent;

    public Transform(GameObject go) {
        this.go = go;
        position = new Vector3();
        localScale = new Vector3(1, 1, 1);
        rotation = new Quanternium();
        parent = GameObject.goList.get(0).transform;
    }
}

class Vector3 implements Serializable, Comparable<Vector3> {

    public float x, y, z;

    @Override
    public String toString() {
        return "Vect:{X:" + x + ",Y:" + y + ",Z:" + z + "}";
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    @Override
    public int compareTo(Vector3 o) {
        //compare code to be written
        int multiplier = 10;
        return (int) ((Math.abs((this.x * multiplier) - (x * multiplier)) + Math.abs((this.y * multiplier) - (y * multiplier)) + Math.abs((this.z * multiplier) - (z * multiplier))) * multiplier);
    }

}

class Quanternium implements Serializable, Comparable<Quanternium> {

    public float w, x, y, z;

    @Override
    public String toString() {
        return "Quat:{W:" + w + ",X:" + x + ",Y:" + y + ",Z:" + z + "}";
    }

    public Quanternium(float x, float y, float z) {
        this(0, x, y, z);
    }

    public Quanternium() {
        this(0, 0, 0, 0);
    }

    public Quanternium(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int compareTo(Quanternium o) {
        //compare code to be written
        int multiplier = 10;
        return (int) ((Math.abs((this.w * multiplier) - (w * multiplier)) + Math.abs((this.y * multiplier) - (y * multiplier)) + Math.abs((this.x * multiplier) - (x * multiplier)) + Math.abs((this.z * multiplier) - (z * multiplier))) * multiplier);
    }

}

interface Component extends MonoBehaviour {
}

interface MonoBehaviour {

    void Start();

    void Update();
}
