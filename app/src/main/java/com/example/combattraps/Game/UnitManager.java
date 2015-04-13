package com.example.combattraps.Game;

import android.graphics.Rect;

import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2;

/**
 * Created by GyungMin on 2015-03-29.
 */
public class UnitManager {
    public PathFinder myPath;
    public Unit myUnitObject;
    public Unit mMyTarget;
    public int mHp;
    public int mSpeed;
    public PathFinder.Node findedPath;
    public double myTime;
    public int mType=0;
    public Rect originHP;
    public Vec2 DrawPosition;
    public SpriteControl m_effect;
    public Vec2 mVSpeed;
    public boolean mThisMove=false;
    public Vec2 m_moveVector;
    public int count=0;
    public Vec2 m_BoundingSpear;
    public int range=190;
    public float distance;

    UnitManager(Unit myUnitObject,int hp,int mSpeed,int type) {
            myPath=new PathFinder();
            this.mHp=hp;
            this.mSpeed=mSpeed;
            this.myUnitObject=myUnitObject;
            this.myTime=0;
        float tempx=(float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x));
        float tempy=(float)(-300 + 25 / 2 * (myUnitObject.Postion.y+myUnitObject.Postion.x));
        DrawPosition=new Vec2((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x)) ,(float)(-300 + 25 / 2 * (myUnitObject.Postion.y+myUnitObject.Postion.x)));
        m_moveVector=new Vec2((float)0,(float)0);
        m_BoundingSpear=new Vec2(tempx+40,tempy+40);
            this.mType=type;
    }
    public void InitEffect()
    {
        m_effect=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
        m_effect.ResizeBitmapRate(3,3);
    }
    public void minus(Vec2 a,Vec2 b)
    {
        float x=a.fx;
        float y=a.fy;
        float x2=b.fx;
        float y2=b.fy;
        m_moveVector=new Vec2(x2-x,y2-y);
    }
    public void WhoEnemy(Unit mMyTarget)
    {
        this.mMyTarget=mMyTarget;
        Targeting();
    }
    public void Targeting()
    {
        findedPath=myPath.find(mMyTarget,myUnitObject);
    }

    //750 + 50 / 2 * (y - x)
    public void Hpbar()
    {
        int x=(int)DrawPosition.fx;
        int y=(int)DrawPosition.fy;
        int dx=x;
        int dy= y;
        originHP=new Rect(dx,
                dy,
                dx+mHp,
                dy+5);

    }

    public void Speed(float x,float y)
    {
          this.mVSpeed=new Vec2(x,y);
    }






}
