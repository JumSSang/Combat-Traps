package com.example.combattraps;

import android.os.AsyncTask;

import com.example.combattraps.immortal.DBManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 경민 on 2015-04-17.
 */


public class NetConnect extends AsyncTask<Void, Void, Void> {


    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public Socket soc;

    NetConnect() {

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            soc = new Socket("203.247.211.230", 7801);
            oos = new ObjectOutputStream(soc.getOutputStream()); //출력용 스트림
            ois = new ObjectInputStream(soc.getInputStream()); //입력용 스트림

            while (true) {

                int a=0;
                a+=1;
                DBManager.getInstance().SetResponse(ois.readUTF());


                //System.out.println(  DBManager.getInstance().getResponse());
            }
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();

        } catch (IOException e1) {
            e1.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {


        super.onPostExecute(result);
    }

}
