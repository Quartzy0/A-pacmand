package com.quartzy.pathfinding.utils;

import java.util.Objects;

public class LeaderboardUser implements Comparable{
    
    private int score;
    private String uuid;
    
    public LeaderboardUser(int score, String uuid){
        this.score = score;
        this.uuid = uuid;
    }
    
    @Override
    public String toString(){
        return "LeaderboardUser{" +
                "score=" + score +
                ", uuid='" + uuid + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof LeaderboardUser)) return false;
        LeaderboardUser that = (LeaderboardUser) o;
        return getScore() == that.getScore() &&
                Objects.equals(getUuid(), that.getUuid());
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(getScore(), getUuid());
    }
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public String getUuid(){
        return uuid;
    }
    
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    
    @Override
    public int compareTo(Object o){
        LeaderboardUser user = (LeaderboardUser) o;
        if(user.getScore()>getScore()){
            return 1;
        }else if(user.getScore()<getScore()){
            return -1;
        }
        return 0;
    }
}
