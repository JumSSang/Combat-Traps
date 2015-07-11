package com.example.combattraps.View.Ready_Room_Dir;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.combattraps.Game_NetWork.NetState;
import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.DBManager;
import com.example.combattraps.immortal.Graphic_image;

/**
 * Created by 경민 on 2015-04-21.
 */
public class SumInfo {
    Rect logoRect;
    Rect enemy_logoRect;

    int m_Width;
    int m_Height;
    public int logo_left;
    public int logo_top;
    int enemylogo_left;
    int enemylogo_top;
    Graphic_image[] sum;

    public SumInfo(int Width, int Height) {

        this.m_Height = Height;
        this.m_Width = Width;

        sum=new Graphic_image[12];
        sum[1] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_1));
        sum[2] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_2));
        sum[3] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_3));
        sum[4] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_4));
        sum[5] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_5));
        sum[6] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_6));
        sum[7] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_7));
        sum[8] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_8));
        sum[9] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_11));
        sum[10] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_11));
        sum[11] = new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_11));

        if(DBManager.getInstance().getNetState()!= NetState.ROBBY)
        {
            logo_left = 5;
            logo_top = 0; //사실 로고 탑임
            enemylogo_left=m_Width/20*16;
            enemylogo_top= 0; //사실 로고 탑임
            logoRect = new Rect(logo_left - 5, logo_top - 5, logo_left + m_Width / 20 * 4, logo_top + m_Height / 20 * 3+10);
            enemy_logoRect= new Rect(enemylogo_left - 5, enemylogo_top - 5, enemylogo_left +  m_Width / 20 * 4, enemylogo_top+m_Height / 20 * 3+10);
            for (int i = 1; i < 12; i++) {
                sum[i].resizebitmap(m_Height / 20 *3, m_Height / 20 * 3);

            }
        }
        if(DBManager.getInstance().getNetState()== NetState.ROBBY) {

            logo_left = 0;
            logo_top = m_Height / 20 * 2 + 5; //사실 로고 탑임
            enemylogo_left=m_Width;
            enemylogo_top= 0; //사실 로고 탑임
            logoRect = new Rect(logo_left - 5, logo_top - 5, logo_left + 130, logo_top + 130);
            for (int i = 1; i < 10; i++) {
                sum[i].resizebitmap(m_Height / 20 * 4, m_Height / 20 * 4 - 5);
            }
        }

    }

    public void EnemyDraw(Canvas canvas)
    {
         Paint paint = new Paint();
        paint.setColor(Color.argb(100,0,0,0));
       // paint.setAlpha(200);
        canvas.drawRect(enemy_logoRect, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);

        if(DBManager.getInstance().GetEnemy().equals("매칭을 시작하기전입니다..")) {
            canvas.drawText("STORY모드 ", enemylogo_left + m_Height / 20 * 3, m_Height / 40 * 1, paint);
            canvas.drawText("STAGE_1 ",enemylogo_left+m_Height / 20 * 3, m_Height/40*3,paint);
        }
        else {
            canvas.drawText("I  D :" + DBManager.getInstance().GetEnemy(), enemylogo_left + m_Height / 20 * 3, m_Height / 40 * 1, paint);
            canvas.drawText("GUILD:", enemylogo_left + m_Height / 20 * 3, m_Height / 40 * 3, paint);
        }
        switch(DBManager.getInstance().GetEnemySum())
        {

            case 1:
                sum[1].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 2:
                sum[2].Draw(canvas,enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 3:
                sum[3].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 4:
                sum[4].Draw(canvas,enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 5:
                sum[5].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 6:
                sum[6].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 7:
                sum[7].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 8:
                sum[8].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 9:
                sum[9].Draw(canvas, enemylogo_left, enemylogo_top);//로고 출력
                break;
            case 10:
                sum[10].Draw(canvas,enemylogo_left, enemylogo_top);//로고 출력
                break;
          default:
                sum[11].Draw(canvas,enemylogo_left, enemylogo_top);//로고 출력
                break;
        }
    }

    public void Draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAlpha(100);
        canvas.drawRect(logoRect, paint);

        switch (DBManager.getInstance().GetSumnumber()) {
            case 1:
                sum[1].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 2:
                sum[2].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 3:
                sum[3].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 4:
                sum[4].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 5:
                sum[5].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 6:
                sum[6].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 7:
                sum[7].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 8:
                sum[8].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 9:
                sum[9].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            case 10:
                sum[10].Draw(canvas, logo_left, logo_top);//로고 출력
                break;
            default:
                break;
        }


    }
}
