package com.example.combattraps.View.Story_room;

import com.example.combattraps.immortal.DBManager;

/**
 * Created by 경민 on 2015-05-02.
 */
public class Story_String {
    private String[] m_secretary_talk;
    private String[] m_secretary_talk2; //스토리1 대사   첫번째 튜토리얼
    private String[] m_gardian_talk;

    public Story_String()
    {
        Story1();
    }

    public void Story1()
    {
        m_secretary_talk=new String[50];
        m_secretary_talk[0]=" 안녕하세요 저는 "+ DBManager.getInstance().GetID()+"님의 모험을 도와줄 "+"#"+" 보좌관 사라입니다.";
        m_secretary_talk[1]="모험에 앞서 간단한 브리핑 부터 드리도록 하겠습니다.";
        m_secretary_talk[2]="우리는 현재 비행선을 이끌고 천공의 섬을 찾아 여행을 다니고 있습니다. ";
        m_secretary_talk[3]="천공의 섬을 찾아 진귀한 보물을 찾는것이 목적이죠";
        m_secretary_talk[4]="물론 천공의 섬을 찾기만 한다고 보물이 들어오는것은 아닙니다.";
        m_secretary_talk[5]="진귀한 보물에는 늘 파리때가 꼬이기마련";
        m_secretary_talk[6]="천공의 섬에는 보물을 지키기위해 수많은 함점,방어타워,가디언들이"+"#"+"항시 대기하고 있습니다.";
        m_secretary_talk[7]="보물을 얻기 위해서는 이러한 방해꾼들을 제압해야만 합니다";
        m_secretary_talk[8]="창너머로 천공의 섬이 보이네요 ";
        m_secretary_talk[9]="백문이 불어일견이라고 전투를 하면서 "+"#"+"나머지 설명드리도록 하겠습니다.";
        m_secretary_talk[10]="보잘것 없는 가디언밖에 보이지 않지만 혹시 모르니";
        m_secretary_talk[11]=DBManager.getInstance().GetID()+"님 덱을 한번더 꼼꼼히 챙겨주세요";

        m_secretary_talk2=new String[50];
        m_secretary_talk2[0]="드디어 첫번째 천공의 섬에 착륙하였습니다.";
        m_secretary_talk2[1]="착륙과 동시에 우주선은 타운홀로 변경됩니다.";
        m_secretary_talk2[2]="우주선이 타운홀로 변환되면 마법의 카드를 실체화 시켜주어"+"#"+"임무 수행이 가능하게 해줍니다.";
        m_secretary_talk2[3]="타운홀이 파괴되면 마나공급이 중단되고"+"#"+"모든 유닛들은 실체화가 풀리게 됩니다.";
        m_secretary_talk2[4]="그렇기에 이 타운홀은 파괴안되게 지키면서 싸워야합니다";
        m_secretary_talk2[5]="아직 상대편 가디언들이 우리를 발견하지 못하였군요";
        m_secretary_talk2[6]=DBManager.getInstance().GetID()+"님 가디언이 우리를 발견 하기전에"+"#"+"빨리 타운홀을 지킬 방어타워를 건설하여주세요.";
        m_secretary_talk2[7]="굿!! "+"#"+"훌륭 합니다";
        m_secretary_talk2[8]="후훗 매직 가디언들이 "+"#"+" 이제 눈치를 챈것 같군요";
        m_secretary_talk2[9]="이미 늦었죠 우리가 건설한 "+"#"+"방어 타워가 가디언을 훌륭히 막아줄겁니다.";

        m_gardian_talk=new String[50];
        m_gardian_talk[0]="더러운 도적놈들이 나타났다!!!!!!!!!!";
        m_gardian_talk[1]="(부들부들)"+"#"+"더러운 도적놈들 모조리 죽여주마!!!";
    }


    public String getSara1(int number)
    {
        return m_secretary_talk[number];
    }
    public String getSara2(int number)
    {
        return m_secretary_talk2[number];
    }

}
