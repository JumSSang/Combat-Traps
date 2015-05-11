package com.example.combattraps.Game.UnitDirect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.Vec2;

import java.util.ArrayList;
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

    private boolean m_roundStart = false;
    private boolean m_Round_end = false;
    public ArrayList<Unit_Imfor> MyUnits;
    public ArrayList<Unit_Imfor> EnemyUnits;
    public ArrayList<Unit_Imfor> Enviroment;
    public ArrayList<Unit_Imfor> UnitList;
    public boolean attack=false;

    public UnitManager() {
        MyUnits = new ArrayList<Unit_Imfor>();
        EnemyUnits = new ArrayList<Unit_Imfor>();
        UnitList = new ArrayList<Unit_Imfor>();
        Enviroment=new ArrayList<Unit_Imfor>();
    }

    public void unitSort() {
        new UnitSort(this.UnitList);
    }

    public void remove(ArrayList<Unit_Imfor> list, Unit_Imfor a) {
        if (a.mHp <= 0) {
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
                    canvas.drawText("?", UnitList.get(i).DrawPosition.fx + 30, UnitList.get(i).DrawPosition.fy - 120, paint);
                    paint.setARGB(50, 100, 100, 100);
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


                case UnitValue.F_ANNA:
                    UnitList.get(i).myUnitObject.Draw(canvas, 1, UnitList.get(i).DrawPosition.fx, UnitList.get(i).DrawPosition.fy - 30);
                    paint.setARGB(50, 100, 100, 100);
                    //canvas.drawCircle(UnitList.get(i).m_BoundingSpear.GetX(),UnitList.get(i).m_BoundingSpear.GetY(),UnitList.get(i).m_BoundingSpear.GetRadius(),paint);
                    paint.setColor(Color.WHITE);
                    paint.setColor(Color.GREEN);
                    UnitList.get(i).Hpbar();
                    UnitList.get(i).originHP.top -= 40;
                    UnitList.get(i).originHP.bottom -= 40;
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    if (UnitList.get(i).getState() != UnitValue.S_BATTLE_MOVE) {
                        canvas.drawText("?", UnitList.get(i).DrawPosition.fx + 10, UnitList.get(i).DrawPosition.fy - 50, paint);
                    } else {
                        canvas.drawText("!", UnitList.get(i).DrawPosition.fx + 10, UnitList.get(i).DrawPosition.fy - 50, paint);
                    }

                    if( UnitList.get(i).m_attck) {

                        UnitList.get(i).m_effect.OneUpdate(System.currentTimeMillis());
                        UnitList.get(i).m_effect.EffectDraw(canvas, UnitList.get(i).my_enemy.DrawPosition.fx-30, UnitList.get(i).my_enemy.DrawPosition.fy-60); // 검색해라!
                        if(UnitList.get(i).m_effect.mbEnd)
                        {
                            UnitList.get(i).m_attck=false;
                            UnitList.get(i).m_effect.mbEnd=false;
                        }

                    }
                    break;
                case UnitValue.F_ROCK1:
                    UnitList.get(i).myUnitObject.Draw(canvas,(int)UnitList.get(i).DrawPosition.fx,(int)UnitList.get(i).DrawPosition.fy-25);
                    break;
                case UnitValue.F_JUMPINGTRAP:
                    UnitList.get(i).myUnitObject.Draw(canvas,(int)UnitList.get(i).DrawPosition.fx,(int)UnitList.get(i).DrawPosition.fy);
                    break;
                case UnitValue.F_TOWNHALL:
                    if(UnitList.get(i).mHp>0)
                    UnitList.get(i).myUnitObject.Draw(canvas,(int)UnitList.get(i).DrawPosition.fx+25,(int)UnitList.get(i).DrawPosition.fy-175);
                    break;
                case UnitValue.F_TREE1:
                    UnitList.get(i).myUnitObject.Draw(canvas,(int)UnitList.get(i).DrawPosition.fx-25,(int)UnitList.get(i).DrawPosition.fy-50);
                    break;
                case UnitValue.F_ROCKE2:
                    UnitList.get(i).myUnitObject.Draw(canvas,(int)UnitList.get(i).DrawPosition.fx,(int)UnitList.get(i).DrawPosition.fy-25);
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
                a.IniteBouningSpear(a.DrawPosition.fx, a.DrawPosition.fy);
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

    public void setRound() {
        m_roundStart = !m_roundStart;
    }

    //유닛에 관환 업데이트 부분
    public void Update(double dt) {

        UnitList.clear();
        UnitList.addAll(MyUnits);
        UnitList.addAll(EnemyUnits);
        UnitList.addAll(Enviroment);
        unitSort();//유닛의 출력순서 정렬 부분


        for (int i = 0; i < MyUnits.size(); i++) {
            add();//아군 유닛 추가 상태 체크 부분
            //unitSort(MyUnits);//유닛의 출력순서 정렬 부분
            animationUpdate(MyUnits.get(i)); //유닛 애니메이션 업데이트 구현부분
            if (m_roundStart == true) {
                if (EnemyUnits.size() != 0)
                    UnitMonitor(MyUnits.get(i), dt,1);
            }
            remove(MyUnits, MyUnits.get(i));//유닛 제거 체크 부분
        }


        for (int i = 0; i < EnemyUnits.size(); i++) {
            add();//적 유닛 추가 상태 체크  부분
            // unitSort(EnemyUnits);//적유닛의 출력순서 정렬 부분

            animationUpdate(EnemyUnits.get(i));//적 에니메이션 구현 부분
            if (m_roundStart == true) {
                if (MyUnits.size() != 0) {
                    UnitMonitor(EnemyUnits.get(i), dt,2);
                }
            }
            remove(EnemyUnits, EnemyUnits.get(i));//적 유닛 제거 체크 부분
        }
        //if(UnitValue.getInstance().getGameStart()==true)

        if (m_roundStart == true) {
            MoveUpdate(dt);
        }
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
        for (int i = 0; i < UnitList.size(); i++) {
            UnitList.get(i).myTime += dt; //유닛의 움직임 타임을 더해준다.
            if (UnitList.get(i).myTime > 0.05f) {  //유닛의 움직임 타임이 0.5보다 커지면 타일을 하나씩 움직인다.
                if (UnitList.get(i).findedPath != null)  //목표타겟이 되는 부분이 Null값이 아니면 다음 문장  실행
                    if (UnitList.get(i).findedPath.parentNode != null) {  //부모노드가 비지않았다면 다음 으로
                        if (UnitList.get(i).mType == UnitValue.F_ANNA && UnitList.get(i).mThisMove == false) {
                            int x = UnitList.get(i).findedPath.m_pos.x - UnitList.get(i).findedPath.parentNode.m_pos.x; //x좌표를 findfaoth  좌표값으로 변경
                            int y = UnitList.get(i).findedPath.m_pos.y - UnitList.get(i).findedPath.parentNode.m_pos.y;
                            DirectionUpdate(x, y, UnitList.get(i), UnitValue.F_ANNA);//방향 업데이트
                            UnitList.get(i).minus(UnitList.get(i).DrawPosition,
                                    new Vec2((float) (750 + 50 / 2 * (UnitList.get(i).findedPath.parentNode.m_pos.y - UnitList.get(i).findedPath.parentNode.m_pos.x)) - (float) Math.random() * 10, (float) (-300 + 25 / 2 * (UnitList.get(i).findedPath.parentNode.m_pos.y + UnitList.get(i).findedPath.parentNode.m_pos.x)) - (float) Math.random() * 10));
                            UnitList.get(i).mThisMove = true; //무브는 트루이다.
                            UnitList.get(i).m_moveVector.Normalize(); //무브 벡터를 노멀라이즈 해준다.
                            UnitList.get(i).Speed(UnitList.get(i).m_moveVector.fx / 6, UnitList.get(i).m_moveVector.fy / 6); //그리고 무브 벡터를 6의로 나눠서 이동해준다.
                            UnitList.get(i).count = 0; //다시 Count는 0이된다???  즉 카운터는 해주는 카운터라 보면된다 6을 나눴으니 6칸이동하면 0으로바꿔줘야함 그래야 이동가능
                        }
                        if (UnitList.get(i).mThisMove == true) {
                            if (UnitList.get(i).count == 6) {
                                // count가 6이 되었으니 이동
                                UnitList.get(i).mThisMove = false;
                                UnitList.get(i).count = 0;//0으로 바꿔줘서 이동 가능하게 해줌
                            } else {
                                UnitList.get(i).count += 1; //이동해주고있다.
                                UnitList.get(i).DrawPosition.fx += UnitList.get(i).mVSpeed.fx; //노멀라이즈 된 6의로 나눈값으로 이동하고있다.
                                UnitList.get(i).DrawPosition.fy += UnitList.get(i).mVSpeed.fy;
                            }
                        }
                        if (UnitList.get(i).mThisMove == false) {
                            UnitList.get(i).findedPath = UnitList.get(i).findedPath.parentNode; //이동이 끝났으니 부모노드값을 바꿔준다. 목표노드값을 변경
                            UnitList.get(i).myUnitObject.SetPos(UnitList.get(i).findedPath.m_pos.x, UnitList.get(i).findedPath.m_pos.y); //포지션 좌표값도 변경해서 후에 계산 편리성 도모
                        }
                        UnitList.get(i).myTime = 0;
                    } else {
                       // UnitList.get(i).setState(UnitValue.S_BATTLE); // 더이상 이동할곳이 없으면 전투 상태로 바꿔준다..
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

    //Agoon은 적인지 아군인지 업데이트할 대상을 의미한다. 1번은 아군 2번은 적
    public void UnitMonitor(Unit_Imfor a, double dt, int Agoon) {

        if (!m_Round_end) {
            switch (a.mType) {

                case UnitValue.F_ELSATOWER:
                    break;
                case UnitValue.F_ANNA:
                    if(Agoon==1) {
                        // 전투중일때는 모든 이동을 멈춘다.
                        if(a.getState()==UnitValue.S_BATTLE)
                        {
                            UnitBattle(a, dt,1);
                        }
                         else {
                            //목표로 하는 유닛이 죽게된다면 타운홀로 다시 찾아가라고 하는 부분이다.
                            if ((a.getState() == UnitValue.S_REMOVE)) {
                                a.my_enemy = EnemyUnits.get(0);
                                a.myPath.LoadMap(UnitValue.m_map);
                                a.setState(UnitValue.S_BATTLE_MOVE);
                                a.findedPath = a.myPath.find(EnemyUnits.get(0).myUnitObject.Postion, a.myUnitObject.Postion);
                            }
                            else if (((a.my_enemy.myUnitObject.Postion.y == a.myUnitObject.Postion.y) && (a.my_enemy.myUnitObject.Postion.x == a.myUnitObject.Postion.x)) && a.getState() != UnitValue.S_BATTLE) {
                                a.setState(UnitValue.S_BATTLE);

                            }

                            //유닛이 찾아가다가 바운딩 스피어 영역에 적군을 만나게 된다면?
                            //바운딩 원으로 근처에 있는 적을 모두 검색한다. 제일 먼저 들어오게된 유닛을 첫번째 타겟으로 삼는다.
                            if (EnemyUnits.size() != 0) {
                                for (int i = 0; i < EnemyUnits.size(); i++) {
                                    if (AABB(a.DrawPosition, EnemyUnits.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.getState() != UnitValue.S_BATTLE) {
//                            // if (AABB(a.DrawPosition, EnemyUnits.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.state != 2) {
                                        a.my_enemy = EnemyUnits.get(i);
                                        a.myPath.LoadMap(UnitValue.m_map);
                                        a.setState(UnitValue.S_BATTLE_MOVE);
                                        a.findedPath = a.myPath.find(EnemyUnits.get(i).myUnitObject.Postion, a.myUnitObject.Postion);

                                    }
                                }


                            }
                        }
                    }
                    else if(Agoon==2)
                    {
                        if ((a.getState() == UnitValue.S_MOVE ||a.getState() ==UnitValue.S_BATTLE_MOVE) && a.my_enemy.mHp <= 0) {
                            a.my_enemy = MyUnits.get(0);
                            //a.setState(UnitValue.S_MOVE);
                            a.myPath.LoadMap(UnitValue.m_map);
                            a.findedPath = a.myPath.find(MyUnits.get(0).myUnitObject.Postion, a.myUnitObject.Postion);
                        }
                        //유닛이 찾아가다가 바운딩 스피어 영역에 적군을 만나게 된다면?
                        //바운딩 원으로 근처에 있는 적을 모두 검색한다. 제일 먼저 들어오게된 유닛을 첫번째 타겟으로 삼는다.
                        for (int i = 0; i < MyUnits.size(); i++) {
                            if (AABB(a.DrawPosition, MyUnits.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.getState() != UnitValue.S_BATTLE) {
//                            // if (AABB(a.DrawPosition, EneEnemyUnits.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.state != 2) {
                                a.my_enemy = MyUnits.get(i);
                                a.myPath.LoadMap(UnitValue.m_map);
                                a.setState(UnitValue.S_BATTLE_MOVE);
                                a.findedPath = a.myPath.find(MyUnits.get(i).myUnitObject.Postion, a.myUnitObject.Postion);
                            }
                        }
                        if (a.my_enemy.myUnitObject.Postion == a.myUnitObject.Postion) {
                            a.setState(UnitValue.S_BATTLE);
                            //UnitBattle(a, dt,2);
                        }




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
    }

    public void setRoundTheEnd(boolean state) {
        m_Round_end = state;
    }

    //1번 아군 2번 적
    public void UnitBattle(Unit_Imfor a, double dt,int whoenemy) {


        if(a.getState()==(UnitValue.S_BATTLE))
        {

            if (a.myAttackDelayTime > 2.0f) {
                if (a.my_enemy.mHp > 0) {
                    Sound.getInstance().play(5);
                    a.m_attck=true;
                    a.my_enemy.mHp -= 1;
                    a.myAttackDelayTime = 0;
                }
                else
                {
                    a.m_attck=false;
                    if(whoenemy==1)
                    {

                        a.setState(UnitValue.S_REMOVE);
                        return;
                       // a.my_enemy=EnemyUnits.get(0);
                    }
                    else if(whoenemy==2)
                    {
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
