/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javax.swing.JFrame;

/**
 *
 * @author Mustafa
 */
public class TEST {
    
    public static void main(String[] args){
        JFrame frame = new JFrame("BorderLayoutDemo");
        frame.setUndecorated(true); // Remove title bar
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
