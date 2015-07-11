package com.example.combattraps.Game.UnitDirect;

import com.example.combattraps.Values.DirState;
import com.example.combattraps.Values.MapState;
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
            MyUnits.get(MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);

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
                EnemyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_ANNA,false));
                EnemyUnits.get(EnemyUnits.size() - 1).InitEffect(UnitValue.F_ANNA);
                EnemyUnits.get(EnemyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                EnemyUnits.get(EnemyUnits.size() - 1).my_enemy = MyUnits.get(0);
                EnemyUnits.get(EnemyUnits.size() - 1).WhoEnemy(MyUnits.get(0).myUnitObject);
                EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.addPosition(EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.Postion);
            }
        }

    }
    static public void CreateMagican(int i,int j,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits,boolean b_myUnit)
    {
        Unit temp = new Unit(GraphicManager.getInstance().m_Magician.m_bitmap);
        temp.CreateWorkUnit(20,UnitValue.F_MAGICAIN);
        temp.SetPos(i, j);
        temp.addPosition(temp.Postion);
        if(b_myUnit)
        //아군
        {
            MyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_MAGICAIN, true));
            MyUnits.get(MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                MyUnits.get(MyUnits.size() - 1).my_enemy = EnemyUnits.get(0);
                MyUnits.get(MyUnits.size() - 1).WhoEnemy(EnemyUnits.get(0).myUnitObject);
                MyUnits.get(MyUnits.size() - 1).myUnitObject.addPosition(MyUnits.get(MyUnits.size() - 1).myUnitObject.Postion);
                MyUnits.get(MyUnits.size() - 1).boundingPositionUpdate(  MyUnits.get(MyUnits.size() - 1).DrawPosition.x,   MyUnits.get(MyUnits.size() - 1).DrawPosition.y);//시작할때 바운딩 영역을 가지고 시작하게 해준다.
                //UnitValue.m_map[Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.x][Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.y]=UnitValue.M_NOTMOVE;
            }
        }
        else
        {
            //적군군
            // temp.CreateSpriteRect(20);
            EnemyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_MAGICAIN,false));
            EnemyUnits.get(EnemyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                EnemyUnits.get(EnemyUnits.size() - 1).my_enemy = MyUnits.get(0);
                EnemyUnits.get(EnemyUnits.size() - 1).WhoEnemy(MyUnits.get(0).myUnitObject);
                EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.addPosition(EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.Postion);
                EnemyUnits.get(EnemyUnits.size() - 1).boundingPositionUpdate(EnemyUnits.get(EnemyUnits.size() - 1).DrawPosition.x,   EnemyUnits.get(EnemyUnits.size() - 1).DrawPosition.y);//시작할때 바운딩 영역을 가지고 시작하게 해준다.
            }
        }
    }
    static public void CreateWorrior(int i,int j,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits,boolean b_myUnit)
    {
        Unit temp = new Unit(GraphicManager.getInstance().m_WorriorUnit.m_bitmap);
        temp.CreateWorkUnit(20,UnitValue.F_WORRIOR);
        temp.SetPos(i, j);
        temp.addPosition(temp.Postion);
        temp.mDirection=DirState.TOP;

        if(b_myUnit)
        //아군
        {
            MyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_WORRIOR, true));
            MyUnits.get(MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                MyUnits.get(MyUnits.size() - 1).setState(UnitValue.S_MOVE);
                MyUnits.get(MyUnits.size() - 1).my_enemy = EnemyUnits.get(0);
                MyUnits.get(MyUnits.size() - 1).WhoEnemy(EnemyUnits.get(0).myUnitObject);
                MyUnits.get(MyUnits.size() - 1).myUnitObject.addPosition(MyUnits.get(MyUnits.size() - 1).myUnitObject.Postion);
                MyUnits.get(MyUnits.size() - 1).boundingPositionUpdate(  MyUnits.get(MyUnits.size() - 1).DrawPosition.x,   MyUnits.get(MyUnits.size() - 1).DrawPosition.y);//시작할때 바운딩 영역을 가지고 시작하게 해준다.
                //UnitValue.m_map[Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.x][Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.y]=UnitValue.M_NOTMOVE;
            }
        }
        else
        {
            //적군군
            // temp.CreateSpriteRect(20);
            EnemyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_WORRIOR,false));
            EnemyUnits.get(EnemyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                MyUnits.get(MyUnits.size() - 1).setState(UnitValue.S_MOVE);
                EnemyUnits.get(EnemyUnits.size() - 1).my_enemy = MyUnits.get(0);
                EnemyUnits.get(EnemyUnits.size() - 1).WhoEnemy(MyUnits.get(0).myUnitObject);
                EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.addPosition(EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.Postion);
                EnemyUnits.get(EnemyUnits.size() - 1).boundingPositionUpdate(EnemyUnits.get(EnemyUnits.size() - 1).DrawPosition.x,   EnemyUnits.get(EnemyUnits.size() - 1).DrawPosition.y);//시작할때 바운딩 영역을 가지고 시작하게 해준다.
            }
        }

    }


    //아처 생성
    static public void CreateArcher(int i,int j,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits,boolean b_myUnit)
    {
        Unit temp = new Unit(GraphicManager.getInstance().m_ArcherUnit.m_bitmap);
        temp.CreateWorkUnit(20,UnitValue.F_ARCHER);
        temp.SetPos(i, j);
        temp.addPosition(temp.Postion);
        if(b_myUnit)
        //아군
        {
            MyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_ARCHER, true));
            MyUnits.get(MyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                MyUnits.get(MyUnits.size() - 1).setState(UnitValue.S_MOVE);
                MyUnits.get(MyUnits.size() - 1).my_enemy = EnemyUnits.get(0);
                MyUnits.get(MyUnits.size() - 1).WhoEnemy(EnemyUnits.get(0).myUnitObject);
                MyUnits.get(MyUnits.size() - 1).myUnitObject.addPosition(MyUnits.get(MyUnits.size() - 1).myUnitObject.Postion);
                MyUnits.get(MyUnits.size() - 1).boundingPositionUpdate(  MyUnits.get(MyUnits.size() - 1).DrawPosition.x,   MyUnits.get(MyUnits.size() - 1).DrawPosition.y);//시작할때 바운딩 영역을 가지고 시작하게 해준다.
                //UnitValue.m_map[Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.x][Units.MyUnits.get(Units.MyUnits.size() - 1).myUnitObject.Postion.y]=UnitValue.M_NOTMOVE;
            }
        }
        else
        {
            //적군군
           // temp.CreateSpriteRect(20);
            MyUnits.get(MyUnits.size() - 1).setState(UnitValue.S_MOVE);
            EnemyUnits.add(new Unit_Imfor(temp, 10, 1, UnitValue.F_ARCHER,false));
            EnemyUnits.get(EnemyUnits.size() - 1).myPath.LoadMap(UnitValue.m_bmap);
            if (EnemyUnits.size() > 0) {
                EnemyUnits.get(EnemyUnits.size() - 1).my_enemy = MyUnits.get(0);
                EnemyUnits.get(EnemyUnits.size() - 1).WhoEnemy(MyUnits.get(0).myUnitObject);
                EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.addPosition(EnemyUnits.get(EnemyUnits.size() - 1).myUnitObject.Postion);
                EnemyUnits.get(EnemyUnits.size() - 1).boundingPositionUpdate(EnemyUnits.get(EnemyUnits.size() - 1).DrawPosition.x,   EnemyUnits.get(EnemyUnits.size() - 1).DrawPosition.y);//시작할때 바운딩 영역을 가지고 시작하게 해준다.
            }
        }

    }


    static public void CreateRock(int i, int j,ArrayList<Unit_Imfor>Enviroment) {
        if (UnitValue.m_bmap[i][j] !=MapState.NotMove) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().rock1.m_bitmap);
            temp.SetPos(i, j);
            Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_ROCK1,true));
            UnitValue.m_dmap[i][j] = UnitValue.M_NOTMOVE;
            UnitValue.m_bmap[i][j]= MapState.NotMove;
        }


    }

    static public void CreateTree1(int i, int j,ArrayList<Unit_Imfor>Enviroment) {
        if (UnitValue.m_bmap[i][j] !=MapState.NotMove) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().tree1.m_bitmap);
            temp.SetPos(i, j);
            Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_TREE1,true));
            UnitValue.m_dmap[i][j] = UnitValue.M_NOTMOVE;
            UnitValue.m_bmap[i][j]= MapState.NotMove;
        }

    }

    static public void CreateRock2(int i, int j,ArrayList<Unit_Imfor>Enviroment) {
        if (UnitValue.m_bmap[i][j] !=MapState.NotMove) {
            Unit temp;
            temp = new Unit(GraphicManager.getInstance().rock2.m_bitmap);
            temp.SetPos(i, j);
            Enviroment.add(new Unit_Imfor(temp, 5000, 0, UnitValue.F_ROCKE2,true));
            UnitValue.m_dmap[i][j] = UnitValue.M_NOTMOVE;
            UnitValue.m_bmap[i][j]= MapState.NotMove;
        }

    }




    static  public void CreateMagicTower(int i, int j, Unit temp,ArrayList<Unit_Imfor>MyUnits,ArrayList<Unit_Imfor>EnemyUnits, boolean whounit) {
        if (whounit == false) {
            if (UnitValue.m_bmap[i][j] != MapState.NotMove && UnitValue.m_bmap[i][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j] != MapState.NotMove) {
//                Sound.getInstance().play(1);
                UnitValue.m_bmap[i][j] = MapState.NotMove;//원 위치
                UnitValue.m_bmap[i][j + 1] = MapState.NotMove; //y값 증가
                UnitValue.m_bmap[i + 1][j] = MapState.NotMove; //left값 증가 +1
                UnitValue.m_bmap[i + 1][j + 1] = MapState.NotMove; //y left값 증가 +1

                UnitValue.m_dmap[i][j]=UnitValue.M_NOTMOVE;
                UnitValue.m_dmap[i][j + 1] = UnitValue.M_NOTMOVE; //y값 증가
                UnitValue.m_dmap[i + 1][j] = UnitValue.M_NOTMOVE;
                UnitValue.m_dmap[i + 1][j + 1] = UnitValue.M_NOTMOVE;

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
            if (UnitValue.m_bmap[i][j] != MapState.NotMove && UnitValue.m_bmap[i][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j] != MapState.NotMove) {

                Sound.getInstance().play(1);
                UnitValue.m_bmap[i][j] = MapState.NotMove;//원 위치
                UnitValue.m_bmap[i][j + 1] = MapState.NotMove; //y값 증가
                UnitValue.m_bmap[i + 1][j] = MapState.NotMove; //left값 증가 +1
                UnitValue.m_bmap[i + 1][j + 1] = MapState.NotMove; //y left값 증가 +1

                UnitValue.m_dmap[i][j]=UnitValue.M_NOTMOVE;
                UnitValue.m_dmap[i][j + 1] = UnitValue.M_NOTMOVE; //y값 증가
                UnitValue.m_dmap[i + 1][j] = UnitValue.M_NOTMOVE;
                UnitValue.m_dmap[i + 1][j + 1] = UnitValue.M_NOTMOVE;

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

            if (UnitValue.m_bmap[i][j] != MapState.NotMove && UnitValue.m_bmap[i][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j] != MapState.NotMove) {
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
        if (UnitValue.m_bmap[i][j] != MapState.NotMove && UnitValue.m_bmap[i][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j] != MapState.NotMove) {
//                Sound.getInstance().play(1);
            UnitValue.m_bmap[i][j] = MapState.NotMove;//원 위치
            UnitValue.m_bmap[i][j + 1] = MapState.NotMove; //y값 증가
            UnitValue.m_bmap[i + 1][j] = MapState.NotMove; //left값 증가 +1
            UnitValue.m_bmap[i + 1][j + 1] = MapState.NotMove; //y left값 증가 +1

            UnitValue.m_dmap[i][j]=UnitValue.M_NOTMOVE;
            UnitValue.m_dmap[i][j + 1] = UnitValue.M_NOTMOVE; //y값 증가
            UnitValue.m_dmap[i + 1][j] = UnitValue.M_NOTMOVE;
            UnitValue.m_dmap[i + 1][j + 1] = UnitValue.M_NOTMOVE;
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
        if (UnitValue.m_bmap[i][j] != MapState.NotMove && UnitValue.m_bmap[i][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j + 1] != MapState.NotMove && UnitValue.m_bmap[i + 1][j] != MapState.NotMove) {

            Sound.getInstance().play(1);
            UnitValue.m_bmap[i][j] = MapState.NotMove;//원 위치
            UnitValue.m_bmap[i][j + 1] = MapState.NotMove; //y값 증가
            UnitValue.m_bmap[i + 1][j] = MapState.NotMove; //left값 증가 +1
            UnitValue.m_bmap[i + 1][j + 1] = MapState.NotMove; //y left값 증가 +1

            UnitValue.m_dmap[i][j]=UnitValue.M_NOTMOVE;
            UnitValue.m_dmap[i][j + 1] = UnitValue.M_NOTMOVE; //y값 증가
            UnitValue.m_dmap[i + 1][j] = UnitValue.M_NOTMOVE;
            UnitValue.m_dmap[i + 1][j + 1] = UnitValue.M_NOTMOVE;

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
