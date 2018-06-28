package com.example.nikhil.minesweeper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    LinearLayout rootLayout;
    public int SIZE=5,mineCount=0,NO_0F_MINES=5,countRevealed,buttonsToberevealed;
    public boolean firstclick=false;
    ArrayList<LinearLayout> rows;
    public MyButton[][]board;

    int NeighbourI[] = {-1, -1, -1, 0, 0, 1, 1, 1};
    int NeighbourJ[] = {-1, 0, 1, 1, -1, -1, 0, 1};
    Random row=new Random();
    Random col=new Random();
    //public static int n=5;
    static final String TAG = "abcde";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout=findViewById(R.id.rootLayout);
        setupBoard();
        setupMine();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.easy){
            SIZE=5;
            NO_0F_MINES=SIZE;
            countRevealed=0;
            buttonsToberevealed=((SIZE*SIZE)+1)-NO_0F_MINES;
            setupBoard();
            setupMine();

        }
        else if(id==R.id.medium){
            SIZE=8;
            NO_0F_MINES=10;
            countRevealed=0;
            buttonsToberevealed=((SIZE*SIZE)+1)-NO_0F_MINES;
            setupBoard();
            setupMine();

        }
        else if(id==R.id.hard){
            SIZE=10;
            NO_0F_MINES=20;
            countRevealed=0;
            buttonsToberevealed=((SIZE*SIZE)+1)-NO_0F_MINES;
            setupBoard();
            setupMine();
        }
        else if(id==R.id.reset){
            resetBoard();
            NO_0F_MINES=SIZE;
            countRevealed=0;
            buttonsToberevealed=((SIZE*SIZE)+1)-NO_0F_MINES;
            setupBoard();
            setupMine();
        }
        return super.onOptionsItemSelected(item);

    }

    public void setupBoard(){
        rows=new ArrayList<>();
        board=new MyButton[SIZE][SIZE];
        rootLayout.removeAllViews();


        for (int i=0;i<SIZE;i++){
            LinearLayout linearLayout=new LinearLayout( this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            linearLayout.setLayoutParams(layoutParams);
            rows.add(linearLayout);
            rootLayout.addView(linearLayout);
        }

        for (int i=0;i< SIZE;i++){

            for (int j=0;j<SIZE;j++){
                MyButton button=new MyButton(this);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(layoutParams);
                LinearLayout row=rows.get(i);
                row.addView(button);
                button.setMine(false);
                button.setReveal(false);
                button.setValue(0);
                button.setFlag(false);
                button.buttonI=i;
                button.buttonJ=j;
                board[i][j]=button;
                //board
            }
        }

    }
    public void setupMine(){
        while (mineCount<SIZE) {
            int i = row.nextInt(SIZE);
            int j = col.nextInt(SIZE);
            if(!board[i][j].isMine()){
                board[i][j].setMine(true);
                board[i][j].setValue(-1);
                mineCount++;
                setupNeigbours(i,j);
            }
        }
    }

    public void setupNeigbours(int x,int y){
        int newI,newJ;
        for (int i = 0; i < 8; i++) {
            newI = x + NeighbourI[i];
            newJ = y + NeighbourJ[i];
            if (newI >= 0 && newI < SIZE && newJ >= 0 && newJ < SIZE && !board[newI][newJ].isMine())
            {
                board[newI][newJ].setValue(board[newI][newJ].getValue() + 1);
                Log.i("MainActivity", " i j value = " + newI + "" + newJ);
                Log.i("MainActivity", " i value = " + board[newI][newJ].getValue());
            }
        }
    //show();

    }

    public void resetBoard(){
       for (int i=0;i<SIZE;i++){
           for (int j=0;j<SIZE;j++){
               MyButton button=board[i][j];
               button.setReveal(false);
               button.setValue(0);
               button.setMine(false);
           }
       }
    }

    public void show(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int t = board[i][j].getValue();

                board[i][j].setText(t + "");
            }
        }
    }

    /*private void checkIfGameComplete() {
        //if the no of unrevealed tiles is equal to no of mines, then the user wins
        if(NO_0F_MINES == buttonsToberevealed){
            Toast.makeText(this,"You win",Toast.LENGTH_LONG).show();
            revealAll();

        }
    }*/

    /*public void revealAll(){
        for(int i = 0;i<SIZE;i++){
            for(int j = 0;j<SIZE;j++){
                MyButton b = board[i][j];
                if(!b.isReveal()) {
                    b.setReveal(true);
                }
            }
        }
    }

    public void revealNeighbours(MyButton bt){
        int row = bt.getRow();
        int col = bt.getColumn();
        for(int i = 0;i<NeighbourI.length;i++) {
                int neighbourRow = row + NeighbourI[i];
                int neighbourCol = col + NeighbourJ[i];
                if (neighbourRow >= 0 && neighbourRow < SIZE && neighbourCol >= 0 && neighbourCol < SIZE ) {
                    MyButton neighbourbutton = board[neighbourRow][neighbourCol];
                    if (!neighbourbutton.isReveal()) {
                        neighbourbutton.setReveal(true);

                        if (neighbourbutton.isEmpty()) {
                            revealNeighbours(neighbourbutton);
                        }
                    }
                }
        }

    }*/

    public boolean gameStatus(int countRevealed)
    {
        if(countRevealed==buttonsToberevealed)
        {
            return false;
        }
        else
            return true;
    }


    public void reveal(MyButton b1) {
        String gt = b1.getText().toString();
        int value = b1.getValue();
        countRevealed++;

        if (gameStatus(countRevealed)) {

            if (value == 0) {

                b1.setReveal(true);
                b1.setEnabled(false);
                int newI, newJ;

                for (int i = 0; i < 8; i++) {
                    newI = b1.buttonI + NeighbourI[i];
                    newJ = b1.buttonJ + NeighbourJ[i];

                    if (newI >= 0 && newI < SIZE && newJ >= 0 && newJ < SIZE)
                    {
                        Log.i("MainActivity", " newi value = " + newI + "" + newJ);

                        if ((!board[newI][newJ].isReveal()) && board[newI][newJ].flag == false)
                        {
                            int nv = board[newI][newJ].getValue();
                            countRevealed++;
                            if(gameStatus(countRevealed)) {
                                if (nv == 0) {
                                    board[newI][newJ].setBackgroundResource(R.drawable.button_bg);
                                    board[newI][newJ].setReveal(true);
                                    board[newI][newJ].setEnabled(false);
                                    reveal(board[newI][newJ]);
                                } else if (nv > 0) {
                                    board[newI][newJ].setReveal(true);
                                    board[newI][newJ].setEnabled(false);
                                    textColor(nv, board[newI][newJ]);
                                    board[newI][newJ].setText(nv + "");
                                }
                            }
                        }
                    }
                }
            }
            if (value == -1) {
                b1.setText(value + "");
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        int mv = board[i][j].getValue();

                        if (mv == -1) {
                            board[i][j].setBackgroundResource(R.drawable.mine_icon);

//                            board[i][j].setText(mv+"");
                        } else if (mv > 0) {

                            textColor(mv, board[i][j]);
                        } else
                            board[i][j].setBackgroundResource(R.drawable.button_bg);

                        board[i][j].setEnabled(false);
                    }
                }
                Toast.makeText(this, "Game over", Toast.LENGTH_LONG).show();
            } else if (value > 0) {
                b1.setReveal(true);
                b1.setEnabled(false);
                textColor(value, b1);

                b1.setText(value + "");
            }

        }
        else
        {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    int mv = board[i][j].getValue();

                    if (mv == -1) {
                        board[i][j].setBackgroundResource(R.drawable.mine_icon);

//                            board[i][j].setText(mv+"");
                    }
                    board[i][j].setEnabled(false);
                }
            }
            Toast.makeText(this, "Game Won", Toast.LENGTH_LONG).show();
        }
    }

    public void textColor(int value,MyButton b1)
    {
        b1.setText(value+"");
        if(value==1)
        {

            b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.BLUE);
        }
        else if(value==2)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.GREEN);
        }
        else if(value==3)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.RED);
        }
        else if(value==4)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.MAGENTA);
        }
        else if(value==5)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.DKGRAY);
        }
        else if(value==6)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.BLACK);
        }
        else if(value==7)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.BLUE);
        }
        else if(value==8)
        { b1.setBackgroundResource(R.drawable.button_bg);
            b1.setTextColor(Color.RED);
        }

    }


    @Override
    public void onClick(View view){
        MyButton bt = (MyButton) view;
        String gt = bt.getText().toString();
        int vl = bt.getValue();

        if(!firstclick && vl==-1)
        {
            resetBoard();
            firstclick=true;
            bt.setReveal(true);
            bt.setBackgroundResource(R.drawable.button_bg);
            setupMine();
            reveal(bt);
            //show();
        }
        else if(!firstclick)
        {
            firstclick=true;
            bt.setReveal(true);
            //   countRevealed++;
            bt.setBackgroundResource(R.drawable.button_bg);
            reveal(bt);
        }
        else if (!gt.equals("F")) {

            bt.setBackgroundResource(R.drawable.button_bg);
            reveal(bt);
        }

    }

    @Override
    public boolean onLongClick(View view){
        //Toggle Flag

        MyButton b=(MyButton)view;
        if(b.isFlag()){
            b.setBackgroundResource(android.R.drawable.btn_default_small);
            b.setFlag(false);
        }else{
            b.setBackgroundResource(R.drawable.flag_icon);
            b.setFlag(true);
        }
        return true;
    }
}
