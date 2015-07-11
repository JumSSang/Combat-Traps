package com.example.combattraps.immortal;

import com.example.combattraps.Game_NetWork.NetState;

import com.example.combattraps.NetConnect;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 경민 on 2015-04-19.
 */


public class DBManager {
    public static ArrayList<String> EventStack;
    public NetConnect connection;
    private static DBManager s_instance;
    private String response;
    public boolean netstate=false;

    public float m_batch_time=1000;
    public boolean m_turn_game_start=false;
    public static double readyroomtime=0;
    private String enemy="매칭을 시작하기전입니다.."; //잠시 적군의 아이디 보내줄 곳
    private String id="로딩중..";//유저의 닉네임
    private int cash; //유저의 캐시정보
    private int level; //유저의 렙레정보
    private int gold; //유저의 골드 정보
    public int victory=0; //유저의 승리 정보
    private int sum_number; //유저의 썸네일 정보
    private String guild="로딩중.."; //유저의 길드 정보
    private int enemysum;
    int state=0;
    private int go_robby=0;
    public boolean nextlobby=false;
    public String m_StringMap;
    public String m_server_getMap;
    public static int FrameCount=0;
    public static int stackCount=30;
    public static boolean nextFrame=true;
    public static boolean b_wiatFrame=false;
    public static boolean b_create=false;
    public static String n_UnitString=null;
    public int team=0;




    public static DBManager getInstance()
    {
        if(s_instance==null)
        {
            s_instance =new DBManager();
            EventStack=new ArrayList<String>();
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
    public void sendMessage(String a) throws IOException {
        connection.oos.writeObject(a);
        connection.oos.flush();
    }
    public int getNetState()
    {
        return go_robby;
    }

    public void setNetState(int state)
    {
        go_robby=0;
        go_robby=state;
    }

    public void setImforDB(String a)
    {

        if(a!=null) {
            String[] result2 = a.split(":");
            this.enemy = result2[0];
            this.enemysum = Integer.parseInt(result2[1]);
            this.team = Integer.parseInt(result2[2]);
        }

    }

    public void SetResponse(String a) throws IOException //서버로부터 신호들어온 문자열을 셋팅한다.
    {




        switch(go_robby)
        {
            case NetState.READY:
               // go_robby=NetState.USERLOAD;

                response=a;
                break;

            case NetState.USERLOAD://처음 들어왔을경우 정보셋팅하는 부분이다.
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
                go_robby=NetState.ROBBY;
                break;
            case NetState.ROBBY:
                response=a;
                if(team==0) {
                    this.enemy = a;
                }

                break;
            case NetState.MULTIGAMESTART: //적의 아이디와 썸네일 분리해서 정보를 넘겨준다.

                    //setImforDB(a);
                     response=a;
                    sendMessage("Commit");
            break;
            case NetState.MUTI_TRUN:
                m_batch_time=Integer.parseInt(a);
                if(m_batch_time<500)
                {
                    DBManager.getInstance().setNetState(NetState.MUTI_TRUN_READY);
                }


                break;
            case NetState.MUTI_TRUN_READY:
                n_UnitString=a;
                DBManager.getInstance().m_turn_game_start=true;
                DBManager.b_create=true;
                break;

            case NetState.SINGLEGAME: //싱글게임 시작시 맵정보를 불러오는 부분이다.
                m_StringMap=a;
                //go_robby=6;
                break;
            case NetState.MULTIGAME:
                String[] gamepacket =a.split(":");
                if(gamepacket[0].equals("nextFrame"))
                {
                    nextFrame=true;
                    if(!gamepacket[1].equals("null"))
                    {
                        n_UnitString=gamepacket[1];
                        b_create=true;
                    }
                    else
                    {
                        n_UnitString="null";
                        b_create=false;
                    }
                }
                break;
            case 7:

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
        // 적의 정보가 어떻게 변화하는지 체크해주는 함수  적아이디가 들어오면 더이상 호출 해주지 않는걸로
        this.enemy=s;
    }

    public int GetEnemySum()
    {
        return enemysum;
    }
}

