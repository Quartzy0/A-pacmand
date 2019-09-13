package com.quartzy.pathfinding.entities;

import com.quartzy.pathfinding.utils.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity{
    
    private int x, y, width, height;
    private boolean alive;
    protected Handler handler;
    
    public Entity(int x, int y, int width, int height, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        alive = true;
        this.handler = handler;
    }
    
    public void tick(int tickNum){
    
    }
    
    public void render(Graphics2D g){
    
    }
    
    protected GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    
    protected BufferedImage rotate(BufferedImage image, double angle) {
        if(image==null)return null;
        int w = image.getWidth(), h = image.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }
    
    public boolean isColliding(Entity entity){
        if(entity==null)return false;
        Rectangle rectangle = new Rectangle(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
        return isColliding(rectangle);
    }
    
    public boolean isColliding(Rectangle rect){
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return rectangle.intersects(rect);
    }
    
    public boolean isCollidingWithWorld(){
        int[][] nodes = handler.getLoadedWorld().getNodes();
        for(int i = 0; i < nodes.length; i++){
            for(int i1 = 0; i1 < nodes[i].length; i1++){
                if(isColliding(new Rectangle(i1*Handler.TILE_SIZE, i*Handler.TILE_SIZE, Handler.TILE_SIZE, Handler.TILE_SIZE)) && nodes[i][i1]!=0){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isColliding(Point point){
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return rectangle.contains(point);
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public int getWidth(){
        return width;
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public boolean isAlive(){
        return alive;
    }
    
    public void setAlive(boolean alive){
        this.alive = alive;
    }
}
