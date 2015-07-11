package com.example.combattraps.Game.UnitDirect;

import com.example.combattraps.Game.ActiveCollusion;
import com.example.combattraps.UI.UnitList;
import com.example.combattraps.immortal.Vec2F;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 경민 on 2015-06-13.
 */
public class Bounding {
    static public ArrayList<ActiveCollusion> tileColl;
    Bounding()
    {

    }

    static public boolean Check_Direction(Vec2F a, Vec2F b)
    {
        return true;
    }

    static public boolean Check_Hit(Vec2F a,Vec2F b,float range)
    {
        //부채꼴 모양의 충돌부분이다.
        //
        return true;
    }

    static public Vec2F pushObject(BoundingSpear a,BoundingSpear b)
    {


        float distance=(float)Math.sqrt((a.GetX()-b.GetX())*(a.GetX()-b.GetX()) + (a.GetY()-b.GetY())*(a.GetY()-b.GetY())); //두 포지션거리를 구해준다.
        Vec2F ting=b.m_position; //튕겨나갈 얘의 값
        ting.sub(a.m_position); //두 벡터를 기준 방향 벡터 구한다.
        ting.normalize(); //정규화
        float goaway=distance-a.GetRadius(); //두 포지션 값에 튕겨나갈 구체의 지름을 빼줘서 겹치지 않게해준다.
        ting.multiply(goaway);

        return ting; //상대편 오브젝트의 방향의 반대 만큼 튕겨 나가게 해준다.


    }

    static public void ting(ArrayList<Unit_Imfor>List,Unit_Imfor a)
    {
        for(int i=0;i<List.size();i++)
        {
            if(List.get(i)!=a) {

                if (Bounding.UnitAABB(List.get(i).m_battleBounding.m_position, a.m_battleBounding.m_position, List.get(i).m_battleBounding.GetRadius(), a.m_battleBounding.GetRadius())) {
                      // List.get(i).m_battleBounding.m_position=pushObject(List.get(i).m_battleBounding,a.m_battleBounding);
                   a.DrawPosition.add(pushObject(List.get(i).m_battleBounding,a.m_battleBounding));
                    return;

                }
            }

        }
    }


    static public boolean AABB(Vec2F A, Vec2F B, float range) {

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

    static public boolean UnitAABB(Vec2F myvect, Vec2F enemyvect, float myarea, float enemyarea) {
        float distance = (float) Math.sqrt((enemyvect.x - myvect.x) * (enemyvect.x - myvect.x) + (enemyvect.y - myvect.y) * (enemyvect.y - myvect.y));
        if ((myarea + enemyarea) > distance) {
            return true;
        } else {
            return false;
        }

    }
    //유닛의 바운딩 영역과 폭팔물의 바운딩 영역이 겹치게 되면 충돌 판정으로 해준다.
    static public boolean ExplosionAABB(Vec2F myvect, Vec2F exvect, float myarea, float exarea) {
        float distance = (float) Math.sqrt((exvect.x - myvect.x) * (exvect.x - myvect.x) + (exvect.y - myvect.y) * (exvect.y - myvect.y));
        if ((myarea + exarea) > distance) {
            return true;
        } else {
            return false;
        }

    }
    static public boolean AABB_Unit_Tile()
    {
        return false;
    }
}
