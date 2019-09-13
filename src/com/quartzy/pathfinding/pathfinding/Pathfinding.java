package com.quartzy.pathfinding.pathfinding;

import com.quartzy.pathfinding.utils.DrawMode;
import com.quartzy.pathfinding.utils.Handler;
import com.quartzy.pathfinding.utils.RenderType;

import java.util.*;
import java.util.List;

public class Pathfinding{
    private Handler handler;
    private static int SQUARE_SIZE;
    
    private Node[][] nodes;
    public static DrawMode drawMode = DrawMode.START;
    private Node prevEnd, prevStart;
    
    /*public MainRenderer(Handler handler){
        this.handler = handler;
        KeyBind keyBind = new KeyBind("Load file", KeyEvent.VK_M);
        keyBind.addOnPressed(new Pressed(){
            @Override
            public void pressed(KeyEvent e) throws IOException{
                String s = JOptionPane.showInputDialog("Enter the name of the image file");
                if(s==null || s.isEmpty())return;
                nodes = handler.getNodesFromImage(ImageIO.read(new File("D:\\Andraz\\IdeaProjects\\Pathfinding\\res\\" + s)));
                prevEnd = null;
                prevStart = null;
            }
        });
        KeyBinds.keyBinds.add(keyBind);
        KeyBind keyBind1 = new KeyBind("Switch mode", KeyEvent.VK_N);
        keyBind1.addOnPressed(new Pressed(){
            @Override
            public void pressed(KeyEvent e) throws IOException{
                List<DrawMode> drawModes = Arrays.asList(DrawMode.values());
                int i = drawModes.indexOf(drawMode);
                if(i+1>=drawModes.size()){
                    drawMode = drawModes.get(0);
                }else {
                    drawMode = drawModes.get(i+1);
                }
            }
        });
        KeyBinds.keyBinds.add(keyBind1);
        Mouse.addClickEvent(new Clicked(){
            @Override
            public void clicked(MouseEvent event){
                Node nodeFromPos = getNodeFromPos(new Vector2(event.getX(), event.getY()));
                if(nodeFromPos==null)return;
                switch(drawMode){
                    case END:
                        if(nodeFromPos.equals(prevStart))prevStart = null;
                        nodeFromPos.setWalkable(true);
                        nodeFromPos.setRenderType(RenderType.END);
                        prevEnd = nodeFromPos;
                        findPath(prevStart, prevEnd);
                        break;
                    case CLEAR:
                        if(nodeFromPos.equals(prevStart))prevStart = null;
                        if(nodeFromPos.equals(prevEnd))prevEnd = null;
                        nodeFromPos.setRenderType(RenderType.EMPTY);
                        nodeFromPos.setWalkable(true);
                        findPath(prevStart, prevEnd);
                        break;
                    case START:
                        prevStart = nodeFromPos;
                        if(nodeFromPos.equals(prevEnd))prevEnd = null;
                        nodeFromPos.setRenderType(RenderType.START);
                        nodeFromPos.setWalkable(true);
                        findPath(prevStart, prevEnd);
                        break;
                    case WALLS:
                        if(nodeFromPos.equals(prevStart))prevStart = null;
                        if(nodeFromPos.equals(prevEnd))prevEnd = null;
                        nodeFromPos.setRenderType(RenderType.WALL);
                        nodeFromPos.setWalkable(false);
                        findPath(prevStart, prevEnd);
                        break;
                }
            }
        });
        Mouse.addMouseDragEvent(new Dragged(){
            @Override
            public void dragged(MouseEvent e){
                Node nodeFromPos = getNodeFromPos(new Vector2(e.getX(), e.getY()));
                if(nodeFromPos==null)return;
                switch(drawMode){
                    case END:
                        if(nodeFromPos.equals(prevStart))prevStart = null;
                        nodeFromPos.setWalkable(true);
                        nodeFromPos.setRenderType(RenderType.END);
                        prevEnd = nodeFromPos;
                        break;
                    case CLEAR:
                        if(nodeFromPos.equals(prevStart))prevStart = null;
                        if(nodeFromPos.equals(prevEnd))prevEnd = null;
                        nodeFromPos.setRenderType(RenderType.EMPTY);
                        nodeFromPos.setWalkable(true);
                        break;
                    case START:
                        prevStart = nodeFromPos;
                        if(nodeFromPos.equals(prevEnd))prevEnd = null;
                        nodeFromPos.setRenderType(RenderType.START);
                        nodeFromPos.setWalkable(true);
                        break;
                    case WALLS:
                        if(nodeFromPos.equals(prevStart))prevStart = null;
                        if(nodeFromPos.equals(prevEnd))prevEnd = null;
                        nodeFromPos.setRenderType(RenderType.WALL);
                        nodeFromPos.setWalkable(false);
                        break;
                }
            }
    
            @Override
            public void draggingFinished(MouseEvent e){
                findPath(prevStart, prevEnd);
            }
        });
        nodes = new Node[50][50];
        for(int a = 0;a<50;a++){
            for(int b = 0;b<50;b++){
                nodes[a][b] = new Node(b, a);
            }
        }
    }
    
    public void tick(int tickNum){
        SQUARE_SIZE = Math.min(handler.getDisplay().getCanvas().getWidth()/nodes[0].length, handler.getDisplay().getCanvas().getHeight()/nodes.length);
    }
    
    public void render(Graphics2D g){
        g.setFont(g.getFont().deriveFont(16F));
        g.drawString("N: Switch draw modes     Current mode: " + drawMode, 10, handler.getDisplay().getHeight()-5);
        g.drawString("M: Load from file", 10, handler.getDisplay().getHeight() - g.getFont().getSize() - 10);
        for(Node[] nodeArr : nodes){
            for(Node node1 : nodeArr){
                switch(node1.getRenderType()){
                    case START:
                        g.setColor(Color.ORANGE);
                        break;
                    case END:
                        g.setColor(Color.GRAY);
                        break;
                    case WALL:
                        g.setColor(Color.BLACK);
                        break;
                    case OPEN:
                        g.setColor(Color.GREEN);
                        break;
                    case EMPTY:
                        g.setColor(Color.WHITE);
                        break;
                    case CLOSED:
                        g.setColor(Color.RED);
                        break;
                    case PATH:
                        g.setColor(Color.BLUE);
                        break;
                }
                g.fillRect(node1.getX() * SQUARE_SIZE, node1.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                g.setColor(Color.BLACK);
                if(SQUARE_SIZE>4){
                    g.drawRect(node1.getX() * SQUARE_SIZE, node1.getY() * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }*/
    
