package com.test.agglomerative;

import java.io.*;
import java.util.*;
public class cluster {
    
    List<points> point = new ArrayList<points>();

    public void setPointd(List<points> pointd) {
        this.point = pointd;
    }

    public List<points> getPoint() {
        return point;
    }

    public void addm(points p){
        point.add(p);
    }

    public void destroy(){
        for(int k=0;k<point.size();k++)
            point.remove(k);}
}
