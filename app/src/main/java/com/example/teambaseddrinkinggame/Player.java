package com.example.teambaseddrinkinggame;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;

    public Player(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    //i.e. if names are equal
    public boolean equals(Player inputPlayer){
        return this.name.equals(inputPlayer.getName());
    }
}