    public static Node getNodeFromPos(Vector2 posIn, Node[][] nodes){
        int nodeIndexX = posIn.getX()/Handler.TILE_SIZE;
        int nodeIndexY = posIn.getY()/Handler.TILE_SIZE;
        
        if(nodeIndexX<0 || nodeIndexX>nodes[0].length-1 || nodeIndexY<0 || nodeIndexY>nodes.length-1)return null;
        
        return nodes[nodeIndexY][nodeIndexX];
    }
    
    public static List<Node> getNeighbours(Node node, Node[][] nodes, boolean searchDiagonal){
        List<Node> neighbours = new ArrayList<>();
        if(searchDiagonal){
            for(int a = -1; a <= 1; a++){
                for(int b = -1; b <= 1; b++){
                    if(a == 0 && b == 0) continue;
                    int i1 = node.getX() + b;
                    int i = node.getY() + a;
                    if(i >= 0 && i < nodes.length && i1 >= 0 && i1 < nodes[0].length){
                        Node e = nodes[i][i1];
                        if(e.isWalkable()){
                            neighbours.add(e);
                        }
                    }
                }
            }
        }else {
            int newX1 = node.getX()+1;
            int newX2 = node.getX()-1;
            int newY1 = node.getY()-1;
            int newY2 = node.getY()+1;
            if(newX1 >= 0 && newX1 < nodes.length){
                Node e = nodes[node.getY()][newX1];
                if(e.isWalkable()){
                    neighbours.add(e);
                }
            }
            if(newX2 >= 0 && newX2 < nodes.length){
                Node e = nodes[node.getY()][newX2];
                if(e.isWalkable()){
                    neighbours.add(e);
                }
            }
            if(newY1 >= 0 && newY1 < nodes.length){
                Node e = nodes[newY1][node.getX()];
                if(e.isWalkable()){
                    neighbours.add(e);
                }
            }
            if(newY2 >= 0 && newY2 < nodes.length){
                Node e = nodes[newY2][node.getX()];
                if(e.isWalkable()){
                    neighbours.add(e);
                }
            }
        }
        return neighbours;
    }
    
