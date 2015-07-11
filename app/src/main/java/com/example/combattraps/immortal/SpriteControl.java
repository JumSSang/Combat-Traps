package com.example.combattraps.immortal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.combattraps.Game.UnitDirect.UnitValue;
import com.example.combattraps.Values.DirState;
import com.example.combattraps.Values.UnitState;

/**
 * Created by GyungMin on 2015-03-26.
 */


public class SpriteControl extends Graphic_image {
    public Rect[][] Move;
    public Rect[][] Attack;
    public Rect[][] Death;
    public Rect[] m_ButtonRect;
    public Rect[] m_EffectRect;

    public int mDirection = 0;

    public boolean state_click = false;
    private int mFPS;
    private int mNoOfFrames;
    private int mCurrentFrame;

    private long mFrameTimer;
    protected boolean mbReply = true;
    public boolean mbEnd = false;
    public Vec2 Postion;

    public SpriteControl(Bitmap bitmap) {

        super(bitmap);
        Postion = new Vec2(0, 0);
        mFrameTimer = 0;
        mCurrentFrame = 0;
        Move = new Rect[10][10];
        Attack = new Rect[10][10];
        Death = new Rect[10][10];
        m_EffectRect = new Rect[31];
    }

    public void setEndState(boolean state) {
        this.mbEnd = state;
    }

    public boolean getEndState() {
        return mbEnd;
    }

    public void SetPos(int x, int y) {
        Postion.x = x;
        Postion.y = y;
    }

    public void noAnimation(int width, int height, int state) {
        int left = 0;
        int top = 0;
        int right = left + width;
        int bottom = top + height;
        for (int i = 0; i < 8; i++) {
            Move[i][0] = new Rect(left, top, right, bottom);
            Attack[i][0] = new Rect(left, top, right, bottom);
            Death[i][0] = new Rect(left, top, right, bottom);
            left += width;
            right += width;
        }
        mNoOfFrames = 0;
    }


    public void SetBulletRect(int FPS) {
        mFPS = 1000 / FPS;
        //Animation(1,10,100,400,1);
        mNoOfFrames = 10;
        EffectAnimation(2, 5, 100, 75);
    }

    public void SetExplosion(int FPS) {
        mFPS = 1000 / FPS;
        //Animation(1,10,100,400,1);
        mNoOfFrames = 12;
        EffectAnimation(4, 3, 100, 100);
    }


    public void AnnaEffect(int FPS) {
        mFPS = 1000 / FPS;
        //Animation(1,10,100,400,1);
        mNoOfFrames = 30;
        EffectAnimation(3, 10, 120, 90);
    }

    public void ButtonInit(int Width, int Height) {
        int left = 0;
        int top = 0;
        int right = left + Width;
        int bottom = top + Height;
        m_ButtonRect = new Rect[2];
        m_ButtonRect[0] = new Rect(left, top, right, bottom);
        left += Width;
        right += Width;
        m_ButtonRect[1] = new Rect(left, top, right, bottom);

    }

    public void Archer(int FPS) // 아처 유닛의 초기화 부분
    {
        int height = 8;
        int width = 5;
        mFPS = 1000 / FPS;
        Animation(5, 8, 58, 70, 0);
    }

    public void InitDirecion(int Dr, int le,int height) //워크 유닛 스프라이트들의 초기화 부분이다. 2번째 파트
    {
        int left = le;
        int top = 0;
        int bottom = 0;
        int right = le + 70;
        for (int i = 0; i < 5; i++) {

            bottom = top + 90;
            Move[Dr][i] = new Rect(left, top, right, bottom);
            top += height;
        }

        for(int i=0;i<5;i++)
        {
            bottom = top + 90;
            Attack[Dr][i]=new Rect(left,top,right,bottom);
            top += height;
        }
    }

    public void WorkUnitDraw(Canvas canvas, int state, int x, int y) {
        RectF dest = new RectF(x, y, x + (70),
                y + 80);
        switch (state) {
            case UnitState.MOVE:

                if (mDirection >= DirState.LEFT_BOTTOM) {
                    canvas.drawBitmap(m_bitmap, Move[mDirection][mCurrentFrame], dest, null);
                } else {
                    canvas.drawBitmap(m_bitmap, Move[mDirection][mCurrentFrame], dest, null);
                }
                break;

            case UnitState.ATTACK:
                try {

                    if (mDirection >= DirState.LEFT_BOTTOM) {

                        canvas.drawBitmap(m_bitmap, Attack[mDirection][mCurrentFrame], dest, null);
                    } else {
                        canvas.drawBitmap(m_bitmap, Attack[mDirection][mCurrentFrame], dest, null);
                    }
                }
                catch(NullPointerException a)
                {

                 }
                break;
            case UnitState.DEATH:
                try {
                    RectF ddest = new RectF(m_x, m_y, m_x + (Death[0][0].right),
                            m_y + Move[0][0].bottom);
                    canvas.drawBitmap(m_bitmap, Death[mDirection][mCurrentFrame], ddest, null);
                }
                catch(NullPointerException e) {

                }
                break;

        }
    }

