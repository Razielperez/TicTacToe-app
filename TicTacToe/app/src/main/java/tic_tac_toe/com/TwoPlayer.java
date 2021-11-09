package tic_tac_toe.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TwoPlayer extends AppCompatActivity {
    private static int count_x=0,count_O=0,count_Drow=0;
    private static boolean stop;
    private ArrayList<ImageView> arr_image=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);
        initGame();
        inClick(arr_image);
    }
    public void initGame(){
        Tic_Tac_Toe.initGame();
        stop=false;
        ( (TextView)findViewById(R.id.count_o)).setText(""+count_O);
        ( (TextView)findViewById(R.id.count_x)).setText(""+count_x);
        ( (TextView)findViewById(R.id.count_draw)).setText(""+count_Drow);
        arr_image.add((ImageView) findViewById(R.id.num_1));arr_image.add((ImageView) findViewById(R.id.num_2));arr_image.add((ImageView) findViewById(R.id.num_3));
        arr_image.add((ImageView) findViewById(R.id.num_4)); arr_image.add((ImageView) findViewById(R.id.num_5));arr_image.add((ImageView) findViewById(R.id.num_6));
        arr_image.add((ImageView) findViewById(R.id.num_7)); arr_image.add((ImageView) findViewById(R.id.num_8)); arr_image.add((ImageView) findViewById(R.id.num_9));
        inClick(arr_image);
        returnButton();

    }

    public void returnButton(){
        findViewById(R.id.returnButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(TwoPlayer.this);
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to exit the game?");
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.show();
            }

        });
    }

    public void inClick(ArrayList<ImageView> arr_image){
        for(final ImageView im:arr_image){
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!stop)
                    {
                        int row=(getIndex(im))/3,col=(getIndex(im))%3;
                        play(im,row,col);
                    }
                }});
        }


    }

    public  void makeToast(String str){
        Toast.makeText(TwoPlayer.this,str,Toast.LENGTH_SHORT).show();
    }

    public  void play(ImageView im,int row,int col){
        if(Tic_Tac_Toe.getBoard()[row][col]==Tic_Tac_Toe.EMPTY) {
            Tic_Tac_Toe.play(im,row,col);
            ImageView currnetPlayer= (ImageView)findViewById(R.id.currentPlayer);
            if ((Tic_Tac_Toe.getCurrentPlayer() == Tic_Tac_Toe.CIRCLE)) {
                currnetPlayer.setImageResource(R.drawable.o2);
            } else {
                currnetPlayer.setImageResource(R.drawable.x2);
            }
        }
        else {
            makeToast("ERROR");
        }
        game_end_check();
    }

    public void game_end_check(){
        if (Tic_Tac_Toe.getCurrentState() == Tic_Tac_Toe.CROSS_WON) {
            makeToast("X is winner!!!");
            count_x++;
            victoryLine();
            finishGame();
        } else if (Tic_Tac_Toe.getCurrentState() == Tic_Tac_Toe.CIRCLE_WON) {
            makeToast("O is winner!!!");
            victoryLine();
            count_O++;
            finishGame();
        } else if (Tic_Tac_Toe.getCurrentState() == Tic_Tac_Toe.DRAW) {
            count_Drow++;
            finishGame();
        }

    }

    public void finishGame(){

        Timer timer=new Timer();
        stop = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        },2000);

    }

    public int getIndex(ImageView im){
        return arr_image.indexOf(im);
    }

    public  void victoryLine(){
        switch (Tic_Tac_Toe.getNumLine()){
            case 0:
                findViewById(R.id.line_0).setVisibility(View.VISIBLE);
                break;
            case 1:
                findViewById(R.id.line_1).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.line_2).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.line_3).setVisibility(View.VISIBLE);
                break;
            case 4:
                findViewById(R.id.line_4).setVisibility(View.VISIBLE);
                break;
            case 5:
                findViewById(R.id.line_5).setVisibility(View.VISIBLE);
                break;
            case 6:
                findViewById(R.id.line_6).setVisibility(View.VISIBLE);
                break;
            case 7:
                findViewById(R.id.line_7).setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }

}