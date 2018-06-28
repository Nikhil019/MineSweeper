package com.example.nikhil.minesweeper;

import android.content.Context;
import android.widget.Button;

public class MyButton extends android.support.v7.widget.AppCompatButton {
    private boolean isMine;
    public boolean flag;
    private boolean reveal = false;
    private int value;
    public int buttonI,buttonJ;

    //location of button in the board
    private int row;
    private int column;



    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public MyButton(Context context) {
        super(context);
        //setPadding(0,0,0,0);
    }


    public void setReveal(boolean reveal){
        this.reveal = reveal;

    }

    public boolean isReveal() {
        return reveal;
    }


    public void setValue(int value){
        this.value=value;
    }
    public int getValue(){
        return this.value;
    }


    public void setFlag(boolean flag){
        this.flag=flag;
    }

    public boolean isFlag(){
       return this.flag;
    }

}
