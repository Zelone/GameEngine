/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zelone.toolBox;

import java.io.InputStream;

/**
 *
 * @author Zelone
 */
public class Location {

    public InputStream getResourceAsStream(String name) {
        return getClass().getResourceAsStream(name);
    }
}
