/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.basic;

/**
 *
 * @author Jhawar
 */
public abstract class Basic {

    public boolean clean = false;

    public abstract void cleanUp();

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        while (!clean) {
            cleanUp();
        }
    }

}
