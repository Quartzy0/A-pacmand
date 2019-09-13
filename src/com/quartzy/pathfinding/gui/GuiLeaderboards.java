package com.quartzy.pathfinding.gui;

import com.quartzy.pathfinding.utils.FontUtils;
import com.quartzy.pathfinding.utils.Handler;
import com.quartzy.pathfinding.utils.PlayerUtils;

import java.awt.*;
import java.util.List;

public class GuiLeaderboards extends Gui{
    
    private GuiText playerNum1 = new GuiText(0, 0, "");
    private GuiText playerNum2 = new GuiText(0, 0, "");
    private GuiText playerNum3 = new GuiText(0, 0, "");
    private GuiText playerNum4 = new GuiText(0, 0, "");
    private GuiText playerNum5 = new GuiText(0, 0, "");
    private GuiText playerNum6 = new GuiText(0, 0, "");
    private GuiText playerNum7 = new GuiText(0, 0, "");
    private GuiText playerNum8 = new GuiText(0, 0, "");
    private GuiText playerNum9 = new GuiText(0, 0, "");
    private GuiText playerNum10 = new GuiText(0, 0, "");
    private GuiButton back;
    
    private Graphics2D g1;
    private boolean firtsTime = true;
    
    public GuiLeaderboards(Handler handler){
        super("Leaderboards", true, handler);
        handler.getDatabase().getValuesFromSpreadSheets();
        back = new GuiButton(handler.getDisplay().getWidth()/2-50, 40, 100, 30, 2, new Color(150, 150, 0), Color.YELLOW, "Back", Color.BLACK);
        back.setClickEvent(new OnClickedEvent(){
            @Override
            public void clicked(){
                handler.setCurrentScreen(new GuiMenu(handler));
            }
        });
        addComponet(back);
    }
    
    @Override
    public void tick(){
        if(g1!=null && firtsTime){
            handler.getDatabase().getValuesFromSpreadSheets();
            List<String> leaderboards = handler.getDatabase().getColumnByName("Leaderboards");
            if(leaderboards.size() > 0){
                playerNum1 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("1. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(0), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(0), handler), g1.getFont(), g1).getWidth())/2 + 120, 200, "1. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(0), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(0), handler), Color.YELLOW);
                if(leaderboards.size() > 1){
                    playerNum2 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("2. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(1), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(1), handler), g1.getFont(), g1).getWidth())/2 + 120, 220, "2. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(1), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(1), handler), Color.YELLOW);
                    if(leaderboards.size() > 2){
                        playerNum3 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("3. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(2), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(2), handler), g1.getFont(), g1).getWidth())/2 + 120, 240, "3. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(2), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(2), handler), Color.YELLOW);
                        if(leaderboards.size() > 3){
                            playerNum4 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("4. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(3), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(3), handler), g1.getFont(), g1).getWidth())/2 + 120, 260, "4. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(3), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(3), handler), Color.YELLOW);
                            if(leaderboards.size() > 4){
                                playerNum5 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("5. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(4), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(4), handler), g1.getFont(), g1).getWidth())/2 + 120, 280, "5. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(4), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(4), handler), Color.YELLOW);
                                if(leaderboards.size() > 5){
                                    playerNum6 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("6. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(5), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(5), handler), g1.getFont(), g1).getWidth())/2 + 120, 300, "6. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(5), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(5), handler), Color.YELLOW);
                                    if(leaderboards.size() > 6){
                                        playerNum7 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("7. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(6), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(6), handler), g1.getFont(), g1).getWidth())/2 + 120, 320, "7. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(6), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(6), handler), Color.YELLOW);
                                        if(leaderboards.size() > 7){
                                            playerNum8 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("8. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(7), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(7), handler), g1.getFont(), g1).getWidth())/2 + 120, 340, "8. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(7), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(7), handler), Color.YELLOW);
                                            if(leaderboards.size() > 8){
                                                playerNum9 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("9. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(8), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(8), handler), g1.getFont(), g1).getWidth())/2 + 120, 360, "9. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(8), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(8), handler), Color.YELLOW);
                                                if(leaderboards.size() > 9){
                                                    playerNum10 = new GuiText((int) (handler.getDisplay().getWidth()/2 - FontUtils.getTextSize("10. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(9), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(9), handler), g1.getFont(), g1).getWidth())/2 + 120, 380, "10. " + PlayerUtils.getUsernameFromUUID(leaderboards.get(9), handler) + " - " + PlayerUtils.getScoreFromUUID(leaderboards.get(9), handler), Color.YELLOW);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            addComponet(playerNum1);
            addComponet(playerNum2);
            addComponet(playerNum3);
            addComponet(playerNum4);
            addComponet(playerNum5);
            addComponet(playerNum6);
            addComponet(playerNum7);
            addComponet(playerNum8);
            addComponet(playerNum9);
            addComponet(playerNum10);
            firtsTime = false;
        }
        super.tick();
    }
    
    @Override
    public void render(Graphics2D g){
        g.setFont(g.getFont().deriveFont(20F));
        g.setColor(Color.YELLOW);
        super.render(g);
        g1 = g;
    }
}
