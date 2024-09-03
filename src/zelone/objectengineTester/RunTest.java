/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.objectengineTester;

import java.nio.file.FileSystemException;

/**
 *
 * @author Zelone
 */
public class RunTest {

    public RunTest() {
        ModelFiles d = ModelFiles.model;
        d.setFolder("RR");
        ModelFiles d2 = ModelFiles.model;
        d2.setFolder("EE");
        System.out.println(d.folderName + "" + d2.folderName);
    }

    public void f() {
        GameFiles gfiles=null;
        try {
            gfiles = new GameFiles("/res/");
        } catch (FileSystemException ex) {
            System.out.println("File Has error" + ex.getMessage());
        }
        GameEngine ge = new GameEngine();
        ge.loadModels(gfiles);
        GameEngine.Entity e = ge.newEntity();

        GameEngine.Terrain t = ge.newTerrain();
        ge.run();
        e.move(90, GameEngine.forwardDirection);
        e.whenClicked(() -> {
        });

    }

    public static void main(String[] args) {
        new RunTest();
    }
}
