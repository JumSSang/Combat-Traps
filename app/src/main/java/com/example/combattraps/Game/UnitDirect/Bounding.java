package com.example.combattraps.Game.UnitDirect;

import com.example.combattraps.Game.ActiveCollusion;
import com.example.combattraps.immortal.Vec2F;

import java.util.ArrayList;

/**
 * Created by 경민 on 2015-06-13.
 */
public class Bounding {
    static public ArrayList<ActiveCollusion> tileColl;
    Bounding()
    {

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
