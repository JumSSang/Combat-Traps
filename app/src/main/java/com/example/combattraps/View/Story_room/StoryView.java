package com.example.combattraps.View.Story_room;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.combattraps.Game.ActiveCollusion;
import com.example.combattraps.View.Ready_Room_Dir.Ready_Room;
import com.example.combattraps.immortal.DBManager;
import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.Game.PathFinder;
import com.example.combattraps.Game.UnitDirect.Unit;
import com.example.combattraps.Game.UnitDirect.UnitManager;
import com.example.combattraps.Game.UnitDirect.UnitValue;
import com.example.combattraps.Game.UnitDirect.Unit_Imfor;
import com.example.combattraps.R;
import com.example.combattraps.UI.UI_Create_Bottom;
import com.example.combattraps.UI.UI_Create_Imfor;
import com.example.combattraps.UI.UnitList;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.IState;
import com.example.combattraps.immortal.ScreenAnimation;
import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.TextEffect;
import com.example.combattraps.immortal.Vec2;

import java.io.IOException;
import java.util.ArrayList;

public class StoryView implements IState {

    private final PathFinder finderOjbect = new PathFinder();
    private int m_SaraSay = 0;
    private int m_timer = 0;
    private double m_batch_gameTime = 5;
    private final int GAMESTART = 0;
    private final int SINARIO = 1;
    private int m_WhoWIn = 0;

    private int my_view = SINARIO;
    private int m_plot = 0;


    Matrix matrix = new Matrix();
    private ArrayList<ActiveCollusion> tileColl;
    private ArrayList<UnitList> UnitDataList;
    private UI_Create_Bottom UI;
    private UI_Create_Imfor UI_imfor;
    public int m_UI_Touch_Postion = 0;
    private Story_String m_talk;
    private ScreenAnimation fade_in;
    private ScreenAnimation fade_out;
    private double timeDelta;
    Paint paint;
    float x;
    float y;
    float m_click_x = 0; //첫번째 터치좌표 x
    float m_click_y = 0; //첫번째 터치좌표 y
    float m_click2_x = 0; //2번째 터치 좌표 x
    float m_click2_y = 0; //2번째 터치 좌표 y
    /////////////////////

    float m_matrix_x = 1.0f;//메트릭스 변화 하는 x비율
    float m_matrix_y = 1.0f;//메트릭스 변화 하는 y비율


    public float m_Width;
    public float m_Height;

    float m_movex = 0;
    float m_movey = 0;
    float m_diffX = 0;
    float m_diffY = 0;
    float m_distx = 0;
    float m_disty = 0;
    ////////////////////////////
    // 드래그 모드인지 핀치줌 모드인지 구분
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    // 드래그시 좌표 저장
    int mode = NONE;
    int posX1 = 0, posX2 = 0, posY1 = 0, posY2 = 0;
    // 핀치시 두좌표간의 거리 저장
    float oldDist = 1f;
    float newDist = 1f;
    ///////////////////////////////////////////
    double currentTime;
    double m_time = 0;
    double m_thread_tiem = 0;
    double m_OpenServerTime = 0;
    double m_checkLoader = 0;
    double fade_endTime = 0;
    UnitManager Units;

    TextEffect m_airtext, m_airtext1, m_airtextVic, m_airTextFebe;

    public void sendMessage(String a) throws IOException {
        DBManager.getInstance().connection.oos.writeObject(a);
        DBManager.getInstance().connection.oos.flush();
    }

