package com.example.combattraps.View;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.combattraps.Game.GraphicManager;
import com.example.combattraps.immortal.IState;

/**
 * Created by 경민 on 2015-05-02.
 */
public class StoryView implements IState {
    @Override
    public void Init() {
        GraphicManager.getInstance().Init();
        GraphicManager.getInstance().background.resizebitmap(3000, 2000);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        GraphicManager.getInstance().background.Draw(canvas);

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
