package com.quartzy.pathfinding.gui;

import java.awt.*;

public class GuiToggleButton extends GuiButton{
    private boolean value = false;
    private String textOn, textOff;
    
    public GuiToggleButton(int x, int y, int width, int height, int borderThickness, Color borderColor, Color insideColor, String textOn, String textOff, Color textColor){
        super(x, y, width, height, borderThickness, borderColor, insideColor, textOff, textColor);
        this.textOn = textOn;
        this.textOff = textOff;
        setClickEvent(new OnClickedEvent(){
            @Override
            public void clicked(){
                value = !value;
                setText(value ? textOn : textOff);
            }
        });
    }
    
    public boolean getValue(){
        return value;
    }
    
    public void setValue(boolean value){
        this.value = value;
    }
    
    public String getTextOn(){
        return textOn;
    }
    
    public void setTextOn(String textOn){
        this.textOn = textOn;
    }
    
    public String getTextOff(){
        return textOff;
    }
    
    public void setTextOff(String textOff){
        this.textOff = textOff;
    }
}
