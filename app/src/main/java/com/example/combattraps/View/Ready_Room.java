package com.example.combattraps.View;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.combattraps.Game.GraphicManager;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.DBManager;
import com.example.combattraps.immortal.Graphic_image;
import com.example.combattraps.immortal.IState;

/**
 * Created by 경민 on 2015-04-18.
 */
public class Ready_Room implements IState {
    @Override
    public void Init() {
        GraphicManager.getInstance().Init();


    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        Paint paint=new Paint();
        paint.setTextSize(20);

        paint.setColor(Color.BLACK);
        Rect logoRect;
        int logo_left=(int)GraphicManager.getInstance().m_Width/20;
        int logo_right=(int)GraphicManager.getInstance().m_Height/40*31;

        logoRect=new Rect(logo_left-5,logo_right-5,logo_left+155,logo_right+155);
       GraphicManager.getInstance().background1.Draw(canvas);
       GraphicManager.getInstance().view_roby_bar.Draw(canvas,0,(int)GraphicManager.getInstance().m_Height/20*15 );
       // GraphicManager.getInstance().btn_dec_setting.ButtonDraw(canvas,false,0, GraphicManager.getInstance().m_Height-200);
       // GraphicManager.getInstance().btn_store.ButtonDraw(canvas,false, GraphicManager.getInstance().m_Width/10, GraphicManager.getInstance().m_Height/10*7);
        GraphicManager.getInstance().btn_start.ButtonDraw(canvas,false,GraphicManager.getInstance().m_Width/10*8, GraphicManager.getInstance().m_Height/10*5+50);
        canvas.drawRect(logoRect,paint);


        GraphicManager.getInstance().user_logo_1.Draw(canvas,(int)GraphicManager.getInstance().m_Width/20,(int)GraphicManager.getInstance().m_Height/40*31);
        canvas.drawText(""+DBManager.getInstance().GetID(),(int)GraphicManager.getInstance().m_Width/20+155,(int)GraphicManager.getInstance().m_Height/40*33,paint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }
}
