package com.example.combattraps.immortal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by 경민 on 2015-05-03.
 */
public class TextEffect {
    private Paint m_TeduriPaint;
    private Paint m_WhitePaint;
    private double m_delayTime=0;
    private String[] m_callText;
    private int count=0;
    private boolean state=false;
    private Paint fadeFaim;
    private int m_fade_alpah;
    private double m_fade_time;
    private String m_NormalText;
    public TextEffect(String a) {
        m_NormalText=a;
        m_fade_alpah=0;
        m_WhitePaint=new Paint();
        m_TeduriPaint=new Paint();
        m_WhitePaint.setTextSize(100);
        m_WhitePaint.setColor(Color.WHITE);
        m_callText=new String[a.length()];
        for (int i = 0; i < a.length(); i++)
        {
            for (int j = 0; j <= i; j++) {
                String aa = a.toUpperCase();
                m_callText[j]= (String)""+aa.charAt(j);
                //m_Width / 20 * (9 + j), m_Height / 2, m_WhitePaint);
            }
        }

    }
    public TextEffect(String a,int fade_a)
    {
        m_NormalText=a;
        m_fade_alpah=0;
        m_WhitePaint=new Paint();
        m_TeduriPaint=new Paint();
        m_WhitePaint.setTextSize(100);
        m_WhitePaint.setColor(Color.WHITE);
        m_callText=new String[a.length()];
        for (int i = 0; i < a.length(); i++)
        {
            for (int j = 0; j <= i; j++) {
                String aa = a.toUpperCase();
                m_callText[j]= (String)""+aa.charAt(j);
                //m_Width / 20 * (9 + j), m_Height / 2, m_WhitePaint);
            }
        }
    }

    public boolean getState()
    {
        return state;
    }
    public void SlowText(Canvas canvas, float m_Width, float m_Height,double timeDelta) {
        if(state==false) {
            for (int i = 0; i < count; i++) {

                canvas.drawText(m_callText[i], m_Width / 20 * (5 + i*2), m_Height / 2, m_WhitePaint);
            }
            if (count < m_callText.length) {
                m_delayTime += timeDelta;
                if (m_delayTime > 0.3) {
                    Sound.getInstance().play(6);
                    count += 1;
                    m_delayTime = 0;
                }
            }
            else
            {
                m_delayTime+=timeDelta;
                if(m_delayTime>1) {
                    state = true;
                    return;
                }
            }
        }




    }
    public void FadeOutText(Canvas canvas,float m_Width,float m_Height,double timeDelta)
    {
        m_WhitePaint.setColor(Color.argb(m_fade_alpah,0,0,0));
        // canvas.drawRect(m_Width/20*12, m_Height/2,m_Width,m_Height,m_WhitePaint);
        canvas.drawText(m_NormalText, m_Width / 20 * 12, m_Height / 2, m_WhitePaint);
        if(m_fade_alpah<255) {
            m_fade_time += timeDelta;
            if (m_fade_time > 0.01f) {
                m_fade_alpah += 2;
                if (m_fade_alpah > 255) {
                    m_fade_alpah = 255;
                }
                m_fade_time = 0;
            }
        }
        else
        {
            state=true;
            return;
        }


    }
    public void FadeInText(Canvas canvas,float m_Width,float m_Height,double timeDelta)
    {
        if(m_fade_alpah>0) {
            m_fade_time += timeDelta;
            if (m_fade_time > 0.001f) {
                m_fade_alpah -= 2;
                if(m_fade_alpah<0)
                {
                    m_fade_alpah=0;
                }
                m_fade_time = 0;
            }
        }
        else
        {
            state=true;
            return;
        }

        m_WhitePaint.setColor(Color.argb(m_fade_alpah,0,0,0));
       // canvas.drawRect(m_Width/20*12, m_Height/2,m_Width,m_Height,m_WhitePaint);
        canvas.drawText(m_NormalText, m_Width / 20 * 12, m_Height / 2, m_WhitePaint);
    }

}
