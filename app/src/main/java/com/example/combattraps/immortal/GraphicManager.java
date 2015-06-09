package com.example.combattraps.immortal;

import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.example.combattraps.R;

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
    ArrayList <Graphic_image> RobbyList;
 //   ArrayList <SpriteControl> RobbyList2;
    ArrayList <Graphic_image> LoadingList;
    ArrayList <SpriteControl> LoadingList2;
    ArrayList <Graphic_image> GameList;
    ArrayList <SpriteControl> GameList2;


    /*
     게임 관련 리소스
     */

    public Graphic_image temptile1;
    public Graphic_image temptile2, temptitle3,temptitle4,temptile5;
    public Graphic_image background;
    public SpriteControl rock1;
    public SpriteControl rock2;
    public SpriteControl tree1;
    public Graphic_image ButtonView_Image;
    public Graphic_image TowerButton;
    public Graphic_image mElsa_Tower;
    public SpriteControl mTownHall;
    public SpriteControl m_anna;
    public SpriteControl m_elsatower;
    public SpriteControl m_effect;
    public SpriteControl m_anna_punch;
    public SpriteControl m_bulletSprite;
    /*
     로비 관련 리소스
     */
    public Graphic_image m_Top_Bar;
    public Graphic_image m_UserView;
    public Graphic_image m_Gold_image;
    public SpriteControl btn_start;
    public Graphic_image view_roby_bar;
    public Graphic_image background1;




    /*
    미션임무 관련 리소스


*/
    public Graphic_image m_Chat_View;
    public Graphic_image m_Sara;
    public SpriteControl m_airplane;
    public Graphic_image ballon_talk;




    public float m_Width;
    public float m_Height;






    public void Init() {

        deleteImage();
        GameList=new ArrayList<>();
        DisplayMetrics metrics = AppManager.getInstance().getResources().getDisplayMetrics();
        m_Width = metrics.widthPixels;
        m_Height = metrics.heightPixels;
        if(AppManager.getInstance().state==AppManager.S_GAME)
        {
            //추후 모든 변수는 ArrayList로 관리 상수를 이용해서 이미지를 처리하게 한다.
            mElsa_Tower = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.elsa_tower));
            ButtonView_Image = new Graphic_image((AppManager.getInstance().getBitmap(R.drawable.button_view)));
            TowerButton = new Graphic_image(((AppManager.getInstance().getBitmap(R.drawable.towersum))));
            mElsa_Tower.resizebitmap(1534, 318);
            mElsa_Tower.ResizeBitmapRate(2, 2);
            TowerButton.resizebitmap((int) m_Width / 12, (int) m_Height / 9);
            ButtonView_Image.resizebitmap((int) m_Width, (int) m_Height / 6);
            m_effect = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
            m_effect.resizebitmap(4000,400);
          // m_effect.ResizeBitmapRate(3, 3);
            m_effect.Effect(1);
            m_elsatower = new SpriteControl(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
            m_elsatower.ElsaTower(30);
            m_anna = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.anna));
            m_anna.resizebitmap(384, 60);
            m_anna.Anna(30);
            //m_elsatower.SetPosition(750 + 50 / 2 * (33 - 15), -300 + 30 / 2 * (33 + 15) + 15);
            mTownHall = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.hall));
            mTownHall.resizebitmap(500, 500);
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
            temptile5=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile3));
            temptile5.resizebitmap(51,51);
            m_bulletSprite=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.point_bullet));
            m_bulletSprite.SetBulletRect(10);
        }

        else if(AppManager.getInstance().state==AppManager.S_ROBBY)
        {

            for(int i=0;i<GameList.size();i++)
            {
                GameList.get(i).m_bitmap=null;
            }
            GameList.clear();
            RobbyList=new ArrayList<>();

            m_anna_punch=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.anna_punch_effect));
            m_anna_punch.AnnaEffect(30);


            //추후 모든 변수는 ArrayList로 관리 상수를 이용해서 이미지를 처리하게 한다.
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

            RobbyList.add(m_anna_punch); //안나파티클 추가
            RobbyList.add(background1); //배경 추가
            RobbyList.add(view_roby_bar); //로비바 추가
            RobbyList.add(btn_start); //시작 버튼 추가
            RobbyList.add(m_UserView); //유저 정보 창 추가
            RobbyList.add(m_Gold_image); //골드 바 추가
            RobbyList.add(m_Top_Bar); //탑바 추가
        }

        else if(AppManager.getInstance().state==AppManager.S_STORY1)
        {
            //GameList=new ArrayList<>();
            //추후 모든 변수는 ArrayList로 관리 상수를 이용해서 이미지를 처리하게 한다.
             for(int i=0;i<RobbyList.size();i++)
             {
                 RobbyList.get(i).m_bitmap=null;
             }
            m_anna_punch=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.anna_punch_effect));
            m_anna_punch.AnnaEffect(30);

            mElsa_Tower = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.elsa_tower));

            ButtonView_Image = new Graphic_image((AppManager.getInstance().getBitmap(R.drawable.button_view)));

            TowerButton = new Graphic_image(((AppManager.getInstance().getBitmap(R.drawable.towersum))));

            mElsa_Tower.resizebitmap(1534, 318);
            mElsa_Tower.ResizeBitmapRate(2, 2);

            TowerButton.resizebitmap((int) m_Width / 12, (int) m_Height / 9);
            ButtonView_Image.resizebitmap((int) m_Width/20*15, (int) m_Height / 6);

            m_effect = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.buble_paritcle));
            m_elsatower = new SpriteControl(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
            m_elsatower.ElsaTower(30);
            m_anna = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.anna));
            m_anna.resizebitmap(384, 60);
            m_anna.Anna(30);
            m_elsatower.SetPosition(750 + 50 / 2 * (33 - 15), -300 + 30 / 2 * (33 + 15) + 15);
            mTownHall = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.hall));
            mTownHall.resizebitmap(359/2, 451/2);
            m_anna.SetPosition(750 + 50 / 2 * (15 - 15), -300 + 30 / 2 * (15 + 15) + 15);
            temptile1 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile1));
            temptile1.resizebitmap(51, 26);
            temptile2 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile2));
            temptile2.resizebitmap(51, 26);
            background = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.back_sky2));
            temptitle3 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile_coll));
            temptitle4=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tree_sprite));
            temptitle4.resizebitmap(51,51);
            temptile5=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tile3));
            temptile5.resizebitmap(51,26);
            temptitle3.resizebitmap(51, 26);
            m_Chat_View=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.bar_btn_view));
            m_Chat_View.resizebitmap((int)m_Width/20*15,(int)m_Height/4);
            m_Sara=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sara));
            m_Sara.resizebitmap((int)m_Width/20*7,(int)m_Height/20*12);
            m_airplane=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.air_go));
            m_airplane.resizebitmap(300,150);
            m_airplane.Air(30);
            ballon_talk=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.ballon_talk));
            ballon_talk.resizebitmap((int)m_Width/20*6,(int)m_Height/20*8);
          //  m_airplane.ButtonInit(250,500);
            rock1=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.rock1));
            rock1.resizebitmap(70,70);
            rock2=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.rock2));
            rock2.resizebitmap(70,70);
            tree1=new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.tree1));
            tree1.resizebitmap(70, 70);

            GameList.add(background);
            GameList.add(m_effect);
            GameList.add(m_elsatower);
            GameList.add(m_anna);
            GameList.add(mElsa_Tower);
            GameList.add(mTownHall);
            GameList.add(m_Chat_View);
            GameList.add(m_airplane);
            GameList.add(TowerButton);
            GameList.add(ButtonView_Image);
            GameList.add(mElsa_Tower);
            GameList.add(m_anna_punch);
            GameList.add(temptile1);
            GameList.add(temptile2);
            GameList.add(temptitle3);
            GameList.add(temptitle4);
            GameList.add(temptile5);
            GameList.add(ballon_talk);


        }
        else if(AppManager.getInstance().state==AppManager.S_LOADING)
        {
            background1 = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.background_lobby));
            background1.resizebitmap((int) m_Width, (int) m_Height);
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
        if(AppManager.getInstance().state==AppManager.S_GAME)
        {
              m_Top_Bar=null;
              m_UserView=null;
              m_Gold_image=null;
              btn_start=null;
              view_roby_bar=null;
              background1=null;
        }
       if(AppManager.getInstance().state==AppManager.S_STORY1)
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
