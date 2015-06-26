package com.example.combattraps.Game.UnitDirect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.combattraps.Game_NetWork.NetState;
import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.DBManager;
import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2;
import com.example.combattraps.immortal.Vec2F;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Md by 경민 on 2015-04-27.
 */

/*유닛의 추가 삭제 모든것을 관리한다.
적군의 유닛도 관리해 준다.
유닛의 출력 순서도 여기서 담당한다.

 */

public class UnitManager {

    private boolean m_roundStart = false;
    private boolean m_Round_end = false;
    public ArrayList<Unit_Imfor> MyUnits;
    public ArrayList<Unit_Imfor> EnemyUnits;
    public ArrayList<Unit_Imfor> Enviroment;
    public ArrayList <Explosive>ExplosiveList;
    private ArrayList<MissleManager> BulletList;
  //  public SpriteControl TestSprite;


    public ArrayList<Unit_Imfor> UnitList;
    public boolean attack = false;

    public UnitManager() {
        MyUnits = new ArrayList<Unit_Imfor>();
        EnemyUnits = new ArrayList<Unit_Imfor>();
        BulletList = new ArrayList<MissleManager>();
        UnitList = new ArrayList<Unit_Imfor>();
        Enviroment = new ArrayList<Unit_Imfor>();
       // boomer_test = new Explosive(new Vec2F(500, 500), 50, 10, true, 0);
        ExplosiveList=new ArrayList<Explosive>();

     //   TestSprite = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.point_bullet));
      //  TestSprite.SetBulletRect(10); //업데이트 아니고 업데이트 갱신 주기 설정이다.


    }

    public void unitSort() {
        new UnitSort(this.UnitList);
    }

    public void remove(ArrayList<Unit_Imfor> list, Unit_Imfor a) {
        if (a.mHp <= 0 || a.boom_erase==true) {
            list.remove(a);
        }
    }

    public void setRoundState(boolean state) {
        m_roundStart = state;
    }

    public void add() {


    }

