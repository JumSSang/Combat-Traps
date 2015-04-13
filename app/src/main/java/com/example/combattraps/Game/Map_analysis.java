package com.example.combattraps.Game;

import java.util.ArrayList;

/**
 * Created by GyungMin on 2015-03-17.
 */

class maplist
{
    private int m_x;
    private int m_y;
    private int m_position;
    maplist(int x,int y,int p)
    {
        m_x=x;
        m_y=y;
        m_position=p;
    }
    public int GetX()
    {
        return m_x;
    }
    public int GetY()
    {
        return m_y;
    }
    public int GetPosition()
    {
        return m_position;
    }
}

public class Map_analysis {

    public ArrayList<maplist> ArrMap;
    public int a;
    Map_analysis(String s)
    {
        ArrMap=new ArrayList();
        String[] result=s.split("a");
        a=result.length;
        for(int i=0;i<result.length ; i++)
        {
            String[] InputData=result[i].split("c");
            int x=Integer.parseInt(InputData[0]);
            int y=Integer.parseInt(InputData[1]);
            int position=Integer.parseInt(InputData[2]);
            maplist temps=new maplist(x,y,position);
            ArrMap.add(temps);

        }


    }
}
