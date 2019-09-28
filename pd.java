package com.test.agglomerative;

import java.io.*;
import java.util.*;

public class pd {
    points point1;
    points point2;
    double distance;

    public void setPoint1(points point1) { this.point1 = point1;}
    public void setPoint2(points point2) {this.point2 = point2;}
    public void setDistance(double distance) {this.distance = distance;}

    public points getPoint1() {return point1;}
    public points getPoint2() {return point2;}
    public double getDistance() {return distance;}

    @Override
    public String toString() {return "pd{" + "point1=" + point1 + ", point2=" + point2 + ", distance=" + distance + '}';}
    
    
    
}
