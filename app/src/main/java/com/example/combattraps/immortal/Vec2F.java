package com.example.combattraps.immortal;

/**
 * Created by GyungMin on 2015-03-23.
 */
public class Vec2F {

    public float x;
    public float y;


    public Vec2F(float x,float y)
    {
        this.x=x;
        this.y=y;
    }

    public void multiply(float b)
    {
        this.x=( this.x*b);
        this.y=( this.y*b);

    }
    public boolean equals(Vec2F o)
    {
        return (int)o.x == (int)x && (int)o.y ==(int) y;
    }

    public void copy(Vec2F o)
    {
        this.x = o.x;
        this.y = o.y;
    }
    public void normalize()
    {
        float distance=getLength();
        this.div(distance);
    }

    public void div(float a)
    {
        this.x=this.x/a;
        this.y=this.y/a;
    }


    public void add(Vec2F o)
    {
        this.x += o.x;
        this.y += o.y;
    }



    public void sub(Vec2F o)
    {
        this.x -=o.x;
        this.y -=o.y;
    }


    public void translate()
    {

    }
    public float getLength()
    {

        return (float)Math.sqrt(Math.pow(this.x,2)  + Math.pow(this.y,2));

    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
