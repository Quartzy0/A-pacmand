package com.quartzy.pathfinding.gui;

import com.quartzy.pathfinding.Game;
import com.quartzy.pathfinding.utils.Handler;
import com.quartzy.pathfinding.utils.LeaderboardUser;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GuiIngame extends Gui {
    
    //Components
    private GuiText score;
    private GuiText highScore;
    private GuiText keybindsLabel;
    private boolean firstTime;

    public GuiIngame(Handler handler) {
        super("Ingame Gui", false, handler);
        score = new GuiText(5, 20, "Score: " + handler.getScore());
        highScore = new GuiText(200, 20, "High score: 0");
        keybindsLabel = new GuiText(5, 40, "R: reset game   Q: Quit game");
        addComponet(score);
        addComponet(highScore);
        addComponet(keybindsLabel);
        firstTime = true;
    }

    @Override
    public void tick() {
        super.tick();
        
        score.setText("Score: " + handler.getScore());
        highScore.setText("High score: " + Game.highScore);
    }
    
    @Override
    public void render(Graphics2D g){
        g.setFont(g.getFont().deriveFont(16F));
        super.render(g);
        if(!handler.getPacMan().isAlive()){
            if(firstTime){
                if(handler.getCurrentPlayerUUID()!=null){
                    handler.getDatabase().getValuesFromSpreadSheets();
                    if(handler.getDatabase().getIntWhere("UUIDS", handler.getCurrentPlayerUUID().toString(), "Highscore") < handler.getScore()){
                        handler.getDatabase().setIntWhere("UUIDS", handler.getCurrentPlayerUUID().toString(), "Highscore", handler.getScore());
                        List<String> leaderboards = handler.getDatabase().getColumnByName("Leaderboards");
                        if(!leaderboards.contains(handler.getCurrentPlayerUUID().toString())){
                            handler.getDatabase().getColumnByName("Leaderboards").add(handler.getCurrentPlayerUUID().toString());
                            leaderboards.add(handler.getCurrentPlayerUUID().toString());
                        }
                        HashSet<LeaderboardUser> usersSet = new HashSet<>();
                        for(int a = 0;a<leaderboards.size();a++){
                            LeaderboardUser leaderboardUser = new LeaderboardUser(handler.getDatabase().getIntWhere("UUIDS", leaderboards.get(a), "Highscore"), leaderboards.get(a));
                            usersSet.add(leaderboardUser);
                        }
                        handler.getDatabase().getColumnByName("Leaderboards").clear();
                        PriorityQueue<LeaderboardUser> users = new PriorityQueue<>(usersSet);
                        for(int i = 0; i < 10; i++){
                            LeaderboardUser element = users.poll();
                            if(element != null){
                                handler.getDatabase().getColumnByName("Leaderboards").add(element.getUuid());
                            }
                        }
                        handler.getDatabase().refreshDatabase();
                    }
                }
                firstTime = false;
            }
            g.setFont(g.getFont().deriveFont(80F));
            g.drawString("GAME OVER!", 0, handler.getDisplay().getHeight() / 2);
        }
        if(handler.isGameWon()){
            if(firstTime){
                if(handler.getCurrentPlayerUUID()!=null){
                    handler.getDatabase().getValuesFromSpreadSheets();
                    if(handler.getDatabase().getIntWhere("UUIDS", handler.getCurrentPlayerUUID().toString(), "Highscore") < handler.getScore()){
                        handler.getDatabase().setIntWhere("UUIDS", handler.getCurrentPlayerUUID().toString(), "Highscore", handler.getScore());
                        List<String> leaderboards = handler.getDatabase().getColumnByName("Leaderboards");
                        if(!leaderboards.contains(handler.getCurrentPlayerUUID().toString())){
                            handler.getDatabase().getColumnByName("Leaderboards").add(handler.getCurrentPlayerUUID().toString());
                            leaderboards.add(handler.getCurrentPlayerUUID().toString());
                        }
                        HashSet<LeaderboardUser> usersSet = new HashSet<>();
                        for(int a = 0;a<leaderboards.size();a++){
                            LeaderboardUser leaderboardUser = new LeaderboardUser(handler.getDatabase().getIntWhere("UUIDS", leaderboards.get(a), "Highscore"), leaderboards.get(a));
                            usersSet.add(leaderboardUser);
                        }
                        handler.getDatabase().getColumnByName("Leaderboards").clear();
                        PriorityQueue<LeaderboardUser> users = new PriorityQueue<>(usersSet);
                        for(int i = 0; i < 10; i++){
                            LeaderboardUser element = users.poll();
                            if(element != null){
                                handler.getDatabase().getColumnByName("Leaderboards").add(element.getUuid());
                            }
                        }
                        handler.getDatabase().refreshDatabase();
                    }
                }
                firstTime = false;
            }
            g.setFont(g.getFont().deriveFont(80F));
            g.drawString("YOU WIN!", 10, handler.getDisplay().getHeight() / 2);
        }
    }
    
    //g.setColor(Color.YELLOW);
    //        g.setFont(g.getFont().deriveFont(20F));
    //        g.drawString("Score: " + handler.getScore(), 5, handler.getDisplay().getHeight()-(g.getFont().getSize()*2 + 5));
    //        g.drawString("High score: " + highScore, 5, handler.getDisplay().getHeight()-(g.getFont().getSize() + 5));
    //        g.drawString("R: reset game   Q: Quit game", (int) (handler.getDisplay().getWidth()- FontUtils.getTextSize("R: reset game   Q: Quit game", g.getFont(), g).getWidth()), handler.getDisplay().getHeight()-(g.getFont().getSize() + 5));
}
