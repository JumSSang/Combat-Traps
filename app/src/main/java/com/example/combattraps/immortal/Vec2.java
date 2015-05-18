package com.example.combattraps.immortal;

/**
 * Created by GyungMin on 2015-03-23.
 */
public class Vec2 {

    public float fx;
    public float fy;
    public int x;
    public int y;
    public float nx;
    public float ny;




    public float normal;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(float x,float y)
    {
        this.fx=x;
        this.fy=y;
    }
    public Vec2(Vec2 pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public void multiply(float b)
    {
        this.nx=this.nx*b;
        this.ny= this.ny*b;
    }
    public boolean equals(Vec2 o)
    {
        return o.x == x && o.y == y;
    }

    public void copy(Vec2 o)
    {
        this.x = o.x;
        this.y = o.y;
    }
    public void normal(float distance)
    {
        //float distance=getLength();
        this.div(distance);
    }

    public void div(float a)
    {
        this.nx=this.fx/a;
        this.ny=this.fy/a;
    }

    public void add(Vec2 o)
    {
        this.x += o.x;
        this.y += o.y;
    }


    public int getDistance(Vec2 target)
    {
        int dx = x-target.x;
        int dy = y-target.y;
        return (int)Math.sqrt(dx*dx+dy*dy);
    }
    public void sub(Vec2 o)
    {
        this.x -=o.x;
        this.y -=o.y;
    }


    public float getLength(Vec2 b)
    {
        return (float)Math.sqrt((this.x-b.x)*(this.x-b.x)   + (this.y-b.y)*(this.y-b.y));
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