    public void RenderUnit(Canvas canvas) {
        Paint paint;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < UnitList.size(); i++) {
            int x = UnitList.get(i).myUnitObject.Postion.x;
            int y = UnitList.get(i).myUnitObject.Postion.y;
            //750 + 50 / 2 * (y - x), -300 + 30 / 2 * (y+ x)
            switch (UnitList.get(i).mType) {
                case UnitValue.F_ELSATOWER:
                    // paint.setARGB(50, 255, 0, 0);

                        if(UnitList.get(i).b_myUnit)
                        {
                            paint.setColor(Color.GREEN);
                        }
                        else
                        {
                            paint.setColor(Color.RED);
                        }
                    paint.setColor(Color.GREEN);

                    //바운딩영역 확인하는 원


                    paint.setColor(Color.WHITE);
                    UnitList.get(i).myUnitObject.Draw(canvas, 1, UnitList.get(i).DrawPosition.x, UnitList.get(i).DrawPosition.y - 110);

                    if (UnitList.get(i).getState() == UnitValue.S_BATTLE) {
                        canvas.drawText("적발견", UnitList.get(i).DrawPosition.x + 30, UnitList.get(i).DrawPosition.y - 120, paint);
                    } else {
                        canvas.drawText("?", UnitList.get(i).DrawPosition.x + 30, UnitList.get(i).DrawPosition.y - 120, paint);
                    }
                    paint.setARGB(50, 100, 100, 100);
                    //canvas.drawCircle(UnitList.get(i).m_BoundingSpear.GetX(),UnitList.get(i).m_BoundingSpear.GetY(),UnitList.get(i).m_BoundingSpear.GetRadius(),paint);

                    UnitList.get(i).Hpbar();
                    UnitList.get(i).originHP.top -= 110;
                    UnitList.get(i).originHP.bottom -= 110;
                    UnitList.get(i).m_effect.Effect(30);
                    UnitList.get(i).m_effect.Draw(canvas, 1, UnitList.get(i).DrawPosition.x - 30, UnitList.get(i).DrawPosition.y - 50);

                    if(UnitList.get(i).b_myUnit)
                    {
                        paint.setColor(Color.GREEN);
                    }
                    else
                    {
                        paint.setColor(Color.RED);
                    }
                  //  canvas.drawCircle(UnitList.get(i).m_battleBounding.GetX(), UnitList.get(i).m_battleBounding.GetY(), UnitList.get(i).m_battleBounding.GetRadius(), paint);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    break;


                case UnitValue.F_ANNA:
                    UnitList.get(i).myUnitObject.Draw(canvas, 1, UnitList.get(i).DrawPosition.x, UnitList.get(i).DrawPosition.y);
                    paint.setARGB(50, 100, 100, 100);
                    UnitList.get(i).Hpbar();

                    UnitList.get(i).originHP.top -= 40;
                    UnitList.get(i).originHP.bottom -= 40;

                    UnitList.get(i).destHP.top -= 40;
                    UnitList.get(i).destHP.bottom -= 40;
                    // canvas.drawCircle(UnitList.get(i).m_BoundingSpear.GetX(),UnitList.get(i).m_BoundingSpear.GetY(),UnitList.get(i).m_BoundingSpear.GetRadius(),paint);


                    paint.setColor(Color.WHITE);
                    canvas.drawRect(UnitList.get(i).destHP, paint);
                    if(UnitList.get(i).b_myUnit)
                    {
                        paint.setColor(Color.GREEN);
                    }
                    else
                    {
                        paint.setColor(Color.RED);
                    }
                   // canvas.drawCircle(UnitList.get(i).m_battleBounding.GetX(), UnitList.get(i).m_battleBounding.GetY(), UnitList.get(i).m_battleBounding.GetRadius(), paint);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    switch (UnitList.get(i).getState()) {
                        case UnitValue.S_MOVE:
                             canvas.drawText("타운홀" , UnitList.get(i).DrawPosition.x + 10, UnitList.get(i).DrawPosition.y - 50, paint);
                            break;
                        case UnitValue.S_BATTLE_MOVE:
                            canvas.drawText("바운딩" , UnitList.get(i).DrawPosition.x + 10, UnitList.get(i).DrawPosition.y - 50, paint);
                            break;
                        case UnitValue.S_REMOVE:
                            canvas.drawText("적제거" , UnitList.get(i).DrawPosition.x + 10, UnitList.get(i).DrawPosition.y - 50, paint);
                            break;

                    }


                    if (UnitList.get(i).m_attck) {
                        UnitList.get(i).m_effect.EffectDraw(canvas, UnitList.get(i).my_enemy.DrawPosition.x - 30, UnitList.get(i).my_enemy.DrawPosition.y - 60); // 검색해라!
                        if (UnitList.get(i).m_effect.mbEnd) {
                            UnitList.get(i).m_attck = false;
                            UnitList.get(i).m_effect.mbEnd = false;
                        }
                    }
                    break;
                case UnitValue.F_ROCK1:
                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y - 25);
                    break;
                case UnitValue.F_JUMPINGTRAP:
                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y);
                    break;
                case UnitValue.F_TOWNHALL:
                    if (UnitList.get(i).mHp > 0)
                        UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x + 25, (int) UnitList.get(i).DrawPosition.y - 175);
                    break;
                case UnitValue.F_TREE1:
                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x - 25, (int) UnitList.get(i).DrawPosition.y - 50);
                    break;
                case UnitValue.F_ROCKE2:
                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y - 25);
                    break;
                case UnitValue.F_TOWER:
                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y);
                   //canvas.drawCircle(UnitList.get(i).m_battleBounding.GetX(),UnitList.get(i).m_battleBounding.GetY(),UnitList.get(i).m_battleBounding.GetRadius(),paint);
                    UnitList.get(i).Hpbar();
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    break;
                case UnitValue.F_BOOM:
                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y);
                 //   canvas.drawCircle(UnitList.get(i).m_battleBounding.GetX(),UnitList.get(i).m_battleBounding.GetY(),UnitList.get(i).m_battleBounding.GetRadius(),paint);
                    UnitList.get(i).Hpbar();
                    if(UnitList.get(i).b_myUnit)
                    {
                        paint.setColor(Color.GREEN);
                    }
                    else
                    {
                        paint.setColor(Color.RED);
                    }
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    if( UnitList.get(i).boom_start)
                    {
                        canvas.drawText(""+(int)UnitList.get(i).myAttackDelayTime ,UnitList.get(i).m_battleBounding.GetX(),UnitList.get(i).m_battleBounding.GetY()-50,paint);
                    }
                    break;


                default:

                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y);
                    UnitList.get(i).Hpbar();
                    if(UnitList.get(i).b_myUnit)
                    {
                        paint.setColor(Color.GREEN);
                    }
                    else
                    {
                        paint.setColor(Color.RED);
                    }

                    canvas.drawRect(UnitList.get(i).originHP, paint);

                    paint.setColor(Color.WHITE);
                    break;
            }
        }
        //  TestSprite.Update(System.currentTimeMillis());
        // TestSprite.EffectDraw(canvas, 100, 100);
        for (int i = 0; i < BulletList.size(); i++) {
            BulletList.get(i).draw(canvas);
        }

        canvas.drawText("" + BulletList.size(), 500, 500, paint);
        ExplosionDraw(canvas);

    }

    public void animationUpdate(Unit_Imfor a) {

        switch (a.mType) {
            case UnitValue.F_ELSATOWER:
                a.myUnitObject.PatrolUpdate(System.currentTimeMillis());
                a.m_effect.Update(System.currentTimeMillis());
                a.boundingPositionUpdate(a.DrawPosition.x, a.DrawPosition.y);
                break;
            case UnitValue.F_ANNA:
                a.boundingPositionUpdate(a.DrawPosition.x, a.DrawPosition.y);
                break;
            case UnitValue.F_ZOMBIE:
                break;
            case UnitValue.F_TOWER:
                a.boundingPositionUpdate(a.DrawPosition.x, a.DrawPosition.y);
                break;
            case UnitValue.F_BOOM:
                a.boundingPositionUpdate(a.DrawPosition.x, a.DrawPosition.y);
                break;
            case UnitValue.F_JUMPINGTRAP:
                break;
            case UnitValue.F_TOWNHALL:
                a.boundingPositionUpdate(a.DrawPosition.x, a.DrawPosition.y);
                break;

            default:

                break;
        }
    }

    public void setRound() {
        m_roundStart = !m_roundStart;
    }

    //유닛에 관환 업데이트 부분

    public void UnitUpdates(double dt)
    {
        for (int i = 0; i < MyUnits.size(); i++) {
            add();//아군 유닛 추가 상태 체크 부분
            //unitSort(MyUnits);//유닛의 출력순서 정렬 부분
            animationUpdate(MyUnits.get(i)); //유닛 애니메이션 업데이트 구현부분
            if(!DBManager.b_wiatFrame) {
                if (m_roundStart == true) {
                    if (EnemyUnits.size() != 0)
                        UnitMonitor(MyUnits.get(i), dt, true);

                }
                remove(MyUnits, MyUnits.get(i));//유닛 제거 체크 부분
            }
        }
        for (int i = 0; i < EnemyUnits.size(); i++) {

            add();//적 유닛 추가 상태 체크  부분
            // unitSort(EnemyUnits);//적유닛의 출력순서 정렬 부분

            animationUpdate(EnemyUnits.get(i));//적 에니메이션 구현 부분
            if(!DBManager.b_wiatFrame) {
                if (m_roundStart == true) {
                    if (MyUnits.size() != 0) {
                        UnitMonitor(EnemyUnits.get(i), dt, false);
                    }
                }
                remove(EnemyUnits, EnemyUnits.get(i));//적 유닛 제거 체크 부분
            }
        }
        //if(UnitValue.getInstance().getGameStart()==true)
        if(!DBManager.b_wiatFrame) {
            if (m_roundStart == true) {
                MoveUpdate(dt);
                MissleUpdate(dt);
                MissleErase();
                for (int i = 0; i < ExplosiveList.size(); i++) {
                    if (ExplosiveList.get(i).life == false) {
                        ExplosiveList.remove(i);
                    }
                }
            }
        }
    }
    public void Update(double dt) {


        UnitList.clear();
        UnitList.addAll(MyUnits);
        UnitList.addAll(EnemyUnits);
        UnitList.addAll(Enviroment);
        unitSort();//유닛의 출력순서 정렬 부분
        if(DBManager.getInstance().getNetState()==NetState.SINGLEGAME)
        {
            UnitUpdates(dt);
        }
        else if(DBManager.getInstance().getNetState()== NetState.MULTIGAME) {

            UnitUpdates(dt);
            DBManager.getInstance().stackCount+=1;
            if( DBManager.getInstance().stackCount>=1) {

                if (DBManager.nextFrame == true) {
                    DBManager.FrameCount += 1;
                    DBManager.b_wiatFrame=false;
                }
                else
                {
                    DBManager.b_wiatFrame=true;
                }
                try {
                    String sendString=null;
                    if(DBManager.EventStack.size()>0)
                    {
                        sendString="";
                    }
                    for(int i=0;i<DBManager.EventStack.size();i++)
                    {
                        sendString+=DBManager.EventStack.get(i);
                    }
                    DBManager.getInstance().sendMessage("true:" + DBManager.FrameCount+":"+sendString+":"+DBManager.getInstance().team);
                    DBManager.EventStack.clear();
                    DBManager.nextFrame = false;
                    DBManager.getInstance().stackCount=0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }






    }

    public void ExplosionDraw(Canvas canvas) {
        for(int i=0;i<ExplosiveList.size();i++) {
            ExplosiveList.get(i).ExplosiveDraw(canvas);
        }
    }
    public void MissleErase() {
        for (int i = 0; i < BulletList.size(); i++) {

            if (BulletList.get(i).state == false) {
                if (BulletList.get(i).m_parents.my_enemy != null) {
                    ExplosiveList.add(new Explosive(new Vec2F(BulletList.get(i).DrawPosition.x+20,BulletList.get(i).DrawPosition.y),40,10,true,0));
                    if(BulletList.get(i).m_parents.b_myUnit) {
                        ExplosiveList.get(ExplosiveList.size() - 1).boom_attack(EnemyUnits);
                    }
                    else
                    {
                        ExplosiveList.get(ExplosiveList.size() - 1).boom_attack(MyUnits);
                    }
                }
                BulletList.remove(i);
            }

            //return;
        }
    }



    public void MissleUpdate(double dt) {
        for (int i = 0; i < BulletList.size(); i++) {
            BulletList.get(i).moveUpdate();
        }
    }

    public void DirectionUpdate(int x, int y, Unit_Imfor a, int type) {

        if (type == UnitValue.F_ANNA) {
            if (x == -1 && y == 1)//left
            {
                a.myUnitObject.mDirection = 1;

            } else if (x == 1 && y == 1)//top
            {
                a.myUnitObject.mDirection = 3;


            } else if (x == 1 && y == -1)//right
            {
                a.myUnitObject.mDirection = 5;


            } else if (x == -1 && y == -1)//bottom
            {
                a.myUnitObject.mDirection = 7;

            } else if (x == 0 && y == 1)//lefttop
            {
                a.myUnitObject.mDirection = 2;

            } else if (x == -1 && y == 0)//leftbottom
            {
                a.myUnitObject.mDirection = 0;

            } else if (x == 1 && y == 0)//righttop
            {
                a.myUnitObject.mDirection = 4;

            } else if (x == 0 && y == -1)//rightbottom
            {
                a.myUnitObject.mDirection = 6;

            }

        }
        if (type == UnitValue.F_ELSATOWER)
        {
        }
    }


    //폭탄 제어 부분
    public void BoomPattern(Unit_Imfor a, boolean myUnit, double dt)
    {
        //아군부분
        if (myUnit == true) {
            if(a.boom_start)
            {
                a.myAttackDelayTime-=dt;
                if(a.myAttackDelayTime<=0)
                {
                    a.boom_erase=true;
                    ExplosiveList.add(new Explosive(new Vec2F(a.m_battleBounding.GetX(),a.m_battleBounding.GetY()),40,10,true,0));
                    ExplosiveList.get(ExplosiveList.size()-1).boom_attack(EnemyUnits);

                }

                return;
            }

            for (int i = 0; i < EnemyUnits.size(); i++) {
                if (Bounding.UnitAABB(a.m_BoundingSpear.m_position, EnemyUnits.get(i).m_battleBounding.m_position, a.m_battleBounding.GetRadius(), EnemyUnits.get(i).m_battleBounding.GetRadius()) ) {
                    a.boom_start=true;
                    return;
                }
            }

        }

        //적부분
        else
        {
            if(a.boom_start)
            {
                a.myAttackDelayTime-=dt;
                if(a.myAttackDelayTime<=0)
                {
                    a.boom_erase=true;
                    ExplosiveList.add(new Explosive(new Vec2F(a.m_battleBounding.GetX(),a.m_battleBounding.GetY()),40,10,true,0));
                    ExplosiveList.get(ExplosiveList.size()-1).boom_attack(MyUnits);

                }

                return;
            }

            for (int i = 0; i < MyUnits.size(); i++) {
                if (MyUnits.get(i) != null &&a!=null) {
                    if (Bounding.UnitAABB(a.m_BoundingSpear.m_position, MyUnits.get(i).m_battleBounding.m_position, a.m_battleBounding.GetRadius(), MyUnits.get(i).m_battleBounding.GetRadius())) {
                        a.boom_start = true;
                        return;
                    }

                }
            }
        }
    }

    //엘사타워 제어부분분
    public void ElsaTowerPattern(Unit_Imfor a, boolean myUnit, double dt) {
        //if (tempEnemy.size() != 0) {

        //아군일 경우
        if (myUnit == true) {
            switch (a.getState()) {
                case UnitValue.S_MOVE:
                    for (int i = 0; i < EnemyUnits.size(); i++) {
                        if (Bounding.UnitAABB(a.m_BoundingSpear.m_position, EnemyUnits.get(i).m_battleBounding.m_position, a.m_BoundingSpear.GetRadius(), EnemyUnits.get(i).m_battleBounding.GetRadius()) ) {


                            switch(EnemyUnits.get(i).mType)
                            {
                                case UnitValue.F_ANNA:
                                    a.my_enemy = EnemyUnits.get(i);
                                    a.setState(UnitValue.S_BATTLE);

                                    return;
                                case UnitValue.F_ELSATOWER:
                                    a.my_enemy = EnemyUnits.get(i);
                                    a.setState(UnitValue.S_BATTLE);
                                    return;

                                case UnitValue.F_TOWER:
                                    a.my_enemy = EnemyUnits.get(i);
                                    a.setState(UnitValue.S_BATTLE);
                                    return;
                                default:
                                    break;

                            }

                        }
                    }
                    break;
                case UnitValue.S_BATTLE: //아군 엘사 타워 적 찾는부분
                    a.myAttackDelayTime += dt;
                    if (a.myAttackDelayTime > 1) {

                        if (Bounding.UnitAABB(a.m_BoundingSpear.m_position, a.my_enemy.m_BoundingSpear.m_position, a.m_BoundingSpear.GetRadius(), a.my_enemy.m_BoundingSpear.GetRadius()) && a.my_enemy.mHp>0) {
                            Vec2F tempVect;
                            switch(a.my_enemy.mType)
                            {
                                case UnitValue.F_ANNA:
                                    tempVect= new Vec2F(a.my_enemy.m_battleBounding.GetX()-35, a.my_enemy.m_battleBounding.GetY()-25);
                                    break;
                                case UnitValue.F_TOWER:
                                    tempVect= new Vec2F(a.my_enemy.m_battleBounding.GetX(), a.my_enemy.m_battleBounding.GetY());
                                    break;
                                default:
                                    tempVect= new Vec2F(a.my_enemy.m_battleBounding.GetX(), a.my_enemy.m_battleBounding.GetY());
                                    break;
                            }
                            BulletList.add(new MissleManager(a.DrawPosition, tempVect, 3, 7, myUnit, 0, a));
                            a.myAttackDelayTime = 0;
                            a.setState(UnitValue.S_MOVE);
                            return;
                        } else {
                            a.myAttackDelayTime = 0;
                            a.setState(UnitValue.S_MOVE);
                            return;
                        }

                    }
                    break;

            }
        } else {
            switch (a.getState()) {
                case UnitValue.S_MOVE: //적 엘사 적 찾는부분
                    for (int i = 0; i < MyUnits.size(); i++) {
                        if (MyUnits.size() > 0 && a != null) {

                            if  (Bounding.UnitAABB(a.m_BoundingSpear.m_position, MyUnits.get(i).m_BoundingSpear.m_position, a.m_BoundingSpear.GetRadius(),MyUnits.get(i).m_BoundingSpear.GetRadius())){
                                switch(MyUnits.get(i).mType)
                                {
                                    case UnitValue.F_ANNA:
                                        a.my_enemy = MyUnits.get(i);
                                        a.setState(UnitValue.S_BATTLE);
                                        return;
                                    case UnitValue.F_ELSATOWER:
                                        a.my_enemy = MyUnits.get(i);
                                        a.setState(UnitValue.S_BATTLE);
                                        return;

                                    case UnitValue.F_TOWER:
                                        a.my_enemy = MyUnits.get(i);
                                        a.setState(UnitValue.S_BATTLE);
                                        return;
                                    default:

                                        break;

                                }
                            }
                        }
                    }
                    break;
                case UnitValue.S_BATTLE: //적 엘사타워 전투
                    a.myAttackDelayTime += dt;
                    if (a.myAttackDelayTime > 3) {

                        if (Bounding.UnitAABB(a.m_battleBounding.m_position, a.my_enemy.m_battleBounding.m_position, a.m_BoundingSpear.GetRadius(), a.my_enemy.m_BoundingSpear.GetRadius()) && a.my_enemy.mHp>0) {
                            Vec2F tempVect;
                            switch(a.my_enemy.mType)
                            {
                                case UnitValue.F_ANNA:
                                    tempVect= new Vec2F(a.my_enemy.m_battleBounding.GetX()-20, a.my_enemy.m_battleBounding.GetY());
                                    break;
                                case UnitValue.F_TOWER:
                                    tempVect= new Vec2F(a.my_enemy.m_battleBounding.GetX(), a.my_enemy.m_battleBounding.GetY());
                                    break;
                                default:
                                     tempVect= new Vec2F(a.my_enemy.m_battleBounding.GetX(), a.my_enemy.m_battleBounding.GetY());
                                    break;
                            }
                            BulletList.add(new MissleManager(a.DrawPosition,tempVect, 3, 3, myUnit, 0, a));
                            a.myAttackDelayTime = 0;
                            a.setState(UnitValue.S_MOVE);
                            return;
                        }
                        else {
                            a.setState(UnitValue.S_MOVE);
                            return;
                        }
                    }
                    break;

            }

        }


    }


    //유닛 움직임을 제어한다.
    public void MoveUpdate(double dt) {


        for (int i = 0; i < UnitList.size(); i++) {
            UnitList.get(i).myTime += dt; //유닛의 움직임 타임을 더해준다.
            if (UnitList.get(i).myTime > 0.05f) {  //유닛의 움직임 타임이 0.5보다 커지면 타일을 하나씩 움직인다.
                if (UnitList.get(i).findedPath != null)  //목표타겟이 되는 부분이 Null값이 아니면 다음 문장  실행
                {
                    if (UnitList.get(i).mType == UnitValue.F_ANNA && UnitList.get(i).getMovestate() == false && UnitList.get(i).findedPath.parentNode != null && UnitList.get(i).getState() != UnitValue.S_BATTLE) //안나일 경우와 무브가 끝나고 다음좌표 실행시에 호출된다.
                    {
                        int x = UnitList.get(i).findedPath.m_pos.x - UnitList.get(i).findedPath.parentNode.m_pos.x; //x좌표를 findfaoth  좌표값으로 변경
                        int y = UnitList.get(i).findedPath.m_pos.y - UnitList.get(i).findedPath.parentNode.m_pos.y;
                        DirectionUpdate(x, y, UnitList.get(i), UnitValue.F_ANNA);//방향 업데이트
                        float a = (float) (750 + 50 / 2 * (UnitList.get(i).findedPath.parentNode.m_pos.y - UnitList.get(i).findedPath.parentNode.m_pos.x)) + 10; //x + 15, y + 50
                        float b = (float) (-300 + 25 / 2 * (UnitList.get(i).findedPath.parentNode.m_pos.y + UnitList.get(i).findedPath.parentNode.m_pos.x)) - 40;
                        UnitList.get(i).m_SpeedVecrt = new Vec2F(a, b);
                        UnitList.get(i).m_SpeedVecrt.sub(UnitList.get(i).DrawPosition);
                        UnitList.get(i).m_SpeedVecrt.normalize();
                        UnitList.get(i).m_SpeedVecrt.multiply(2);
                        //안나타일 중심좌표랑 상대타일 중심좌표를 이용하여 계산 이동좌표 설정해줌
                        float jegop = ((a - UnitList.get(i).DrawPosition.x) * (a - UnitList.get(i).DrawPosition.x) + (b - UnitList.get(i).DrawPosition.y) * (b - UnitList.get(i).DrawPosition.y));
                        UnitList.get(i).m_distance = (float) Math.sqrt(jegop);
                        UnitList.get(i).setMovestate(true); //무브는 트루이다.
                    }
                    //바운딩 영역이 겹치게 되면 이동 못하게 해준다.
                    else if ((Bounding.UnitAABB(UnitList.get(i).m_battleBounding.m_position, UnitList.get(i).my_enemy.m_battleBounding.m_position, UnitList.get(i).m_battleBounding.GetRadius(), UnitList.get(i).my_enemy.m_battleBounding.GetRadius()) == true)) {
                        UnitList.get(i).findedPath = null;
                        UnitList.get(i).setState(UnitValue.S_BATTLE);
                        int count=0;
                        for (int a = 0; a < 50; a++) {
                            for (int j = 0; j < 50; j++) {
                                if (Bounding.tileColl.get(count).resultCal(UnitList.get(i).m_battleBounding.GetX() , UnitList.get(i).m_battleBounding.GetY()) == true && UnitValue.m_map[i][j] != 3) {
                                    UnitList.get(i).myUnitObject.SetPos(a,j);
                                    return;

                                }
                            }
                            count++;
                        }
                    } else if ((UnitList.get(i).getMovestate() == true)) {
                        if (UnitList.get(i).m_distance <= 0) {
                            if (UnitList.get(i).findedPath.parentNode != null) {
                                if (UnitList.get(i).my_enemy != null && UnitList.get(i).my_enemy.myUnitObject.unitPosition.size() > 0) {

                                    UnitList.get(i).myUnitObject.SetPos(UnitList.get(i).findedPath.parentNode.m_pos.x, UnitList.get(i).findedPath.parentNode.m_pos.y); //포지션 좌표값도 변경해서 후에 계산 편리성 도모
                                    UnitList.get(i).myPath.LoadMap(UnitValue.m_map);
                                    UnitList.get(i).findedPath = UnitList.get(i).myPath.find(enemyPositionTarget(UnitList.get(i).myUnitObject.Postion, UnitList.get(i).my_enemy.myUnitObject.unitPosition, UnitList.get(i)), UnitList.get(i).myUnitObject.Postion);
                                }
                                UnitList.get(i).setMovestate(false);
                            }
                        } else if (UnitList.get(i).m_distance > 0) {
                            UnitList.get(i).DrawPosition.add(UnitList.get(i).m_SpeedVecrt);
                            UnitList.get(i).m_distance -= 2;
                        }


                    }
                }
            }
        }
    }



    public Vec2 enemyPositionTarget(Vec2 a, ArrayList<Vec2> blist, Unit_Imfor b) {
        double dist = 9999;
        int number = 0;
        Vec2 result = null;
        double tempdist;

        for (int i = 0; i < blist.size(); i++) {
            tempdist = ((Math.pow((a.x - blist.get(i).x), 2) + Math.pow((a.y - blist.get(i).y), 2)));
            if (tempdist < dist) {
                dist = tempdist;
                result = blist.get(i);
                number = i;

            }
        }
        b.findingTilenumber = number;
        return result;
    }




    public void AnnaPattern(Unit_Imfor a, double dt, ArrayList<Unit_Imfor> tempEnemy) {
        switch (a.getState()) {
            case UnitValue.S_MOVE: //주변에 유닛이나 타워가 없으면 무브상태로 된다 .이때는 타운홀로만 향하게 된다. 이때 바운딩 영역 체크를 하는 부분이 생긴다.
                ///바운딩 영역을 체크하는 부분은 무브에서만 한다.
                if (tempEnemy.size() != 0) {
                    for (int i = 0; i < tempEnemy.size(); i++) {
                        if (Bounding.AABB(a.DrawPosition, tempEnemy.get(i).DrawPosition, a.m_BoundingSpear.GetRadius())) {
                            a.my_enemy = tempEnemy.get(i);
                            if (tempEnemy.get(i).myUnitObject.unitPosition.size() > 0) {
                                a.setMovestate(false);
                                a.myPath.LoadMap(UnitValue.m_map);
                                a.findedPath = a.myPath.find(enemyPositionTarget(a.myUnitObject.Postion, tempEnemy.get(i).myUnitObject.unitPosition, a), a.myUnitObject.Postion);
                                a.setState(UnitValue.S_BATTLE_MOVE);
                            }
                            break;
                        }
                    }
                }
                if(a.my_enemy!=null) {
                    if (a.my_enemy.mHp <= 0) {
                        a.setState(UnitValue.S_REMOVE);
                    }
                }
                break;
            case UnitValue.S_BATTLE: //하나의 유닛을 인식을 하게 되고 위치까지 도달하게 되는데 이때 공격을 하는데 자신의 공격 범위안에 유닛이 사라지면 S_BAttleMove를 호출하고 상대 유닛이 제거된 상태면 S_Move상태로 바꿔준다.

                int count=0;
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j < 50; j++) {
                        Unit temp;
                        if (Bounding.tileColl.get(count).resultCal(a.DrawPosition.x , a.DrawPosition.y) == true && UnitValue.m_map[i][j] != 3) {
                                a.myUnitObject.SetPos(i,j);
                            }
                        }
                        count++;
                    }

                if (a.my_enemy.mType == UnitValue.F_ANNA) {
                    a.my_enemy.myUnitObject.unitPosition.clear();
                    a.my_enemy.myUnitObject.unitPosition.add(a.my_enemy.myUnitObject.Postion);
                }
                UnitBattle(a, dt, 1);
                break;
            case UnitValue.S_REMOVE: //상대의 유닛이 제거 되었을때 행동할 패턴이다. 제거되고 나면 먼저 바운딩영역안 적을 검색하고 없으면 PathFinde로 타운홀로 향한다.

                if (tempEnemy.size() != 0) {
                    for (int i = 0; i < tempEnemy.size(); i++) {
                        if (Bounding.AABB(a.DrawPosition, tempEnemy.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.getState() != UnitValue.S_BATTLE && tempEnemy.get(i).mHp > 0) {
                            a.my_enemy = tempEnemy.get(i);
                            a.myPath.LoadMap(UnitValue.m_map);
                            a.setState(UnitValue.S_BATTLE_MOVE);
                            a.findedPath = a.myPath.find(enemyPositionTarget(a.myUnitObject.Postion, tempEnemy.get(i).myUnitObject.unitPosition, a), a.myUnitObject.Postion);
                            return;
                        }
                    }
                    a.setState(UnitValue.S_MOVE);
                    a.my_enemy = tempEnemy.get(0);
                    a.myPath.LoadMap(UnitValue.m_map);
                    if (tempEnemy.get(0).myUnitObject.unitPosition.size() > 0) {
                        a.findedPath = a.myPath.find(enemyPositionTarget(a.myUnitObject.Postion, tempEnemy.get(0).myUnitObject.unitPosition, a), a.myUnitObject.Postion);
                    }
                }

                break;
            case UnitValue.S_BATTLE_MOVE://상대유닛이나 타워 타일 영역 앞까지 무사히 안착시켜주는 부분이다. 겹치는 현상 없애줌
                  if(a.my_enemy.mHp<=0)
                  {
                      a.setState(UnitValue.S_REMOVE);
                  }

                break;
        }
    }


    //Agoon은 적인지 아군인지 업데이트할 대상을 의미한다. 1번은 아군 2번은 적
    public void UnitMonitor(Unit_Imfor a, double dt, boolean Agoon) {

        if (!m_Round_end) {
            switch (a.mType) {

                case UnitValue.F_ELSATOWER:
                    ElsaTowerPattern(a, Agoon, dt);
                    break;
                case UnitValue.F_ANNA:
                    if (Agoon == true) {
                        AnnaPattern(a, dt, EnemyUnits);
                    } else if (Agoon == false) {
                        AnnaPattern(a, dt, MyUnits);
                    }
                    break;
                case UnitValue.F_BOOM:
                    if(Agoon==true)
                    {
                        BoomPattern(a,true,dt);
                    }
                    else
                    {
                        BoomPattern(a,false,dt);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void setRoundTheEnd(boolean state) {
        m_Round_end = state;
    }


    public void MagicTowerBattle(Unit_Imfor a, double dt) {

        a.myAttackDelayTime += dt;
        if (a.myAttackDelayTime > 1) {
            a.myAttackDelayTime = 0;
        }

    }

    //1번 아군 2번 적
    public void UnitBattle(Unit_Imfor a, double dt, int whoenemy) {


        if (a.getState() == (UnitValue.S_BATTLE)) {

            if (a.myAttackDelayTime > 1.0f) {
                if (a.my_enemy.mHp > 0&&a.my_enemy.myUnitObject.unitPosition.size()>0 &&a.my_enemy!=null) {
                    float y = a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).y;
                    float x = a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).x;
                    if (Bounding.UnitAABB(a.m_battleBounding.m_position, a.m_battleBounding.m_position, a.m_battleBounding.GetRadius(), a.my_enemy.m_battleBounding.GetRadius())) {
                        int tempy = a.myUnitObject.Postion.y - a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).y;
                        int tempx = a.myUnitObject.Postion.x - a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).x;
                        DirectionUpdate(tempx, tempy, a, UnitValue.F_ANNA);
                        //   Sound.getInstance().play(5);
                        a.m_attck = true;
                        a.my_enemy.mHp -= 1;
                        a.myAttackDelayTime = 0;
                        return;
                    } else {
                        a.findedPath = null;
                        a.myPath.LoadMap(UnitValue.m_map);
                        a.m_attck = false;
                        a.findedPath = a.myPath.find(enemyPositionTarget(a.myUnitObject.Postion, a.my_enemy.myUnitObject.unitPosition, a), a.myUnitObject.Postion);
                        a.setState(UnitValue.S_BATTLE_MOVE);
                        return;
                    }

                } else {
                    a.m_attck = false;
                    if (whoenemy == 1) {
                        a.my_enemy.myUnitObject.unitPosition.clear();
                        a.setState(UnitValue.S_REMOVE);
                        return;
                        // a.my_enemy=EnemyUnits.get(0);
                    } else if (whoenemy == 2) {
                        a.my_enemy.myUnitObject.unitPosition.clear();
                        a.setState(UnitValue.S_REMOVE);

                        return;
                        //a.my_enemy=MyUnits.get(0);
                    }
                    //a.setState(UnitValue.S_MOVE);
                }
            } else {
                a.myAttackDelayTime += dt;
            }
        }

    }
    //유닛을 제거하는 역할을 한다.
}


