/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.rawengineTester;

import java.util.ArrayList;

/**
 *
 * @author Zelone
 */
public class ComponentPropertySystem {

    class A {

        ArrayList<B> componentList;

        A() {
            componentList = new ArrayList<B>();
        }

        void update(float dt) {
            for (B b : componentList) {
                b.update(dt);
            }
        }

        void start() {
            for (B b : componentList) {
                b.start();
            }
        }

        void addComponent(B b) {
            componentList.add(b);
            b.gameObject = this;
        }

        public <T extends B> T getComponent(Class<T> b) {
            for (B b1 : componentList) {
                if (b.isAssignableFrom(b1.getClass())) {
                    try {
                        return b.cast(b1);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                        assert false : "Error: Casting component.";
                    }
                }
            }
            return null;
        }

        public <T extends B> void removeComponent(Class<T> b) {
            for (int i = 0; i < componentList.size(); i++) {
                B b1 = componentList.get(i);
                if (b.isAssignableFrom(b1.getClass())) {
                    try {
                        componentList.remove(i);
                        return;
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                        assert false : "Error: Casting component.";
                    }
                }
            }
        }
    }

    abstract class B {

        A gameObject;

        public B() {

        }

        abstract void update(float dt);

        void start() {
        }

    }

    class C extends B {

        public C() {

        }

        @Override
        void update(float dt) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }

    class A0 {

        public A0() {
            A tree = new A();
            tree.addComponent(new C());
            tree.start();
            tree.update(0.7f);
        }

    }

}
