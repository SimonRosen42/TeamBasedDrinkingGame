package com.example.teambaseddrinkinggame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SetUpScreen extends AppCompatActivity {


    private int noOfTeams, noOfPlayers;
    Team teams[];

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_set_up_screen);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        String playerNames[] = bundle.getStringArray("playerNames");
        for (int i = 0; i< playerNames.length; i++){
            Log.d("SetUpScreen: playerNames["+i+"]","playerNames["+i+"]: "+playerNames[i]);
        }
        createTeams(playerNames);
        setUpScreen();

        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //set names of teams
                EditText team1Txt = (EditText) findViewById(R.id.team1EditTxt);
                EditText team2Txt = (EditText) findViewById(R.id.team2EditTxt);
                EditText team3Txt = (EditText) findViewById(R.id.team3EditTxt);
                EditText team4Txt = (EditText) findViewById(R.id.team4EditTxt);
                //team 1
                teams[0].setName(team1Txt.getText().toString());
                //team 2
                teams[1].setName(team2Txt.getText().toString());
                //team 3
                if (noOfTeams>=3){
                    teams[2].setName(team3Txt.getText().toString());
                }
                //team 4
                if (noOfTeams==4){
                    teams[3].setName(team4Txt.getText().toString());
                }
                //parse teams to next activity
                Intent i = new Intent(SetUpScreen.this, MainScreen.class);
                i.putExtra("teams", teams);
                startActivity(i);
            }
        });
    }

    //show relevant TextViews and EditTexts
    private void setUpScreen(){
        int elementID;

        //team name Edit Texts
        EditText teamNameEditTxts[] = new EditText[4];
        for (int i = 1; i<= 4; i++){
            elementID = getResources().getIdentifier("team" + i + "EditTxt", "id", getPackageName());
            teamNameEditTxts[i-1] = (EditText) findViewById(elementID);
        }

        //player name Text Views
        TextView playerTxtViews[] = new TextView[16];
        for (int i = 1; i<= 16; i++){
            elementID = getResources().getIdentifier("player" + i + "TxtView", "id", getPackageName());
            playerTxtViews[i-1] = (TextView) findViewById(elementID);
        }

        //team 1
        int temp = teams[0].getTeamSize();

        for(int i = 0; i<teams[0].getTeamSize();i++){
            playerTxtViews[i].setVisibility(View.VISIBLE);
            playerTxtViews[i].setText(teams[0].getPlayerNameAt(i));
        }

        int playerIndex;
        //team 2
        for(int i = 0; i<teams[1].getTeamSize();i++){
            playerIndex = i+4;
            playerTxtViews[playerIndex].setVisibility(View.VISIBLE);
            playerTxtViews[playerIndex].setText(teams[1].getPlayerNameAt(i));
        }

        //team 3
        if (noOfTeams>=3){
            teamNameEditTxts[2].setVisibility(View.VISIBLE);
            for(int i = 0; i<teams[2].getTeamSize();i++){
                playerIndex = i+8;
                playerTxtViews[playerIndex].setVisibility(View.VISIBLE);
                playerTxtViews[playerIndex].setText(teams[2].getPlayerNameAt(i));
            }
        }

        //team 4
        if (noOfTeams==4){
            teamNameEditTxts[3].setVisibility(View.VISIBLE);
            for(int i = 0; i<teams[3].getTeamSize();i++){
                playerIndex = i+12;
                playerTxtViews[playerIndex].setVisibility(View.VISIBLE);
                playerTxtViews[playerIndex].setText(teams[3].getPlayerNameAt(i));
            }
        }
    }

    public void createTeams(String playerNames[]){
        noOfPlayers = playerNames.length;
        if (noOfPlayers<9){
            noOfTeams = 2;
        } else if (noOfPlayers<13){
            noOfTeams = 3;
        } else {
            noOfTeams = 4;
        }
        teams = new Team[noOfTeams];
        ArrayList<String> playerNamesArrList = new ArrayList<String>();
        for (int i = 0;i<noOfPlayers;i++){
            playerNamesArrList.add(playerNames[i]);
        }
        //randomise list of names
        Collections.shuffle(playerNamesArrList);
        //team 1
        String teamPlayerNames[]; //temp
        if (noOfPlayers==4){ //2 team mates
            teamPlayerNames = new String[2];
            for(int i = 0; i< 2;i++){
                teamPlayerNames[i] = playerNamesArrList.get(0);
                playerNamesArrList.remove(0);
            }
            teams[0] = new Team(teamPlayerNames);
        } else if (noOfPlayers==5||noOfPlayers==6||noOfPlayers==9){ //3 team mates
            teamPlayerNames = new String[3];
            for(int i = 0; i< 3;i++){
                teamPlayerNames[i] = playerNamesArrList.get(0);
                playerNamesArrList.remove(0);
            }
            teams[0] = new Team(teamPlayerNames);
        } else { //4 team mates
            teamPlayerNames = new String[4];
            for(int i = 0; i< 4;i++){
                teamPlayerNames[i] = playerNamesArrList.get(0);
                playerNamesArrList.remove(0);
            }
            teams[0] = new Team(teamPlayerNames);
        }

        //team 2
        if (noOfPlayers==4||noOfPlayers==5){ //2 team mates
            teamPlayerNames = new String[2];
            for(int i = 0; i< 2;i++){
                teamPlayerNames[i] = playerNamesArrList.get(0);
                playerNamesArrList.remove(0);
            }
            teams[1] = new Team(teamPlayerNames);
        } else if (noOfPlayers==8||noOfPlayers==11||noOfPlayers==12||noOfPlayers>=14){ //4 team mates
            teamPlayerNames = new String[4];
            for(int i = 0; i< 4;i++){
                teamPlayerNames[i] = playerNamesArrList.get(0);
                playerNamesArrList.remove(0);
            }
            teams[1] = new Team(teamPlayerNames);
        } else { //3 team mates
            teamPlayerNames = new String[3];
            for(int i = 0; i< 3;i++){
                teamPlayerNames[i] = playerNamesArrList.get(0);
                playerNamesArrList.remove(0);
            }
            teams[1] = new Team(teamPlayerNames);
        }

        //team 3
        if (noOfTeams>=3){
            if (noOfPlayers==12||noOfPlayers>=15){ //4 team mates
                teamPlayerNames = new String[4];
                for(int i = 0; i< 4;i++){
                    teamPlayerNames[i] = playerNamesArrList.get(0);
                    playerNamesArrList.remove(0);
                }
                teams[2] = new Team(teamPlayerNames);
            } else { //3 team mates
                teamPlayerNames = new String[3];
                for(int i = 0; i< 3;i++){
                    teamPlayerNames[i] = playerNamesArrList.get(0);
                    playerNamesArrList.remove(0);
                }
                teams[2] = new Team(teamPlayerNames);
            }
        }

        if (noOfTeams==4) {
            //team 4
            if (noOfPlayers == 16) { //4 team mates
                teamPlayerNames = new String[4];
                for (int i = 0; i < 4; i++) {
                    teamPlayerNames[i] = playerNamesArrList.get(i);
                }
                teams[3] = new Team(teamPlayerNames);
            } else { //3 team mates
                teamPlayerNames = new String[3];
                for (int i = 0; i < 3; i++) {
                    teamPlayerNames[i] = playerNamesArrList.get(i);
                }
                teams[3] = new Team(teamPlayerNames);
            }
        }
    }
}
