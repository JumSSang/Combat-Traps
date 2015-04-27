package com.example.combattraps.Game;

import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.example.combattraps.R;

import com.example.combattraps.View.St_Battle;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.GameView;
import com.example.combattraps.immortal.Graphic_image;
import com.example.combattraps.immortal.SpriteControl;

import java.util.ArrayList;

/**
 * Created by GyungMin on 2015-03-26.
 */

enum Move {
    left, top, right, bottom, leftbottom, lefttop, rightbottom, righttop
}

class UnitSpriteRect {
    public Rect[][] Move;
    public Rect[][] Attack;
    public Rect[][] Death;

    public UnitSpriteRect(Rect m[][], Rect[][] A, Rect D[][]) {
        Move = m;
        Attack = A;
        Death = D;

    }
}

public class GraphicManager {
    private static GraphicManager s_instance;

    /*
     게임 관련 리소스
     */

    public Graphic_image temptile1;
    public Graphic_image temptile2, temptitle3,temptitle4;
    public Graphic_image background;

    public Graphic_image ButtonView_Image;
    public Graphic_image TowerButton;
    public Graphic_image mElsa_Tower;
    public SpriteControl mTownHall;
    public SpriteControl m_anna;
    public SpriteControl m_elsatower;
    public SpriteControl m_effect;

    /*
     로비 관련 리소스
     */
    public Graphic_image m_Top_Bar;
    public Graphic_image m_UserView;
    public Graphic_image m_Gold_image;
    public SpriteControl btn_start;
    public Graphic_image view_roby_bar;
    public Graphic_image background1;


    public float m_Width;
    public float m_Height;






    public void Init() {

        deleteImage();
        DisplayMetrics metrics = AppManager.getInstance().getResources().getDisplayMetrics();
        m_Width = metrics.widthPixels;
        m_Height = metrics.heightPixels;
        if(AppManager.getInstance().state==AppManager.game)
        {
            mElsa_Tower = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.elsa_tower));
            ButtonView_Image = new Graphic_image((AppManager.getInstance().getBitmap(R.drawable.button_view)));
            TowerButton = new Graphic_image(((AppManager.getInstance().getBitmap(R.drawable.towersum))));
            mElsa_Tower.resizebitmap(1534, 318);
            mElsa_Tower.ResizeBitmapRate(2, 2);
            TowerButton.resizebitmap((int) m_Width / 12, (int) m_Height / 9);
            ButtonView_Image.resizebitmap((int) m_Width, (int) m_Height / 6);
            m_effect = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
            m_effect.ResizeBitmapRate(3, 3);
            m_effect.Effect(1);
            m_elsatower = new SpriteControl(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
            m_elsatower.ElsaTower(30);
            m_anna = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.anna));
            m_anna.resizebitmap(384, 60);
            m_anna.Anna(30);
            m_elsatower.SetPosition(750 + 50 / 2 * (33 - 15), -300 + 30 / 2 * (33 + 15) + 15);
            mTownHall = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.hall));
            mTownHall.ResizeBitmapRate(2, 2);
            m_anna.SetPosition(750 + 50 / 2 * (15 - 15), -300 + 30 / 2 * (15 + 15) + 15);
            temptile1 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile1));
            temptile1.resizebitmap(51, 26);
            temptile2 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile2));
            temptile2.resizebitmap(51, 26);
            background = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.back_sky2));
            temptitle3 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile_coll));
            temptitle4=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tree_sprite));
            temptitle4.resizebitmap(51,51);
            temptitle3.resizebitmap(51, 26);

        }

        else if(AppManager.getInstance().state==AppManager.robby)
        {
            background1 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.background_lobby));
            background1.resizebitmap((int) m_Width, (int) m_Height);
            view_roby_bar=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.bar_btn_view));
            view_roby_bar.resizebitmap((int)m_Width,(int)m_Height/10*3);
            btn_start = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.btn_start));
            btn_start.resizebitmap((int)m_Width/20*10,(int)m_Width/20*2);//button을 화면 크기의 1/5로 줄인다 .
            btn_start.ButtonInit((int) m_Width /20*5, (int) m_Width/20*2); //ButtonSprite 만들기 위한 함수
            m_Gold_image=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.gold_icon_s));
            m_Gold_image.resizebitmap((int)m_Width/20*5,(int)m_Height/20*2);
            m_UserView=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.bar_btn_view));
            m_UserView.resizebitmap((int)m_Width/20*6,(int)m_Height/20*4);
            m_Top_Bar=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.robby_top_bar));
            m_Top_Bar.resizebitmap((int)m_Width/20*16,(int)m_Height/20*2);
        }











     /*   m_effect=new Sprite(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
        m_effect.ResizeBitmapRate(3,3);
        m_effect.InitSpriteData(0,390/3,700/3,1,10);
        m_effect.SetPosition((750 + 50 / 2 * (33 - 15))-30, (-300 + 30 / 2 * (33 + 15)+15)+40);
*/
        // m_elsatower.SetPosition(750 + 50 / 2 * (15 - 15), -300 + 30 / 2 * (15 + 15)+15);



    }
    public void deleteImage()
    {
        if(AppManager.getInstance().state==AppManager.game)
        {
              m_Top_Bar=null;
              m_UserView=null;
              m_Gold_image=null;
              btn_start=null;
              view_roby_bar=null;
              background1=null;
        }

    }


    public static GraphicManager getInstance() {
        if (s_instance == null) {
            s_instance = new GraphicManager();
        }
        return s_instance;
    }
}
