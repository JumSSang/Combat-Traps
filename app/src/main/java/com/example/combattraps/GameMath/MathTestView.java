package com.example.combattraps.GameMath;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.immortal.IState;
import com.example.combattraps.immortal.Vec2;
import com.example.combattraps.immortal.Vec2F;

/**
 * Created by 경민 on 2015-05-18.
 */
public class MathTestView implements IState {
    Vec2F down1;
    Vec2F from,to;
    Vec2F temp1,temp2,temp3;
    @Override
    public void Init() {





        to =new Vec2F(200,200);
        from= new Vec2F(400,400);

        to.sub(from);
        to.normalize();

        to.multiply(100);
        to.add(from);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        Paint paint,greenPaint;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        greenPaint=new Paint();
        greenPaint.setColor(Color.GREEN);
       canvas.drawRect(0,0,1200,1200,paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);

        canvas.drawLine(20,20,40,40,paint);
       // canvas.drawLine(20,20,80,80,paint);
       // canvas.drawCircle(down2.x,down2.y,10,paint);
        canvas.drawCircle(to.x,to.y,10,paint);
        canvas.drawCircle(from.x,from.y,10,greenPaint);
      //  canvas.drawCircle(down2.x+down2.nx,down2.y+down2.ny,10,greenPaint);
       // canvas.drawCircle(down3.x+down3.nx,down3.y+down3.ny,10,greenPaint);


    //    canvas.drawCircle(down3.x+down3.nx,down3.ny+down3.y,2,paint);
     //  canvas.drawText("좌표3"+down3.x +" "+down3.y ,0,450,paint);
      //  canvas.drawText("좌표2"+down2.x +" "+down2.y ,0,500,paint);



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
