package com.example.combattraps;

import android.app.Activity;
import android.os.Bundle;

import com.example.combattraps.GameMath.MathTestView;
import com.example.combattraps.View.LoadingView;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.GameView;

/**
 * Created by 경민 on 2015-05-18.
 */
public class TestActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GameView w =new GameView(this, new MathTestView());
        AppManager.getInstance().state=AppManager.S_LOADING;
        setContentView(w);

    }

}
