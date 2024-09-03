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
public class GameFiles {

    ModelFiles d = ModelFiles.model;

    public GameFiles(String folder) throws FileSystemException {
        d.setFolder(folder + "model/");
        throw new FileSystemException(folder, null, "reason");
    }

}
