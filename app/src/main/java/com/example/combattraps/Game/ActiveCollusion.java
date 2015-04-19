package com.example.combattraps.Game;

import java.util.ArrayList;

/**
 * Created by GyungMin on 2015-02-10.
 */

class Pos
{
    public float m_x;
public float m_y;

            Pos(float x,float y)
            {
                m_x=x;
                m_y=y;
            }

        }

public class ActiveCollusion
{
    public ArrayList<Pos> PointSpot;

    //마름모의 세로/가로 비. 대략 1/2으로 직선의 기울기를 뜻한다.
    float m = 0.6f;
    float d1;
    float d2;
    float d3;
    float d4;
    float[] Gradiant;

    float result1,result2,result3,result4;



    public ActiveCollusion()
    {
        PointSpot=new ArrayList<Pos>();
        Gradiant =new float[4];
    }
    public void addSpot(float x,float y)
    {
        Pos temp=new Pos(x,y);
        PointSpot.add(temp);
    }
    public void distanceCal()
    {
        Gradiant[0]=((PointSpot.get(0).m_y-PointSpot.get(1).m_y)/
                (PointSpot.get(0).m_x-PointSpot.get(1).m_x));

        Gradiant[1]=((PointSpot.get(1).m_y-PointSpot.get(2).m_y)/
                (PointSpot.get(1).m_x-PointSpot.get(2).m_x));

        Gradiant[2]=((PointSpot.get(2).m_y-PointSpot.get(3).m_y)/
                (PointSpot.get(2).m_x-PointSpot.get(3).m_x));

        Gradiant[3]=((PointSpot.get(3).m_y-PointSpot.get(0).m_y)/
                (PointSpot.get(3).m_x-PointSpot.get(0).m_x));

        d1=PointSpot.get(0).m_y-Gradiant[0] *PointSpot.get(0).m_x;
        d2=PointSpot.get(1).m_y-Gradiant[1] *PointSpot.get(1).m_x;
        d3=PointSpot.get(2).m_y-Gradiant[2] *PointSpot.get(2).m_x;
        d4=PointSpot.get(3).m_y-Gradiant[3] *PointSpot.get(3).m_x;

    }
    public boolean resultCal(float x,float y)
    {

        if (Gradiant[0]*x +d1<y && Gradiant[1]*x +d2<y &&Gradiant[2]*x +d3>y &&Gradiant[3]*x +d4>y)
        {
            //성공~
            return true;
        }
        else
        {
            return false;
        }


    }


}
