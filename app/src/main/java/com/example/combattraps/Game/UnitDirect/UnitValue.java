package com.example.combattraps.Game.UnitDirect;

/**
 * Created by 경민 on 2015-04-27.
 */
public class UnitValue {
    static final int F_TOWER = 1;
    static final int F_JUMPINGTRAP = 2;
    static final int F_ZOMBIE = 3;
    static final int F_GOLDRUN = 4;
    static final int F_ELSATOWER = 5;
    static final int F_ANNA = 6;
    static final int F_TOWNHALL = 7;
    private boolean m_GameStart=false;
    private static UnitValue s_instance;
    public static int[][] m_map = new int[50][50];
    public static UnitValue getInstance()
    {
        if(s_instance==null)
        {
            s_instance =new UnitValue();
        }
        return s_instance;
    }
    public void setGameStart()
    {
        m_GameStart=true;
    }
    public boolean getGameStart()
    {
        return m_GameStart;
    }


}
