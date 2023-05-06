/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.Object;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Zelone
 */
public class All {

    public All() {
        try {
            SystemObject so = Instance.getSystemObjectOrigin();
            SystemObject so2 = SystemObject.createSystemObject("Base1", so);
            for (int i = 0; i < 10; i++) {
                SystemObject.createSystemObject("Base" + (i + 2), so);
            }

            System.out.println(so.transform.parent.so);
            System.out.println(so);

            so2.Save_as("t.javaobject");
            SystemObject so3 = SystemObject.loadSystemObject("t.javaobject");
            System.out.println(so3.transform.parent.so);
            System.out.println(so3.transform.parent.so.transform.parent.so);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new All();
    }
}

class Tree<V> {

    Tree next;
    V value;
    ArrayList<Tree> multipleNext;

    void add(V value) {
        if (this.value == null) {
            this.value = value;
            return;
        }
        if (next == null) {
            next = new Tree();
        }
        next.add(value);
    }

    void addmultiple(V parent, V child) {
        if(value==null){
            this.value=parent;
            addmultiple(parent, child);
            return;
        }
        if (value == parent) {
            if (multipleNext == null) {
                multipleNext = new ArrayList<Tree>();
            }
            Tree t = new Tree();
            t.addmultiple(parent,child);
            multipleNext.add(t);
            return;
        }
        for (Tree tree : multipleNext) {
            if (tree.value == parent) {
                tree.addmultiple(parent, child);
                return;
            }
        }
        
    }

}

class SystemObject implements Serializable, Component {

    public String name;
    //Transform are for lower level Physical Addition to SystemObject
    public Transform transform;
    //Components are for additional Qualitative Addition to SystemObject
    private ArrayList<Component> components;

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean t) {
        String str = "SysO:{name:" + name + ",component:[null";
        for (Component component : components) {
            str = str + "," + component;
        }
        str = str + "]" + ((t) ? "," + transform.toString(false) : "") + "}";
        return str;
    }

    private SystemObject(String name, SystemObject soParent) {
        this.name = name;
        this.transform = Transform.createTransform(this, soParent);
        if (soParent != null) {
            System.out.println("Parent:" + soParent.name + " Child:" + name);
            soParent.add(this);
        }
        components = new ArrayList<Component>();
    }

    public boolean add(SystemObject so) {
        return transform.addChild(so);
    }

    public boolean add(Component component) {
        return components.add(component);
    }

    public boolean remove(SystemObject so) {
        return transform.removeChild(so);
    }

    public boolean remove(Component component) {
        return components.remove(component);
    }

    @Override
    public void Start() {
        transform.Start();
        for (Component component : components) {
            component.Start();
        }
    }

    @Override
    public void Update() {
        transform.Update();
        for (Component component : components) {
            component.Update();
        }
    }

    void Save_as(String path) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(this);
        oos.close();
    }

    public static SystemObject loadSystemObject(String path) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        SystemObject so = (SystemObject) ois.readObject();
        ois.close();
        return so;
    }

    public static SystemObject createSystemObject(String name, SystemObject soParent) {
        //to be made for original files 
        return new SystemObject(name, soParent);
    }

}

class Transform implements Serializable, Component {

    public SystemObject so;

    public Vector3 position;
    public Vector3 localScale;
    public Quanternium rotation;

    public ArrayList<Transform> children;
    public Transform parent;
    private static int count_null_parent;

    @Override
    public String toString() {

        return toString(true);
    }

    public String toString(boolean t) {

        String str = "Tran:{Pos:" + position + ",localScale:" + localScale + ",rotation:" + rotation + ",children:[null";
        for (Transform transform : children) {
            str = str + "," + transform;
        }
        str = str + "]" + ((t) ? "," + so.toString(false) : "") + "}";
        return str;
    }

    private Transform() {
        children = new ArrayList<Transform>();
        position = new Vector3(0, 0, 0);
        localScale = new Vector3(1, 1, 1);
        rotation = new Quanternium(0, 0, 0, 0);
    }

    public static Transform createTransform(SystemObject soChild, SystemObject parent) {

        Transform t = new Transform();
        t.so = soChild;
        if (parent == null) {
            if (count_null_parent == 0) {
                t.parent = null;
                count_null_parent++;
            } else {
                return null;
            }
        } else {
            t.parent = parent.transform;

        }
        return t;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Transform[] getChildren() {
        return (Transform[]) children.toArray();
    }

    public boolean addChild(SystemObject so) {
        return children.add(createTransform(so, this.so));
    }

    public boolean removeChild(SystemObject so) {
        so.transform.parent = null; // to be tested in mind and after running just wrote without thinking 
        return children.remove(so.transform);
    }

    @Override
    public void Start() {
        for (Transform child : children) {
            child.so.Start();
            child.Start();
        }
    }

    @Override
    public void Update() {
        for (Transform child : children) {
            child.so.Start();
            child.Update();
        }
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

interface Component extends Serializable {

    void Start();

    void Update();

    @Override
    public String toString();

}

class Instance {

    private Instance() {
    }
    private static SystemObject systemObjectOriginal;
    private static ArrayList<SystemObject> scenes;
    public static int currentScene;

    static {
        systemObjectOriginal = SystemObject.createSystemObject("null", null);
        scenes = new ArrayList<SystemObject>();
        generateScene();
        //Loadfromfile
    }

    public static int createNewScene() {
        Instance.currentScene = scenes.size();
        generateScene();
        return currentScene;
    }

    public static boolean switchScene(int scene) {
        if (scene > scenes.size() && scene > 0) {
            return false;
        }
        currentScene = scene;
        //update view to change dimentions;
        return true;
    }

    static SystemObject getSystemObjectOrigin() {
        if (scenes.size() < currentScene && scenes.get(currentScene) == null) {
            generateScene();
        }
        return scenes.get(currentScene);
    }

    private static void generateScene() {
        scenes.add(SystemObject.createSystemObject("Scene" + (currentScene + 1), systemObjectOriginal));
    }

}