    @Override
    public void Init() {
        DBManager.getInstance().go_robby = 4;
        try {
            sendMessage("1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        m_talk = new Story_String();
        AppManager.getInstance().state = AppManager.S_STORY1;
        GraphicManager.getInstance().Init();
        Sound.getInstance().addList(1, R.raw.buildingsaw);
        Sound.getInstance().backgroundPlay(R.raw.story_1);
        Sound.getInstance().addList(2, R.raw.smallchain);
        Sound.getInstance().addList(3, R.raw.zombie_create_sound);
        Sound.getInstance().addList(4, R.raw.hello);
        Sound.getInstance().addList(5, R.raw.smash);
        Sound.getInstance().addList(6, R.raw.text_call);

        currentTime = System.currentTimeMillis() / 1000;
        tileColl = new ArrayList<ActiveCollusion>();
        UnitDataList = new ArrayList<UnitList>();
        DisplayMetrics metrics = AppManager.getInstance().getResources().getDisplayMetrics();
        m_Width = metrics.widthPixels;
        m_Height = metrics.heightPixels;
        Units = new UnitManager();
        matrix.setScale(m_matrix_x, m_matrix_y);
        InitMap();
        GraphicManager.getInstance().background.resizebitmap((int) (m_Width * 2), (int) (m_Height * 2));
        UnitAdd(); //데이터 베이스로 부터 유닛 목록 받아온다.
        UI = new UI_Create_Bottom(m_Width, m_Height, UnitDataList.size(), 0, UnitDataList);
        //id,gold,gname,log,glogo
        UI_imfor = new UI_Create_Imfor(m_Width, m_Height);
        Unit Mtemp;
        Mtemp = new Unit(GraphicManager.getInstance().mTownHall.m_bitmap);

        CreateHall(45, 5, 0);

        CreateHall(5, 45, 1);
        GraphicManager.getInstance().m_airplane.Air(5);
        fade_in = new ScreenAnimation((int) m_Width, (int) m_Height);
        fade_out = new ScreenAnimation((int) m_Width, (int) m_Height);
        fade_out.InitFadeOut();
        fade_in.InitFadeIn();
        m_airtext = new TextEffect("배치시작!");
        m_airtext1 = new TextEffect("전투시작!");
        m_airtextVic = new TextEffect("전투 승리!", 0);
        m_airTextFebe = new TextEffect("전투 패배...", 0);
        // LoadEnemy();
    }

    public void InitMap() {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                ActiveCollusion temp = new ActiveCollusion();
                temp.addSpot(750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i) + 12);//p1 왼쪽
                temp.addSpot(750 + 50 / 2 * (j - i) + 25, -300 + 25 / 2 * (j + i));//p2 //위
                temp.addSpot(750 + 50 / 2 * (j - i) + 50, -300 + 25 / 2 * (j + i) + 12);//p3 오른쪽
                temp.addSpot(750 + 50 / 2 * (j - i) + 25, -300 + 25 / 2 * (j + i) + 25);//p4 아래
                temp.distanceCal();
                tileColl.add(temp);

                if ((i + j) % 2 == 0) {
                    UnitValue.m_map[i][j] = 1;
                } else {
                    UnitValue.m_map[i][j] = 2;
                }
                if (i == 0 || i == 49 || j == 0 || j == 49) {
                    UnitValue.m_map[i][j] = 4;
                }

            }
        }
    }

    //덱 창안에 유닛을 더하는 역할을 한다.
    public void UnitAdd() {
        UnitList temp = new UnitList();
        UnitList temp1 = new UnitList();
        UnitList temp2 = new UnitList();
        UnitList temp3 = new UnitList();

        temp1.set(UnitValue.F_JUMPINGTRAP, 1, 0);
        UnitDataList.add(temp1);
        temp2.set(UnitValue.F_ELSATOWER, 1, 0);
        UnitDataList.add(temp2);//2
        temp3.set(UnitValue.F_ANNA, 1, 0);
        UnitDataList.add(temp3);//2


    }

    public void LoadingDBMap(String a) {
        String[] result = a.split("=");
        String[] imfors;
        for (int i = 0; i < result.length; i++) {

            int x = 0;
            int y = 0;
            int level = 0;
            int type = 0;
            imfors = result[i].split("a");

            x = Integer.parseInt(imfors[0]);
            y = Integer.parseInt(imfors[1]);
            level = Integer.parseInt(imfors[2]);
            type = Integer.parseInt(imfors[3]);
            switch (type) {
                case UnitValue.F_TOWER:

                    break;
                case UnitValue.F_JUMPINGTRAP:
                    break;
                case UnitValue.F_ZOMBIE:
                    break;
                case UnitValue.F_GOLDRUN:
                    break;
                case UnitValue.F_ELSATOWER:

                    break;
                case UnitValue.F_ANNA:

                    break;
                case UnitValue.F_TOWNHALL:
                    break;
                case UnitValue.F_TREE1:
                    CreateTree1(x, y);
                    break;

                case UnitValue.F_ROCK1:
                    CreateRock(x, y);
                    break;
                case UnitValue.F_ROCKE2:
                    CreateRock2(x, y);
                    break;


            }

        }

    }

    @Override
    public void Destroy() {
    }

    @Override
    public void Update() {

        if (AppManager.getInstance().state != AppManager.S_LOADING) {
            // long frameEndTime = System.currentTimeMillis();
            //long delta = frameEndTime - frameStartTime;
            double newTime = System.currentTimeMillis() / 1000.0;
            timeDelta = newTime - currentTime;
            currentTime = newTime;
            m_time += timeDelta;
            m_thread_tiem += timeDelta;

            if (my_view == SINARIO) {
                GraphicManager.getInstance().m_airplane.Update(System.currentTimeMillis());
                if (m_SaraSay > 11) {
                    fade_out.fadeOutUpdate(timeDelta);
                }
            }
            if (my_view == GAMESTART) {

                if (fade_endTime > 2)
                    fade_in.fadeInUpdate(timeDelta);
                else {
                    fade_endTime += timeDelta;
                }
                Units.Update(timeDelta);

            }
            // fade_in.fadeOutUpdate(timeDelta);

            //GraphicManager.getInstance().m_effect.Update(System.currentTimeMillis());
            if (m_airtext.getState() == true) {
                m_batch_gameTime -= timeDelta;
            }

        }
    }

    //m_time=m_time*1000;

    public void SenariDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        GraphicManager.getInstance().background.Draw(canvas, -750, -450);
        GraphicManager.getInstance().m_Chat_View.Draw(canvas, 0, (int) m_Height / 20 * 15);
        GraphicManager.getInstance().m_Sara.Draw(canvas, (int) m_Width / 20 * 14, (int) m_Height / 20 * 10);

        if (m_SaraSay > 11) {
            //canvas.drawRect(0,0,m_Width,m_Height,paint);
            fade_out.fadeDraw(canvas);

            if (!fade_out.getAnimationState()) {
                my_view = GAMESTART;
                Sound.getInstance().backgroundRelease();
                Sound.getInstance().backgroundPlay(R.raw.battle_bgm);
                m_SaraSay = 0;
                return;
            }
        } else {
            String temp = m_talk.getSara1(m_SaraSay);
            String[] result = temp.split("#");
            for (int i = 0; i < result.length; i++) {
                if (i == 0)
                    canvas.drawText(result[i], m_Width / 20, m_Height / 20 * 17, paint);
                else if (i == 1) {
                    canvas.drawText(result[i], m_Width / 20, m_Height / 20 * 18, paint);
                } else if (i == 2) {
                    canvas.drawText(result[i], m_Width / 20, m_Height / 20 * 19, paint);
                }
            }
        }


    }

    public void GameDraw(Canvas canvas) {
        Paint paint;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.save();
        canvas.setMatrix(matrix);
        canvas.translate(m_diffX, m_diffY);
        //타일 한번 깔아준다.
        GraphicManager.getInstance().background.Draw(canvas, -750, -450);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (UnitValue.m_map[i][j] == 1) {
                    GraphicManager.getInstance().temptile1.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));

                } else if (UnitValue.m_map[i][j] == 2) {
                    GraphicManager.getInstance().temptile2.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));
                } else if (UnitValue.m_map[i][j] == 3) {
                    GraphicManager.getInstance().temptitle4.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));
                } else if (UnitValue.m_map[i][j] == UnitValue.M_NOTMOVE) {
                    GraphicManager.getInstance().temptile5.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));
                }

            }
        }

        //나무가 타일에 겹쳐지지 않게 그려주기 위해 한번더 연산해 주었다.
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (UnitValue.m_map[i][j] == 4) {
                    GraphicManager.getInstance().temptile2.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));
                    GraphicManager.getInstance().temptitle4.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i) - 25);
                }
            }
        }
        Units.RenderUnit(canvas);
        canvas.restore();

        GraphicManager.getInstance().ButtonView_Image.Draw(canvas, 0, (int) m_Height - (int) m_Height / 6);
        //유저 정보를 뿌려주는 인스턴스의 Draw
        UI_imfor.Draw(canvas);
        //UI 버튼위치마다 번호를 매겨서 글자 출력 해준다.
        for (int i = 0; i < UI.Button.size(); i++) {
            UI.Button.get(i).Draw(canvas);
            canvas.drawText("" + i, (int) (UI.Button.get(i).GetX()), (int) (UI.Button.get(i).GetY()), paint);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        talkUnit(canvas);
        //클릭 위치마다 사각형을 그려준다 오브젝트에 사각형
        canvas.drawRect((m_UI_Touch_Postion * 5) + m_UI_Touch_Postion * m_Width / 12, m_Height - m_Height / 6, (m_UI_Touch_Postion * 5) + m_UI_Touch_Postion * m_Width / 12 + m_Width / 12, m_Height - m_Height / 18, paint);

    }

    public void talkUnit(Canvas canvas) {

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(25);

        switch (m_plot) {
            case 0: //처음으로 간단한 설명을 하는 장면의 대화 시뮬 부분이다.

                GraphicManager.getInstance().m_Sara.Draw(canvas, (int) m_Width / 20 * 14, (int) m_Height / 20 * 10);
                GraphicManager.getInstance().ballon_talk.Draw(canvas, (int) m_Width / 20 * 12, (int) m_Height / 20 * 2);
                String temp = m_talk.getSara2(m_SaraSay);
                String[] result = temp.split("#");
                for (int i = 0; i < result.length; i++) {
                    if (i == 0)
                        canvas.drawText(result[i], m_Width / 20 * 12, (int) m_Height / 20 * 3, paint);
                    else if (i == 1) {
                        canvas.drawText(result[i], m_Width / 20 * 12, (int) m_Height / 20 * 4, paint);
                    } else if (i == 2) {
                        canvas.drawText(result[i], m_Width / 20 * 12, (int) m_Height / 20 * 5, paint);
                    }
                }
                if (m_SaraSay > 6) {
                    setEnviroMent();
                    LoadingDBMap(DBManager.getInstance().m_StringMap);
                    m_plot = 1;
                }
                break;
            case 1:   //배치시작이라는 글자가 휙휙 날라오는 장면이다.
                m_airtext.SlowText(canvas, m_Width, m_Height, timeDelta);
                if (m_airtext.getState() == true) {
                    m_plot = 2;
                }
                break;
            case 2: //이 장면은 배치 제한 시간 30초가 나오는 장면이다.
                canvas.drawText("" + (int) m_batch_gameTime, m_Width / 2, m_Height / 20, paint);
                if (m_batch_gameTime <= 0) {
                    if (m_SaraSay < 9) {
                        LoadEnemy();
                        m_plot = 3;
                    }
                }
                break;
            case 3: //배치가 끝난 이후 사라가 말을 시작한 장면
            {
                GraphicManager.getInstance().m_Sara.Draw(canvas, (int) m_Width / 20 * 14, (int) m_Height / 20 * 10);
                GraphicManager.getInstance().ballon_talk.Draw(canvas, (int) m_Width / 20 * 12, (int) m_Height / 20 * 2);
                temp = m_talk.getSara2(m_SaraSay);
                result = temp.split("#");
                for (int i = 0; i < result.length; i++) {
                    if (i == 0)
                        canvas.drawText(result[i], m_Width / 20 * 12, (int) m_Height / 20 * 3, paint);
                    else if (i == 1) {
                        canvas.drawText(result[i], m_Width / 20 * 12, (int) m_Height / 20 * 4, paint);
                    } else if (i == 2) {
                        canvas.drawText(result[i], m_Width / 20 * 12, (int) m_Height / 20 * 5, paint);
                    }
                }

            }
            if (m_SaraSay > 8) {
                LoadEnemy();
                m_plot = 4;
            }
            break;
            case 4:
                //LoadEnemy();
                m_airtext1.SlowText(canvas, m_Width, m_Height, timeDelta);
                // m_airtext1.SlowText(canvas, m_Width, m_Height, timeDelta);
                if (m_airtext1.getState() == true) {

                    Units.setRoundState(true);
                    m_plot = 5;
                }
                break;
            case 5:
                if (Units.EnemyUnits.size() == 0) {
                    Units.setRoundTheEnd(true);
                    Units.setRoundState(false);
                    m_plot = 6;
                    m_WhoWIn = 1;
                    SetMap();
                } else if (Units.MyUnits.size() == 0) {
                    Units.setRoundTheEnd(true);
                    Units.setRoundState(false);
                    m_plot = 6;
                    m_WhoWIn = 2;
                    SetMap();
                }

                // m_plot=6;
                break;
            case 6:

                // canvas.drawText("게임 끝",100,100,paint);
                if (m_WhoWIn == 1) {
                    m_airtextVic.FadeOutText(canvas, m_Width, m_Height, timeDelta);
                    if (m_airtextVic.getState() == true) {
                        DBManager.getInstance().go_robby = 2;
                        m_plot = 7;
                    }
                    //아군 승리
                } else if (m_WhoWIn == 2) {
                    m_airTextFebe.FadeOutText(canvas, m_Width, m_Height, timeDelta);
                    m_plot = 7;
                    //적 승리
                } else {
                    //무 승부
                }
                break;
            case 7:
                Sound.getInstance().backgroundRelease();
                AppManager.getInstance().state = AppManager.S_LOADING;
                AppManager.getInstance().getGameView().ChangeGameState(new Ready_Room());

                break;
        }


    }

    /*  public void AirText(Canvas canvas,String a) throws InterruptedException {
          for(int i=0;i<a.length();i++) {
              for(int j=0;j<=i;j++)
              {
                  String aa=a.toUpperCase();
                  canvas.drawText(""+aa.charAt(j),m_Width/20*(9+j),m_Height/2,paint);
              }
      }*/
    @Override
    public void Render(Canvas canvas) {
        if (AppManager.getInstance().state != AppManager.S_LOADING) {


            switch (my_view) {
                case GAMESTART:

                    GraphicManager.getInstance().background.Draw(canvas, -750, -450);
                    GameDraw(canvas);
                    fade_in.fadeDraw(canvas);

                    break;
                case SINARIO:
                    GraphicManager.getInstance().background.Draw(canvas, -750, -450);
                    SenariDraw(canvas);
                    GraphicManager.getInstance().m_airplane.Draw(canvas, 1, m_Width / 2, m_Height / 2);
                    break;
            }
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void TouchGame(float x, float y) {
        int count = 0;
        if (y > (int) m_Height - (int) m_Height / 6) {
            for (int i = 0; i < UI.Button.size(); i++) {
                if (UI.Button.get(i).Collusion(m_click_x, m_click_y, UI.rsizex, UI.rsizey)) {
                    m_UI_Touch_Postion = i;
                }
            }
        } else {
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    Unit temp;
                    if (tileColl.get(count).resultCal(m_click_x / m_matrix_x - m_diffX, m_click_y / m_matrix_y - m_diffY) == true) {
                        //  UnitValue.m_map[i][j]=3;
                        //점핑 트랩 생산
                        if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == UnitValue.F_JUMPINGTRAP && UI_imfor.GetGold() >= 10) {
                            if (UnitValue.m_map[i][j] != 3) {
                                temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.trap));
                                temp.resizebitmap(25, 25);
                                CreateJumoingTrap(i, j, temp);
                                m_UI_Touch_Postion = 0;
                            }
                            //아처 타워 생산
                        } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == UnitValue.F_TOWER && UI_imfor.GetGold() >= 100) {
                            temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.archortower));
                            CreateArchorTower(i, j, temp, 0);

                            //좀비 생산
                        } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == UnitValue.F_ZOMBIE && UI_imfor.GetGold() >= 50) {
                            temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.zombie));
                            temp.resizebitmap(25, 25);
                            CreateZombie(i, j, temp);

                            //골드 전사 생산
                        } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == UnitValue.F_GOLDRUN && UI_imfor.GetGold() >= 50) {
                            temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.zombie));
                            temp.resizebitmap(25, 25);
                            CreateZombie(i, j, temp);

                            //엘사 타워생산
                        } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == UnitValue.F_ELSATOWER && UI_imfor.GetGold() >= 200) {
                            temp = new Unit(GraphicManager.getInstance().mElsa_Tower.m_bitmap);

                            CreateMagicTower(i, j, temp, false);

                            ///안나 생산
                        } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == UnitValue.F_ANNA && UI_imfor.GetGold() >= 10) {

                            CreateAnna(i, j, 1);
                        }

                    } else {
                        //  UnitValue.m_map[i][j]=3;
                    }
                    count++;
                }


            }

        }
    }

    public void setEnviroMent() {

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (UnitValue.m_testmap[i][j] == UnitValue.F_ROCK1) {
                    CreateRock(i, j);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        String strMsg = "";
        Log.i("액션" + strMsg, "" + strMsg);
        boolean statetimeer = false;
        x = event.getX();
        y = event.getY();
        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: {

                m_click_x = x;
                m_click_y = y;
                //temptitle3.SetPosition((int)(m_click_x),(int)(m_click_y));
                m_movex = x;
                m_movey = y;

                switch (m_plot) {
                    case 2: //배치 시작 터치 부분
                        TouchGame(x, y);
                        break;
                }
                // TouchGame(x, y);
                if (my_view == GAMESTART) {
                    if (m_SaraSay < 7 || m_batch_gameTime <= 0)
                        m_SaraSay += 1;
                }
                if (my_view == SINARIO) {
                    if (m_SaraSay < 12)
                        m_SaraSay += 1;
                }
                mode = DRAG;
                Log.d("zoom", "mode=DRAG");
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                m_time += 1;
                if (mode == DRAG) {
                    m_distx = x - m_movex;
                    m_disty = y - m_movey;

                    m_movex = event.getX();
                    m_movey = event.getY();
                    if (m_time > 2) {
                        if (m_diffY + m_disty > -400 && m_diffY + m_disty < 400) {
                            m_diffY += m_disty;
                        }
                        if (m_diffX + m_distx > -700 && m_diffX + m_distx < 500) {
                            m_diffX += m_distx;
                        }
                    }
                } else if (mode == ZOOM) {
                    m_click_x = event.getX(0);
                    m_click_y = event.getY(0);

                    //  newDist = spacing(event);
                    m_click2_x = event.getX(1);
                    m_click2_y = event.getY(1);
                    newDist = spacing(event);

                    if (newDist - oldDist > 20) { // zoom in
                        if (m_matrix_x < 2) {
                            m_matrix_x = m_matrix_x + (m_matrix_x * 0.02f);
                            m_matrix_y = m_matrix_y + (m_matrix_y * 0.02f);
                        }
                        oldDist = newDist;

                        matrix.setScale(m_matrix_x, m_matrix_y);
                    } else if (oldDist - newDist > 20) { // zoom out
                        oldDist = newDist;

                        m_matrix_x = m_matrix_x - (m_matrix_x * 0.02f);
                        m_matrix_y = m_matrix_y - (m_matrix_y * 0.02f);

                        matrix.setScale(m_matrix_x, m_matrix_y);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP: {


                m_time = 0;
                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                //두번째 손가락 터치(손가락 2개를 인식하였기 때문에 핀치줌으로 판별
                mode = ZOOM;

                newDist = spacing(event);
                oldDist = spacing(event);
                m_click2_x = event.getX(1);
                m_click2_y = event.getY(1);
                Log.d("zoom", "newDist=" + newDist);
                Log.d("zoom", "oldDist=" + oldDist);
                Log.d("zoom", "mode=ZOOM");
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_CANCEL:
            default:
                break;


        }
        return true;
    }

    //줌인 줌 아웃을 위한 터치 이벤트 계산 함수
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    public void CreateMagicTower(int i, int j, Unit temp, boolean enemy) {
        if (enemy == true) {
            if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {
                Sound.getInstance().play(1);
                UnitValue.m_map[i][j] = 3;//원 위치
                UnitValue.m_map[i][j + 1] = 3; //y값 증가
                UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
                UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1
                m_UI_Touch_Postion = 0;
                temp.SetPos(i, j);
                temp.ElsaTower(1);
                //temp.resizebitmap(100-100/3,60);
                Unit_Imfor stemp = new Unit_Imfor(temp, 50, 0, UnitValue.F_ELSATOWER);
                stemp.InitEffect(UnitValue.F_ELSATOWER);
                //  Units.EnemyUnits.add(stemp);
                stemp.myUnitObject.addPosition(new Vec2(i, j));
                stemp.myUnitObject.addPosition(new Vec2(i, j + 1));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j + 1));

                Units.EnemyUnits.add(stemp);
            }
        } else {
            if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {
                UI_imfor.BuyUnit(300);
                Sound.getInstance().play(1);
                UnitValue.m_map[i][j] = 3;//원 위치
                UnitValue.m_map[i][j + 1] = 3; //y값 증가
                UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
                UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1
                m_UI_Touch_Postion = 0;
                temp.SetPos(i, j);
                temp.ElsaTower(1);
                //temp.resizebitmap(100-100/3,60);
                Unit_Imfor stemp = new Unit_Imfor(temp, 50, 0, UnitValue.F_ELSATOWER);
                stemp.InitEffect(UnitValue.F_ELSATOWER);
                stemp.myUnitObject.addPosition(new Vec2(i, j));
                stemp.myUnitObject.addPosition(new Vec2(i, j + 1));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j + 1));

                Units.MyUnits.add(stemp);
            }
        }
    }

    public void LoadEnemys() {
        Unit temp, temp1, temp2;
        temp = new Unit(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
        CreateMagicTower(10, 10, temp, true);
        temp1 = new Unit(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
        CreateMagicTower(10, 15, temp1, true);
        temp2 = new Unit(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
        CreateMagicTower(20, 20, temp2, true);
    }

    public void CreateZombie(int i, int j, Unit temp) {
        Sound.getInstance().play(3);
        UnitValue.m_map[i][j] = 3;
        UI_imfor.BuyUnit(30);
        temp.SetPos(i, j);
        //temp.SetPosition(i,j);

        Units.MyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_ZOMBIE));
        //Unit lastUnit = Units.MyUnits.get(Units.MyUnits.size()-1);
        //findedPath = finderOjbect.find(Units.MyUnits.get(0), lastUnit); // 찾기

        Units.MyUnits.get(Units.MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_map);
        Units.MyUnits.get(Units.MyUnits.size() - 1).WhoEnemy(Units.MyUnits.get(0).myUnitObject);


    }

    public void CreateJumoingTrap(int i, int j, Unit temp) {
        Sound.getInstance().play(2);
        UnitValue.m_map[i][j] = 3;
        UI_imfor.BuyUnit(10);
        temp.SetPos(i, j);

        Units.MyUnits.add(new Unit_Imfor(temp, 0, 0, UnitValue.F_JUMPINGTRAP));
        Units.MyUnits.get(Units.MyUnits.size() - 1).mHp = 1;
    }

    public void CreateRock(int i, int j) {
        Unit temp;
        temp = new Unit(GraphicManager.getInstance().rock1.m_bitmap);
        temp.SetPos(i, j);
        Units.Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_ROCK1));
        UnitValue.m_map[i][j] = 3;

    }

    //안나생성 부분  whounit.1번은 아군 2번은 적
    public void CreateAnna(int i, int j, int whounit) {
        Sound.getInstance().play(4);
        UI_imfor.BuyUnit(10);
        Unit temp = new Unit(GraphicManager.getInstance().m_anna.m_bitmap);
        temp.Anna(1);
        temp.SetPos(i, j);
        temp.addPosition(temp.Postion);
        //temp.SetPosition(i,j);
        //Unit lastUnit = Units.MyUnits.get(Units.MyUnits.size()-1);
        //findedPath = finderOjbect.find(Units.MyUnits.get(0), lastUnit); // 찾기

        switch (whounit) {
            case 1:
                //아군


                Units.MyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_ANNA));
                Units.MyUnits.get(Units.MyUnits.size() - 1).InitEffect(UnitValue.F_ANNA);
                Units.MyUnits.get(Units.MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_map);
                Units.MyUnits.get(Units.MyUnits.size() - 1).my_enemy = Units.EnemyUnits.get(0);
                Units.MyUnits.get(Units.MyUnits.size() - 1).WhoEnemy(Units.EnemyUnits.get(0).myUnitObject);

                break;
            case 2:
                //적군군
                //적군군
                temp.Anna(1);
                Units.EnemyUnits.add(new Unit_Imfor(temp, 100, 1, UnitValue.F_ANNA));
                Units.EnemyUnits.get(Units.EnemyUnits.size() - 1).InitEffect(UnitValue.F_ANNA);
                Units.EnemyUnits.get(Units.EnemyUnits.size() - 1).myPath.LoadMap(UnitValue.m_map);
                Units.EnemyUnits.get(Units.EnemyUnits.size() - 1).my_enemy = Units.MyUnits.get(0);
                Units.EnemyUnits.get(Units.EnemyUnits.size() - 1).WhoEnemy(Units.MyUnits.get(0).myUnitObject);
                break;
        }/**/

    }

    public void CreateArchorTower(int i, int j, Unit temp, int whounit) {
        if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {

            UI_imfor.BuyUnit(100);
            Sound.getInstance().play(1);
            UnitValue.m_map[i][j] = 3;//원 위치

            UnitValue.m_map[i][j - 1] = 3; //y값 증가
            UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
            UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1
            m_UI_Touch_Postion = 0;
            temp.SetPos(i, j);
            temp.resizebitmap(100 - 100 / 3, 60);
            if (whounit == 0) {
                Units.MyUnits.add(new Unit_Imfor(temp, 30, 0, UnitValue.F_TOWER));
            } else {
                Units.EnemyUnits.add(new Unit_Imfor(temp, 30, 0, UnitValue.F_TOWER));
            }
        }
    }

    public void SetMap() {
        for (int i = 0; i < Units.MyUnits.size(); i++) {
            if (Units.MyUnits.get(i).mType == UnitValue.F_JUMPINGTRAP) {
                UnitValue.m_testmap[Units.MyUnits.get(i).myUnitObject.Postion.x][Units.MyUnits.get(i).myUnitObject.Postion.y] = UnitValue.F_ROCK1;
            }
        }

    }

    public void LoadEnemy() {


        CreateMagicTower(20,20,new Unit(GraphicManager.getInstance().m_elsatower.m_bitmap),true);
        CreateMagicTower(20,15,new Unit(GraphicManager.getInstance().m_elsatower.m_bitmap),true);
        CreateMagicTower(20,10,new Unit(GraphicManager.getInstance().m_elsatower.m_bitmap),true);
        CreateMagicTower(10,20,new Unit(GraphicManager.getInstance().m_elsatower.m_bitmap),true);
        CreateMagicTower(8,40,new Unit(GraphicManager.getInstance().m_elsatower.m_bitmap),true);

    }

    //타운홀 생성 부분
    public void CreateHall(int i, int j, int whounit) {
        //  UnitValue.m_map[i][j] = 3;//원 위치
        // UnitValue.m_map[i][j + 1] = 3; //y값 증가
        //UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
        //UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1
        Unit temp;
        temp = new Unit(GraphicManager.getInstance().mTownHall.m_bitmap);
        temp.SetPos(i, j);

        if (whounit == 0) {
            Units.MyUnits.add(new Unit_Imfor(temp, 5, 1, UnitValue.F_TOWNHALL));
            Units.MyUnits.get(0).myUnitObject.addPosition(new Vec2(i, j));
        } else if (whounit == 1)
        {
            Units.EnemyUnits.add(new Unit_Imfor(temp, 5, 1, UnitValue.F_TOWNHALL));
        Units.EnemyUnits.get(0).myUnitObject.addPosition(new Vec2(i, j));
      }
   }

    //아처 타워 생성 부분 추후 수정 예정 부분


    public void CreateTree1(int i, int j) {
        if (UnitValue.m_map[i][j] != UnitValue.M_NOTMOVE) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().tree1.m_bitmap);
            temp.SetPos(i, j);
            Units.Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_TREE1));
            UnitValue.m_map[i][j] = UnitValue.M_NOTMOVE;
        }

    }

    public void CreateRock2(int i, int j) {
        if (UnitValue.m_map[i][j] != UnitValue.M_NOTMOVE) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().rock2.m_bitmap);
            temp.SetPos(i, j);
            Units.Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_ROCKE2));
            UnitValue.m_map[i][j] = UnitValue.M_NOTMOVE;
        }

    }
}