class UnitSort {
    UnitSort(ArrayList<Unit_Imfor> thisUnit) {
        // Collections.sort(thisUnit, new XNoAscCompare());
        Collections.sort(thisUnit, new NoAscCompare());
    }

    //X값으로 정렬


    static class XNoAscCompare implements Comparator<Unit_Imfor> {

        /**
         * 오름차순(ASC)
         */
        @Override
        public int compare(Unit_Imfor arg0, Unit_Imfor arg1) {

            return arg0.myUnitObject.Postion.x < arg1.myUnitObject.Postion.x ? -1 : arg0.myUnitObject.Postion.x > arg1.myUnitObject.Postion.x ? 1 : 0;
        }
    }


    static class XNoDescCompare implements Comparator<Unit_Imfor> {
        /**
         * 내림차순(DESC)
         */
        @Override
        public int compare(Unit_Imfor arg0, Unit_Imfor arg1) {
            // TODO Auto-generated method stub
            return arg0.myUnitObject.Postion.x > arg1.myUnitObject.Postion.x ? -1 : arg0.myUnitObject.Postion.x < arg1.myUnitObject.Postion.x ? 1 : 0;
        }

    }


    //Y값으로 정렬
    static class NoAscCompare implements Comparator<Unit_Imfor> {

        /**
         * 오름차순(ASC)
         */
        @Override
        public int compare(Unit_Imfor arg0, Unit_Imfor arg1) {

            return arg0.myUnitObject.Postion.y + arg0.myUnitObject.Postion.x < arg1.myUnitObject.Postion.y + arg1.myUnitObject.Postion.x ? -1 : arg0.myUnitObject.Postion.y + arg0.myUnitObject.Postion.x >
                    arg1.myUnitObject.Postion.y + arg1.myUnitObject.Postion.x ? 1 : 0;
        }
    }

    static class NoDescCompare implements Comparator<Unit_Imfor> {
        /**
         * 내림차순(DESC)
         */
        @Override
        public int compare(Unit_Imfor arg0, Unit_Imfor arg1) {
            // TODO Auto-generated method stub
            return arg0.myUnitObject.Postion.y > arg1.myUnitObject.Postion.y ? -1 : arg0.myUnitObject.Postion.y < arg1.myUnitObject.Postion.y ? 1 : 0;
        }

    }

}
