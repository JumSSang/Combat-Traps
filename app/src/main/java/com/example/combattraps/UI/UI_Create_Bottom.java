package com.example.combattraps.UI;

import com.example.combattraps.Game.UnitDirect.UnitValue;
import com.example.combattraps.R;
import com.example.combattraps.immortal.AppManager;
import com.example.combattraps.immortal.Graphic_image;

import java.util.ArrayList;

public class UI_Create_Bottom
{

    public ArrayList<UnitList>CheckTable;
    public ArrayList <Graphic_image>Button;//버튼이미지 생성
    public int u_number=0; //버튼이 생성 되어야 할 갯수   최대 11개
    public int state_buliding=1; //화면에 뿌려줄 버튼 View 번호
    public float rsizex;
    public float rsizey;


    public UI_Create_Bottom(float x, float y, int un, int tnm, ArrayList<UnitList> temp)//2개의 맴버변수는 화면의 Width와 Height값
    {
       Button=new ArrayList<Graphic_image>();
       CheckTable=new ArrayList<UnitList>();
       CheckTable=temp;
        u_number=CheckTable.size();
       CreateSize(x,y);
        rsizex=x/12;
        rsizey=y/6;
    }


    public void  CreateSize(float x,float y) //버튼위치를 잡아주는 함수
    {
        Graphic_image temp;
        float setx=0;
        float sety=y/6;
        for(int i=0;i<CheckTable.size();i++)
        {

            switch (CheckTable.get(i).retruncode())
            {
                case 0:                //스압버튼
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.swapbutton));
                    break;
                case 1:  //타워
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.towersum));
                    break;

                case 2:  //점핑트랩
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.jump_sum));
                    break;

                case 3: //좀비
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.zombie_sum));
                    break;

                case 4: //황금기사
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.glodsum));
                    break;

                case 5://엘사타워
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_elsa));
                    break;

                case 6: //안나
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.sum_anna));
                    break;

                case UnitValue.F_ROCK1:
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.rock1));
                    break;

                case UnitValue.F_ROCKE2:
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.rock2));
                    break;
                case UnitValue.F_TREE1:
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.tree1));
                    break;
                case UnitValue.F_BOOM:
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.trap_bommer));
                    break;



                default:
                    temp=new Graphic_image(AppManager.getInstance().getBitmap(R.drawable.towersum));
                    break;
            }
            temp.resizebitmap((int)x/12,(int)y/10);
            temp.SetPosition((int)(setx),(int)(y-sety));
            Button.add(temp);
            setx+=(x/12)+5;
        }
    }

}

