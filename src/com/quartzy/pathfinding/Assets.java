package com.quartzy.pathfinding;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Assets{
    
    public static BufferedImage pacman;
    public static BufferedImage pacmanFull;
    public static BufferedImage ghostBlue;
    public static BufferedImage ghostDead;
    public static BufferedImage ghostYellow;
    public static BufferedImage ghostRed;
    public static BufferedImage ghostGreen;
    public static BufferedImage ghostPink;
    public static BufferedImage ghostPurple;
    
    public static void init(){
        try{
            pacman = ImageIO.read(Assets.class.getResource("/textures/Pacman.png"));
            pacmanFull = ImageIO.read(Assets.class.getResource("/textures/PacmanFull.png"));
            ghostBlue = ImageIO.read(Assets.class.getResource("/textures/ghostBlue.png"));
            ghostDead = ImageIO.read(Assets.class.getResource("/textures/ghostDead.png"));
            ghostYellow = ImageIO.read(Assets.class.getResource("/textures/ghostYellow.png"));
            ghostRed = ImageIO.read(Assets.class.getResource("/textures/ghostRed.png"));
            ghostGreen = ImageIO.read(Assets.class.getResource("/textures/ghostGreen.png"));
            ghostPink = ImageIO.read(Assets.class.getResource("/textures/ghostPink.png"));
            ghostPurple = ImageIO.read(Assets.class.getResource("/textures/ghostPurple.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
