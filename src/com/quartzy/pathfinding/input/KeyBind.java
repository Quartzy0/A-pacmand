package com.quartzy.pathfinding.input;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeyBind {
    private boolean pressed;
    private String name;
    private int keyCode;
    private List<Pressed> pressedAction = new ArrayList<>();

    public KeyBind(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public boolean isPressed() {
        return pressed;
    }

    public String getName() {
        return name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setPressed(boolean pressed){
        this.pressed = pressed;
    }

    public void press(KeyEvent e){
        for (int a = 0;a<pressedAction.size();a++){
            try{
                pressedAction.get(a).pressed(e);
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void addOnPressed(Pressed pressed){
        pressedAction.add(pressed);
    }
}
