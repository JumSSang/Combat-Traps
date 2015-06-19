package com.example.combattraps.Game.UnitDirect;

import com.example.combattraps.immortal.GraphicManager;
import com.example.combattraps.immortal.Sound;
import com.example.combattraps.immortal.Vec2;

import java.util.ArrayList;

/**
 * Created by 경민 on 2015-06-15.
 */
public class CreateUnit {


    static public void CreateAnna(int i, int j,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits,boolean b_myUnit) {
//        Sound.getInstance().play(4);
        //   UI_imfor.BuyUnit(10);
        Unit temp = new Unit(GraphicManager.getInstance().m_anna.m_bitmap);
        temp.Anna(1);
        temp.SetPos(i, j);
        temp.addPosition(temp.Postion);
        //temp.SetPosition(i,j);
        //Unit lastUnit = Units.MyUnits.get(Units.MyUnits.size()-1);
        //findedPath = finderOjbect.find(Units.MyUnits.get(0), lastUnit); // 찾기

        if(b_myUnit)
                //아군
        {
            MyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_ANNA, true));
            MyUnits.get(MyUnits.size() - 1).InitEffect(UnitValue.F_ANNA);
            MyUnits.get(MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_map);

            if (EnemyUnits.size() > 0) {
                MyUnits.get(MyUnits.size() - 1).my_enemy = EnemyUnits.get(0);
                MyUnits.get(MyUnits.size() - 1).WhoEnemy(EnemyUnits.get(0).myUnitObject);
                MyUnits.get(MyUnits.size() - 1).myUnitObject.addPosition(MyUnits.get(MyUnits.size() - 1).myUnitObject.Postion);
                //UnitValue.m_map[Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.x][Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.y]=UnitValue.M_NOTMOVE;
            }
        }
        else
        {
                //적군군
                temp.Anna(1);
                EnemyUnits.add(new Unit_Imfor(temp, 100, 1, UnitValue.F_ANNA,false));
                EnemyUnits.get(EnemyUnits.size() - 1).InitEffect(UnitValue.F_ANNA);
                EnemyUnits.get(EnemyUnits.size() - 1).myPath.LoadMap(UnitValue.m_map);
            if (EnemyUnits.size() > 0) {
                EnemyUnits.get(EnemyUnits.size() - 1).my_enemy = MyUnits.get(0);
                EnemyUnits.get(EnemyUnits.size() - 1).WhoEnemy(MyUnits.get(0).myUnitObject);
                EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.addPosition(EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.Postion);
            }
        }

    }


    static public void CreateRock(int i, int j,ArrayList<Unit_Imfor>Enviroment) {
        if (UnitValue.m_map[i][j] != UnitValue.M_NOTMOVE) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().rock1.m_bitmap);
            temp.SetPos(i, j);
            Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_ROCK1,true));
            UnitValue.m_map[i][j] = UnitValue.M_NOTMOVE;
        }


    }

    static public void CreateTree1(int i, int j,ArrayList<Unit_Imfor>Enviroment) {
        if (UnitValue.m_map[i][j] != UnitValue.M_NOTMOVE) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().tree1.m_bitmap);
            temp.SetPos(i, j);
            Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_TREE1,true));
            UnitValue.m_map[i][j] = UnitValue.M_NOTMOVE;
        }

    }

    static public void CreateRock2(int i, int j,ArrayList<Unit_Imfor>Enviroment) {
        if (UnitValue.m_map[i][j] != UnitValue.M_NOTMOVE) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().rock2.m_bitmap);
            temp.SetPos(i, j);
            Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_ROCKE2,true));
            UnitValue.m_map[i][j] = UnitValue.M_NOTMOVE;
        }

    }




    static  public void CreateMagicTower(int i, int j, Unit temp,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits, boolean whounit) {
        if (whounit == false) {
            if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {
//                Sound.getInstance().play(1);
                UnitValue.m_map[i][j] = 3;//원 위치
                UnitValue.m_map[i][j + 1] = 3; //y값 증가
                UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
                UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1

                temp.SetPos(i, j);
                temp.ElsaTower(1);
                //temp.resizebitmap(100-100/3,60);
                Unit_Imfor stemp = new Unit_Imfor(temp, 50, 0, UnitValue.F_ELSATOWER,false);
                stemp.InitEffect(UnitValue.F_ELSATOWER);
                //  Units.EnemyUnits.add(stemp);
                stemp.setState(UnitValue.S_MOVE);
                //움직이는 유닛 같은 경우에는 이 타일도 같이 움직여 줘야한다.
                stemp.myUnitObject.addPosition(new Vec2(i, j));
                stemp.myUnitObject.addPosition(new Vec2(i, j + 1));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j + 1));

                EnemyUnits.add(stemp);
            }
        } else {
            if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {

                Sound.getInstance().play(1);
                UnitValue.m_map[i][j] = 3;//원 위치
                UnitValue.m_map[i][j + 1] = 3; //y값 증가
                UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
                UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1

                temp.SetPos(i, j);
                temp.ElsaTower(1);

                //temp.resizebitmap(100-100/3,60);
                Unit_Imfor stemp = new Unit_Imfor(temp, 50, 0, UnitValue.F_ELSATOWER,true);
                stemp.InitEffect(UnitValue.F_ELSATOWER);
                stemp.setState(UnitValue.S_MOVE);
                stemp.myUnitObject.addPosition(new Vec2(i, j));
                stemp.myUnitObject.addPosition(new Vec2(i, j + 1));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j));
                stemp.myUnitObject.addPosition(new Vec2(i + 1, j + 1));
                MyUnits.add(stemp);
            }
        }
    }


    static public void CreateHall(int i, int j,ArrayList<Unit_Imfor>MyUnits, boolean whounit) {
        //  UnitValue.m_map[i][j] = 3;//원 위치
        // UnitValue.m_map[i][j + 1] = 3; //y값 증가
        //UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
        //UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1
        Unit temp;
        temp = new Unit(GraphicManager.getInstance().mTownHall.m_bitmap);
        temp.SetPos(i, j);

        if (whounit) {
            MyUnits.add(new Unit_Imfor(temp, 5, 1, UnitValue.F_TOWNHALL,true));
            MyUnits.get(0).myUnitObject.addPosition(new Vec2(i, j));
        } else
        {
            MyUnits.add(new Unit_Imfor(temp, 5, 1, UnitValue.F_TOWNHALL,false));
            MyUnits.get(0).myUnitObject.addPosition(new Vec2(i, j));
        }
    }

    static public void CreateBoom(int i,int j,ArrayList<Unit_Imfor>MyUnits)
    {

            if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {
//                Sound.getInstance().play(1);
                //UnitValue.m_map[i][j] = 3;//원 위치
                //temp.resizebitmap(100-100/3,60);

                Unit temp= new Unit(GraphicManager.getInstance().m_Boom.m_bitmap);
                temp.SetPos(i,j);
                temp.addPosition(temp.Postion);
                Unit_Imfor stemp = new Unit_Imfor(temp, 10, 0, UnitValue.F_BOOM,false);
                //  Units.EnemyUnits.add(stemp);
                stemp.setState(UnitValue.S_MOVE);
                //움직이는 유닛 같은 경우에는 이 타일도 같이 움직여 줘야한다.
                //stemp.myUnitObject.addPosition(new Vec2(i, j));
                MyUnits.add(stemp);
            }




    }

    static public void CreateArchorTower(int i, int j,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits, boolean whounit)
    {
    if (whounit == true) {
        if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {
//                Sound.getInstance().play(1);
            UnitValue.m_map[i][j] = 3;//원 위치
            UnitValue.m_map[i][j + 1] = 3; //y값 증가
            UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
            UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1
            //temp.resizebitmap(100-100/3,60);
           Unit temp= new Unit(GraphicManager.getInstance().m_ArchorTower.m_bitmap);
            temp.SetPos(i,j);
            Unit_Imfor stemp = new Unit_Imfor(temp, 50, 0, UnitValue.F_TOWER,false);

            //  Units.EnemyUnits.add(stemp);
            stemp.setState(UnitValue.S_MOVE);
            //움직이는 유닛 같은 경우에는 이 타일도 같이 움직여 줘야한다.
            stemp.myUnitObject.addPosition(new Vec2(i, j));
            stemp.myUnitObject.addPosition(new Vec2(i, j + 1));
            stemp.myUnitObject.addPosition(new Vec2(i + 1, j));
            stemp.myUnitObject.addPosition(new Vec2(i + 1, j + 1));
            MyUnits.add(stemp);
        }
    } else {
        if (UnitValue.m_map[i][j] != 3 && UnitValue.m_map[i][j + 1] != 3 && UnitValue.m_map[i + 1][j + 1] != 3 && UnitValue.m_map[i + 1][j] != 3) {

            Sound.getInstance().play(1);
            UnitValue.m_map[i][j] = 3;//원 위치
            UnitValue.m_map[i][j + 1] = 3; //y값 증가
            UnitValue.m_map[i + 1][j] = 3; //left값 증가 +1
            UnitValue.m_map[i + 1][j + 1] = 3; //y left값 증가 +1

            //temp.resizebitmap(100-100/3,60);
            Unit temp= new Unit(GraphicManager.getInstance().m_ArchorTower.m_bitmap);
            temp.SetPos(i,j);
            Unit_Imfor stemp = new Unit_Imfor(temp, 50, 0, UnitValue.F_TOWER,false);

            stemp.InitEffect(UnitValue.F_ELSATOWER);
            stemp.setState(UnitValue.S_MOVE);
            stemp.myUnitObject.addPosition(new Vec2(i, j));
            stemp.myUnitObject.addPosition(new Vec2(i, j + 1));
            stemp.myUnitObject.addPosition(new Vec2(i + 1, j));
            stemp.myUnitObject.addPosition(new Vec2(i + 1, j + 1));
            EnemyUnits.add(stemp);
        }
    }
}


}
