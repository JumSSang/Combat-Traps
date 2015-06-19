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
    public Vec2F m_position;
    private float m_x;
    private float m_y;


    BoundingSpear(float x,float y,float r)
    {
        m_radius=r*100;
        m_x=x;
        m_y=y;

        m_position=new Vec2F(m_x,m_y);
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
    public Vec2F middlePosition;
    public PathFinder myPath;
    public Unit myUnitObject;
    public Unit mMyTarget;
    public int mHp;
    public int mSpeed;
    public PathFinder.Node findedPath;
    public double myTime;
    public int mType=0;

    public Rect originHP;
    public Rect destHP;
    public Vec2F DrawPosition;

    public Unit_Imfor my_enemy; //적정보
    public boolean b_myUnit=false;
    public double myAttackDelayTime=0;

    public SpriteControl m_effect;
    public Vec2 mVSpeed;
    private boolean mThisMove=false;
    public boolean nextstate=false; //다음 상태로 들어가기 위해 필요한 상태

    public Vec2 m_moveVector;
    public int count=0;
    public int m_range=2; //유닛이 전투 상태에 돌입하게 해주는 바운딩 영역의 반지름이 된다.
    public float m_ac_range=2.0f; //유닛이 상대를 인식하게 되는 바운딩 거리
 //   private int range=2;
    public BoundingSpear m_BoundingSpear;
    public BoundingSpear m_battleBounding;

    public boolean m_attck=false;
    public int findingTilenumber=0;
    public int m_time_pathfinder=0;
    public Vec2F m_SpeedVecrt;
    public float m_distance=0;
    private int tempHp;

    public boolean boom_start=false;
    public boolean boom_erase=false;





    private int state=1; //0은 평화 1은 이동 2는 전투


    public void setMovestate(boolean a)
    {
        this.mThisMove=a;
    }
    public boolean getMovestate()
    {
        return mThisMove;
    }
    public Unit_Imfor(Unit myUnitObject, int hp, int mSpeed, int type,boolean b_myUnit) {
            myPath=new PathFinder();
            this.mHp=hp;
        this.tempHp=hp;
            this.mSpeed=mSpeed;
            this.myUnitObject=myUnitObject;
            this.myTime=0;
        this.b_myUnit=b_myUnit;

        switch(type)
        {
            case UnitValue.F_ANNA:
                middlePosition=  new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x))+10+ 16, (float) (-300 + 25 / 2 * (myUnitObject.Postion.y + myUnitObject.Postion.x))-40 + 50);
                DrawPosition = new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x))+10  , (float) (-300 + 25 / 2 * (myUnitObject.Postion.y + myUnitObject.Postion.x))-40);
                break;
            case UnitValue.F_ELSATOWER:
                DrawPosition = new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x)), (float) (-300 + 25 / 2 * (myUnitObject.Postion.y + myUnitObject.Postion.x)));
                break;
            case UnitValue.F_TOWER:
                DrawPosition = new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x))-15, (float) (-300 + 25 / 2 * (myUnitObject.Postion.y + myUnitObject.Postion.x))-50);
                break;
            case UnitValue.F_BOOM:
                DrawPosition = new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x)), (float) (-300 + 25 / 2 * (myUnitObject.Postion.y + myUnitObject.Postion.x)-25));
                myAttackDelayTime=5;
                break;
            default:
                DrawPosition = new Vec2F((float) (750 + 50 / 2 * (myUnitObject.Postion.y - myUnitObject.Postion.x)), (float) (-300 + 25 / 2 * (myUnitObject.Postion.y + myUnitObject.Postion.x)));
                break;
        }

        m_moveVector=new Vec2((float)0,(float)0);

            this.mType=type;
    }
    public void setRange(int r)
    {
        this.m_range=r;
    }

    public void boundingPositionUpdate(float x,float y)
    {
        switch(this.mType)
        {
            case UnitValue.F_ANNA:
                this.m_battleBounding = new BoundingSpear(x + 16, y + 50, 0.2f); //안나의 바운딩 영역 위치 생성
                this.m_BoundingSpear = new BoundingSpear(x + 15, y + 50, 2);
                break;
            case UnitValue.F_ELSATOWER:
                this.m_battleBounding=new BoundingSpear(x+25,y+25,0.4f);
                this.m_BoundingSpear=new BoundingSpear(x+25,y+25,2);
                break;
            case UnitValue.F_TOWER:
                this.m_battleBounding=new BoundingSpear(x+35,y+75,0.4f);
                this.m_BoundingSpear=new BoundingSpear(x+35,y+75,2);
                break;
            case UnitValue.F_BOOM:
                this.m_battleBounding = new BoundingSpear(x + 20, y+25, 0.2f); //안나의 바운딩 영역 위치 생성
                this.m_BoundingSpear = new BoundingSpear(x + 20, y+25 , 2);
                break;
            default:
                this.m_battleBounding=new BoundingSpear(x+10,y,0.2f);
                this.m_BoundingSpear=new BoundingSpear(x+10,y,2);
                break;
        }

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

    public boolean boundingUnit()
    {

        return false;
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
        destHP =new Rect(dx,
                dy,
                dx+tempHp
                ,dy+5);

    }

    public void Speed(float x,float y)
    {
          this.mVSpeed=new Vec2(x,y);
    }






}
