package com.example.combattraps.Game.UnitDirect;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.combattraps.UI.UnitList;
import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2;
import com.example.combattraps.immortal.Vec2F;

/**
 * Created by 경민 on 2015-06-09.
 */
public class MissleManager {
    public SpriteControl img_Sprite;

    Vec2F m_Target; //미사일 타겟
    Vec2F m_StartPosition; //미사일 시작 지점
    Vec2F m_SpeedVector;//방향성을 가지는 벡터 미사일 이동 할때 add해줄값
    public Vec2F DrawPosition;
    float m_Damage; //미사일의 데미지
    float m_Speed; //미사일의 속도
    BoundingSpear m_BoundgArea; //미사일을 감싸고있는 바운딩 영역이다.
    boolean b_team = false;
    int type = 0;
    float m_distance = 0;
    boolean state=false;

    MissleManager(Vec2F StartPosition, Vec2F target, float damage, float speed, boolean team, int type) {
        this.m_StartPosition = StartPosition;
        this.m_Target = target;
        this.m_Damage = damage;
        this.m_Speed = speed;
        this.b_team = team;
        this.type = type;
        switch (type) {
            case UnitValue.BU_SPPOT:
                img_Sprite = GraphicManager.getInstance().m_bulletSprite;
                directionVector(); //방향및 위치 거리 계산 해주는 함수
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        state=true;
    }

    public void directionVector() {
        m_SpeedVector = new Vec2F(DrawPosition.x, DrawPosition.y);
        m_SpeedVector.sub(DrawPosition);
        m_SpeedVector.normalize();
        m_SpeedVector.multiply(m_Speed);
        float jegop = ((m_Target.x - m_StartPosition.x) * (m_Target.x - m_StartPosition.x) + (m_Target.y - m_StartPosition.y) * (m_Target.y - m_StartPosition.y));
        m_distance = (float) Math.sqrt(jegop);
    }

    public void moveUpdate() {
        if (m_distance <= 0) {
            state=false;
        } else {
            m_distance -= m_Speed;
        }
    }

    public void draw(Canvas canvas, float x, float y) {
        img_Sprite.EffectDraw(canvas, x, y);
    }


}
