package com.example.combattraps.View;

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
import android.view.VelocityTracker;

import com.example.combattraps.Game.ActiveCollusion;
import com.example.combattraps.Game.GraphicManager;
import com.example.combattraps.Game.Map_analysis;
import com.example.combattraps.Game.PathFinder;
import com.example.combattraps.Game.Unit;
import com.example.combattraps.Game.UnitManager;
import com.example.combattraps.Game_NetWork.GameNet;
import com.example.combattraps.Game_NetWork.ListItem;
import com.example.combattraps.R;
import com.example.combattraps.UI.UI_Create_Bottom;
import com.example.combattraps.UI.UI_Create_Imfor;
import com.example.combattraps.UI.UnitList;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.GameThread;
import com.example.combattraps.immortal.Graphic_image;
import com.example.combattraps.immortal.IState;
import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.Vec2;
import com.example.combattraps.Game.ActiveCollusion;

import java.util.ArrayList;

public class St_Battle implements IState {

    private final PathFinder finderOjbect = new PathFinder();
    private PathFinder.Node findedPath;

    GameNet St_Network;
    ArrayList<ListItem> MyListImmforList = new ArrayList();
    Map_analysis AnaMap;
    Matrix matrix = new Matrix();
    private GameThread m_thread;
    private ArrayList<Graphic_image> MapTile;
    private ArrayList<ActiveCollusion> tileColl;
    private ArrayList<UnitList> UnitDataList;
    private VelocityTracker m_Velocity;
    private UI_Create_Bottom UI;
    private UI_Create_Imfor UI_imfor;
    private ArrayList<UnitManager> PlayerUnit;
    public int m_UI_Touch_Postion = 0;
    float m_click_x = 0; //첫번째 터치좌표 x
    float m_click_y = 0; //첫번째 터치좌표 y
    float m_click2_x = 0; //2번째 터치 좌표 x
    float m_click2_y = 0; //2번째 터치 좌표 y
    /////////////////////

    float m_matrix_x = 1.0f;//메트릭스 변화 하는 x비율
    float m_matrix_y = 1.0f;//메트릭스 변화 하는 y비율

    static final int f_tower = 1;
    static final int f_jumpingtrap = 2;
    static final int f_zombie = 3;
    static final int f_goldrun = 4;
    static final int f_elsatower = 5;
    static final int f_anna = 6;
    static final int f_townhall = 7;
    static final int Store = 0;
    static final int Game = 1;

    public float m_Width;
    public float m_Height;

    int[][] m_map = new int[50][50];
    int[][] m_bmap = new int[50][50];
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

