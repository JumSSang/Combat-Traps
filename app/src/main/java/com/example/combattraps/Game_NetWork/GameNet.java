package com.example.combattraps.Game_NetWork;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by GyungMin on 2015-03-17.
 */
public class GameNet {
    public String MyID;
    private phpDown task;
    public ArrayList<ListItem> listItems ;

    public GameNet()
    {
        listItems =new ArrayList<ListItem>();
        task = new phpDown();
        task.execute("http://203.247.211.230:7800/TrapACombat");
    }
    private class phpDown extends AsyncTask<String, Integer,String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(3000); //요청 응답 타임아웃 설정
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }
        @Override
        protected void onPostExecute(String str) {
            String player1;
            String player2;
            String connect_time1;
            String connect_time2;
            String start_time;
            String end_time;
            String map;
            String a =str;
            try {
                JSONObject root = new JSONObject(str);//JsonObject root 을 선언하고   JsonObject(str)로 할당한다.
                int asdf=1;
                JSONArray ja = root.getJSONArray("results"); //JSonArray ja를 선언하고 root의 JsonArray의 결과값으로 초기화 한다.
                for (int i = 0; i < ja.length(); i++) {   //ja 객체의 길이 즉 객체의 숫자만큼 반복한다.
                    JSONObject jo = ja.getJSONObject(i); //jo오브젝트는 jsonArray 의i 번째 값으로 초기화 한다.
                    player1 = jo.getString("User_ID_1");
                    player2 = jo.getString("User_ID_2");
                    connect_time1 = jo.getString("User_ID_ConnectTime1");
                    connect_time2 = jo.getString("User_ID_ConnectTime2");
                    start_time = jo.getString("StartGame_Time");
                    end_time = jo.getString("EndGame_Time");
                    map = jo.getString("User_1_MapArray");
                    listItems.add(new ListItem(player1, player2, connect_time1, connect_time2, start_time, end_time, map));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  txtView.setText("닉네임 :"+listItems.get(0).getData(0)+"\n이메일:"+ listItems.get(0).getData(1)+"\n소지골드:"+listItems.get(0).getData(2));
        }

    }
}