    public void CreateWorkUnit(int FPS,int type)//워크 유닛 스프라이트들의 초기화 부분이다. 1번째 파트
    {
        mFPS = 1000 / 10;
        this.mDirection = DirState.LEFT;
        int height=90;
            InitDirecion(DirState.TOP, 0, height);
            InitDirecion(DirState.RIGHT_TOP, 70, height);
            InitDirecion(DirState.RIGHT, 140, height);
            InitDirecion(DirState.RIGHT_BOTTOM, 210, height);
            InitDirecion(DirState.BOTTOM, 280, height);
            InitDirecion(DirState.LEFT_TOP, 210, height);
            InitDirecion(DirState.LEFT, 140, height);
            InitDirecion(DirState.LEFT_BOTTOM, 70, height);
        //this.img_mirror();
        mNoOfFrames = 4;
/*        Move[UnitState.MOVE][2];
        Move[UnitState.MOVE][3];
        Move[UnitState.MOVE][4];*/


    }

    public void ElsaTower(int FPS) //엘사 타워 애니메이션
    {

        mFPS = 1000 / FPS;

        //noAnimation(100, 159, UnitState.MOVE);
        int left = 0;
        int top = 0;
        int right = left + 100;
        int bottom = top + 159;

        Move[DirState.BOTTOM][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.LEFT_BOTTOM][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.LEFT][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.LEFT_TOP][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.TOP][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.RIGHT_TOP][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.RIGHT][0] = new Rect(left, top, right, bottom);
        left += 100;
        right = left + 100;
        Move[DirState.RIGHT_BOTTOM][0] = new Rect(left, top, right, bottom);
        mNoOfFrames = 0;
    }

    public void Air(int FPS) //비행기 애니메이션
    {
        int height;
        int start_width = 9;
        mFPS = 1000 / FPS;
        Animation(1, 2, 150, 150, 1);
        mNoOfFrames = 2;
    }

    public void Effect(int FPS) //이펙트 출력
    {

        mFPS = 1000 / FPS;
        //Animation(1,10,100,400,1);
        mNoOfFrames = 9;
        Animation(1, 9, 212, 100, 1);
        // m_effect.InitSpriteData(0,390/3,700/3,1,10);
    }

    public void Anna(int FPS) {
        int height = 8;
        int start_width = 8;
        mFPS = 100 / FPS;
        mNoOfFrames = 8;
        noAnimation(48, 63, UnitState.MOVE);
    }

    public void Zombie(int FPS) {

    }

    public void ArchorTower(int FPS) {

    }

    public void ZumpingTrap(int FPS) {

    }

    public void Cannon(int FPS) {

    }

    public void EffectAnimation(int height, int width, int Width, int Height) //이펙트 사각형 초기화부분이다.
    {
        int left = 0;
        int right = 0 + Width;
        int top = 0;
        int bottom = 0 + Height;
        int count = 0;
        for (int i = 0; i < height; i++) {
            left = 0;
            right = 0 + Width;
            for (int j = 0; j < width; j++) {
                m_EffectRect[count] = new Rect(left, top, right, bottom);
                left += Width;
                right += Width;
                count += 1;
            }
            top += Height;
            bottom += Height;
        }
    }


    //애니메이션을 출력하는 부분이다.
    //방향성을 가지고있는 케릭터 애니메이션 처리를 담당한다. 사각형 부분 초기화임 ㅇㅇㅇㅇ
    public void Animation(int height, int width, int Width, int Height, int state) {

        int move_default = height;

        for (int i = 0; i < height; i++) {
            int left = 0;
            int right = 0 + Width;
            int top = 0;
            int bottom = 0 + Height;
            switch (state) {
                case UnitState.MOVE:
                    for (int j = 0; j < width; j++) {
                        Move[i][j] = new Rect(left, top, right, bottom);
                        left += Width;
                        right += Width;
                    }
                    break;
                case UnitState.ATTACK:
                    for (int j = 0; j < width; j++) {
                        Attack[i][j] = new Rect(left, top, right, bottom);
                        left += Width;
                        right += Width;
                    }
                    break;

            }
            top += Height;
            bottom += Height;
        }
    }

