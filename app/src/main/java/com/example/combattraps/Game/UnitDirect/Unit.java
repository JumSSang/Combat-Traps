package com.example.combattraps.Game.UnitDirect;

import android.graphics.Bitmap;

import com.example.combattraps.immortal.SpriteControl;
import com.example.combattraps.immortal.Vec2;

/**
 * Created by GyungMin on 2015-02-23.
 */
public class Unit extends SpriteControl {

    //public Vec2 Postion;

    public Unit(Bitmap bitmap)
    {
        super(bitmap);
        Postion=new Vec2(0,0);
    }


    public void SetPos(int x,int y)
    {
        Postion.x=x;
        Postion.y=y;
    }



}
