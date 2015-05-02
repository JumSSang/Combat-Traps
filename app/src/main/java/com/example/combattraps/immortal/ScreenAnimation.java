package com.example.combattraps.immortal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by 경민 on 2015-05-02.
 */
public class ScreenAnimation {

    private double m_fade_time=0;
    private int m_fade_alpah=0;
    private Paint paint;
    private float m_Width,m_Height;
    private boolean m_AnimationState=true;

    public ScreenAnimation(int m_Width,int m_Height)
    {
        paint=new Paint();
        paint.setColor(Color.argb(m_fade_alpah,0,0,0));
        this.m_Width=m_Width;
        this.m_Height=m_Height;
    }
    public boolean getAnimationState()
    {
        return m_AnimationState;
    }
    public void InitFadeOut()
    {
        m_fade_alpah=0;
    }
    public void InitFadeIn()
    {
        m_fade_alpah=255;
    }


    public void fadeInUpdate(double dt)
    {
        if(m_fade_alpah>0) {
            m_fade_time += dt;
            if (m_fade_time > 0.005f) {
                m_fade_alpah -= 1;
                m_fade_time = 0;
            }
        }
        else
        {
            m_AnimationState=false;
        }
    }

    public void fadeOutUpdate(double dt)
    {
        if(m_fade_alpah<255) {
            m_fade_time += dt;
            if (m_fade_time > 0.005f) {
                m_fade_alpah += 1;
                m_fade_time = 0;

            }
        }
        else
        {
            m_AnimationState=false;
        }
    }
    public void fadeDraw(Canvas canvas)
    {
        paint.setColor(Color.argb(m_fade_alpah,0,0,0));
        canvas.drawRect(0,0,m_Width,m_Height,paint);
    }
    public void Draw()
    {

    }
}
