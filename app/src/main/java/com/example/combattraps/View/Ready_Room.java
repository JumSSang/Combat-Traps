package com.example.combattraps.View;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.combattraps.Game.GraphicManager;
import com.example.combattraps.R;
import com.example.combattraps.View.Ready_Room_Dir.SumInfo;
import com.example.combattraps.View.Ready_Room_Dir.UserInfo;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.DBManager;
import com.example.combattraps.immortal.Graphic_image;
import com.example.combattraps.immortal.IState;
import com.example.combattraps.immortal.SpriteControl;

import java.io.IOException;

/**
 * Created by 경민 on 2015-04-18.
 */
public class Ready_Room implements IState {


    int m_Width;
    int m_Height;
    Rect btb_startRect;
    Rect btn_sotreRect;
    Rect btn_partbattleRect;
    Graphic_image gear;
    SpriteControl btn_Store;
    SpriteControl btn_partbattle;
    UserInfo m_myImfoRender;
    SumInfo m_SumImfoRender;
    @Override
    public void Init() {
        AppManager.getInstance().state = AppManager.robby;
        GraphicManager.getInstance().Init();

        m_Width = (int) GraphicManager.getInstance().m_Width;
        m_Height = (int) GraphicManager.getInstance().m_Height;

        //버튼 이미지 충돌 영역 생성 부분
        int btn_left = m_Width / 20 * 15;
        int btn_top = m_Height / 20 * 15;
        btb_startRect = new Rect(btn_left, btn_top, btn_left + m_Width / 20 * 5, btn_top + (int) m_Width / 20 * 2);


        int inven_left = m_Width / 20 * 11;
        int inven_top = m_Height / 20 * 15;
        btn_partbattleRect = new Rect(inven_left, inven_top, inven_left + m_Width / 8, inven_top + (int) m_Height / 5);

        btn_Store = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.btn_store));

        btn_partbattle = new SpriteControl(AppManager.getInstance().getBitmap(R.drawable.part_start));

        gear = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.gear));
        gear.resizebitmap(100, 100);

        btn_partbattle.resizebitmap((int) m_Width / 20 * 10, (int) m_Width / 20 * 2);//button을 화면 크기의 20/10으로 초기화.
        btn_partbattle.ButtonInit((int) m_Width / 20 * 5, (int) m_Width / 20 * 2); //ButtonSprite 만들기 위한 함수

        btn_Store.resizebitmap((int) m_Width / 20 * 8, (int) m_Width / 20 * 2);//button을 화면 크기의 1/5로 줄인다 .
        btn_Store.ButtonInit((int) m_Width / 20 * 4, (int) m_Width / 20 * 2); //ButtonSprite 만들기 위한 함수
        int store_left = m_Width / 10 * 3;
        int store_top = m_Height / 20 * 15;
        btn_sotreRect = new Rect(store_left, store_top, store_left + m_Width / 20 * 4, store_top + (int) m_Width / 20 * 2);


        int part_lett = store_left + m_Width / 20 * 4 - 10;
        int part_top = m_Height / 20 * 15;
        btn_partbattleRect = new Rect(part_lett, part_top, part_lett + m_Width / 20 * 4, part_top + m_Width / 20 * 2);
        m_myImfoRender=new UserInfo(m_Width,m_Height);
        m_SumImfoRender=new SumInfo(m_Width,m_Height);

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {


        if(AppManager.getInstance().state==AppManager.robby) {

            int logo_left = 0;
            int logo_top = m_Height / 20 * 2 + 5; //사실 로고 탑임
            GraphicManager.getInstance().background1.Draw(canvas);
            GraphicManager.getInstance().btn_start.ButtonDraw(canvas, GraphicManager.getInstance().btn_start.state_click, btb_startRect.left, btb_startRect.top);
            btn_Store.ButtonDraw(canvas, btn_Store.state_click, btn_sotreRect.left, btn_sotreRect.top);
            btn_partbattle.ButtonDraw(canvas, btn_partbattle.state_click, btn_partbattleRect.left, btn_partbattleRect.top);
            GraphicManager.getInstance().m_UserView.Draw(canvas, m_Width / 20 * 2, m_Height / 20 * 2); //유저 정보 뷰 출력
            GraphicManager.getInstance().m_Top_Bar.Draw(canvas, 0, 0);
            gear.Draw(canvas, m_Width / 20 * 18, 0);
            m_myImfoRender.onDraw(canvas);
            m_SumImfoRender.Draw(canvas);
            if (GraphicManager.getInstance().btn_start.state_click == true && (DBManager.getInstance().GetEnemy().equals("매칭을 시작하기전입니다..") || DBManager.getInstance().GetEnemy().equals("대전 상대 검색중입니다..") || DBManager.getInstance().GetEnemy().equals("검색취소"))) {
                DBManager.getInstance().SetEnemy("대전 상대 검색중입니다..");
            } else if (GraphicManager.getInstance().btn_start.state_click == false && DBManager.getInstance().GetEnemy().equals("대전 상대 검색중입니다..")) {
                DBManager.getInstance().SetEnemy("검색취소");
                try {
                    DBManager.getInstance().connection.oos.writeObject("서치취소");
                    DBManager.getInstance().connection.oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if(!DBManager.getInstance().GetEnemy().equals("매칭을 시작하기전입니다..") && !DBManager.getInstance().GetEnemy().equals("대전 상대 검색중입니다..") &&!DBManager.getInstance().GetEnemy().equals("검색취소"))
            {
                AppManager.getInstance().getGameView().ChangeGameState(new St_Battle());
            }

        }



        // canvas.drawText("승리 : 0 ",(int)GraphicManager.getInstance().m_Width/20*8,(int)GraphicManager.getInstance().m_Height/40*33,paint);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void Search() {
        try {
            DBManager.getInstance().connection.oos.writeObject("서치모드");
            DBManager.getInstance().connection.oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        // m_MediaPlayer.start();


        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: {
                if (Collusion((int) x, (int) y, btb_startRect)) {
                    GraphicManager.getInstance().btn_start.state_click = !GraphicManager.getInstance().btn_start.state_click;
                    Search();


                }
                if (Collusion((int) x, (int) y, btn_partbattleRect)) {
                    btn_partbattle.state_click = !btn_partbattle.state_click;


                }
                if (Collusion((int) x, (int) y, btn_sotreRect)) {
                    btn_Store.state_click = !btn_Store.state_click;
                }
            }
            case MotionEvent.ACTION_UP:


                break;
        }
        return true;
    }

    public boolean Collusion(int x, int y, Rect r) {
        if (x > r.left && x < r.right && y > r.top && r.bottom > y) {
            return true;
        } else
            return false;
    }
}
