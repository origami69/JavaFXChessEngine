package com.example.demo;

import java.util.ArrayList;

public class Piece {
    int x;
    int y;
    String pointerToAsset;
    public Piece(int x, int y, String pointerToAsset){
        this.x = x;
        this.y = y;
        this.pointerToAsset = pointerToAsset;
    }
    public String getAsset(){
        return pointerToAsset;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
