package com.example.teambaseddrinkinggame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;

public class StartScreen extends AppCompatActivity {

    private Button addPlayerBtn, removePlayerBtn, startBtn;
    private int playerCount;
    private boolean isFirstTimeGetFocused[];
    //private int onFocusNo;
    private String playerNames[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_start_screen);
        getSupportActionBar().hide();
        //set focus to title
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.requestFocus();
        //
        playerCount = 4;
        //onFocusNo = 0;
        isFirstTimeGetFocused = new boolean[17]; //fix cause I'm an idiot and didn't think ahead
        Arrays.fill(isFirstTimeGetFocused, Boolean.TRUE);

        addPlayerBtn = (Button) findViewById(R.id.addPlayerBtn);
        final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);

        addPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (playerCount<16) {
                    playerCount++;
                    int editTextID = getResources().getIdentifier("nameInput" + playerCount, "id", getPackageName());
                    EditText playerInput = (EditText) findViewById(editTextID);
                    playerInput.setVisibility(View.VISIBLE);
                    //addPlayerBtn.requestFocus();
                    playerInput.requestFocus();
                }//change activity
                if (playerCount==5){
                    removePlayerBtn.setVisibility(View.VISIBLE);
                }
                if (playerCount==16){ //hide addPlayerBtn if max no of players has been reached
                    addPlayerBtn.setVisibility(View.GONE);
                }
                //launchActivity("student");
            }
        });

        removePlayerBtn = (Button) findViewById(R.id.removePlayerBtn);

        removePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (playerCount>4) {
                    playerCount--;
                    int editTextID = getResources().getIdentifier("nameInput" + (playerCount+1), "id", getPackageName());
                    EditText playerInput = (EditText) findViewById(editTextID);
                    playerInput.setText("");
                    playerInput.setVisibility(View.GONE);
                    if (playerCount==4){ //hide removePlayerBtn if min no of players has been reached
                        removePlayerBtn.setVisibility(View.GONE);
                    }
                    if (playerCount==15){
                        addPlayerBtn.setVisibility(View.VISIBLE);
                    }
                    //addPlayerBtn.requestFocus();
                    //
                    //playerInput.requestFocus();
                }//change activity
                //launchActivity("student");
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                EditText playerInput;
                playerNames = new String[playerCount];
                boolean validClick = true;
                for (int i = 0; i< playerCount; i++){
                    int editTextID = getResources().getIdentifier("nameInput" + (i+1), "id", getPackageName());
                    playerInput = (EditText) findViewById(editTextID);
                    if (playerInput.getText().toString().equals("")){
                        //throw error -> player name cannot be empty.
                        Log.d("tag","Error");
                        AlertDialog alertDialog = new AlertDialog.Builder(StartScreen.this).create();
                        alertDialog.setTitle("Player Name Empty");
                        alertDialog.setMessage("The name for a player cannot be empty. Please enter a name.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        validClick = false;
                        break;
                    } else {
                        playerNames[i]= playerInput.getText().toString();
                    }
                    Log.d("playerNames["+i+"]","playerNames["+i+"]: "+playerNames[i]);
                }
                if (validClick){
                    Intent intent = new Intent(getApplicationContext(),SetUpScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("playerNames", playerNames);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        //horrendous way of doing this but I don't know else to do this
        //soz :/
        //fix so that this is less shit
        final EditText nameInput1 = (EditText) findViewById(R.id.nameInput1);
        final EditText nameInput2 = (EditText) findViewById(R.id.nameInput2);
        final EditText nameInput3 = (EditText) findViewById(R.id.nameInput3);
        final EditText nameInput4 = (EditText) findViewById(R.id.nameInput4);
        final EditText nameInput5 = (EditText) findViewById(R.id.nameInput5);
        final EditText nameInput6 = (EditText) findViewById(R.id.nameInput6);
        final EditText nameInput7 = (EditText) findViewById(R.id.nameInput7);
        final EditText nameInput8 = (EditText) findViewById(R.id.nameInput8);
        final EditText nameInput9 = (EditText) findViewById(R.id.nameInput9);
        final EditText nameInput10 = (EditText) findViewById(R.id.nameInput10);
        final EditText nameInput11 = (EditText) findViewById(R.id.nameInput11);
        final EditText nameInput12 = (EditText) findViewById(R.id.nameInput12);
        final EditText nameInput13 = (EditText) findViewById(R.id.nameInput13);
        final EditText nameInput14 = (EditText) findViewById(R.id.nameInput14);
        final EditText nameInput15 = (EditText) findViewById(R.id.nameInput15);
        final EditText nameInput16 = (EditText) findViewById(R.id.nameInput16);


//        nameInput1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[1]) {
//                    nameInput1.setText("");
//                    isFirstTimeGetFocused[1] = false;
//                }
//            }
//        });
//
//        nameInput1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[1]) {
//                    nameInput1.setText("");
//                    isFirstTimeGetFocused[1] = false;
//                }
//            }
//        });
//
//        nameInput2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[2]) {
//                    nameInput2.setText("");
//                    isFirstTimeGetFocused[2] = false;
//                }
//            }
//        });
//
//        nameInput3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[3]) {
//                    nameInput3.setText("");
//                    isFirstTimeGetFocused[3] = false;
//                }
//            }
//        });
//
//        nameInput4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[4]) {
//                    nameInput4.setText("");
//                    isFirstTimeGetFocused[4] = false;
//                }
//            }
//        });
//
//        nameInput5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[5]) {
//                    nameInput5.setText("");
//                    isFirstTimeGetFocused[5] = false;
//                }
//            }
//        });
//
//        nameInput6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[6]) {
//                    nameInput6.setText("");
//                    isFirstTimeGetFocused[6] = false;
//                }
//            }
//        });
//
//        nameInput7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[7]) {
//                    nameInput7.setText("");
//                    isFirstTimeGetFocused[7] = false;
//                }
//            }
//        });
//
//        nameInput8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[8]) {
//                    nameInput8.setText("");
//                    isFirstTimeGetFocused[8] = false;
//                }
//            }
//        });
//
//        nameInput9.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[9]) {
//                    nameInput9.setText("");
//                    isFirstTimeGetFocused[9] = false;
//                }
//            }
//        });
//
//        nameInput10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[10]) {
//                    nameInput10.setText("");
//                    isFirstTimeGetFocused[10] = false;
//                }
//            }
//        });
//
//        nameInput11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[11]) {
//                    nameInput11.setText("");
//                    isFirstTimeGetFocused[11] = false;
//                }
//            }
//        });
//
//        nameInput12.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[12]) {
//                    nameInput12.setText("");
//                    isFirstTimeGetFocused[12] = false;
//                }
//            }
//        });
//
//        nameInput13.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[13]) {
//                    nameInput13.setText("");
//                    isFirstTimeGetFocused[13] = false;
//                }
//            }
//        });
//
//        nameInput14.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[14]) {
//                    nameInput14.setText("");
//                    isFirstTimeGetFocused[14] = false;
//                }
//            }
//        });
//
//        nameInput15.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[15]) {
//                    nameInput15.setText("");
//                    isFirstTimeGetFocused[15] = false;
//                }
//            }
//        });
//
//        nameInput16.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && isFirstTimeGetFocused[16]) {
//                    nameInput16.setText("");
//                    isFirstTimeGetFocused[16] = false;
//                }
//            }
//        });

        //soz for this super super super [*1000] shitty coding

    }

}
