package com.quartzy.pathfinding.fx;

import com.quartzy.pathfinding.entities.Ghost;
import com.quartzy.pathfinding.utils.Handler;
import com.quartzy.pathfinding.pathfinding.Node;
import com.quartzy.pathfinding.utils.RenderType;
import com.quartzy.pathfinding.pathfinding.Vector2;
import com.quartzy.pathfinding.entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World{
    private int[][] nodes;
    private List<Entity> entities;
    private Handler handler;
    private int[][] coinData;
    
    public World(int[][] nodes, List<Entity> entities, Handler handler){
        this.nodes = nodes;
        this.entities = entities;
        this.handler = handler;
        coinData = new int[nodes.length][nodes[0].length];
        Random r = new Random(System.nanoTime());
        for(int i = 0; i < coinData.length; i++){
            for(int i1 = 0; i1 < coinData[i].length; i1++){
                coinData[i][i1] = 1;
                if(r.nextInt(20)==1){
                    coinData[i][i1] = 2;
                }
            }
        }
    }
    
    public World(int[][] nodes, List<Entity> entities, int[][] coinData, Handler handler){
        this.nodes = nodes;
        this.entities = entities;
        this.handler = handler;
        this.coinData = coinData;
    }
    
    public void tick(int tickNum){
        for(Entity entity : entities){
            entity.tick(tickNum);
        }
        boolean won = true;
        for(int i = 0; i < coinData.length; i++){
            for(int i1 = 0; i1 < coinData[i].length; i1++){
                if(coinData[i][i1]==1 || coinData[i][i1]==2)won = false;
                if(handler.getPacMan().isColliding(new Rectangle(i1*Handler.TILE_SIZE, i*Handler.TILE_SIZE, Handler.TILE_SIZE, Handler.TILE_SIZE))){
                    if(coinData[i][i1]==0){
                        handler.setScore(handler.getScore()+1);
                    }else if(coinData[i][i1]==2){
                        handler.setScore(handler.getScore()+1);
                        handler.getPacMan().setCanEatGhost(true);
                    }
                    if(coinData[i][i1]!=3){
                        coinData[i][i1] = 1;
                    }
                }
                for(Entity entity : entities){
                    if(entity instanceof Ghost){
                        Ghost ghost = (Ghost) entity;
                        if(ghost.isColliding(new Rectangle(i1 * Handler.TILE_SIZE, i * Handler.TILE_SIZE, Handler.TILE_SIZE, Handler.TILE_SIZE))){
                            if(coinData[i][i1] == 3){
                                ghost.setSuperUltraTurboMode(true);
                                coinData[i][i1] = 1;
                            }
                        }
                    }
                }
            }
        }
        handler.setGameWon(won);
    }
    
    public void render(Graphics2D g){
        for(int i = 0; i < coinData.length; i++){
            for(int i1 = 0; i1 < coinData[i].length; i1++){
                if(coinData[i][i1]==0){
                    g.setColor(Color.WHITE);
                    g.fillOval(i1*Handler.TILE_SIZE + Handler.TILE_SIZE/2-2, i*Handler.TILE_SIZE + Handler.TILE_SIZE/2-2, 4, 4);
                }else if(coinData[i][i1]==2){
                    g.setColor(Color.WHITE);
                    g.fillOval(i1*Handler.TILE_SIZE + Handler.TILE_SIZE/2-3, i*Handler.TILE_SIZE + Handler.TILE_SIZE/2-3, 6, 6);
                }else if(coinData[i][i1]==3){
                    g.setColor(Color.RED);
                    g.fillOval(i1*Handler.TILE_SIZE + Handler.TILE_SIZE/2-3, i*Handler.TILE_SIZE + Handler.TILE_SIZE/2-3, 6, 6);
                }
            }
        }
        for(int i = 0; i < nodes.length; i++){
            for(int i1 = 0; i1 < nodes[i].length; i1++){
                int id = nodes[i][i1];
                switch(id){
                    case 0:
                        break;
                    case 1:
                        g.setColor(Color.BLUE);
                        g.fillRect(i1 * Handler.TILE_SIZE, i * Handler.TILE_SIZE, Handler.TILE_SIZE, Handler.TILE_SIZE);
                }
            }
        }
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).render(g);
        }
    }
    
    public int[][] getNodes(){
        return nodes;
    }
    
    public void setNodes(int[][] nodes){
        this.nodes = nodes;
    }
    
    public List<Entity> getEntities(){
        return entities;
    }
    
    public void setEntities(List<Entity> entities){
        this.entities = entities;
    }
    
    public Handler getHandler(){
        return handler;
    }
    
    public void setHandler(Handler handler){
        this.handler = handler;
    }
    
    public int getDistanceToClosestSuperUltraModePowerup(Entity entity){
        int closest = Integer.MAX_VALUE;
        for(int i = 0; i < coinData.length; i++){
            for(int i1 = 0; i1 < coinData[i].length; i1++){
                if(coinData[i][i1]==3){
                    int i2 = Math.abs(entity.getX() / Handler.TILE_SIZE - i1) + Math.abs(entity.getY() / Handler.TILE_SIZE - i);
                    if(i2 < closest){
                        closest = i2;
                    }
                }
            }
        }
        return closest;
    }
    
    public Vector2 getClosestSuperUltraModePowerup(Entity entity){
        int closest = Integer.MAX_VALUE;
        Vector2 pos = new Vector2(0, 0);
        for(int i = 0; i < coinData.length; i++){
            for(int i1 = 0; i1 < coinData[i].length; i1++){
                if(coinData[i][i1]==3){
                    int i2 = Math.abs(entity.getX() / Handler.TILE_SIZE - i1) + Math.abs(entity.getY() / Handler.TILE_SIZE - i);
                    if(i2 < closest){
                        closest = i2;
                        pos = new Vector2(i1, i);
                    }
                }
            }
        }
        return pos;
    }
    
    public Node[][] toNodeArray(){
        Node[][] nodes1 = new Node[nodes.length][nodes[0].length];
        for(int i = 0; i < nodes.length; i++){
            for(int i1 = 0; i1 < nodes[i].length; i1++){
                Node node = new Node(i1, i);
                node.setWalkable(true);
                node.setRenderType(RenderType.EMPTY);
                node.setCost(0);
                if(nodes[i][i1]!=0){
                    node.setWalkable(false);
                    node.setRenderType(RenderType.WALL);
                }
                if(isEntityOnPoint(new Point(i1*Handler.TILE_SIZE + Handler.TILE_SIZE/2, i*Handler.TILE_SIZE + Handler.TILE_SIZE/2))){
                    node.setCost(5);
                }
                nodes1[i][i1] = node;
            }
        }
        return nodes1;
    }
    
    public boolean isEntityOnPoint(Point point){
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i).isColliding(point) && entities.get(i) instanceof Ghost){
                if(!((Ghost) entities.get(i)).isDead()){
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public Vector2 getRandomWalkablePos(){
        Random r = new Random(System.nanoTime());
        Vector2 v = null;
        do{
            for(int i = 0; i < nodes.length; i++){
                for(int i1 = 0; i1 < nodes[i].length; i1++){
                    boolean isEntityHere = false;
                    if(entities!=null){
                        for(Entity entity : entities){
                            if(entity.getX() / Handler.TILE_SIZE == i1 && entity.getY() / Handler.TILE_SIZE == i){
                                isEntityHere = true;
                                break;
                            }
                        }
                    }
                    if(nodes[i][i1] == 0 && r.nextInt(10) == 5 && !isEntityHere){
                        v = new Vector2(i1, i);
                        break;
                    }
                }
            }
        }while(v==null);
        return v;
    }
    
    public void addEntity(Entity entity){
        if(entities==null)entities = new ArrayList<>();
        entities.add(entity);
    }
}
