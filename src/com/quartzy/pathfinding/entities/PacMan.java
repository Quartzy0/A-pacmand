package com.quartzy.pathfinding.entities;

import com.quartzy.pathfinding.Assets;
import com.quartzy.pathfinding.utils.Direction;
import com.quartzy.pathfinding.utils.FontUtils;
import com.quartzy.pathfinding.utils.Handler;
import com.quartzy.pathfinding.input.KeyBind;
import com.quartzy.pathfinding.input.KeyBinds;
import com.quartzy.pathfinding.input.Pressed;
import com.quartzy.pathfinding.utils.MathUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PacMan extends Entity{
    private boolean canEatGhost;
    private Direction direction;
    private long eatingGhostStart = -1L;
    private BufferedImage texture;
    
    public PacMan(int x, int y, Handler handler){
        super(x, y, Handler.TILE_SIZE-6, Handler.TILE_SIZE-6, handler);
        canEatGhost = false;
        direction = Direction.UP;
        handler.setPacMan(this);
        KeyBind W = new KeyBind("W", KeyEvent.VK_W);
        KeyBind A = new KeyBind("A", KeyEvent.VK_A);
        KeyBind S = new KeyBind("S", KeyEvent.VK_S);
        KeyBind D = new KeyBind("D", KeyEvent.VK_D);
        W.addOnPressed(new Pressed(){
            @Override
            public void pressed(KeyEvent e){
                direction = Direction.UP;
            }
        });
        A.addOnPressed(new Pressed(){
            @Override
            public void pressed(KeyEvent e){
                direction = Direction.LEFT;
            }
        });
        S.addOnPressed(new Pressed(){
            @Override
            public void pressed(KeyEvent e){
                direction = Direction.DOWN;
            }
        });
        D.addOnPressed(new Pressed(){
            @Override
            public void pressed(KeyEvent e){
                direction = Direction.RIGHT;
            }
        });
        KeyBinds.keyBinds.add(W);
        KeyBinds.keyBinds.add(A);
        KeyBinds.keyBinds.add(S);
        KeyBinds.keyBinds.add(D);
    }
    
    @Override
    public void tick(int tickNum){
        if(isCanEatGhost() && tickNum % 30 == 0){
            if(texture.equals(Assets.pacman)){
                texture = Assets.pacmanFull;
            }else {
                texture = Assets.pacman;
            }
        }
        if(!isCanEatGhost()){
            texture = Assets.pacman;
        }
        if(isAlive()){
            if(System.currentTimeMillis()-eatingGhostStart>=6000 && eatingGhostStart!=-1L){
                this.canEatGhost = false;
                eatingGhostStart = -1L;
            }
            if(tickNum % 2 == 0){
                switch(direction){
                    case UP:
                        int y = getY() - 1;
                        setY(y);
                        if(isCollidingWithWorld()){
                            setY(y + 1);
                        }
                        break;
                    case DOWN:
                        y = getY() + 1;
                        setY(y);
                        if(isCollidingWithWorld()){
                            setY(y - 1);
                        }
                        break;
                    case LEFT:
                        int x = getX() - 1;
                        setX(x);
                        if(isCollidingWithWorld()){
                            setX(x + 1);
                        }
                        break;
                    case RIGHT:
                        x = getX() + 1;
                        setX(x);
                        if(isCollidingWithWorld()){
                            setX(x - 1);
                        }
                        break;
                }
            }
        }
    }
    
    @Override
    public void render(Graphics2D g){
        double angle = 0;
        if(isAlive() && !handler.isGameWon()){
            switch(direction){
                case RIGHT:
                    angle = 180;
                    break;
                case LEFT:
                    angle = 0;
                    break;
                case UP:
                    angle = 90;
                    break;
                case DOWN:
                    angle = 270;
                    break;
            }
        }
        g.drawImage(rotate(texture, angle), getX(), getY(), getWidth()+4, getHeight()+4, null);
        g.setColor(Color.YELLOW);
        if(isCanEatGhost()){
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(15F));
            String s = MathUtils.round((6000D -(double)(System.currentTimeMillis() - eatingGhostStart)) / 1000D, 1) + "";
            g.drawString(s, (float) (getX() + FontUtils.getTextSize(s, g.getFont(), g).getWidth()/2), getY()+5);
        }
    }
    
    public boolean isCanEatGhost(){
        return canEatGhost;
    }
    
    public void setCanEatGhost(boolean canEatGhost){
        this.canEatGhost = canEatGhost;
        if(this.canEatGhost){
            this.eatingGhostStart = System.currentTimeMillis();
        }
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    
    @Override
    public boolean isColliding(Rectangle rect){
        Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        return rectangle.intersects(rect);
    }
}
