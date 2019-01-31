package com.example.teambaseddrinkinggame;

import java.io.Serializable;
import java.sql.Array;
import java.util.Arrays;

public class Team implements Serializable {
    private String teamName;
    private String playerNames[];
    private Player players[];
    private int teamSize;
    private int score;
    public Team(String teamName, String playerNames[]){
        //
    }

    public Team(String playerNames[]){
        this.playerNames = playerNames;
        this.playerNames= Arrays.copyOf(playerNames,playerNames.length);
        teamSize = playerNames.length;
        players = new Player[teamSize];
        for (int i = 0; i<teamSize; i++){
            players[i] = new Player(playerNames[i]);
        }
        score = 150;
    }


    public void setName(String teamName){
       this.teamName = teamName;
    }

    //double check if this works
    public void replacePlayer(Player newPlayer, Player oldPlayer){
        for (int i = 0; i< teamSize; i++){
            if (oldPlayer.equals(players[i])){
                players[i]=newPlayer;
            }
        }
    }

    //decrease score by specified increment then return new score
    public void decreaseScore(int increment){
        if (score-increment>0){
            score -= increment;
        } else {
            score = 0;
        }
    }

    public int getScore(){
        return score;
    }

    public Player getPlayerAt(int index){
        return players[index];
    }

    public String getName(){
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