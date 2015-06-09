package com.example.combattraps.Game.UnitDirect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2;
import com.example.combattraps.immortal.Vec2F;

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
    private ArrayList<MissleManager> BulletList;
    public SpriteControl TestSprite;

    public ArrayList<Unit_Imfor> UnitList;
    public boolean attack = false;

    public UnitManager() {
        MyUnits = new ArrayList<Unit_Imfor>();
        EnemyUnits = new ArrayList<Unit_Imfor>();
        BulletList = new ArrayList<MissleManager>();
        UnitList = new ArrayList<Unit_Imfor>();
        Enviroment = new ArrayList<Unit_Imfor>();


        TestSprite = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.point_bullet));
        TestSprite.SetBulletRect(10); //업데이트 아니고 업데이트 갱신 주기 설정이다.


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
                    if (UnitList.get(i).m_battleBounding != null)
                        paint.setColor(Color.GREEN);
                    canvas.drawCircle(UnitList.get(i).m_battleBounding.GetX(), UnitList.get(i).m_battleBounding.GetY(), UnitList.get(i).m_BoundingSpear.GetRadius(), paint);
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

                    paint.setColor(Color.GREEN);
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
                    canvas.drawCircle(UnitList.get(i).m_battleBounding.GetX(), UnitList.get(i).m_battleBounding.GetY(), UnitList.get(i).m_battleBounding.GetRadius(), paint);

                    paint.setColor(Color.WHITE);
                    canvas.drawRect(UnitList.get(i).destHP, paint);
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    switch (UnitList.get(i).getState()) {
                        case UnitValue.S_MOVE:
                            canvas.drawText("타운홀" + UnitList.get(i).myUnitObject.Postion + UnitList.get(i).getMovestate(), UnitList.get(i).DrawPosition.x + 10, UnitList.get(i).DrawPosition.y - 50, paint);
                            break;
                        case UnitValue.S_BATTLE_MOVE:
                            canvas.drawText("바운딩" + UnitList.get(i).myUnitObject.Postion, UnitList.get(i).DrawPosition.x + 10, UnitList.get(i).DrawPosition.y - 50, paint);
                            break;
                        case UnitValue.S_REMOVE:
                            canvas.drawText("적제거" + UnitList.get(i).myUnitObject.Postion, UnitList.get(i).DrawPosition.x + 10, UnitList.get(i).DrawPosition.y - 50, paint);
                            break;

                    }


                    if (UnitList.get(i).m_attck) {
                        UnitList.get(i).m_effect.OneUpdate(System.currentTimeMillis());
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

                default:

                    UnitList.get(i).myUnitObject.Draw(canvas, (int) UnitList.get(i).DrawPosition.x, (int) UnitList.get(i).DrawPosition.y);
                    UnitList.get(i).Hpbar();
                    paint.setColor(Color.GREEN);
                    canvas.drawRect(UnitList.get(i).originHP, paint);
                    paint.setColor(Color.WHITE);
                    break;
            }
        }
        TestSprite.Update(System.currentTimeMillis());
        TestSprite.EffectDraw(canvas, 100, 100);
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
            case UnitValue.F_JUMPINGTRAP:
                break;
            case UnitValue.F_TOWNHALL:
                a.boundingPositionUpdate(a.DrawPosition.x, a.DrawPosition.y);
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
                    UnitMonitor(MyUnits.get(i), dt, false);
            }
            remove(MyUnits, MyUnits.get(i));//유닛 제거 체크 부분
        }

        for (int i = 0; i < EnemyUnits.size(); i++) {
            add();//적 유닛 추가 상태 체크  부분
            // unitSort(EnemyUnits);//적유닛의 출력순서 정렬 부분

            animationUpdate(EnemyUnits.get(i));//적 에니메이션 구현 부분
            if (m_roundStart == true) {
                if (MyUnits.size() != 0) {
                    UnitMonitor(EnemyUnits.get(i), dt, true);
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
        if (type == UnitValue.F_ELSATOWER) {

        }
    }

    public void ElsaTowerPattern(Unit_Imfor a,boolean myUnit) {
        //if (tempEnemy.size() != 0) {

        switch (a.getState()) {
            case UnitValue.S_MOVE:
                for (int i = 0; i < EnemyUnits.size(); i++) {
                    if (UnitAABB(a.DrawPosition, EnemyUnits.get(i).DrawPosition, a.m_BoundingSpear.GetRadius(), EnemyUnits.get(i).m_BoundingSpear.GetRadius())) {
                        a.my_enemy = EnemyUnits.get(i);
                        a.setState(UnitValue.S_BATTLE);
                        return;
                    }
                }
                break;
            case UnitValue.S_BATTLE:
                if(a.myAttackDelayTime>2)
                {
                    BulletList.add(new MissleManager(a.DrawPosition,a.my_enemy.DrawPosition,3,3,myUnit,0));
                }
                break;

        }


    }


    //유닛 움직임을 제어한다.
    public void MoveUpdate(double dt) {


        for (int i = 0; i < UnitList.size(); i++) {
            UnitList.get(i).myTime += dt; //유닛의 움직임 타임을 더해준다.
            if (UnitList.get(i).myTime > 0.05f) {  //유닛의 움직임 타임이 0.5보다 커지면 타일을 하나씩 움직인다.
                if (UnitList.get(i).findedPath != null)  //목표타겟이 되는 부분이 Null값이 아니면 다음 문장  실행
                {
                    if (UnitList.get(i).mType == UnitValue.F_ANNA && UnitList.get(i).getMovestate() == false && UnitList.get(i).findedPath.parentNode != null) //안나일 경우와 무브가 끝나고 다음좌표 실행시에 호출된다.
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
                    } else if ((UnitAABB(UnitList.get(i).DrawPosition, UnitList.get(i).my_enemy.DrawPosition, UnitList.get(i).m_battleBounding.GetRadius(), UnitList.get(i).my_enemy.m_battleBounding.GetRadius()) == true)) {
                        UnitList.get(i).findedPath = null;
                        UnitList.get(i).setState(UnitValue.S_BATTLE);
                        return;
                    } else if ((UnitList.get(i).getMovestate() == true)) {
                        if (UnitList.get(i).m_distance <= 0) {
                            UnitList.get(i).myUnitObject.SetPos(UnitList.get(i).findedPath.parentNode.m_pos.x, UnitList.get(i).findedPath.parentNode.m_pos.y); //포지션 좌표값도 변경해서 후에 계산 편리성 도모
                            UnitList.get(i).findedPath = null;
                            UnitList.get(i).myPath.LoadMap(UnitValue.m_map);
                            UnitList.get(i).findedPath = UnitList.get(i).myPath.find(enemyPositionTarget(UnitList.get(i).myUnitObject.Postion, UnitList.get(i).my_enemy.myUnitObject.unitPosition, UnitList.get(i)), UnitList.get(i).myUnitObject.Postion);
                            UnitList.get(i).setMovestate(false);
                            return;
                        } else if (UnitList.get(i).m_distance > 0) {
                            UnitList.get(i).DrawPosition.add(UnitList.get(i).m_SpeedVecrt);
                            UnitList.get(i).m_distance -= 2;
                        }

                    }
                }
            }
        }
    }

    public boolean boundingAABB(BoundingSpear A, BoundingSpear B, float r1, float r2) {
        float distance = (float) Math.sqrt((B.GetX() - A.GetX()) * (B.GetX() - A.GetX()) + (B.GetY() - A.GetY() * (B.GetY() - A.GetY())));
        if (r1 + r2 >= distance) {
            return true;
        } else
            return false;
    }

    public boolean AABB(Vec2F A, Vec2F B, float range) {

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
        float distance = (float) Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        // A.distance=distance;
        if (range >= distance) {
            return true;
        } else {
            return false;
        }

    }

    public boolean UnitAABB(Vec2F myvect, Vec2F enemyvect, float myarea, float enemyarea) {
        float distance = (float) Math.sqrt((enemyvect.x - myvect.x) * (enemyvect.x - myvect.x) + (enemyvect.y - myvect.y) * (enemyvect.y - myvect.y));
        if ((myarea + enemyarea) > distance) {
            return true;
        } else {
            return false;
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


    //타워 바운딩 체크부분
    public boolean CheckTowerCollusion() {

        return false;
    }

    public void AnnaPattern(Unit_Imfor a, double dt, ArrayList<Unit_Imfor> tempEnemy) {
        switch (a.getState()) {
            case UnitValue.S_MOVE: //주변에 유닛이나 타워가 없으면 무브상태로 된다 .이때는 타운홀로만 향하게 된다. 이때 바운딩 영역 체크를 하는 부분이 생긴다.
                ///바운딩 영역을 체크하는 부분은 무브에서만 한다.
                if (tempEnemy.size() != 0) {
                    for (int i = 0; i < tempEnemy.size(); i++) {
                        if (AABB(a.DrawPosition, tempEnemy.get(i).DrawPosition, a.m_BoundingSpear.GetRadius())) {
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
                break;
            case UnitValue.S_BATTLE: //하나의 유닛을 인식을 하게 되고 위치까지 도달하게 되는데 이때 공격을 하는데 자신의 공격 범위안에 유닛이 사라지면 S_BAttleMove를 호출하고 상대 유닛이 제거된 상태면 S_Move상태로 바꿔준다.

                if (a.my_enemy.mType == UnitValue.F_ANNA) {
                    a.my_enemy.myUnitObject.unitPosition.clear();
                    a.my_enemy.myUnitObject.unitPosition.add(a.my_enemy.myUnitObject.Postion);
                }
                UnitBattle(a, dt, 1);
                break;
            case UnitValue.S_REMOVE: //상대의 유닛이 제거 되었을때 행동할 패턴이다. 제거되고 나면 먼저 바운딩영역안 적을 검색하고 없으면 PathFinde로 타운홀로 향한다.

                if (tempEnemy.size() != 0) {
                    for (int i = 0; i < tempEnemy.size(); i++) {
                        if (AABB(a.DrawPosition, tempEnemy.get(i).DrawPosition, a.m_BoundingSpear.GetRadius()) && a.getState() != UnitValue.S_BATTLE && tempEnemy.get(i).mHp > 0) {
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

                if (a.my_enemy.myUnitObject.unitPosition.size() > 0) {
                    for (int y = -1; y <= 1; y++) {
                        for (int x = -1; x <= 1; x++) {
                            if ((a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).x + x == a.myUnitObject.Postion.x) && (a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).y + y == a.myUnitObject.Postion.y)) {
                                a.setState(UnitValue.S_BATTLE);
                                return;
                            }
                        }
                    }

                    break;
                }
        }
    }


    //Agoon은 적인지 아군인지 업데이트할 대상을 의미한다. 1번은 아군 2번은 적
    public void UnitMonitor(Unit_Imfor a, double dt, boolean Agoon) {

        if (!m_Round_end) {
            switch (a.mType) {

                case UnitValue.F_ELSATOWER:
                    ElsaTowerPattern(a,Agoon);
                    break;
                case UnitValue.F_ANNA:
                    if (Agoon == true) {
                        AnnaPattern(a, dt, EnemyUnits);
                    } else if (Agoon == false) {
                        AnnaPattern(a, dt, MyUnits);
                    }
                    break;
            }
        }
    }

    public void setRoundTheEnd(boolean state) {
        m_Round_end = state;
    }


    public void missleCreate() {

    }

    public void MagicTowerBattle(Unit_Imfor a, double dt) {
        a.my_enemy.mHp -= 1;
    }

    //1번 아군 2번 적
    public void UnitBattle(Unit_Imfor a, double dt, int whoenemy) {


        if (a.getState() == (UnitValue.S_BATTLE)) {

            if (a.myAttackDelayTime > 2.0f) {
                if (a.my_enemy.mHp > 0) {


                    float y = a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).y;
                    float x = a.my_enemy.myUnitObject.unitPosition.get(a.findingTilenumber).x;


                    if (UnitAABB(a.DrawPosition, a.my_enemy.DrawPosition, a.m_battleBounding.GetRadius(), a.my_enemy.m_battleBounding.GetRadius())) {
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
