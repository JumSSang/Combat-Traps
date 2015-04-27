package com.example.combattraps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.combattraps.immortal.DBManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;

public class StartActivity extends Activity implements View.OnClickListener {

    EditText edit_password;
    EditText edit_id;
    AlertDialog.Builder builder;    // 여기서 this는 Activity의 this
    boolean connect = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actvity_login);
        Button btnSend = (Button) findViewById(R.id.btn_login);
        btnSend.setOnClickListener(this);
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_password = (EditText) findViewById(R.id.edit_secret);
        builder = new AlertDialog.Builder(this);
        DBManager.getInstance().connection = new NetConnect();
        DBManager.getInstance().connection.execute(null, null, null);
    }

    public void onClick(View v) {

        int a = 0;
        switch (v.getId()) {
            case R.id.btn_login:
                try {
                    connect = true;
                    DBManager.getInstance().connection.oos.writeObject(edit_id.getText() + ":" + edit_password.getText());
                    DBManager.getInstance().connection.oos.flush();
                    while (connect) {
                        if (DBManager.getInstance().getResponse().equals("접속성공")) {
                            DBManager.getInstance().go_robby=1;

                            connect = false;
                            final Intent i = new Intent(this, GameActiviry.class);
                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(StartActivity.this);
                            alert_confirm.setMessage("접속 성공!").setCancelable(false).setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                                    startActivity(i);


                                        }
                                    });
                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                          //  DBManager.getInstance().SetResponse("");
                        }else if (DBManager.getInstance().getResponse().equals("중복로그인")) {
                            connect = false;
                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(StartActivity.this);
                            alert_confirm.setMessage("접속 중복로그인").setCancelable(false).setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 'YES'
                                        }
                                    });
                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                           // DBManager.getInstance().SetResponse("");
                        }

                        else if (DBManager.getInstance().getResponse().equals("접속실패")) {
                            connect = false;
                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(StartActivity.this);
                            alert_confirm.setMessage("접속 실패").setCancelable(false).setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 'YES'
                                        }
                                    });
                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                         //   DBManager.getInstance().SetResponse("");
                        }
                        else
                        {
                            connect=false;
                          //  DBManager.getInstance().SetResponse("");
                        }
                    }
                    //한번에 4kb쓰는게 빠르다. 한번에 패킷을 크게 보내는게 오버헤드가 적다
                    //그것을 만든게 버퍼드 스트림 이다. flush를 할때까지 저장
                    //flush 는 버버드 아웃풋 시스템

                    // connection.ois.readUTF();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;

        }
    }

    public void send_Message(String str) { // 서버로 메세지를 보내는 메소드
        try {

            DBManager.getInstance().connection.oos.writeObject(str);
            DBManager.getInstance().connection.ois.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
