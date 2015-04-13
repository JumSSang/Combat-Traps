package com.example.combattraps.Game_NetWork;

/**
 * Created by GyungMin on 2015-01-23.
 */

public class ListItem {

    private String[] mData;
    public final static int PLAYER1_NAME=0;
    public final static int PLAYER2_NAME=1;
    public final static int PLAYER1_CONNECT=3;
    public final static int PLAYER2_CONNECT=4;
    public final static int ALLPLAYER_STARTTIME=5;
    public final static int ALLPLAYER_ENDTIME=6;
    public final static int PLAYER1_MAP=6;
    public final static int PLAYER2_MAP=7;
    public final static int PLAYER_WIN=8;



    public ListItem(String[] data ){
        mData = data;
    }


    /**1번재 유저1번째 아이디
  2번째 유저2번째 아이디
  3번째 유저1번째의 접속시간
  4번째 유저2번째의 접속시간
  5번째 게임 시작시간
  6번째 종료 시간시간
  */
    public ListItem(String name1, String name2, String p1t,String p2t,String ast,String ent){

        mData = new String[9];
        mData[PLAYER1_NAME] = name1;
        mData[PLAYER2_NAME] = name2;
        mData[PLAYER1_CONNECT] = p1t;
        mData[PLAYER2_CONNECT] = p2t;
        mData[ALLPLAYER_STARTTIME] = ast;
        mData[ALLPLAYER_ENDTIME] = ent;


    }
    public ListItem()
    {

    }
    //맵쿼리 테스트용 생성자
    public ListItem(String name1, String name2, String p1t,String p2t,String ast,String ent,String map){

        mData = new String[9];
        mData[PLAYER1_NAME] = name1;
        mData[PLAYER2_NAME] = name2;
        mData[PLAYER1_CONNECT] = p1t;
        mData[PLAYER2_CONNECT] = p2t;
        mData[ALLPLAYER_STARTTIME] = ast;
        mData[ALLPLAYER_ENDTIME] = ent;
        mData[PLAYER1_MAP] = map;
    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }

}