package com.example.combattraps.immortal;

import com.example.combattraps.Game.UnitDirect.UnitValue;
import com.example.combattraps.NetConnect;

import java.io.IOException;

/**
 * Created by 경민 on 2015-04-19.
 */
public class DBManager {
    public NetConnect connection;
    private static DBManager s_instance;
    private String response;

    public static double readyroomtime=0;
    private String enemy="매칭을 시작하기전입니다.."; //잠시 적군의 아이디 보내줄 곳
    private String id="로딩중..";//유저의 닉네임
    private int cash; //유저의 캐시정보
    private int level; //유저의 렙레정보
    private int gold; //유저의 골드 정보
    private int victory; //유저의 승리 정보
    private int sum_number; //유저의 썸네일 정보
    private String guild="로딩중.."; //유저의 길드 정보
    private int enemysum;
    int state=0;
    public int go_robby=2;
    public boolean nextlobby=false;
    public String m_StringMap;
    public String m_server_getMap;
    public static DBManager getInstance()
    {
        if(s_instance==null)
        {
            s_instance =new DBManager();
        }
        return s_instance;
    }
    public String GetEnemy()
    {
        return enemy;
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
        switch(go_robby)
        {
            case 1://처음 들어왔을경우 정보셋팅하는 부분이다.
                /*
                1. 닉네임
                2.골드
                3.캐시
                4.레벨
                5.승리횟수
                6.썸네일 번호
                7.길드
                 */
                String[] result=a.split(":");
                this.id=result[0];
                this.gold= Integer.parseInt(result[1]);
                this.cash=Integer.parseInt(result[2]);
                this.level=Integer.parseInt(result[3]);
                this.victory=Integer.parseInt(result[4]);
                this.sum_number=Integer.parseInt(result[5]);
                this.guild=result[6];

                break;
            case 2: //뭐지?!!!
                this.response=a;
                break;
            case 3: //적의 아이디와 썸네일 분리해서 정보를 넘겨준다.
                String[] result2=a.split(":");

                this.enemy=result2[0];
                this.enemysum= Integer.parseInt(result2[1]);

                break;
            case 4: //싱글게임 시작시 맵정보를 불러오는 부분이다.
                m_StringMap=a;

                break;
            case 5:


                break;
            case 6:

                break;
        }

       // this.response=a;
    }
    public int GetGold()
    {
        return gold;

    }
    public int GetVictory()
    {
        return victory;
    }
    public int GetCash()
    {
        return cash;
    }
    public String getGuild()
    {
        return guild;
    }
    public int GetLevel()
    {
        return level;
    }
    public int GetSumnumber()
    {
        return sum_number;
    }
    public void SetEnemy(String s)
    {
        this.enemy=s;
    }
    public int GetEnemySum()
    {
        return enemysum;
    }
}

