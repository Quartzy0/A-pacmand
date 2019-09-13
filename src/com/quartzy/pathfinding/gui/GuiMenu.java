package com.quartzy.pathfinding.gui;

import com.quartzy.pathfinding.Game;
import com.quartzy.pathfinding.entities.Ghost;
import com.quartzy.pathfinding.entities.PacMan;
import com.quartzy.pathfinding.gui.login.Controller;
import com.quartzy.pathfinding.gui.login.Login;
import com.quartzy.pathfinding.pathfinding.Vector2;
import com.quartzy.pathfinding.utils.Handler;

import java.awt.*;
import java.util.ArrayList;

public class GuiMenu extends Gui{
    
    private GuiText ghostAmount;
    private GuiButton addGhosts;
    private GuiButton removeGhosts;
    private GuiButton startGame;
    private GuiText label1;
    private GuiToggleButton drawAStarNodes;
    private GuiButton leaderboards;
    
    private int ghostCount = 4;
    
    public GuiMenu(Handler handler){
        super("Main menu", true, handler);
        label1 = new GuiText(130, 150, "Add/Remove ghosts", Color.YELLOW);
        ghostAmount = new GuiText(260, 175, ghostCount + "", Color.YELLOW);
        addGhosts = new GuiButton(300, 160, 30, 30, 2, new Color(150, 150, 0), Color.YELLOW, "+", Color.BLACK);
        removeGhosts = new GuiButton(190, 160, 30, 30, 2, new Color(150, 150, 0), Color.YELLOW, "-", Color.BLACK);
        startGame = new GuiButton(210, 260, 100, 50, 2, new Color(150, 150, 0), Color.YELLOW, "START", Color.BLACK);
        drawAStarNodes = new GuiToggleButton(210, 320, 100, 50, 2, new Color(150, 150, 0), Color.YELLOW, "A* nodes: ON", "A* nodes: OFF", Color.BLACK);
        leaderboards = new GuiButton(210, 390, 100, 50, 2, new Color(150, 150, 0), Color.YELLOW, "Leaderboards", Color.BLACK);
        
        leaderboards.setClickEvent(new OnClickedEvent(){
            @Override
            public void clicked(){
                handler.setCurrentScreen(new GuiLeaderboards(handler));
            }
        });
        addGhosts.setClickEvent(new OnClickedEvent(){
            @Override
            public void clicked(){
                if(ghostCount<=200){
                    ghostCount++;
                }
            }
        });
        removeGhosts.setClickEvent(new OnClickedEvent(){
            @Override
            public void clicked(){
                if(ghostCount!=1){
                    ghostCount--;
                }
            }
        });
        
        startGame.setClickEvent(new OnClickedEvent(){
            @Override
            public void clicked(){
                handler.getLoadedWorld().setEntities(new ArrayList<>());
                handler.setGhostCount(ghostCount);
                handler.getLoadedWorld().addEntity(new PacMan(Handler.TILE_SIZE +1, 5*Handler.TILE_SIZE, handler));
                for(int a = 0;a<ghostCount;a++){
                    Vector2 randomWalkablePos = handler.getLoadedWorld().getRandomWalkablePos();
                    handler.getLoadedWorld().addEntity(new Ghost(randomWalkablePos.getX()*Handler.TILE_SIZE, randomWalkablePos.getY()*Handler.TILE_SIZE, a % 6, handler));
                }
                handler.setCurrentScreen(new GuiIngame(handler));
            }
        });
        
        addComponet(ghostAmount);
        addComponet(addGhosts);
        addComponet(removeGhosts);
        addComponet(startGame);
        addComponet(label1);
        addComponet(drawAStarNodes);
        addComponet(leaderboards);
    
        if(handler.getCurrentPlayerUUID()==null){
            Controller.setHandler(handler);
            Login login = new Login();
            try{
                login.doIt();
            } catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void tick(){
        super.tick();
        ghostAmount.setText(ghostCount + "");
        handler.setDrawAStarNodes(drawAStarNodes.getValue());
    }
    
    @Override
    public void render(Graphics2D g){
        ghostAmount.setCustomFont(g.getFont().deriveFont(20F));
        label1.setCustomFont(g.getFont().deriveFont(28F));
        super.render(g);
    }
}
