package com.quartzy.pathfinding.entities;

import com.quartzy.pathfinding.*;
import com.quartzy.pathfinding.pathfinding.Node;
import com.quartzy.pathfinding.pathfinding.Pathfinding;
import com.quartzy.pathfinding.pathfinding.Vector2;
import com.quartzy.pathfinding.utils.Handler;
import com.quartzy.pathfinding.utils.RenderType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ghost extends Entity{
    private List<Node> path = new ArrayList<>();
    private Node[][] nodes;
    private Vector2 prevPos = new Vector2(-1, -1);
    private boolean dead;
    private long timeWhenNoDead = -1L;
    private boolean flip;
    private int variant;
    private boolean prevCanEatGhost = false;
    private boolean isRunning = false;
    private Node prevEndNode;
    private boolean superUltraTurboMode;
    private boolean prevSuperUltraTurboMode;
    private long superUltraModeStart;
    
    public Ghost(int x, int y, int variant, Handler handler){
        super(x, y, Handler.TILE_SIZE-1, Handler.TILE_SIZE-1, handler);
        this.variant = variant;
    }
    
    @Override
    public void tick(int tickNum){
        if(handler.getPacMan().isAlive()){
            if(this.isColliding(handler.getPacMan()) && !dead){
                if(handler.getPacMan().isCanEatGhost()){
                    this.dead = true;
                    handler.setScore(handler.getScore()+3);
                } else{
                    handler.getPacMan().setAlive(false);
                }
            } else{
                if(path != null && path.size() > 0 && tickNum % 2 == 0){
                    Node node = path.get(0);
                    double x = ((((double) getX() + getWidth()/2) - ((double) node.getX() * Handler.TILE_SIZE + Handler.TILE_SIZE/2)));
                    double y = ((((double) getY() + getHeight()/2) - ((double) node.getY() * Handler.TILE_SIZE + Handler.TILE_SIZE/2)));
                    if(dead && new Vector2(8, 8).equals(new Vector2(node.getX(), node.getY())) && timeWhenNoDead==-1L){
                        timeWhenNoDead = System.currentTimeMillis();
                    }
                    Random r = new Random();
                    if(System.currentTimeMillis()-timeWhenNoDead>=r.nextInt(1000) && timeWhenNoDead!=-1L){
                        dead = false;
                        timeWhenNoDead = -1L;
                    }
                    if(prevPos.equals(new Vector2(node.getX(), node.getY()))){
                        path.remove(0);
                    } else{
                        if(Math.sqrt(x * x + y * y) < 1){
                            prevPos = new Vector2(node.getX(), node.getY());
                            path.remove(0);
                        } else{
                            int vx = Math.abs(x) > Math.abs(y) ? 1 : 0;
                            int vy = Math.abs(x) > Math.abs(y) ? 0 : 1;
                            if(superUltraTurboMode){
                                vx = Math.abs(x) > Math.abs(y) ? 2 : 0;
                                vy = Math.abs(x) > Math.abs(y) ? 0 : 2;
                            }
                            vx *= Math.signum(x);
                            vy *= Math.signum(y);
                            flip = superUltraTurboMode ? vx == 2 : vx == 1;
                            setX(getX() - vx);
                            setY(getY() - vy);
                        }
                    }
                }
            }
            if(System.currentTimeMillis()-superUltraModeStart>=3000 && superUltraModeStart!=-1L){
                superUltraTurboMode = false;
                superUltraModeStart = -1L;
            }
            if(tickNum % 20 == 0){
                nodes = handler.getLoadedWorld().toNodeArray();
                int i = (getY() + getHeight() / 2) / Handler.TILE_SIZE;
                int i1 = (getX() + getWidth() / 2) / Handler.TILE_SIZE;
                Node startNode = nodes[i][i1];
                startNode.setRenderType(RenderType.START);
                Node endNode = nodes[handler.getPacMan().getY() / Handler.TILE_SIZE][handler.getPacMan().getX() / Handler.TILE_SIZE];
                if(handler.getLoadedWorld().getDistanceToClosestSuperUltraModePowerup(this)<5 && !isSuperUltraTurboMode()){
                    Vector2 powerup = handler.getLoadedWorld().getClosestSuperUltraModePowerup(this);
                    endNode = nodes[powerup.getY()][powerup.getX()];
                }
                if(superUltraTurboMode && !prevSuperUltraTurboMode){
                    superUltraModeStart = System.currentTimeMillis();
                }
                if(!handler.getPacMan().isCanEatGhost() && prevCanEatGhost){
                    isRunning = false;
                }
                if(isRunning){
                    endNode = prevEndNode;
                }
                if(handler.getPacMan().isCanEatGhost() && !prevCanEatGhost){
                    Vector2 pos;
                    do{
                        pos = handler.getLoadedWorld().getRandomWalkablePos();
                    }while(Math.abs(pos.getX()-handler.getPacMan().getX()/Handler.TILE_SIZE) + Math.abs(pos.getY()-handler.getPacMan().getY()/Handler.TILE_SIZE)<20);
                    endNode= nodes[pos.getY()][pos.getX()];
                    isRunning = true;
                }
                if(this.dead){
                    endNode = nodes[8][8];
                }
                endNode.setRenderType(RenderType.END);
                path = Pathfinding.findPath(startNode, endNode, nodes, false, true);
                if(path != null){
                    Collections.reverse(path);
                }
                prevCanEatGhost = handler.getPacMan().isCanEatGhost();
                prevEndNode = endNode;
                prevSuperUltraTurboMode = superUltraTurboMode;
            }
        }
    }
    
    @Override
    public void render(Graphics2D g){
        BufferedImage ghostTexture;
        switch(variant){
            case 1:
                ghostTexture = Assets.ghostYellow;
                break;
            case 2:
                ghostTexture = Assets.ghostRed;
                break;
            case 3:
                ghostTexture = Assets.ghostGreen;
                break;
            case 4:
                ghostTexture = Assets.ghostPink;
                break;
            case 5:
                ghostTexture = Assets.ghostPurple;
                break;
            default:
                ghostTexture = Assets.ghostBlue;
                break;
        }
        g.drawImage(dead ? Assets.ghostDead : ghostTexture, flip ? getX()+getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight(), null);
        if(handler.isDrawAStarNodes() && nodes!=null){
            for(int i = 0; i < nodes.length; i++){
                for(int i1 = 0; i1 < nodes[i].length; i1++){
                    switch(nodes[i][i1].getRenderType()){
                        case OPEN:
                            g.setColor(new Color(0, 255, 0, 100));
                            break;
                        case PATH:
                            g.setColor(new Color(0, 0, 255, 100));
                            break;
                        case CLOSED:
                            g.setColor(new Color(255, 0, 0, 100));
                            break;
                        case EMPTY:
                            continue;
                        case WALL:
                            g.setColor(Color.BLACK);
                            break;
                        case END:
                            g.setColor(new Color(150, 150, 150, 100));
                            break;
                        case START:
                            g.setColor(new Color(255, 207, 0, 100));
                            break;
                    }
                    g.fillRect(i1 * Handler.TILE_SIZE, i * Handler.TILE_SIZE, Handler.TILE_SIZE, Handler.TILE_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(i1 * Handler.TILE_SIZE, i * Handler.TILE_SIZE, Handler.TILE_SIZE, Handler.TILE_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(i1 * Handler.TILE_SIZE + Handler.TILE_SIZE/2, i * Handler.TILE_SIZE + Handler.TILE_SIZE/2, 2, 2);
                }
            }
        }
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public void setDead(boolean dead){
        this.dead = dead;
    }
    
    public boolean isSuperUltraTurboMode(){
        return superUltraTurboMode;
    }
    
    public void setSuperUltraTurboMode(boolean superUltraTurboMode){
        this.superUltraTurboMode = superUltraTurboMode;
    }
}
