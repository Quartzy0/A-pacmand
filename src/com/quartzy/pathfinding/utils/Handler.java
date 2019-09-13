package com.quartzy.pathfinding.utils;

import com.quartzy.Databases.Database;
import com.quartzy.pathfinding.entities.PacMan;
import com.quartzy.pathfinding.fx.Display;
import com.quartzy.pathfinding.fx.World;
import com.quartzy.pathfinding.gui.Gui;
import com.quartzy.pathfinding.pathfinding.Node;

import javax.net.ssl.SSLHandshakeException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Handler{
    private Display display;
    private PacMan pacMan;
    private World loadedWorld;
    private int score;
    private boolean gameWon = false;
    private Gui currentScreen;
    private int ghostCount = 4;
    private boolean drawAStarNodes;
    private Database database;
    private UUID currentPlayerUUID;
    private EncryptedFile config;
    private static Handler instance;
    
    public UUID getCurrentPlayerUUID(){
        return currentPlayerUUID;
    }
    
    public void setCurrentPlayerUUID(UUID currentPlayerUUID){
        this.currentPlayerUUID = currentPlayerUUID;
    }
    
    public boolean isDrawAStarNodes(){
        return drawAStarNodes;
    }
    
    public void setDrawAStarNodes(boolean drawAStarNodes){
        this.drawAStarNodes = drawAStarNodes;
    }
    
    public int getGhostCount(){
        return ghostCount;
    }
    
    public void setGhostCount(int ghostCount){
        this.ghostCount = ghostCount;
    }
    
    public static final int TILE_SIZE = 16;
    
    public Gui getCurrentScreen(){
        return currentScreen;
    }
    
    public void setCurrentScreen(Gui currentScreen){
        this.currentScreen = currentScreen;
    }
    
    public static final String GAME_DIR = System.getenv("APPDATA") + File.separator + "Pac-man" + File.separator;
    
    public boolean isGameWon(){
        return gameWon;
    }
    
    public void setGameWon(boolean gameWon){
        this.gameWon = gameWon;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public World getLoadedWorld(){
        return loadedWorld;
    }
    
    public void setLoadedWorld(World loadedWorld){
        this.loadedWorld = loadedWorld;
    }
    
    public static Handler getInstance(){
        return instance;
    }
    
    public Handler(Display display){
        this.display = display;
        try{
            database = new Database("1jZdKg-iRJ_RcFQxmpfoci2YKRwY2LyT03JRwkbr385U");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            database = new Database("1jZdKg-iRJ_RcFQxmpfoci2YKRwY2LyT03JRwkbr385U");
        }
        File file = new File(GAME_DIR + "config.pcf");
        config = new EncryptedFile(file);
        instance = this;
        if(!file.exists()){
            config.create();
            if(!file.exists()){
                try{
                    file.createNewFile();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public EncryptedFile getConfig(){
        return config;
    }
    
    public static String getDifference(Date earlier, Date later){
        LocalDateTime date1Ldt = LocalDateTime.ofInstant(earlier.toInstant(), ZoneId.systemDefault());
        LocalDateTime date2Ldt = LocalDateTime.ofInstant(later.toInstant(), ZoneId.systemDefault());
        long years = ChronoUnit.YEARS.between(date1Ldt, date2Ldt);
        long months = ChronoUnit.MONTHS.between(date1Ldt, date2Ldt) - (12 * years);
        Calendar cal = Calendar.getInstance();
        cal.setTime(earlier);
        int month = cal.get(Calendar.MONTH)+1;
        System.out.println(month);
        YearMonth yearMonthObject = YearMonth.of(earlier.getYear()+1900, month);
        long days = ChronoUnit.DAYS.between(date1Ldt, date2Ldt) - (yearMonthObject.lengthOfMonth() * ChronoUnit.MONTHS.between(date1Ldt, date2Ldt));
        long hours = ChronoUnit.HOURS.between(date1Ldt, date2Ldt) - (24 * ChronoUnit.DAYS.between(date1Ldt, date2Ldt));
        long minutes = ChronoUnit.MINUTES.between(date1Ldt, date2Ldt) - (60 * ChronoUnit.HOURS.between(date1Ldt, date2Ldt));
        long seconds = ChronoUnit.SECONDS.between(date1Ldt, date2Ldt) - (60 * ChronoUnit.MINUTES.between(date1Ldt, date2Ldt));
        
        
        String banString = "";
        if (years>1)banString+=years + " years ";
        if (years==1)banString+=years + " year ";
        if (months>1)banString+=months + " months ";
        if (months==1)banString+=months + " month ";
        if (days>1)banString+=days + " days ";
        if (days==1)banString+=days + " day ";
        if (hours>1)banString+=hours + " hours ";
        if (hours==1)banString+=hours + " hour ";
        if (minutes>1)banString+=minutes + " minutes ";
        if (minutes==1)banString+=minutes + " minute ";
        if (seconds>1)banString+=seconds + " seconds ";
        if (seconds==1)banString+=seconds + " second ";
        return banString;
    }
    
    public void setConfig(EncryptedFile config){
        this.config = config;
    }
    
    public Database getDatabase(){
        return database;
    }
    
    public PacMan getPacMan(){
        return pacMan;
    }
    
    public void setPacMan(PacMan pacMan){
        this.pacMan = pacMan;
    }
    
    public Display getDisplay(){
        return display;
    }
    
    public Node[][] getNodesFromImage(BufferedImage img){
        Node[][] nodes = new Node[img.getHeight()][img.getWidth()];
        for(int a = 0;a<img.getHeight();a++){
            for(int b = 0;b<img.getWidth();b++){
                Color color = new Color(img.getRGB(b, a));
                Node node = new Node(b, a);
                node.setWalkable(false);
                node.setRenderType(RenderType.WALL);
                if(color.getRed() + color.getGreen() + color.getBlue() > 600){
                    node.setWalkable(true);
                    node.setRenderType(RenderType.EMPTY);
                }
                nodes[a][b] = node;
            }
        }
        return nodes;
    }
}