    public static List<Node> findPath(Node startNode, Node endNode, Node[][] nodes, boolean searchDiagonal, boolean returnStart){
        if(startNode==null)return null;
        if(endNode==null)return null;
    
        for(int i = 0; i < nodes.length; i++){
            for(int i1 = 0; i1 < nodes[i].length; i1++){
                if(nodes[i][i1].getRenderType()== RenderType.OPEN || nodes[i][i1].getRenderType()==RenderType.CLOSED || nodes[i][i1].getRenderType()==RenderType.PATH){
                    nodes[i][i1].setRenderType(RenderType.EMPTY);
                }
            }
        }
        
        PriorityQueue<Node> openNodes = new PriorityQueue<>(nodes.length*nodes[0].length);
        List<Node> closedNodes = new ArrayList<>();
        
        openNodes.add(startNode);
        Node currentNode = startNode;
        while(openNodes.size()>0){
            if(currentNode.equals(endNode)){
                List<Node> nodes1 = retraceSteps(startNode, endNode, returnStart);
                for(Node node1 : nodes1){
                    node1.setRenderType(RenderType.PATH);
                }
                return nodes1;
            }
    
            List<Node> neighbours = getNeighbours(currentNode, nodes, searchDiagonal);
            for(Node neighbour : neighbours){
                if(closedNodes.contains(neighbour))continue;
                int distanceFromNodesH = getDistanceFromNodes(startNode, neighbour);
                int distanceFromNodesG = getDistanceFromNodes(neighbour, endNode);
                if(openNodes.contains(neighbour)){
                    if(neighbour.getgCost()>distanceFromNodesG){
                        openNodes.remove(neighbour);
                    }
                    else {
                        continue;
                    }
                }
                neighbour.setParent(currentNode);
                neighbour.sethCost(distanceFromNodesH);
                neighbour.setgCost(distanceFromNodesG);
                openNodes.add(neighbour);

                if(!neighbour.equals(startNode) && !neighbour.equals(endNode)){
                    neighbour.setRenderType(RenderType.OPEN);
                }
                
            }
    
            closedNodes.add(currentNode);
            openNodes.remove(currentNode);
            if(!currentNode.equals(startNode) && !currentNode.equals(endNode)){
                currentNode.setRenderType(RenderType.CLOSED);
            }
    
    
            currentNode = openNodes.element();
        }
    
        System.out.println("No path found");
        return null;
    }
    
    public static int getDistanceFromNodes(Node nodeA, Node nodeB){
        int disX = Math.abs(nodeA.getX()-nodeB.getX());
        int disY = Math.abs(nodeA.getY()-nodeB.getY());
        
        return Math.toIntExact(Math.round(Math.sqrt(disX*disX + disY*disY)));
//        return disX + disY;
    }
    
    public static List<Node> retraceSteps(Node startNode, Node endNode, boolean returnStart){
        List<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        if(currentNode==null || startNode==null)return null;
        while(currentNode!=null && !currentNode.equals(startNode)){
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        if(returnStart){
            path.add(startNode);
        }
        return path;
    }
}
