package com.example.combattraps.Game;

import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.Graphic_image;
import com.example.combattraps.immortal.SpriteControl;

import java.util.ArrayList;

/**
 * Created by GyungMin on 2015-03-26.
 */

enum Move{left,top,right,bottom,leftbottom,lefttop,rightbottom,righttop}

class UnitSpriteRect
{
    public Rect[][] Move;
    public Rect[][] Attack;
    public Rect[][] Death;

    public UnitSpriteRect(Rect m[][],Rect[][] A,Rect D[][])
    {
        Move=m;
        Attack=A;
        Death=D;

    }
}

public class GraphicManager {
    private static GraphicManager s_instance;

    public Graphic_image temptile1;
    public Graphic_image temptile2,temptitle3;
    public Graphic_image background;
    public Graphic_image jumpingTrap,ArchorTower;
    public Graphic_image Store_Image,Store_button;
    public Graphic_image ButtonView_Image;
    public Graphic_image TowerButton;
    public Graphic_image mElsa_Tower;

    public SpriteControl mTownHall;
    public SpriteControl m_anna;
    public SpriteControl m_elsatower;
    public SpriteControl m_effect;

    private float m_Width;
    private float m_Height;
    ArrayList<Rect> SpriteRect;
    ArrayList<UnitSpriteRect>UnitSR; // 유닛의 스프라이트 애니메이션 사각형 위치 저장소

    public void Init()
    {
        UnitSR=new ArrayList<UnitSpriteRect>();
        SpriteRect=new ArrayList<Rect>();
        DisplayMetrics metrics = AppManager.getInstance().getResources().getDisplayMetrics();
        m_Width = metrics.widthPixels;
        m_Height = metrics.heightPixels;
        temptile1=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile1));
        temptile1.resizebitmap(51,26);
        temptile2=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile2));
        temptile2.resizebitmap(51,26);
        background=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.back));
        temptitle3=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile_coll));
        temptitle3.resizebitmap(51,26);
        Store_button=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.store_button));
        Store_Image=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.store_background));
        ButtonView_Image=new Graphic_image((AppManager.getInstance().getBitmap(R.drawable.button_view)));
        TowerButton=new Graphic_image(((AppManager.getInstance().getBitmap(R.drawable.towersum))));

        mElsa_Tower=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.elsa_tower));
        mElsa_Tower.resizebitmap(1534,318);
        mElsa_Tower.ResizeBitmapRate(2, 2);

        TowerButton.resizebitmap((int)m_Width/12,(int)m_Height/9);
        background.resizebitmap(3000,2000);
        ButtonView_Image.resizebitmap((int)m_Width,(int)m_Height/6);

        m_effect=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
        m_effect.ResizeBitmapRate(3,3);
        //m_effect.InitSpriteData(0,390/3,700/3,1,10);
       // m_effect.SetPosition((750 + 50 / 2 * (33 - 15))-30, (-300 + 30 / 2 * (33 + 15)+15)+40);
        m_effect.Effect(1);

        /////


        m_elsatower=new SpriteControl(GraphicManager.getInstance().mElsa_Tower.m_bitmap);

        m_elsatower.ElsaTower(30);

        /*m_zombie=new Sprite(AppManager.getInstance().getBitmap(R.drawable.zombie_create));
        m_zombie.resizebitmap(1500,100);
        m_zombie.InitSpriteData(0,145, 75, 1, 20);
        m_zombie.SetPosition(750 + 50 / 2 * (10 - 10), -300 + 30 / 2 * (10 + 10) + 15);*/

        m_anna=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.anna));
        m_anna.resizebitmap(384,60);
        m_anna.Anna(30);

     /*   m_effect=new Sprite(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
        m_effect.ResizeBitmapRate(3,3);
        m_effect.InitSpriteData(0,390/3,700/3,1,10);
        m_effect.SetPosition((750 + 50 / 2 * (33 - 15))-30, (-300 + 30 / 2 * (33 + 15)+15)+40);
*/
        // m_elsatower.SetPosition(750 + 50 / 2 * (15 - 15), -300 + 30 / 2 * (15 + 15)+15);

        m_elsatower.SetPosition(750 + 50 / 2 * (33 - 15), -300 + 30 / 2 * (33 + 15)+15);
        mTownHall=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.hall));

        mTownHall.ResizeBitmapRate(2,2);

    /*    m_hall=new Sprite(AppManager.getInstance().getBitmap(R.drawable.hall_shodw));

mTownHall
        m_hall.ResizeBitmapRate(2,2);
        m_hall.InitSpriteData(0,300,300,1,1);*/
        m_anna.SetPosition(750 + 50 / 2 * (15 - 15), -300 + 30 / 2 * (15 + 15)+15);
      /*  m_hall.SetPosition(750 + 50 / 2 * (15 - 15), -300 + 30 / 2 * (15 + 15)+15);*/




    }


    public static GraphicManager getInstance()
    {
        if(s_instance==null)
        {
            s_instance=new GraphicManager();
        }
        return s_instance;
    }
}
