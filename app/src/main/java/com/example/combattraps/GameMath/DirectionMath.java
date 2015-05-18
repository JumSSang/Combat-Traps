package com.example.combattraps.GameMath;

import com.example.combattraps.immortal.Vec2;

/**
 * Created by 경민 on 2015-05-18.
 */
public class DirectionMath {

    private double dx;
    private double dy;
    private double M;
    private Vec2 Point;
    private double T;

    DirectionMath()
    {

    }
    public void setGiulgi(Vec2 a, Vec2 b)
    {
        double tb=0;
        M=Math.sqrt(Math.pow((a.x-b.x),2)+Math.pow(a.y-b.y,2));
        //Point.y =M*Point.x +tb;


    }

    public double getAngle(Vec2 a,Vec2 b)
    {
        return Math.atan2(a.x-b.x,a.y-b.y)*180/Math.PI;
    }

    public void getSpeedMove(double T)
    {

    }

}
