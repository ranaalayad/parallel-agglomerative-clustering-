package com.test.agglomerative;

import java.io.*;
import java.util.*;

public class points {
    String n;
    int x;
    int y;

    
    public points(String n, int x, int y) {
        this.n = n;
        this.x = x;
        this.y = y;
    }

    public points() {
    }
    

    
    public String getN() {return n;}
    public int getx() {return x;}
    public int gety() {return y;}

    public void setN(String n) {this.n = n;}
    public void setx(int x) {this.x = x;}
    public void sety(int y) {this.y = y;}

    @Override
    public String toString() {
        return "points{" + "n=" + n + ", x=" + x + ", y=" + y + '}';}
   
}
