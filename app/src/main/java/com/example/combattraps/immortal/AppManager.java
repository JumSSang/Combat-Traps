package com.example.combattraps.immortal;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by GyungMin on 2015-02-03.
 */
public class AppManager {

    private static AppManager s_instance;
    private GameView m_gameView;
    private Resources m_resource;
    public float width;
    public float height;
    //public NetManager NetWork;
    public double m_time=0;

  public Bitmap getBitmap(int r)
    {
        return BitmapFactory.decodeResource(m_resource, r);
    }
    public static AppManager getInstance()
    {
        if(s_instance==null)
        {
            s_instance =new AppManager();
        }
        return s_instance;
    }

    public void setGameView(GameView _gameView)
    {

        m_gameView =_gameView;
    }
    public void setResources(Resources _resources)
    {

        m_resource=_resources;
    }

    public GameView getGameView()
    {
        return m_gameView;
    }

    public Resources getResources()
    {
        return m_resource;
    }




}
