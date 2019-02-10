package com.example.teambaseddrinkinggame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainScreen extends AppCompatActivity {

    private Team teams[];
    private int noTeams, noPlayers;
    private Player players[];
    private GameType currGameType;

    //instructions
    private ArrayList<String> categories;
    private ArrayList<String> drinkIfYouHave;
    private ArrayList<String> generalRules;
    private ArrayList<String> groupInstructions;
    private ArrayList<String> targetedOnceOff;
    private ArrayList<String> targetedPersistent;

    //current rules in plays
    private ArrayList<Rule> currGeneralRules;
    private ArrayList<Rule> currTargetedPersistent;

    private ArrayList<String[]> challenges; //2 element String arrays - {[Title],[Description]}
    private ArrayList<String[]> wouldYouRather; //2 element String arrays - {[Option 1],[Option 2]}

    //rounds since last instruction/rule
    private int roundsSinceLastChallenge;
    private final int minRoundsSinceLastChallenge = 10;

    private int roundsSinceLastGroupRule;
    private final int minRoundsSinceLastGroupRule = 3;

    //team buttons
    private Button team1Btn, team2Btn, team3Btn, team4Btn;
    //team scores
    private TextView team1ScoreTxt, team2ScoreTxt, team3ScoreTxt, team4ScoreTxt;

    //
    private TextView titleTxt, instructionTxt, descriptionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main_screen);
        getSupportActionBar().hide();

        //receive
        teams = (Team[]) getIntent().getSerializableExtra("teams");

        noTeams = teams.length;
        noPlayers= 0;
        for (int i = 0; i<noTeams; i++){
            noPlayers += teams[i].getTeamSize();
        }

        //players[0] = teams[0].getPlayerAt(0);
        //add players to common array
        int count = 0;
        players = new Player[noPlayers];
        for (int i = 0; i<noTeams; i++){
            for (int i2 = 0; i2<teams[i].getTeamSize();i2++){
                players[count] = teams[i].getPlayerAt(i2);
                count++;
            }
        }

        setUpTeamHeaderBar();

        titleTxt = findViewById(R.id.titleTxt);
        instructionTxt = findViewById(R.id.instructionTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);

        getDataFromFiles();

        //otherwise throws error
        currGeneralRules = new ArrayList<Rule>();
        currTargetedPersistent = new ArrayList<Rule>();

        roundsSinceLastChallenge=0;

        updateInstruction(true);//show first rule

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //menu_photos.setOnClickListener(new View.OnClickListener() {
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInstruction(true);

                //setUpGeneralRules();
                //setUpChallenges();
                //setUpCategories();
                //setUpDrinkIfYouHave();
            }
        });

        count =0;
    }

    public void setUpTeamHeaderBar(){
        //get necessary elements
        team1Btn = (Button) findViewById(R.id.team1Btn);
        team2Btn = (Button) findViewById(R.id.team2Btn);
        team3Btn = (Button) findViewById(R.id.team3Btn);
        team4Btn = (Button) findViewById(R.id.team4Btn);

        team1ScoreTxt = (TextView) findViewById(R.id.team1ScoreTxt);
        team2ScoreTxt = (TextView) findViewById(R.id.team2ScoreTxt);
        team3ScoreTxt = (TextView) findViewById(R.id.team3ScoreTxt);
        team4ScoreTxt = (TextView) findViewById(R.id.team4ScoreTxt);

        //team 1
        team1Btn.setText(teams[0].getName());
        team1ScoreTxt.setText(teams[0].getScore()+"");

        //team 2
        team2Btn.setText(teams[1].getName());
        team2ScoreTxt.setText(teams[1].getScore()+"");

        //team 3
        if (noTeams>=3){
            team3Btn.setText(teams[2].getName());
            team3ScoreTxt.setText(teams[2].getScore()+"");
            team3Btn.setVisibility(View.VISIBLE);
            team3ScoreTxt.setVisibility(View.VISIBLE);
        }

        //team 4
        if (noTeams==4){
            team4Btn.setText(teams[3].getName());
            team4ScoreTxt.setText(teams[3].getScore()+"");
            team4Btn.setVisibility(View.VISIBLE);
            team4ScoreTxt.setVisibility(View.VISIBLE);
        }

    }

    //this method may be recalled encase a certain method is invalid i.e. if an instruction arraylist is empty
    public void updateInstruction(Boolean isGameRoundIncr){
        if (isGameRoundIncr){
            //update round counts of persistent instructions
            //general rules
            for (int i = 0;i< currGeneralRules.size();i++){
                currGeneralRules.get(i).incrRoundCount();
            }
            //targeted instructions
            for (int i = 0; i< currTargetedPersistent.size();i++){
                currTargetedPersistent.get(i).incrRoundCount();
            }
            //increment rounds since last challenge and group rule
            roundsSinceLastChallenge++;
            roundsSinceLastGroupRule++;
        }
        //TODO: Calculate rule/instruction probability weightings
        //The methods called by this function should no longer recursively call this method to satisfy their specific rules
        //i.e. the minimum no of rounds between challenges being enforced here not in the setUpChallenges method

        currGameType = getRandGameType();
        ConstraintLayout constLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        int color = Color.WHITE;
        switch (currGameType){
            case Categories:
                setUpCategories();
                //color = Color.GREEN;
                break;
            case Challenges:
                setUpChallenges();
                //color = Color.RED;
                break;
            case DrinkIfYouHave:
                setUpDrinkIfYouHave();
                //color=Color.BLUE;
                break;
            case Targeted:
                setUpTargetedOnceOff();
                //color=Color.YELLOW;
                break;
            case GeneralRules:
                setUpGeneralRules();
                //color=Color.CYAN;
                break;
            case GroupInstructions:
                setUpGroupInstructions();
                //color = Color.MAGENTA;
                break;
            case WouldYouRather:
                setUpWouldYouRather();
                //color=Color.DKGRAY;
                break;
        }
        constLayout.setBackgroundColor(color);
        Log.d("Current Game Type",currGameType.getName());
    }

    //change these to return true or false depending whether they still had instructions to update
    public void setUpCategories(){
        String instruction = "";
        if (categories.size()>0){
            instruction = "Go around the group and name " + categories.get(0);
            categories.remove(0);
            //
            titleTxt.setText("CATEGORIES");
            instructionTxt.setText(instruction);
            descriptionTxt.setText("First person to mess up drinks, along with their team.\nPlease click which team/s had to drink");
        } else{
            instruction = "All categories used up :/"; //just for error checking
        }
    }

    public void setUpChallenges(){
        //set timer so that challenges cannot occur within a certain no of rounds than each other
        String title ="";
        String instruction = "";
        if (roundsSinceLastChallenge>minRoundsSinceLastChallenge){
            roundsSinceLastChallenge = 0;
            if (challenges.size()>0){
                title = ("challenge: " + challenges.get(0)[0]).toUpperCase();
                instruction = challenges.get(0)[1];
                challenges.remove(0);
            }
            titleTxt.setText(title);
            instructionTxt.setText(instruction);
            descriptionTxt.setText("Please click which team/s had to drink");
        } else {
            updateInstruction(false);
        }
    }

    public void setUpDrinkIfYouHave(){
        String instruction = "";
        if (drinkIfYouHave.size()>0){
            instruction = "Drink if you have ever " + drinkIfYouHave.get(0);
            drinkIfYouHave.remove(0);
            //
            titleTxt.setText("TRUTH");
            instructionTxt.setText(instruction);
            descriptionTxt.setText("If a teammate drinks, the whole team drinks.\nPlease click which team/s had to drink");
        } else{
            instruction = "All drinkIfYouHave used up :/"; //just for error checking
        }
    }

    public void setUpTargetedOnceOff(){
        String instruction = "";
        //once off
        Player randPlayer = players[(int)(Math.random()*noPlayers)]; //random player
        if (targetedOnceOff.size()>0){
            instruction = randPlayer.getName() + ", " + targetedOnceOff.get(0);
            targetedOnceOff.remove(0);
            //
            titleTxt.setText("TARGETED");
            instructionTxt.setText(instruction);
            descriptionTxt.setText("Please click which team/s had to drink");
        } else {
            updateInstruction(false);
        }
    }

    public void setUpGeneralRules(){
        //each one lasts at least 6 rounds?
        String title = "GROUP RULE";
        String instruction = "";
        Boolean ruleEnded = false;
        int maxRulesInPlay = 2;
        if (generalRules.size()>0){
            //check if any of the current rules have been in play for too long
            for (int i = 0; i< currGeneralRules.size();i++){
                if (currGeneralRules.get(i).isTooOld()){
                    //remove and indicate that rule is no longer in play
                    title = title + " OVER";
                    instruction = currGeneralRules.get(i).getName() + " is no longer in play";
                    currGeneralRules.remove(i);
                    ruleEnded = true;
                    break;
                }
            }
            //roundsSinceLastGroupRule bit causes a glitch where the screen just goes to "GROUP RULE" title and nothing else
            if (!ruleEnded && roundsSinceLastGroupRule>=minRoundsSinceLastGroupRule){
                roundsSinceLastGroupRule= 0;
                if (currGeneralRules.size()<3){ //i.e. do not add new rule if max no of rules has been reached
                    currGeneralRules.add(new Rule(generalRules.get(0)));
                    instruction = generalRules.get(0);
                    generalRules.remove(0);
                    titleTxt.setText(title);
                    instructionTxt.setText(instruction);
                    descriptionTxt.setText("");
                } else {
                    updateInstruction(false);
                }
            } else if (roundsSinceLastGroupRule<minRoundsSinceLastGroupRule){
                updateInstruction(false);
            }
        } else {
            updateInstruction(false);

        }

    }

    public void setUpGroupInstructions(){
        //
        String instruction = "";
        if (groupInstructions.size()>0){
            instruction = groupInstructions.get(0);
            groupInstructions.remove(0);
            //
            titleTxt.setText("EVERYONE");
            instructionTxt.setText(instruction);
            descriptionTxt.setText("");
        } else {
            instruction = "All GroupInstructions used up :/"; //just for error checking
        }
    }

    public void setUpWouldYouRather(){
        //

        titleTxt.setText("WOULD YOU RATHER");
    }

    //get weighted random game type i.e. some options are weighted
    public GameType getRandGameType(){
        GameType gameType = null; //will return null if an error occurs
        int randValue =(int) (Math.random()*100 + 1);
        Log.d("randValue","randValue: "+randValue);
        if (randValue<=16){
            gameType = GameType.Categories;
        } else if (randValue<=26){
            gameType = GameType.Challenges;
        } else if (randValue<=42){ //Is the game mode Drink If You Have Ever the answer to Life, the Universe and Everything?
            gameType = GameType.DrinkIfYouHave;
        }else if (randValue<=58){
            gameType = GameType.Targeted;
        }else if (randValue<=74){
            gameType = GameType.GeneralRules;
        }else if (randValue<=84){
            gameType = GameType.GroupInstructions;
        }else if (randValue<=100){
            gameType = GameType.WouldYouRather;
        }
        return gameType;
    }

    private void getDataFromFiles(){
        BufferedReader reader = null;
        try {
            //categories
            categories = new ArrayList<String>();
            String fileName = "Categories.txt";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            String line;
            while ((line = reader.readLine()) != null) {
                categories.add(line);
            }
            //randomise
            Collections.shuffle(categories);

            //challenges
            challenges = new ArrayList<String[]>();
            fileName = "Challenges.txt";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            while ((line = reader.readLine()) != null) {
                String temp[] = line.split("\\|");
                challenges.add(temp); //add 2 element array to arraylist
            }
            //randomise
            Collections.shuffle(challenges);

            //drinkIfYouHave
            drinkIfYouHave = new ArrayList<String>();
            fileName = "DrinkIfYouHave.txt";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            while ((line = reader.readLine()) != null) {
                drinkIfYouHave.add(line);
            }
            //randomise
            Collections.shuffle(drinkIfYouHave);

            //generalRules
            generalRules = new ArrayList<String>();
            fileName = "GeneralRules.txt";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            while ((line = reader.readLine()) != null) {
                generalRules.add(line);
            }
            //randomise
            Collections.shuffle(generalRules);

            //groupInstructions
            groupInstructions= new ArrayList<String>();
            fileName = "GroupInstructions.txt";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            while ((line = reader.readLine()) != null) {
                groupInstructions.add(line);
            }
            //randomise
            Collections.shuffle(groupInstructions);

            //targetedOnceOff
            targetedOnceOff= new ArrayList<String>();
            fileName = "TargetedOnceOff.txt";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            while ((line = reader.readLine()) != null) {
                targetedOnceOff.add(line);
            }
            //randomise
            Collections.shuffle(targetedOnceOff);

            //targetedPersistent
            targetedPersistent= new ArrayList<String>();
            fileName = "targetedPersistent";
            reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));

            while ((line = reader.readLine()) != null) {
                targetedPersistent.add(line);
            }
            //randomise
            Collections.shuffle(targetedPersistent);
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    public enum GameType{
        Categories, Challenges, DrinkIfYouHave, Targeted, GeneralRules, GroupInstructions, WouldYouRather;
        public String getName(){
            String gameTypeName = "";
            switch (this){
                case Categories: gameTypeName = "Categories";
                    break;
                case Challenges: gameTypeName = "Challenges";
                    break;
                case DrinkIfYouHave: gameTypeName = "DrinkIfYouHave";
                    break;
                case Targeted: gameTypeName = "Targeted";
                    break;
                case GeneralRules: gameTypeName = "GeneralRules";
                    break;
                case GroupInstructions: gameTypeName = "GroupInstructions";
                    break;
                case WouldYouRather: gameTypeName = "WouldYouRather";
                    break;
            }
            return  gameTypeName;
        }
    }
}
