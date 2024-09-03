/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.engineTester;

/**
 *
 * @author Zelone
 */
public class testier {

    public static void main(String[] args) {

        Tree head = new Tree((val, currentval) -> {
            return val - currentval;
        });

        for (int i = 0; i < 100; i++) {
            head.add(i);
        }
        System.out.println(head);
    }
}

class Node {

    private Node nextnode; // linked list
    int val;
    boolean hasval = false;

    public void add(int val) {
        if (!this.hasval) {
            this.val = val;
            this.hasval = true;
            return; //0 //1 //2 //3
        }
        if (nextnode == null) {
            nextnode = new Node();
        }
        nextnode.add(val); //1 2 2 3 3 3
    }

    @Override
    public String toString() {
        return "" + this.val + " " + ((nextnode == null) ? "" : nextnode);
    }

}

@FunctionalInterface
interface ShiftNode {

    public int shiftcheck(int val, int currentval);
}

class Tree {

    private Tree nextnodeLeft; // linked list    
    private Tree nextnodeRight; // linked list
    private ShiftNode shiftcheck;
    int val;
    boolean hasval = false;

    public Tree(ShiftNode shiftcheck) {
        this.shiftcheck = shiftcheck;
    }

    private Tree() {
    }

    public void add(int val) {
        if (!this.hasval) {
            this.val = val;
            this.hasval = true;
            return; //0 //1 //2 //3
        }
        if (shiftcheck.shiftcheck(val, this.val) > 0) {
            if (nextnodeRight == null) {
                nextnodeRight = new Tree();
            }
            nextnodeRight.add(val);
        } else {
            if (nextnodeLeft == null) {
                nextnodeLeft = new Tree();
            }
            nextnodeLeft.add(val);
        }

    }

    @Override
    public String toString() {
        return "" + ((nextnodeLeft == null) ? "" : nextnodeLeft) + "" + this.val + " " + ((nextnodeRight == null) ? "" : nextnodeRight);
    }

}
