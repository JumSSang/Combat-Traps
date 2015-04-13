package com.example.combattraps.immortal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by GyungMin on 2015-03-26.
 */


public class SpriteControl extends Graphic_image {
    public Rect[][] Move;
    public Rect[][] Attack;
    public Rect[][] Death;
    static final int MOVE=1;
    static final int ATTACK=2;
    static final int DEATH=3;
    public int mDirection=0;

    private int mFPS;
    private int mNoOfFrames;
    private int mCurrentFrame;

    private long mFrameTimer;
    protected boolean mbReply =true;
    protected boolean mbEnd = false;
    public Vec2 Postion;








    public SpriteControl(Bitmap bitmap) {

        super(bitmap);
        Postion=new Vec2(0,0);
        mFrameTimer =0;
        mCurrentFrame =0;
        Move=new Rect[10][10];
        Attack=new Rect[10][10];
        Death=new Rect[10][10];

    }
    public void SetPos(int x,int y)
    {
        Postion.x=x;
        Postion.y=y;
    }
    public void noAnimation(int width,int height,int state)
    {
        int left=0;
        int top=0;
        int right=left+width;
        int bottom=top+height;
        for(int i=0;i<8;i++) {
            Move[i][0] = new Rect(left, top, right, bottom);
            Attack[i][0]=new Rect(left, top, right, bottom);
            Death[i][0]=new Rect(left, top, right, bottom);
            left+=width;
            right+=width;
        }
        mNoOfFrames=0;
    }

    public void ElsaTower(int FPS)
    {
        int height=8;
        int start_width=8;
        mFPS=1000 /FPS;
        noAnimation(100, 159, MOVE);
        mNoOfFrames=8;
    }

    public void Effect(int FPS)
    {
        int height;
        int start_width=9;
        mFPS=1000/FPS;
        Animation(1,10,700/3,390/3,1);
        mNoOfFrames=8;
       // m_effect.InitSpriteData(0,390/3,700/3,1,10);
    }
    public void Anna(int FPS)
    {
        int height=8;
        int start_width=8;
        mFPS=100 /FPS;
        mNoOfFrames=8;
        noAnimation( 48, 63, MOVE);
    }
    public void Zombie(int FPS)
    {

    }
    public void ArchorTower(int FPS)
    {

    }
    public void ZumpingTrap(int FPS)
    {

    }
    public void Cannon(int FPS)
    {

    }



    public void Animation(int height,int width,int Width,int Height,int state)
    {

        int move_default=height;

        for(int i=0;i<height;i++)
        {
            int left=0;
            int right=0+Width;
            int top=0;
            int bottom=0+Height;
               switch(state)
               {
                   case MOVE:
                       for (int j = 0; j <width; j++) {
                           Move[i][j] = new Rect(left, top, right, bottom);
                           left += Width;
                           right += Width;
                       }
                       break;
                   case ATTACK:
                       for (int j = 0; j < width; j++) {
                           Attack[i][j] = new Rect(left, top, right, bottom);
                           left += Width;
                           right += Width;
                       }
                       break;

               }
            top+=Height;
            bottom+=Height;
            }
        }

    public void PatrolUpdate(long GameTime)
    {
        if (!mbEnd) {

            if (GameTime > mFrameTimer + mFPS) {
                mFrameTimer = GameTime;
                mDirection = (int)(Math.random()*8);

            }
        }
    }

    public void Update(long GameTime) {
        if (!mbEnd) {

            if (GameTime > mFrameTimer + mFPS) {
                mFrameTimer = GameTime;
                mCurrentFrame += 1;
                if (mCurrentFrame >= mNoOfFrames) {
                    if (mbReply) {
                        mCurrentFrame = 0;
                    } else {
                        mbEnd = true;
                    }
                }
            }
        }
    }





    public void Draw(Canvas canvas,int statenumber,float x,float y){
        switch(statenumber)
        {
            case MOVE:
                RectF dest = new  RectF(x, y,x +(Move[0][0].right),
                        y + Move[0][0].bottom);
                canvas.drawBitmap(m_bitmap, Move[mDirection][mCurrentFrame], dest, null);
                break;

            case ATTACK:
                RectF adest = new  RectF(m_x, m_y,m_x +(Attack[0][0].right),
                        m_y + Move[0][0].bottom);
                canvas.drawBitmap(m_bitmap, Attack[mDirection][mCurrentFrame], adest, null);
                break;
            case DEATH:
                RectF ddest = new  RectF(m_x, m_y,m_x +(Death[0][0].right),
                        m_y + Move[0][0].bottom);
                canvas.drawBitmap(m_bitmap, Death[mDirection][mCurrentFrame], ddest, null);
                break;

        }

    }

    public void MotionCheck()
    {

    }

}