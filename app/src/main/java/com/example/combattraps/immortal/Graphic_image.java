package com.example.combattraps.immortal;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by GyungMin on 2015-02-03.
 */
public class Graphic_image {

    public Bitmap m_bitmap;
    protected int m_x;
    protected int m_y;

    public Graphic_image(Bitmap bitmap)
    {
        m_bitmap = bitmap;
        m_x = 0;
        m_y = 0;
    }

    public void SetPosition(int x,int y){
        m_x = x;
        m_y = y;
    }

    public boolean Collusion(float x,float y,float rx,float ry) //마우스x,y 버튼의 리사이징된 x,y 크기
    {
        if(m_x<x && m_x+rx>x && m_y<y &&m_y+ry>y)
        {
            return true;
        }
        else
            return false;
    }
    public void Draw(Canvas canvas,int x,int y)
    {
        canvas.drawBitmap(m_bitmap,x,y,null);
    }

    public void Draw(Canvas canvas){
        canvas.drawBitmap(m_bitmap,m_x,m_y,null);
    }
    public void SetX(int x)
    {
        m_x=x;
    }
    public void SetY(int y) {m_y=y;}
    public int GetX(){
        return m_x;
    }
    public int GetY(){
        return m_y;
    }
    public void resizebitmap(int x,int y)
    {
        m_bitmap=Bitmap.createScaledBitmap(m_bitmap,x, y,true);
    }
    public void ResizeBitmapRate(int ratex,int ratey)
    {

        int iWidth = m_bitmap.getWidth();      //비트맵이미지의 넓이
        int iHeight = m_bitmap.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
       // float rate = 0.0f;

        iWidth=iWidth/ratex;
        iHeight=iHeight/ratey;
        m_bitmap=Bitmap.createScaledBitmap(m_bitmap,iWidth, iHeight,true);
    }







}
