package com.example.combattraps.Game.UnitDirect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2F;

import java.util.ArrayList;

/**
 * Created by 경민 on 2015-06-10.
 */
public class Explosive {

    Vec2F DrawPosition;
    Vec2F middlePosition;
    BoundingSpear m_Boom_area;
    float m_damage;
    boolean b_team = false;
    int m_type = 0;
    boolean state=false;
    public SpriteControl img_Sprite;
    public float m_range=40;
    public boolean life=true;

    public Explosive(Vec2F Dp,float range,float damage,boolean team,int type)
    {
        Dp.x-=50;
        Dp.y-=53;
        this.DrawPosition=Dp;
        this.m_damage=damage;
       // this.m_range=range;
        this.m_type=type;
        this.b_team=team;
        middlePosition=new Vec2F(Dp.x+50, Dp.y+53);
        img_Sprite=new SpriteControl(GraphicManager.getInstance().m_newClearSprite.m_bitmap);
        img_Sprite.SetExplosion(15);

    }
    public void ExplosiveDraw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        img_Sprite.OneUpdate(System.currentTimeMillis());
        img_Sprite.EffectDraw(canvas, DrawPosition.x, DrawPosition.y);
        paint.setStyle(Paint.Style.STROKE);
        //canvas.drawCircle(middlePosition.x,middlePosition.y,m_range,paint);
        if(img_Sprite.getEndState()==true)
        {
            life=false;
        }
    }
    public void boom_attack(ArrayList<Unit_Imfor> enemy)
    {
        for(int i=0;i<enemy.size();i++)
        {
            if(enemy.get(i).mHp>0 &&enemy.get(i)!=null)
            {
                try {
                    if (Bounding.ExplosionAABB(middlePosition, enemy.get(i).m_battleBounding.m_position, m_range, enemy.get(i).m_battleBounding.GetRadius())) {
                        enemy.get(i).mHp -= m_damage;
                    }
                }
                catch(NullPointerException e)
                {

                }
            }
        }

    }

}
