package com.example.combattraps.Game_NetWork;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by GyungMin on 2015-03-17.
 */
public class Connect {
    public String MyID;
    private phpDown task;

    Connect()
    {    task = new phpDown();
        task.execute("http://203.247.211.230:7800/TrapACombat");
    }
    private class phpDown extends AsyncTask<String, Integer,String> {
        @Override
        protected String doInBackground(String... urls) {
            try{
                HttpPost request=new HttpPost(urls[0]);
                //전달할 인자들
                Vector<NameValuePair>nameValue =new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("ID","go7072"));
                nameValue.add(new BasicNameValuePair("PASSWORD","7072"));
                //웹 접속 방식
                HttpEntity enty = new UrlEncodedFormEntity(nameValue,HTTP.UTF_8);
                request.setEntity(enty);
                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while((tmp = reader.readLine())!= null)
                {
                    if(tmp != null)
                    {
                        total += tmp;
                    }
                }
                im.close();
                //결과창뿌려주기 - ui 변경시 에러
                //result.setText(total);
                return total;
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }

        @Override
        protected void onPostExecute(String str) {

            //  txtView.setText("닉네임 :"+listItems.get(0).getData(0)+"\n이메일:"+ listItems.get(0).getData(1)+"\n소지골드:"+listItems.get(0).getData(2));
        }

    }
}
