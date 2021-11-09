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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class OnePlayers extends AppCompatActivity {
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int CIRCLE = 2;
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int CIRCLE_WON = 3;

    private static final int ROWS = 3, COLS = 3;
    private static int count_x=0,count_O=0,count_Draw=0;
    private static boolean stop,finish;
    private ArrayList<ImageView> arr_image=new ArrayList();

    private int[][] board=Tic_Tac_Toe.getBoard();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_players);
        initGame();
        inClick(arr_image);
    }

    public void initGame () {
        Tic_Tac_Toe.initGame();
        stop = false;
        finish = false;
        ((TextView) findViewById(R.id.count_o_two)).setText("" + count_O);
        ((TextView) findViewById(R.id.count_x_two)).setText("" + count_x);
        ((TextView) findViewById(R.id.count_draw_two)).setText("" + count_Draw);
        arr_image.add((ImageView) findViewById(R.id.num_11));
        arr_image.add((ImageView) findViewById(R.id.num_22));
        arr_image.add((ImageView) findViewById(R.id.num_33));
        arr_image.add((ImageView) findViewById(R.id.num_44));
        arr_image.add((ImageView) findViewById(R.id.num_55));
        arr_image.add((ImageView) findViewById(R.id.num_66));
        arr_image.add((ImageView) findViewById(R.id.num_77));
        arr_image.add((ImageView) findViewById(R.id.num_88));
        arr_image.add((ImageView) findViewById(R.id.num_99));
        inClick(arr_image);
        returnButton();

    }

    public void returnButton () {
        findViewById(R.id.returnButton_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(OnePlayers.this);
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

    public void inClick (ArrayList < ImageView > arr_image) {
        for (final ImageView im : arr_image) {
            System.out.println(im.getId());
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!finish && !stop) {

                        int row = (getIndex(im))/ 3, col = (getIndex(im))% 3;
                        if(board[row][col]==EMPTY) {
                            stop=true;
                            playAuto(im, row, col);

                        }
                        else
                            makeToast("ERROR");


                    }
                }
            });
        }
    }

    public void playAuto(ImageView im,int row,int col){
        play(im, row, col);
        if(Tic_Tac_Toe.getCurrentState() ==PLAYING)
            playAndroid();
        stop=false;


    }

    public void playAndroid(){
        if(!win())
            if(!defense())
                if(!attack())
                    random();
    }

    public boolean defense(){
        return find(CROSS,2,1);
    }

    public boolean attack(){
        return find(CIRCLE,1,2);
    }

    public boolean win(){
        return find(CIRCLE,2,1);
    }

    public void random(){
        int col,row;
        Random rand = new Random();
        do {
            col=rand.nextInt(COLS);
            row=rand.nextInt(ROWS);
        } while (board[row][col]!=EMPTY);
        play(arr_image.get(row*3+col),row,col);
    }

    public boolean find(int currentPlayer,int c,int empty){
        return findInRow(currentPlayer, c, empty)||findInCol(currentPlayer, c, empty)||findInDiagonal(currentPlayer, c, empty);
    }

    public boolean findInRow(int currentPlayer,int c,int empty){
        int count_c,count_empty,col;
        ArrayList<Integer> cellEmpty=new ArrayList<>();
        Random rand = new Random();
        for(int i=0;i<ROWS;i++){
            count_c=count_empty=0;
            for(int j=0;j<COLS;j++){
                count_c=(board[i][j]==currentPlayer)? (count_c+1) :count_c;
                count_empty=(board[i][j]==EMPTY)? (count_empty+1) :count_empty;
            }
            if (count_c==c &&count_empty==empty)
            {
                for(int k=0;k<COLS;k++){
                    if(board[i][k]==EMPTY){
                        cellEmpty.add(k);
                    }
                }
                col=cellEmpty.get(rand.nextInt(cellEmpty.size()));
                //
                play(arr_image.get(i*3+col),i,col);
                return true;
            }
        }
        return false;
    }

    public boolean findInCol(int currentPlayer,int c,int empty){
        int count_c,count_empty,row;
        ArrayList<Integer> cellEmpty=new ArrayList<>();
        Random rand = new Random();
        for(int i=0;i<COLS;i++){
            count_c=count_empty=0;
            for(int j=0;j<ROWS;j++){
                count_c=(board[j][i]==currentPlayer)? (count_c+1) :count_c;
                count_empty=(board[j][i]==EMPTY)? (count_empty+1) :count_empty;
            }
            if (count_c==c &&count_empty==empty)
            {
                for(int k=0;k<COLS;k++){
                    if(board[k][i]==EMPTY){
                        cellEmpty.add(k);
                    }
                }
                row=cellEmpty.get(rand.nextInt(cellEmpty.size()));
                //
                play(arr_image.get(row*3+i),row,i);
                return true;
            }
        }
        return false;
    }

    public boolean findInForwardDiagonal(int currentPlayer,int c,int empty){
        int count_c,count_empty,cell;
        ArrayList<Integer> cellEmpty=new ArrayList<>();
        Random rand = new Random();
        count_c = count_empty = 0;
        for(int i=0;i<COLS;i++) {
            count_c = (board[i][i] == currentPlayer) ? (count_c + 1) : count_c;
            count_empty = (board[i][i] == EMPTY) ? (count_empty + 1) : count_empty;
        }
        if (count_c==c &&count_empty==empty)
        {
            for(int k=0;k<COLS;k++){
                if(board[k][k]==EMPTY){
                    cellEmpty.add(k);
                }
            }
            cell=cellEmpty.get(rand.nextInt(cellEmpty.size()));
            //
            play(arr_image.get(cell*3+cell),cell,cell);
            return true;
        }
        return false;
    }

    public boolean findInBackwardDiagonal(int currentPlayer,int c,int empty){
        int count_c,count_empty,row;
        ArrayList<Integer> cellEmpty=new ArrayList<>();
        Random rand = new Random();
        count_c = count_empty = 0;
        for(int i=0;i<ROWS;i++) {
            count_c = (board[i][2-i] == currentPlayer) ? (count_c + 1) : count_c;
            count_empty = (board[i][2-i] == EMPTY) ? (count_empty + 1) : count_empty;
        }
        if (count_c==c &&count_empty==empty)
        {
            for(int k=0;k<COLS;k++){
                if(board[k][2-k]==EMPTY){
                    cellEmpty.add(k);
                }
            }
            row=cellEmpty.get(rand.nextInt(cellEmpty.size()));
            //
            play(arr_image.get(row*3+(2-row)),row,2-row);
            return true;
        }
        return false;
    }

    public boolean findInDiagonal(int currentPlayer,int c,int empty) {
        return findInForwardDiagonal(currentPlayer, c, empty)||findInBackwardDiagonal(currentPlayer, c, empty);
    }

    public  void play(ImageView im,int row,int col){

        Tic_Tac_Toe.play(im,row,col);
        ImageView currentPlayer= (ImageView)findViewById(R.id.currentPlayer_two);
        if ((Tic_Tac_Toe.getCurrentPlayer() ==CIRCLE)) {
            currentPlayer.setImageResource(R.drawable.o2);
        } else {
            currentPlayer.setImageResource(R.drawable.x2);
        }
        gameEndCheck();



    }

    public void gameEndCheck () {
        if (Tic_Tac_Toe.getCurrentState() ==CROSS_WON) {
            makeToast("You beat Android, well done !!!");
            count_x++;
            victoryLine();
            finishGame();
        } else if (Tic_Tac_Toe.getCurrentState() ==CIRCLE_WON) {
            makeToast("Android won this time, not terrible !!!");
            victoryLine();
            count_O++;
            finishGame();
        } else if (Tic_Tac_Toe.getCurrentState() ==DRAW) {
            makeToast("Draw, try again !!!");
            count_Draw++;
            finishGame();
        }

    }

    public void victoryLine () {
        switch (Tic_Tac_Toe.getNumLine()) {
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

    public void finishGame () {

        Timer timer = new Timer();
        finish = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        }, 2000);

    }

    public void makeToast (String str) {
        Toast.makeText(OnePlayers.this, str, Toast.LENGTH_SHORT).show();
    }

    public int getIndex (ImageView im){
        return arr_image.indexOf(im);
    }
}
