package com.quartzy.pathfinding.input;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyListener implements java.awt.event.KeyListener {

    private int prevKey;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (int a = 0;a<KeyBinds.keyBinds.size();a++){
            if (KeyBinds.keyBinds.get(a).getKeyCode()==e.getKeyCode()){
                KeyBinds.keyBinds.get(a).setPressed(true);
                KeyBinds.keyBinds.get(a).press(e);
            }
        }
        for (Pressed allKeyPressEvent : KeyBinds.allKeyPressEvents) {
            try{
                allKeyPressEvent.pressed(e);
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
        prevKey = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (int a = 0;a<KeyBinds.keyBinds.size();a++){
            if (KeyBinds.keyBinds.get(a).getKeyCode()==e.getKeyCode()){
                KeyBinds.keyBinds.get(a).setPressed(false);
            }
        }
    }
}
