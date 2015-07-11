package com.example.combattraps.View.Ready_Room_Dir;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.immortal.DBManager;

/**
 * Created by 경민 on 2015-04-21.
 */
public class UserInfo {


    Rect logoRect;
    int m_Width;
    int m_Height;
    Paint paint;
    Paint whitePaint;
    Paint teduriPaint;
    Paint goldPaint;
    int logo_left;
    int logo_top;
    public UserInfo(int Width, int Height)
    {
        this.m_Height=Height;
        this.m_Width=Width;
        paint=new Paint();
        goldPaint=new Paint();
        teduriPaint=new Paint();
        whitePaint=new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(25);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);

        goldPaint.setColor(Color.YELLOW);
        goldPaint.setTextSize(25);
        teduriPaint.setTextSize(25);
        teduriPaint.setAntiAlias(true);
        teduriPaint.setColor(Color.BLACK);
        teduriPaint.setStyle(Paint.Style.STROKE);
        teduriPaint.setStrokeWidth(4);
        logo_left=0;
         logo_top=m_Height/20*2+5; //사실 로고 탑임
        logoRect=new Rect(logo_left-5,logo_top-5,logo_left+130,logo_top+130);
    }
    public void onDraw(Canvas canvas)
    {

        canvas.drawText("LV.1", m_Width/100*15, logoRect.top + 30, teduriPaint);
        canvas.drawText("LV.1",m_Width/100*15,logoRect.top+30,goldPaint);
        canvas.drawText(" "+ DBManager.getInstance().GetID(),m_Width/100*20,logoRect.top+30,teduriPaint);
        canvas.drawText(" "+DBManager.getInstance().GetID(),m_Width/100*20,logoRect.top+30,whitePaint); //아아디 출력

        canvas.drawText("길드 :    " +DBManager.getInstance().getGuild(),m_Width/100*15,logoRect.top+100,teduriPaint);
        canvas.drawText("길드 :    " +DBManager.getInstance().getGuild(),m_Width/100*15,logoRect.top+100,goldPaint);


        canvas.drawText(""+DBManager.getInstance().GetEnemy(),(int) GraphicManager.getInstance().m_Width/20*4,(int)GraphicManager.getInstance().m_Height/40*10,whitePaint); //적군아이디 출력
       // canvas.drawText(" "+(int)DBManager.readyroomtime,(int) GraphicManager.getInstance().m_Width/20*4,(int)GraphicManager.getInstance().m_Height/40*15,whitePaint); //적군아이디 출력


        canvas.drawText(""+DBManager.getInstance().GetGold(),m_Width/100*30,m_Height/100*7,teduriPaint); //골드 정보
        canvas.drawText(""+DBManager.getInstance().GetGold(),m_Width/100*30,m_Height/100*7,goldPaint); //골드 정보

        canvas.drawText(""+DBManager.getInstance().GetVictory(),m_Width/100*10,m_Height/100*7,teduriPaint); //승리 횟수
        canvas.drawText(""+DBManager.getInstance().GetVictory(),m_Width/100*10,m_Height/100*7,whitePaint); //승리 횟수


        canvas.drawText(""+DBManager.getInstance().GetCash(),m_Width/100*48,m_Height/100*7,teduriPaint); //캐시
        canvas.drawText(""+DBManager.getInstance().GetCash(),m_Width/100*48,m_Height/100*7,whitePaint); //캐시

    }
}
