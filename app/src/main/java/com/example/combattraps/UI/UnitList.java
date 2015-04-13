package com.example.combattraps.UI;

/**
 * Created by GyungMin on 2015-02-03.
 */



public class UnitList
{
    private int m_name_code; //유닛의 네임코드
    private int m_level; //유닛의 레벨
    private int m_type;
   public UnitList()
    {

    }
    public void set(int code,int level,int type) //유닛 식별코드번호,레벨,타입
    {
        m_name_code=code;
        m_level=level;
        m_type=type;
    }

    public int retruncode()
    {
        return m_name_code;
    }

}