    // 정찰 하는 애니메이션을 담당하는 부분이다.,
    public void PatrolUpdate(long GameTime) //엘사 타워의 랜덤 에니메이션 출력 업데이트 부분이다.
    {
        if (!mbEnd) {

            if (GameTime > mFrameTimer + mFPS) {
                mFrameTimer = GameTime;
                // mDirection = (int)(Math.random()*8);

            }
        }
    }


    public void Update(long GameTime) {  //일반적인 애니메이션의 업데이트 부분이다.
        if (!mbEnd) {

            if (GameTime > mFrameTimer + mFPS) {
                mFrameTimer = GameTime;
                mCurrentFrame += 1;
                if (mCurrentFrame >= mNoOfFrames) {
                    if (mbReply) {
                        mCurrentFrame = 0;
                    } else {
                        mbEnd = false;
                    }
                }
            }
        }
    }

    public void OneUpdate(long GameTime) {  //일반적인 애니메이션의 업데이트 부분이다.
        if (!mbEnd) {

            if (GameTime > mFrameTimer + mFPS) {
                mFrameTimer = GameTime;
                mCurrentFrame += 1;
                if (mCurrentFrame >= mNoOfFrames) {
                    if (mbReply) {
                        mCurrentFrame = 0;
                        mbEnd = true;
                    } else {
                        mbEnd = true;
                    }

                }
            }
        }
    }


    //버튼을 그려주는 부분이다.
    public void ButtonDraw(Canvas canvas, boolean click, float x, float y) {
        if (click == true) {
            RectF dest = new RectF(x, y, x + (m_ButtonRect[0].right),
                    y + m_ButtonRect[0].bottom);
            canvas.drawBitmap(m_bitmap, m_ButtonRect[1], dest, null);
        } else {
            RectF dest = new RectF(x, y, x + (m_ButtonRect[0].right),
                    y + m_ButtonRect[0].bottom);
            canvas.drawBitmap(m_bitmap, m_ButtonRect[0], dest, null);
        }
    }

    public void EffectDraw(Canvas canvas, float x, float y) {

        RectF dest = new RectF(x, y, x + (m_EffectRect[0].right),
                y + m_EffectRect[0].bottom);
        canvas.drawBitmap(m_bitmap, m_EffectRect[mCurrentFrame], dest, null);


    }

    public void ElsaTowerDraw(Canvas canvas, int statenumber, float x, float y)
    {
        //스프라이트 특이성 때문에 다른곳에서 그려주게 만들었다;;;; (다음부터 통일되게 만들자 ㅠㅠ)
        switch(statenumber)
        {
            case UnitState.MOVE:
                RectF dest = new  RectF(x, y,x +(Move[DirState.BOTTOM][0].right),
                        y + Move[DirState.BOTTOM][0].bottom);
                canvas.drawBitmap(m_bitmap, Move[mDirection][mCurrentFrame], dest, null);
                break;

            case UnitState.ATTACK:
                RectF adest = new  RectF(m_x, m_y,m_x +(Move[DirState.BOTTOM][0].right),
                        m_y + Move[DirState.BOTTOM][0].bottom);
                canvas.drawBitmap(m_bitmap, Attack[mDirection][mCurrentFrame], adest, null);
                break;
            case UnitState.DEATH:

                RectF ddest = new  RectF(m_x, m_y,m_x +(Move[DirState.BOTTOM][0].right),
                        m_y + Move[DirState.BOTTOM][0].bottom);
                canvas.drawBitmap(m_bitmap, Death[mDirection][mCurrentFrame], ddest, null);
                break;

        }
    }

    public void Draw(Canvas canvas,int statenumber,float x,float y){
        switch(statenumber)
        {
            case UnitState.MOVE:
                RectF dest = new  RectF(x, y,x +(Move[DirState.TOP][0].right),
                        y + Move[DirState.TOP][0].bottom);
                canvas.drawBitmap(m_bitmap, Move[mDirection][mCurrentFrame], dest, null);
                break;

            case UnitState.ATTACK:
                RectF adest = new  RectF(m_x, m_y,m_x +(Attack[0][0].right),
                        m_y + Move[0][0].bottom);
                canvas.drawBitmap(m_bitmap, Attack[mDirection][mCurrentFrame], adest, null);
                break;
            case UnitState.DEATH:

                RectF ddest = new  RectF(m_x, m_y,m_x +(Death[0][0].right),
                        m_y + Move[0][0].bottom);
                canvas.drawBitmap(m_bitmap, Death[mDirection][mCurrentFrame], ddest, null);
                break;

        }

    }

    public void MotionCheck()
    {

    }




    public void bulletInit(int FPS)  //Init역할
    {
        mFPS=1000/FPS;
        //Animation(1,10,100,400,1);
        mNoOfFrames=9;
        Animation(1,9,212,100,1);
        // m_effect.InitSpriteData(0,390/3,700/3,1,10);
    }



}