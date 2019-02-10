package com.example.teambaseddrinkinggame;

public class Rule {
    private String name;
    private int roundCount;
    private int roundMinLimit, roundMaxLimit;

    public Rule(String name){
        //randomise max limit
        this(name,5,10); //default values for min and max round limits
    }
    public Rule (String name, int roundMinLimit, int roundMaxLimit){
        this.name = name;
        roundCount = 0;
        this.roundMinLimit = roundMinLimit;
        this.roundMaxLimit = roundMaxLimit;
    }

    public void setRoundMinLimit(int roundMinLimit){
        this.roundMinLimit = roundMinLimit;
    }

    public void setRoundMaxLimit(int roundMaxLimit){
        this.roundMaxLimit = roundMaxLimit;
    }

    public void incrRoundCount(){
        roundCount++;
    }

    //i.e. if rule has been in play for too long and must be disposed
    public boolean isTooOld(){
        return roundCount>roundMaxLimit;
    }

    //i.e. if rule has been in play too little to be disposed
    public boolean isTooYoung(){
        return roundCount<roundMinLimit;
    }

    public String getName(){
        return name;
    }

    public int getRoundCount(){
        return roundCount;
    }

    public int getRoundMinLimit(){
        return roundMinLimit;
    }

    public int getRoundMaxLimit(){
        return roundMaxLimit;
    }

}
