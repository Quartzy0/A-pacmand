package com.quartzy.pathfinding.pathfinding;

import com.quartzy.pathfinding.utils.RenderType;

import java.util.Objects;

public class Node implements Comparable{
    
    private int x, y;
    private int gCost, hCost;
    private boolean walkable = true;
    private Node parent;
    private RenderType renderType = RenderType.EMPTY;
    private int cost = 0;
    
    public Node(int x, int y){
        this.x = x;
        this.y = y;
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
    
    public int getgCost(){
        return gCost+cost;
    }
    
    public void setgCost(int gCost){
        this.gCost = gCost;
    }
    
    public int getfCost(){
        return getgCost() + gethCost();
    }
    
    public void sethCost(int hCost){
        this.hCost = hCost;
    }
    
    public int getCost(){
        return cost;
    }
    
    public void setCost(int cost){
        this.cost = cost;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getX() == node.getX() &&
                getY() == node.getY();
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(getX(), getY());
    }
    
    public boolean isWalkable(){
        return walkable;
    }
    
    public void setWalkable(boolean walkable){
        this.walkable = walkable;
    }
    
    public Node getParent(){
        return parent;
    }
    
    public void setParent(Node parent){
        this.parent = parent;
    }
    
    public int gethCost(){
        return hCost;
    }
    
    @Override
    public int compareTo(Object o){
        Node n = (Node) o;
        return getfCost() - n.getfCost();
//        if(n.gethCost()==gethCost()){
//            return 0;
//        }
//        if(n.gethCost()>gethCost()){
//            return -1;
//        }else if(n.gethCost()<gethCost()){
//            return 1;
//        }
//        return 0;
    }
    
    public RenderType getRenderType(){
        return renderType;
    }
    
    public void setRenderType(RenderType renderType){
        this.renderType = renderType;
    }
    
    @Override
    public String toString(){
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", gCost=" + gCost +
                ", hCost=" + hCost +
                ", walkable=" + walkable +
                ", parent=" + parent +
                ", renderType=" + renderType +
                '}';
    }
}
