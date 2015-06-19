package com.example.combattraps.immortal;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by GyungMin on 2015-02-03.
 */
public class GameThread extends Thread {
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameView;

    private boolean m_run =false;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameview)
    {
        m_surfaceHolder =surfaceHolder;
        m_gameView=gameview;
    }


    public void setRunning(boolean run)
    {
        m_run =run;
    }
    @Override
    public void run()
    {
        Canvas _canvas;
        while(m_run)
        {
            _canvas =null;
            try{

                _canvas=m_surfaceHolder.lockCanvas(null);
                synchronized(m_surfaceHolder)
                {
                    m_gameView.onDraw(_canvas);
                }
            }
            finally{
                if(_canvas!=null)
                {
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
                }
            }

        }

    }
    public void Update()
    {

    }

}
