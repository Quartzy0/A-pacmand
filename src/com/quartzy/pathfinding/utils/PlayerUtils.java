package com.quartzy.pathfinding.utils;

public class PlayerUtils{
    
    public static String getUsernameFromUUID(String uuid, Handler handler){
        return handler.getDatabase().getStringWhere("UUIDS", uuid, "Usernames");
    }
    
    public static int getScoreFromUUID(String uuid, Handler handler){
        return handler.getDatabase().getIntWhere("UUIDS", uuid, "Highscore");
    }
}
