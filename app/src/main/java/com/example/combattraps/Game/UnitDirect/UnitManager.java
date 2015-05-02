package com.example.combattraps.Game.UnitDirect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.Vec2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by 경민 on 2015-04-27.
 */

/*유닛의 추가 삭제 모든것을 관리한다.
적군의 유닛도 관리해 준다.
유닛의 출력 순서도 여기서 담당한다.

 */

public class UnitManager {


    public ArrayList<Unit_Imfor> MyUnits;
    public ArrayList<Unit_Imfor> EnemyUnits;
    public ArrayList<Unit_Imfor>UnitList;
    public UnitManager() {
        MyUnits = new ArrayList<Unit_Imfor>();
        EnemyUnits = new ArrayList<Unit_Imfor>();
        UnitList=new ArrayList<Unit_Imfor>();
    }
    public void unitSort() {
        new UnitSort(this.UnitList);
    }
    public void remove(ArrayList<Unit_Imfor> list, Unit_Imfor a) {
        if(a.mHp<=0) {
            list.remove(a);
        }
    }

    public void add() {


    }
    public void RenderUnit(Canvas canvas)
    {
        Paint paint;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        for (int i = 0; i < UnitList.size(); i++) {
            int x = UnitList.get(i).myUnitObject.Postion.x;
            int y = UnitList.get(i).myUnitObject.Postion.y;
            //750 + 50 / 2 * (y - x), -300 + 30 / 2 * (y+ x)
            switch (UnitList.get(i).mType) {
                case UnitValue.F_ELSATOWER:
                   // paint.setARGB(50, 255, 0, 0);
                   // canvas.drawCircle(UnitList.get(i).m_BoundingSpear.fx, UnitList.get(i).m_BoundingSpear.fy, 200, paint);
                    UnitList.get(i).myUnitObject.Draw(canvas, 1, UnitList.get(i).DrawPosition.fx, UnitList.get(i).DrawPosition.fy - 100);
                    UnitList.get(i).IniteBouningSpear(750 + 50 / 2 * (UnitList.get(i).myUnitObject.Postion.y - UnitList.get(i).myUnitObject.Postion.x) + 25,
                            -300 + 25 / 2 * (UnitList.get(i).myUnitObject.Postion.y + UnitList.get(i).myUnitObject.Postion.x) + 25);
                    canvas.drawText("?", UnitList.get(i).DrawPosition.fx+30, UnitList.get(i).DrawPosition.fy-120,paint);
                    paint.setARGB(50,100,100,100);
                    //canvas.drawCircle(UnitList.get(i).m_BoundingSpear.GetX(),UnitList.get(i).m_BoundingSpear.GetY(),UnitList.get(i).m_BoundingSpear.GetRadius(),paint);

                    UnitList.get(i).Hpbar();
                    UnitList.get(i).originHP.top -= 110;
                    UnitList.get(i).originHP.bottom -= 110;
                    UnitList.get(i).m_effect.Effect(30);
                    UnitList.get(i).m_effect.Draw(canvas, 1, UnitList.get(i).DrawPosition.fx - 30, UnitList.get(i).DrawPosition.fy - 50);
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    break;
                case UnitValue.F_TOWNHALL:
                    UnitList.get(i).myUnitObject.Draw(canvas, UnitList.get(i).DrawPosition.x + 60, UnitList.get(i).DrawPosition.y - 160);
                    break;

                case UnitValue.F_ANNA:
                    UnitList.get(i).myUnitObject.Draw(canvas, 1, UnitList.get(i).DrawPosition.fx, UnitList.get(i).DrawPosition.fy - 30);



                    paint.setARGB(50,100,100,100);
                    //canvas.drawCircle(UnitList.get(i).m_BoundingSpear.GetX(),UnitList.get(i).m_BoundingSpear.GetY(),UnitList.get(i).m_BoundingSpear.GetRadius(),paint);
                    paint.setColor(Color.WHITE);

                    paint.setColor(Color.GREEN);
                    UnitList.get(i).Hpbar();
                    UnitList.get(i).originHP.top -= 40;
                    UnitList.get(i).originHP.bottom -= 40;
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    if(UnitList.get(i).state!=2) {
                        canvas.drawText("?", UnitList.get(i).DrawPosition.fx + 10, UnitList.get(i).DrawPosition.fy - 50, paint);
                    }
                    else
                    {
                        canvas.drawText("!", UnitList.get(i).DrawPosition.fx + 10, UnitList.get(i).DrawPosition.fy - 50, paint);
                    }
                    break;

                default:

                    UnitList.get(i).myUnitObject.Draw(canvas, UnitList.get(i).DrawPosition.x, UnitList.get(i).DrawPosition.y);
                    UnitList.get(i).Hpbar();
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    break;
            }
        }
    }
    public void animationUpdate(Unit_Imfor a) {

        switch (a.mType) {
            case UnitValue.F_ELSATOWER:
                a.myUnitObject.PatrolUpdate(System.currentTimeMillis());
                a.m_effect.Update(System.currentTimeMillis());

                break;
            case UnitValue.F_ANNA:
                a.IniteBouningSpear(a.DrawPosition.fx,a.DrawPosition.fy);
                break;
            case UnitValue.F_ZOMBIE:
                break;
            case UnitValue.F_JUMPINGTRAP:
                break;
            case UnitValue.F_TOWNHALL:
                break;
            case UnitValue.F_TOWER:
                break;
            default:
                break;
        }
    }

