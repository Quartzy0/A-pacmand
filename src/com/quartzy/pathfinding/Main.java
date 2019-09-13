package com.quartzy.pathfinding;

import com.quartzy.pathfinding.fx.Display;
import com.quartzy.pathfinding.utils.Handler;

import javax.swing.*;

public class Main{

    public static Display display;

    public static void main(String[] args){
        try{
            display = new Display("A* pathfinding", Handler.TILE_SIZE * 32, Handler.TILE_SIZE * 32);
            display.init();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
