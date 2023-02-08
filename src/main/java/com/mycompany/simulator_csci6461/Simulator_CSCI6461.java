/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.simulator_csci6461;

import java.awt.EventQueue;

/**
 *
 * @author primasanghvi
 */
public class Simulator_CSCI6461 {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrontPanel panel = new FrontPanel();
                panel.setVisible(true);
            }
            });
        }
    }