    //유닛에 관환 업데이트 부분
    public void Update(double dt) {

        UnitList.clear();
        UnitList.addAll(MyUnits);
        UnitList.addAll(EnemyUnits);
        unitSort();//유닛의 출력순서 정렬 부분
        for (int i = 0; i < MyUnits.size(); i++) {
            add();//아군 유닛 추가 상태 체크 부분
            //unitSort(MyUnits);//유닛의 출력순서 정렬 부분
            animationUpdate(MyUnits.get(i)); //유닛 애니메이션 업데이트 구현부분
            UnitMonitor(MyUnits.get(i),dt);
            remove(MyUnits, MyUnits.get(i));//유닛 제거 체크 부분
        }
        for (int i = 0; i < EnemyUnits.size(); i++) {
            add();//적 유닛 추가 상태 체크  부분
           // unitSort(EnemyUnits);//적유닛의 출력순서 정렬 부분

            animationUpdate(EnemyUnits.get(i));//적 에니메이션 구현 부분
            UnitMonitor(EnemyUnits.get(i),dt);
            remove(EnemyUnits, EnemyUnits.get(i));//적 유닛 제거 체크 부분
        }
        //if(UnitValue.getInstance().getGameStart()==true)
            MoveUpdate(dt);
    }

    public void DirectionUpdate(int x, int y, Unit_Imfor a, int type) {

        if (type == UnitValue.F_ANNA) {
            if (x == -1 && y == 1)//left
            {
                a.myUnitObject.mDirection = 1;


            } else if (x == 1 && y == 1)//top
            {
                a.myUnitObject.mDirection = 4;


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

            a.mThisMove = true;
        }
        if (type == UnitValue.F_ELSATOWER) {

        }
    }
    //유닛 움직임을 제어한다.
    public void MoveUpdate(double dt) {
        for (int i = 1; i < UnitList.size(); i++) {
            UnitList.get(i).myTime += dt; //유닛의 움직임 타임을 더해준다.
            if (UnitList.get(i).myTime > 0.05f) {  //유닛의 움직임 타임이 0.5보다 커지면 타일을 하나씩 움직인다.
                if (UnitList.get(i).findedPath != null)  //목표타겟이 되는 부분이 Null값이 아니면 다음 문장  실행
                    if (UnitList.get(i).findedPath.parentNode != null) {  //부모노드가 비지않았다면 다음 으로

                        if (UnitList.get(i).mType == UnitValue.F_ANNA && UnitList.get(i).mThisMove == false) {
                            int x = UnitList.get(i).findedPath.m_pos.x - UnitList.get(i).findedPath.parentNode.m_pos.x;
                            int y = UnitList.get(i).findedPath.m_pos.y - UnitList.get(i).findedPath.parentNode.m_pos.y;
                            DirectionUpdate(x, y, UnitList.get(i),  UnitValue.F_ANNA);
                            UnitList.get(i).minus(UnitList.get(i).DrawPosition,
                                    new Vec2((float) (750 + 50 / 2 * (UnitList.get(i).findedPath.parentNode.m_pos.y - UnitList.get(i).findedPath.parentNode.m_pos.x)) - (float) Math.random() * 10, (float) (-300 + 25 / 2 * (UnitList.get(i).findedPath.parentNode.m_pos.y + UnitList.get(i).findedPath.parentNode.m_pos.x)) - (float) Math.random() * 10));
                            UnitList.get(i).mThisMove = true;
                            UnitList.get(i).m_moveVector.Normalize();
                            UnitList.get(i).Speed(UnitList.get(i).m_moveVector.fx / 6, UnitList.get(i).m_moveVector.fy / 6);
                            UnitList.get(i).count = 0;
                        }
                        if (UnitList.get(i).mThisMove == true) {
                            if (UnitList.get(i).count == 6) {
                                UnitList.get(i).mThisMove = false;
                                UnitList.get(i).count = 0;
                            } else {
                                UnitList.get(i).count += 1;
                                UnitList.get(i).DrawPosition.fx += UnitList.get(i).mVSpeed.fx;
                                UnitList.get(i).DrawPosition.fy += UnitList.get(i).mVSpeed.fy;
                            }
                        }
                        if (UnitList.get(i).mThisMove == false) {
                            UnitList.get(i).findedPath = UnitList.get(i).findedPath.parentNode;
                            UnitList.get(i).myUnitObject.SetPos(UnitList.get(i).findedPath.m_pos.x,UnitList.get(i).findedPath.m_pos.y);
                        }
                        UnitList.get(i).myTime = 0;
                    }
                else
                    {
                        UnitList.get(i).state=3;
                    }
            }

        }


    }
    public boolean AABB(Vec2 A, Vec2 B, float range) {

        /*bool isCollision(Sphere* m1,Sphere *m2)
        {
            중점사이의거리= sqrt((float)((m2->x-m1->x)*(m2->x-m1->x))
                    +((m2->y-m1->y)*(m2->y-m1->y))
                    +((m2->z-m1->z)*(m2->z-m1->z)));
            if(m1->r+m2->r>=중점사이의거리)
                return TRUE;66666666666
            else
                return FALSE;
        }*/
        float distance = (float) Math.sqrt((B.fx - A.fx) * (B.fx - A.fx) + (B.fy - A.fy) * (B.fy - A.fy));
        // A.distance=distance;
        if (range >= distance) {
            return true;
        } else {
            return false;
        }

    }
    public void UnitMonitor(Unit_Imfor a,double dt)
    {

        switch (a.mType) {
            case UnitValue.F_ELSATOWER:
                break;
            case UnitValue.F_ANNA:
                if(a.state==3 &&a.my_enemy.mHp<=0)
                {
                    a.my_enemy=EnemyUnits.get(0);
                    a.state=1;
                   a.myPath.LoadMap(UnitValue.m_map);
                    a.findedPath=a.myPath.find(EnemyUnits.get(0).myUnitObject.Postion,a.myUnitObject.Postion);
                }

                if( a.state!=3) {
                    for (int i = 0; i < EnemyUnits.size(); i++) {
                        if (AABB(a.DrawPosition, EnemyUnits.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.state != 2) {
                            a.state = 2;
                            a.my_enemy=EnemyUnits.get(i);
                            a.findedPath = a.myPath.find(EnemyUnits.get(i).myUnitObject.Postion, a.myUnitObject.Postion);
                        }

                    }
                }
                else
                {
                    UnitBattle(a,dt);
                }
                break;
            case UnitValue.F_ZOMBIE:
                break;
            case UnitValue.F_JUMPINGTRAP:
                break;
            case UnitValue.F_TOWNHALL:
                break;
            case UnitValue.F_TOWER:
                break;
            default:
                break;
        }
    }

    public void UnitBattle(Unit_Imfor a,double dt)
    {
        if(a.myAttackDelayTime>2.0f) {
            if (a.my_enemy.mHp > 0) {
                Sound.getInstance().play(5);
                a.my_enemy.mHp -= 1;
                a.myAttackDelayTime=0;
            }
        }
        else
        {
            a.myAttackDelayTime+=dt;
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

            return arg0.DrawPosition.y+ arg0.DrawPosition.x< arg1.DrawPosition.y+arg1.DrawPosition.x ? -1 : arg0.DrawPosition.y +arg0.DrawPosition.x >
                    arg1.DrawPosition.y +arg1.DrawPosition.x? 1 : 0;
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