    @Override
    public void Init() {
        Sound.getInstance().addList(1, R.raw.buildingsaw);
        Sound.getInstance().backgroundPlay(R.raw.thetruth);
        Sound.getInstance().addList(2, R.raw.smallchain);
        Sound.getInstance().addList(3, R.raw.zombie_create_sound);
        Sound.getInstance().addList(4, R.raw.hello);
        GraphicManager.getInstance().background.resizebitmap(3000, 2000);
       // GraphicManager.getInstance().Init();
        currentTime = System.currentTimeMillis() / 1000;
        St_Network = new GameNet();
        tileColl = new ArrayList<ActiveCollusion>();
        UnitDataList = new ArrayList<UnitList>();
        PlayerUnit = new ArrayList<UnitManager>();
        DisplayMetrics metrics = AppManager.getInstance().getResources().getDisplayMetrics();
        m_Width = metrics.widthPixels;
        m_Height = metrics.heightPixels;
        matrix.setScale(m_matrix_x, m_matrix_y);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                ActiveCollusion temp = new ActiveCollusion();
                temp.addSpot(750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i) + 12);//p1 왼쪽
                temp.addSpot(750 + 50 / 2 * (j - i) + 25, -300 + 25 / 2 * (j + i));//p2 //위
                temp.addSpot(750 + 50 / 2 * (j - i) + 50, -300 + 25 / 2 * (j + i) + 12);//p3 오른쪽
                temp.addSpot(750 + 50 / 2 * (j - i) + 25, -300 + 25 / 2 * (j + i) + 25);//p4 아래
                temp.distanceCal();
                tileColl.add(temp);
                m_bmap[i][j] = 0;
                if ((i + j) % 2 == 0) {
                    m_map[i][j] = 1;
                } else {
                    m_map[i][j] = 2;
                }
            }
        }
        finderOjbect.LoadMap(m_map);

        UnitAdd(); //데이터 베이스로 부터 유닛 목록 받아온다.
        UI = new UI_Create_Bottom(m_Width, m_Height, UnitDataList.size(), 0, UnitDataList);
        //id,gold,gname,log,glogo
        UI_imfor = new UI_Create_Imfor(m_Width, m_Height, "go7072", 100000000, "NLIP", 1, 1, "JJUMSANG", 100000000, "한강정모", 1, 1);
        Unit Mtemp;
        Mtemp = new Unit(GraphicManager.getInstance().mTownHall.m_bitmap);
        Mtemp.SetPos(49, 0);

        CreateHall(1, 48, Mtemp);
        Unit aTemp;
    }

    public void UnitAdd() {

        UnitList temp = new UnitList();
        UnitList temp1 = new UnitList();
        UnitList temp2 = new UnitList();
        UnitList temp3 = new UnitList();
        UnitList temp4 = new UnitList();
        UnitList temp5 = new UnitList();
        UnitList temp6 = new UnitList();
        temp.set(0, 1, 0);
        UnitDataList.add(temp);//1
        temp1.set(1, 1, 0);
        UnitDataList.add(temp1);//2
        temp2.set(2, 1, 0);
        UnitDataList.add(temp2);//3
        temp3.set(3, 1, 0);
        UnitDataList.add(temp3);//3
        temp4.set(4, 1, 0);
        UnitDataList.add(temp4);//3
        temp5.set(5, 1, 0);
        UnitDataList.add(temp5);//3
        temp6.set(6, 1, 0);
        UnitDataList.add(temp6);//3


    }

    @Override
    public void Destroy() {
    }

    @Override
    public void Update() {
        // long frameEndTime = System.currentTimeMillis();
        //long delta = frameEndTime - frameStartTime;
        double newTime = System.currentTimeMillis() / 1000.0;
        double timeDelta = newTime - currentTime;
        currentTime = newTime;
        m_time += timeDelta;
        m_thread_tiem += timeDelta;
        if (m_checkLoader == 0) {
            m_OpenServerTime += timeDelta;
        }
        MoveUpdate(timeDelta);

        if (m_OpenServerTime > 10) {

            m_OpenServerTime = 0;
            m_checkLoader++;
        }
        for (int i = 0; i < PlayerUnit.size(); i++) {
            if (PlayerUnit.get(i).mType == f_elsatower) {
                PlayerUnit.get(i).myUnitObject.PatrolUpdate(System.currentTimeMillis());
                PlayerUnit.get(i).m_effect.Update(System.currentTimeMillis());
                for (int j = 0; j < PlayerUnit.size(); j++) {
                    if (AABB(PlayerUnit.get(i).DrawPosition, PlayerUnit.get(j).DrawPosition, PlayerUnit.get(i).range) && PlayerUnit.get(j).mType == f_anna) {
                        PlayerUnit.remove(j);
                        if (PlayerUnit.get(i).mHp > 0) {
                            PlayerUnit.get(i).mHp -= 1;
                        } else {
                            PlayerUnit.remove(i);
                        }
                    }
                }
                //if(AABB(PlayerUnit.get(i).DrawPosition,))
                //PlayerUnit.get(i).AABB(PlayerUnit.get(i).DrawPosition,)
            }
        }
        //GraphicManager.getInstance().m_effect.Update(System.currentTimeMillis());

    }

    //m_time=m_time*1000;

    @Override
    public void Render(Canvas canvas) {

        Paint paint;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.save();
        canvas.setMatrix(matrix);
        canvas.translate(m_diffX, m_diffY);

        GraphicManager.getInstance().background.Draw(canvas, -750, -450);
        //타일 한번 깔아준다.
        for (int i = 0; i < 50; i++) {

            for (int j = 0; j < 50; j++) {
                if (m_map[i][j] == 1) {
                    GraphicManager.getInstance().temptile1.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));

                } else if (m_map[i][j] == 2) {
                    GraphicManager.getInstance().temptile2.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));
                } else if (m_map[i][j] == 3) {
                    GraphicManager.getInstance().temptitle3.Draw(canvas, 750 + 50 / 2 * (j - i), -300 + 25 / 2 * (j + i));
                }
            }
        }
        for (int i = 0; i < PlayerUnit.size(); i++) {

            int x = PlayerUnit.get(i).myUnitObject.Postion.x;
            int y = PlayerUnit.get(i).myUnitObject.Postion.y;
            //750 + 50 / 2 * (y - x), -300 + 30 / 2 * (y+ x)
            if (PlayerUnit.get(i).mType == f_elsatower) {
                paint.setARGB(50, 255, 0, 0);
                canvas.drawCircle(PlayerUnit.get(i).m_BoundingSpear.fx, PlayerUnit.get(i).m_BoundingSpear.fy, 200, paint);
                PlayerUnit.get(i).myUnitObject.Draw(canvas, 1, PlayerUnit.get(i).DrawPosition.fx, PlayerUnit.get(i).DrawPosition.fy - 100);
                PlayerUnit.get(i).Hpbar();
                PlayerUnit.get(i).originHP.top -= 110;
                PlayerUnit.get(i).originHP.bottom -= 110;
                PlayerUnit.get(i).m_effect.Effect(30);
                PlayerUnit.get(i).m_effect.Draw(canvas, 1, PlayerUnit.get(i).DrawPosition.fx - 30, PlayerUnit.get(i).DrawPosition.fy - 50);
                paint.setColor(Color.GREEN);
                canvas.drawRect(PlayerUnit.get(i).originHP, paint);
                paint.setColor(Color.WHITE);

            } else if (PlayerUnit.get(i).mType == f_townhall) {
                PlayerUnit.get(i).myUnitObject.Draw(canvas, PlayerUnit.get(i).DrawPosition.x + 60, PlayerUnit.get(i).DrawPosition.y - 160);
            } else if (PlayerUnit.get(i).mType == f_anna) {
                PlayerUnit.get(i).myUnitObject.Draw(canvas, 1, PlayerUnit.get(i).DrawPosition.fx, PlayerUnit.get(i).DrawPosition.fy - 30);
                paint.setColor(Color.GREEN);
                PlayerUnit.get(i).Hpbar();
                PlayerUnit.get(i).originHP.top -= 50;
                PlayerUnit.get(i).originHP.bottom -= 50;
                canvas.drawRect(PlayerUnit.get(i).originHP, paint);
                paint.setColor(Color.WHITE);
            } else {


                PlayerUnit.get(i).myUnitObject.Draw(canvas, PlayerUnit.get(i).DrawPosition.x, PlayerUnit.get(i).DrawPosition.y);

                PlayerUnit.get(i).Hpbar();
                paint.setColor(Color.GREEN);
                canvas.drawRect(PlayerUnit.get(i).originHP, paint);
                paint.setColor(Color.WHITE);
            }

        }

        paint.setColor(Color.WHITE);

        canvas.drawCircle((m_click_x / m_matrix_x - m_diffX), (m_click_y / m_matrix_y - m_diffY), 5, paint);

        canvas.drawCircle(m_click2_x - m_diffX, m_click2_y - m_diffY, 5, paint);
        canvas.restore();
        GraphicManager.getInstance().ButtonView_Image.Draw(canvas, 0, (int) m_Height - (int) m_Height / 6);
        UI_imfor.Draw(canvas);
        for (int i = 0; i < UI.Button.size(); i++) {
            UI.Button.get(i).Draw(canvas);
            canvas.drawText("" + i, (int) (UI.Button.get(i).GetX()), (int) (UI.Button.get(i).GetY()), paint);
        }
        //
        //  TowerButton.Draw(canvas,100,((int)m_Height-(int)m_Height/7));
        // Store_button.Draw(canvas, 0, (int) m_Height - (int) m_Height / 4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);

        canvas.drawRect((m_UI_Touch_Postion * 5) + m_UI_Touch_Postion * m_Width / 12, m_Height - m_Height / 6, (m_UI_Touch_Postion * 5) + m_UI_Touch_Postion * m_Width / 12 + m_Width / 12, m_Height - m_Height / 18, paint);

        paint.setStrokeWidth(0);
        paint.setColor(Color.WHITE);
        canvas.drawText("", 100, 100, paint);
        canvas.drawText("" + m_time, 500, 500, paint);
        if (PlayerUnit.size() > 2) {
           /* canvas.drawText("타워 x의값 : " + PlayerUnit.get(1).DrawPosition.fy, 800,200, paint);
            canvas.drawText("타워 y의값 : " + PlayerUnit.get(1).DrawPosition.fy, 800, 250, paint);*/
            float srcx = PlayerUnit.get(1).DrawPosition.fx;
            float srcy = PlayerUnit.get(1).DrawPosition.fy;
            float destx = PlayerUnit.get(PlayerUnit.size() - 1).DrawPosition.fx;
            float desty = PlayerUnit.get(PlayerUnit.size() - 1).DrawPosition.fy;

            float distance = (float) Math.sqrt(Math.pow((destx - srcx), 2) + Math.pow(desty - srcy, 2));
        /*    canvas.drawText("distance: " +distance,800,300,paint);*/
        }/*
        canvas.drawText("x의값 : "+PlayerUnit.get(PlayerUnit.size()-1).DrawPosition.fx,800,100,paint);
        canvas.drawText("y의값 : "+PlayerUnit.get(PlayerUnit.size()-1).DrawPosition.fy,800,150,paint);
*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        String strMsg = "";
        Log.i("액션" + strMsg, "" + strMsg);
        //Sound.getInstance().play(1);
        //Sound.getInstance().backgroundPlay(R.raw.zombie_background);
        // LoadMap(); DB로 부터 맵 정보 불러오기
        //Sound.getInstance().loopplay(1);
        boolean statetimeer = false;
        float x = event.getX();
        float y = event.getY();
        // m_MediaPlayer.start();


        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN: {

                m_click_x = x;
                m_click_y = y;
                //temptitle3.SetPosition((int)(m_click_x),(int)(m_click_y));
                m_movex = x;
                m_movey = y;
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
                                // m_map[i][j]=3;
                                if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == f_jumpingtrap && UI_imfor.GetGold() >= 10) {
                                    if (m_map[i][j] != 3) {
                                        temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.trap));
                                        temp.resizebitmap(25, 25);
                                        CreateJumoingTrap(i, j, temp);
                                        if (PlayerUnit.size() > 2) {

                                        }
                                        m_UI_Touch_Postion = 0;
                                    }
                                } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == f_tower && UI_imfor.GetGold() >= 100) {
                                    temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.archortower));
                                    CreateArchorTower(i, j, temp);
                                } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == f_zombie && UI_imfor.GetGold() >= 50) {
                                    temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.zombie));
                                    temp.resizebitmap(25, 25);
                                    CreateZombie(i, j, temp);
                                } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == f_goldrun && UI_imfor.GetGold() >= 50) {
                                    temp = new Unit(AppManager.getInstance().getBitmap(R.drawable.zombie));
                                    temp.resizebitmap(25, 25);
                                    CreateZombie(i, j, temp);
                                } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == f_elsatower && UI_imfor.GetGold() >= 200) {
                                    temp = new Unit(GraphicManager.getInstance().mElsa_Tower.m_bitmap);
                                    // temp.resizebitmap(25,25);
                                    CreateMagicTower(i, j, temp);
                                } else if (UI.CheckTable.get(m_UI_Touch_Postion).retruncode() == f_anna && UI_imfor.GetGold() >= 10) {
                                    temp = new Unit(GraphicManager.getInstance().m_anna.m_bitmap);
                                    temp.Anna(1);
                                    // temp.resizebitmap(25,25);
                                    CreateAnna(i, j, temp);
                                }

                            } else {
                                // m_map[i][j]=3;
                            }
                            count++;
                        }


                    }
                    mode = DRAG;
                    Log.d("zoom", "mode=DRAG");
                }
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

    public void CreateMagicTower(int i, int j, Unit temp) {
        if (m_map[i][j] != 3 && m_map[i][j + 1] != 3 && m_map[i + 1][j + 1] != 3 && m_map[i + 1][j] != 3) {
            m_bmap[i][j] = f_elsatower;
            UI_imfor.BuyUnit(300);

            Sound.getInstance().play(1);
            m_map[i][j] = 3;//원 위치
            m_map[i][j + 1] = 3; //y값 증가
            m_map[i + 1][j] = 3; //left값 증가 +1
            m_map[i + 1][j + 1] = 3; //y left값 증가 +1
            m_UI_Touch_Postion = 0;
            temp.SetPos(i, j);
            temp.ElsaTower(1);

            //temp.resizebitmap(100-100/3,60);
            UnitManager stemp = new UnitManager(temp, 50, 0, f_elsatower);
            stemp.InitEffect();
            PlayerUnit.add(stemp);

        }
    }

    public void CreateZombie(int i, int j, Unit temp) {
        Sound.getInstance().play(3);
        m_bmap[i][j] = f_zombie;
        m_map[i][j] = 3;
        UI_imfor.BuyUnit(30);
        temp.SetPos(i, j);
        //temp.SetPosition(i,j);

        PlayerUnit.add(new UnitManager(temp, 10, 1, f_zombie));
        //Unit lastUnit = PlayerUnit.get(PlayerUnit.size()-1);
        //findedPath = finderOjbect.find(PlayerUnit.get(0), lastUnit); // 찾기

        PlayerUnit.get(PlayerUnit.size() - 1).myPath.LoadMap(m_map);
        PlayerUnit.get(PlayerUnit.size() - 1).WhoEnemy(PlayerUnit.get(0).myUnitObject);
        if (findedPath == null) {
            Log.i("aaa", "못찾음");
        }
    }

    public void CreateJumoingTrap(int i, int j, Unit temp) {
        Sound.getInstance().play(2);
        m_bmap[i][j] = f_jumpingtrap;
        m_map[i][j] = 3;
        UI_imfor.BuyUnit(10);

        temp.SetPos(i, j);
        PlayerUnit.add(new UnitManager(temp, 0, 0, f_jumpingtrap));
    }

    public void CreateAnna(int i, int j, Unit temp) {
        Sound.getInstance().play(4);
        m_bmap[i][j] = f_anna;

        UI_imfor.BuyUnit(10);
        temp.SetPos(i, j);
        //temp.SetPosition(i,j);

        PlayerUnit.add(new UnitManager(temp, 10, 1, f_anna));
        //Unit lastUnit = PlayerUnit.get(PlayerUnit.size()-1);
        //findedPath = finderOjbect.find(PlayerUnit.get(0), lastUnit); // 찾기

        PlayerUnit.get(PlayerUnit.size() - 1).myPath.LoadMap(m_map);
        PlayerUnit.get(PlayerUnit.size() - 1).WhoEnemy(PlayerUnit.get(0).myUnitObject);
        if (findedPath == null) {
            Log.i("aaa", "못찾음");
        }
    }

    public void CreateHall(int i, int j, Unit temp) {
        m_map[i][j] = 3;//원 위치
        m_map[i][j + 1] = 3; //y값 증가
        m_map[i + 1][j] = 3; //left값 증가 +1
        m_map[i + 1][j + 1] = 3; //y left값 증가 +1
        PlayerUnit.add(new UnitManager(temp, 300, 1, f_townhall));

    }

    public void CreateArchorTower(int i, int j, Unit temp) {
        if (m_map[i][j] != 3 && m_map[i][j + 1] != 3 && m_map[i + 1][j + 1] != 3 && m_map[i + 1][j] != 3) {
            m_bmap[i][j] = f_tower;
            UI_imfor.BuyUnit(100);
            Sound.getInstance().play(1);
            m_map[i][j] = 3;//원 위치

            m_map[i][j - 1] = 3; //y값 증가
            m_map[i + 1][j] = 3; //left값 증가 +1
            m_map[i + 1][j + 1] = 3; //y left값 증가 +1
            m_UI_Touch_Postion = 0;
            temp.SetPos(i, j);
            temp.resizebitmap(100 - 100 / 3, 60);
            PlayerUnit.add(new UnitManager(temp, 30, 0, f_tower));
        }
    }

    public void MoveUpdate(double dt) {


        //PlayerUnit.get(0).SetX(finderOjbect.closeNode.get(m_findnubmer).m_x);
        //PlayerUnit.get(0).SetY(finderOjbect.closeNode.get(m_findnubmer).m_y);
        // if(findedPath.parentNode!=null) {
        //int x = findedPath.m_pos.x - findedPath.parentNode.m_pos.x;
        //int y = findedPath.m_pos.y - findedPath.parentNode.m_pos.y;
        for (int i = 1; i < PlayerUnit.size(); i++) {
            PlayerUnit.get(i).myTime += dt; //유닛의 움직임 타임을 더해준다.
            if (PlayerUnit.get(i).myTime > 0.05f) {  //유닛의 움직임 타임이 0.5보다 커지면 타일을 하나씩 움직인다.
                if (PlayerUnit.get(i).findedPath != null)  //목표타겟이 되는 부분이 Null값이 아니면 다음 문장  실행
                    if (PlayerUnit.get(i).findedPath.parentNode != null) {  //부모노드가 비지않았다면 다음 으로

                        if (PlayerUnit.get(i).mType == f_anna && PlayerUnit.get(i).mThisMove == false) {
                            int x = PlayerUnit.get(i).findedPath.m_pos.x - PlayerUnit.get(i).findedPath.parentNode.m_pos.x;
                            int y = PlayerUnit.get(i).findedPath.m_pos.y - PlayerUnit.get(i).findedPath.parentNode.m_pos.y;
                            DirectionUpdate(x, y, PlayerUnit.get(i), f_anna);
                            PlayerUnit.get(i).minus(PlayerUnit.get(i).DrawPosition,
                                    new Vec2((float) (750 + 50 / 2 * (PlayerUnit.get(i).findedPath.parentNode.m_pos.y - PlayerUnit.get(i).findedPath.parentNode.m_pos.x)) - (float) Math.random() * 10, (float) (-300 + 25 / 2 * (PlayerUnit.get(i).findedPath.parentNode.m_pos.y + PlayerUnit.get(i).findedPath.parentNode.m_pos.x)) - (float) Math.random() * 10));
                            PlayerUnit.get(i).mThisMove = true;

                            PlayerUnit.get(i).m_moveVector.Normalize();
                            PlayerUnit.get(i).Speed(PlayerUnit.get(i).m_moveVector.fx / 6, PlayerUnit.get(i).m_moveVector.fy / 6);
                            PlayerUnit.get(i).count = 0;
                        }
                        if (PlayerUnit.get(i).mThisMove == true) {
                            if (PlayerUnit.get(i).count == 6) {
                                PlayerUnit.get(i).mThisMove = false;
                                PlayerUnit.get(i).count = 0;
                            } else {
                                PlayerUnit.get(i).count += 1;
                                PlayerUnit.get(i).DrawPosition.fx += PlayerUnit.get(i).mVSpeed.fx;
                                PlayerUnit.get(i).DrawPosition.fy += PlayerUnit.get(i).mVSpeed.fy;
                            }
                        }
                        if (PlayerUnit.get(i).mThisMove == false) {
                            PlayerUnit.get(i).findedPath = PlayerUnit.get(i).findedPath.parentNode;
                        }
                        PlayerUnit.get(i).myTime = 0;
                    }
            }

        }


    }

    public void DirectionUpdate(int x, int y, UnitManager a, int type) {

        if (type == f_anna) {
            if (x == -1 && y == 1)//left
            {
                a.myUnitObject.mDirection = 1;


            } else if (x == 1 && y == 1)//top
            {
                a.myUnitObject.mDirection = 4;


            } else if (x == 1 && y == -1)//right
            {
                a.myUnitObject.mDirection = 5;


            } else if (x == -1 && y == -1)//bottom
            {
                a.myUnitObject.mDirection = 7;

            } else if (x == 0 && y == 1)//lefttop
            {
                a.myUnitObject.mDirection = 2;

            } else if (x == -1 && y == 0)//leftbottom
            {
                a.myUnitObject.mDirection = 0;

            } else if (x == 1 && y == 0)//righttop
            {
                a.myUnitObject.mDirection = 4;

            } else if (x == 0 && y == -1)//rightbottom
            {
                a.myUnitObject.mDirection = 6;

            }

            a.mThisMove = true;
        }
        if (type == f_elsatower) {

        }
    }

    public boolean AABB(Vec2 A, Vec2 B, float range) {

        /*bool isCollision(Sphere* m1,Sphere *m2)
        {
            중점사이의거리= sqrt((float)((m2->x-m1->x)*(m2->x-m1->x))
                    +((m2->y-m1->y)*(m2->y-m1->y))
                    +((m2->z-m1->z)*(m2->z-m1->z)));
            if(m1->r+m2->r>=중점사이의거리)
                return TRUE;66666666666
            else
                return FALSE;
        }*/
        float distance = (float) Math.sqrt((B.fx - A.fx) * (B.fx - A.fx) + (B.fy - A.fy) * (B.fy - A.fy));
        // A.distance=distance;
        if (range >= distance) {
            return true;
        } else {
            return false;
        }

    }

}


