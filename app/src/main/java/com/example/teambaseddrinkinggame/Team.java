package com.example.teambaseddrinkinggame;

import java.sql.Array;
import java.util.Arrays;

public class Team {
    private String teamName;
    private String playerNames[];
    private int teamSize;
    public Team(String teamName, String playerNames[]){
        //
    }

    public Team(String playerNames[]){
        this.playerNames = playerNames;
        this.playerNames= Arrays.copyOf(playerNames,playerNames.length);
        teamSize = playerNames.length;
    }
    public Team(){
    }

    public void setTeamName(String teamName){
       this.teamName = teamName;
    }

    public String getTeamName(){
        return teamName;
    }

    public String[] getPlayerNames(){
        return playerNames;
    }

    public String getPlayerNameAt(int index){
        return playerNames[index];
    }

    public int getTeamSize(){
        return  teamSize;
    }
}
