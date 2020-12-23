/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zelone.engine;

import com.zelone.basic.Basic;

/**
 *
 * @author Jhawar
 */
public class Manager extends Basic
{

    @Override
    public void cleanUp()
    {
        clean=true;
        DisplayManager.closeDisplay();
    }
    
}
