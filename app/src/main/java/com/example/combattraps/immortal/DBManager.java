package com.example.combattraps.immortal;

import com.example.combattraps.NetConnect;

/**
 * Created by 경민 on 2015-04-19.
 */
public class DBManager {
    public NetConnect connection;
    private static DBManager s_instance;
    private String response;
    private String id;
    private int gold;
    int state=0;
    public int go_robby=2;
    public boolean nextlobby=false;
    public static DBManager getInstance()
    {
        if(s_instance==null)
        {
            s_instance =new DBManager();
        }
        return s_instance;
    }
    public void SetID(String a)
    {
        this.id=a;
    }
    public String GetID()
    {
        return id;
    }
    public String getResponse() //접속이라던가 모든 문자열을 보내준다.
    {
        return response;
    }
    public void SetResponse(String a) //서버로부터 신호들어온 문자열을 셋팅한다.
    {
        this.response=a;
    }

}
