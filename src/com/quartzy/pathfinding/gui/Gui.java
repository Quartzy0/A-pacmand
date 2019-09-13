package com.quartzy.pathfinding.gui;

import com.quartzy.pathfinding.utils.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui {
    private List<GuiComponent> components = new ArrayList<>();
    private String name;
    private boolean pauseGame;
    protected Handler handler;
    private boolean first = true;

    public Gui(String name, boolean pauseGame, Handler handler) {
        this.name = name;
        this.pauseGame = pauseGame;
        this.handler = handler;
    }
    
    public void init(){
    
    }

    public void tick(){
        if(first){
            init();
            first = false;
            return;
        }
        for (GuiComponent component : components) {
            if(component!=null){
                component.tick();
            }
        }
    }

    public void render(Graphics2D g){
        for (GuiComponent component : components) {
            if(component!=null){
                component.render(g);
            }
        }
    }

    public boolean isPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    public String getName() {
        return name;
    }

    public List<GuiComponent> getComponents() {
        return components;
    }

    public void addComponet(GuiComponent component){
        components.add(component);
    }
}
