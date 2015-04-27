package com.example.combattraps.immortal;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/*
 * [[ 빠른 그래픽 처리를 위한 SurfaceView 사용하기 ]]
 * 1. surfaceview 클래스를 상속받는다.
 * 2. surfaceHolder.callback인터페이스를 구현한다.
 * 3. 인터페이스의 추상메소드를 오버라이딩 한다.
 * 4. 백그라운드에서 작업할 스레드를 만들어서 SurfaceHolder 객체를 넘겨준다.
 * 5. 스레드에서는 surfaceholder 객체를 이용해서 그래픽 작업한다.
 * 6.
 */


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameThread  m_thread;
    private IState m_state;
    public String ID;

    private MediaPlayer m_MediaPlayer;

    public GameView(Context context, IState state) {
        super(context);
        Sound.getInstance().Init(context);
        //Sound.getInstance().addList(1, R.raw.wave);
       //Sound.getInstance().addList(1, R.raw.insect_voice);

        ID="go7072";
      //  AppManager.getInstance().setId("go7072");
        getHolder().addCallback(this);
        m_thread=new GameThread(getHolder(),this);
        // TODO Auto-generated constructor stub

        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());

        ChangeGameState(state);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        m_thread.setRunning(true);
        m_thread.start();
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

        boolean retry =true;
        m_thread.setRunning(false);
        while(retry)
        {
            try
            {
                m_thread.join();
                retry=false;
            }
            catch (InterruptedException e)
            {
                //�����尡 ����ǵ��� ��� �õ��Ѵ�.
            }
        }

    }
    @Override
    public void onDraw(Canvas canvas)
    {
        m_state.Render(canvas);
        Update();
    }

    public void Update()
    {
        m_state.Update();
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        m_state.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    public IState getState()
    {
        return m_state;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        m_state.onTouchEvent(event);

        return true;
    }
    public void ChangeGameState(IState _state){

        if(m_state!=null)
        {
            m_state.Destroy();
        }
        if(AppManager.getInstance().state==AppManager.game)
        {
            m_state.Destroy();
        }
        _state.Init();
        m_state = _state;
    }

}
