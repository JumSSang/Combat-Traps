package com.example.combattraps.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.combattraps.R;

import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.Graphic_image;

/**
 * Created by GyungMin on 2015-03-21.
 */
public class UI_Create_Imfor {
    private float ScreenWidth=0;
    private float ScreenHeight=0;
    private String m_ID;
    private String m_GuildID;
    private int m_myGold;

    ////
    private String m_enemyID;
    private int m_enemyGold;
    private String m_enemyGuildID;
    private int m_enemyLogo;
    private int m_enemyGuildLogo;



    ///
    private int m_RoundPoint=0;
    private Graphic_image enemyGoldBar;
    private Graphic_image GoldBar;
    private Graphic_image PointBar;

    private Graphic_image My_Logo;
    private Graphic_image Guild_Logo;

    private Graphic_image Enemy_Logo;
    private Graphic_image Enemy_Guild_Logo;



//id,gold,gname,log,glogo
    public UI_Create_Imfor(float x, float y,String myID,int myGold,String guildname,int Logonumber,int GuildLogo,
                                            String enemyID,int enemyGold,String enemyGuildname,int enemyLogo,int enemyGuildLogo)
    {
        ScreenHeight=y;
        ScreenWidth=x;
        m_ID=myID;
        m_enemyID=enemyID;
        m_myGold=9999999;
        GoldBar= new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.gold_icon));
        enemyGoldBar=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.gold_icon));

        m_GuildID=guildname;
        ////
        m_enemyID=enemyID;
        m_enemyGold=enemyGold;
        m_enemyGuildID=enemyGuildname;
        m_enemyLogo=enemyLogo;
        m_enemyGuildLogo=enemyGuildLogo;

        My_Logo=SetUserLogo(Logonumber);
        Guild_Logo=SetGuildLogo(GuildLogo);
        Enemy_Logo=SetUserLogo(enemyLogo);
        Enemy_Guild_Logo=SetGuildLogo(enemyGuildLogo);


        GoldBar.resizebitmap((int)x/7,(int)y/12);
        GoldBar.SetPosition((int)(0),(int)(y/10));
        enemyGoldBar.resizebitmap((int)x/7,(int)y/12);
        enemyGoldBar.SetPosition((int)(ScreenWidth-ScreenWidth/5),(int)(y/10));

        /////////////////////////
        Guild_Logo.resizebitmap((int)50,(int)50);
        Guild_Logo.SetPosition((int)(0),(int)(y/18));
        My_Logo.resizebitmap((int)50,(int)50);
        /////////////////////////////
        Enemy_Logo.resizebitmap((int)50,(int)50);
        Enemy_Guild_Logo.resizebitmap((int)50,(int)50);
        Enemy_Guild_Logo.SetPosition((int)(ScreenWidth-ScreenWidth/5),(int)(y/18));
        Enemy_Logo.SetPosition((int)(ScreenWidth-ScreenWidth/5),0);

    }
    public void  Draw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        GoldBar.Draw(canvas);
        enemyGoldBar.Draw(canvas);
        My_Logo.Draw(canvas);
        Guild_Logo.Draw(canvas);

        Enemy_Logo.Draw(canvas);
        Enemy_Guild_Logo.Draw(canvas);

        canvas.drawText(""+m_ID, (int) ScreenWidth / 30, (int) (ScreenHeight / 20), paint);
        canvas.drawText(""+m_GuildID,(int)ScreenWidth/30,(int)(ScreenHeight/20)*2,paint);


        canvas.drawText(""+m_enemyID, (int) ScreenWidth-ScreenWidth/6, (int) (ScreenHeight / 20), paint);
        canvas.drawText(""+m_enemyGuildID,(int)ScreenWidth-ScreenWidth/6,(int)(ScreenHeight/20)*2,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("1000/"+m_enemyGold,(int)ScreenWidth-ScreenWidth/6,(int)(ScreenHeight/20)*3,paint);
        canvas.drawText("1000/"+m_myGold,(int)ScreenWidth/30,(int)(ScreenHeight/20)*3,paint);



    }
    public int GetGold()
    {
        return m_myGold;
    }

    public Graphic_image SetUserLogo(int id)
    {
        Graphic_image temp;
        switch(id)
        {
            case 1:
                temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.rockfish_logo));
                break;
            default:
                temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.rockfish_logo));
                break;
        }
        return temp;
    }
    public void BuyUnit(int id)
    {
        m_myGold-=id;
    }

    public Graphic_image SetGuildLogo(int id)
    {
        Graphic_image temp;
        switch(id)
        {
            case 1:
                temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.logo));
                break;
            default:
                temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.logo));
                break;
        }
        return temp;
    }

    public void SetGold(int _gold)
    {

    }
    public void SetRoundPoint(int _point)
    {
        m_RoundPoint=_point;
    }
    public int GetRoundPoint()
    {
        return m_RoundPoint;
    }
    public void  CreateSize(float x,float y) //UI적절하게 잡아주는 함수
    {
         float setx = 0;
        float sety = y / 6;
    }


}
