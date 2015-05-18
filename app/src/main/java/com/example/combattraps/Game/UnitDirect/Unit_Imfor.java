package com.example.combattraps.Game.UnitDirect;

import android.graphics.Rect;

import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.Game.PathFinder;
import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2;
import com.example.combattraps.immortal.Vec2F;

/**
 * Created by GyungMin on 2015-03-29.
 */
class BoundingSpear
{
    private float m_radius;
    private float m_x;
    private float m_y;
    private int m_middlePosition;

    BoundingSpear(float x,float y,float r)
    {
        m_radius=r*100;
        m_x=x;
        m_y=y;
    }

    public float GetX()
    {
        return m_x;
    }
    public float GetY()
    {
        return m_y;
    }
    public float GetRadius()
    {
        return m_radius;
    }
    public boolean collision(int x,int y)
    {

     return false;
    }
}
public class Unit_Imfor {
    public PathFinder myPath;
    public Unit myUnitObject;
    public Unit mMyTarget;
    public int mHp;
    public int mSpeed;
    public PathFinder.Node findedPath;
    public double myTime;
    public int mType=0;
    public Rect originHP;
    public Vec2F DrawPosition;
    public Vec2 m_RealPosition;
    public Unit_Imfor my_enemy;
    public double myAttackDelayTime=0;
    public SpriteControl m_effect;
    public Vec2 mVSpeed;
    public boolean mThisMove=false;
    public boolean nextstate=false; //다음 상태로 들어가기 위해 필요한 상태
    public Vec2 m_moveVector;
    public int count=0;
    private int range=2;
    public BoundingSpear m_BoundingSpear;
    public boolean m_attck=false;
    public int findingTilenumber=0;
    public int m_time_pathfinder=0;
    public Vec2F m_SpeedVecrt;
    public float m_distance=0;





    private int state=1; //0은 평화 1은 이동 2는 전투


    public Unit_Imfor(Unit myUnitObject, int hp, int mSpeed, int type) {
            myPath=new PathFinder();
            this.mHp=hp;
            this.mSpeed=mSpeed;
            this.myUnitObject=myUnitObject;
            this.myTime=0;
        float tempx=(float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x));
        float tempy=(float)(-300 + 25 / 2 * (myUnitObject.Postion.y+myUnitObject.Postion.x));
        DrawPosition=new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x)) ,(float)(-300 + 25 / 2 * (myUnitObject.Postion.y+myUnitObject.Postion.x)));
        m_RealPosition=new Vec2((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x)) ,(float)(-300 + 25 / 2 * (myUnitObject.Postion.y+myUnitObject.Postion.x)));
        m_moveVector=new Vec2((float)0,(float)0);
        //m_BoundingSpear=new Vec2(tempx+40,tempy+40);
            this.mType=type;
    }
    public void SetRange(int r)
    {
        this.range=r;
    }
    public void IniteBouningSpear(float x,float y)
    {
        this.m_BoundingSpear=new BoundingSpear(x,y,this.range);
    }
    public void setState(int a)
    {
        this.state=a;
    }
    public int getState()
    {
        return this.state;
    }
    public void InitEffect(int Value)
    {
        if(Value==UnitValue.F_ELSATOWER) {
            m_effect = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
            m_effect.resizebitmap(2000, 100);
            m_effect.Effect(1);
        }
        else if(Value==UnitValue.F_ANNA)
        {
            m_effect =new SpriteControl(GraphicManager.getInstance().m_anna_punch.m_bitmap);
            m_effect.AnnaEffect(200);
         }

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
        findedPath=myPath.find(mMyTarget.Postion,myUnitObject.Postion);
    }

    //750 + 50 / 2 * (y - x)
    public void Hpbar()
    {
        int x=(int)DrawPosition.x;
        int y=(int)DrawPosition.y;
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
