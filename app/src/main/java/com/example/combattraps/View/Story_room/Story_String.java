package com.example.combattraps.View.Story_room;

import com.example.combattraps.immortal.DBManager;

/**
 * Created by 경민 on 2015-05-02.
 */
public class Story_String {
    private String[] m_secretary_talk;

    public Story_String()
    {
        m_secretary_talk=new String[50];
        m_secretary_talk[0]=" 안녕하세요 저는 "+ DBManager.getInstance().GetID()+"님의 모험을 도와줄 "+"#"+" 보좌관 사라입니다.";
        m_secretary_talk[1]="모험에 앞서 간단한 브리핑 부터 드리도록 하겠습니다.";
        m_secretary_talk[2]="우리는 현재 비행선을 이끌고 천공의 섬을 찾아 여행을 다니고 있습니다. ";
        m_secretary_talk[3]="천공의 섬을 찾아 진귀한 보물을 찾는것이 목적이죠";
        m_secretary_talk[4]="물론 천공의 섬을 찾기만 한다고 보물이 들어오는것은 아닙니다.";
        m_secretary_talk[5]="진귀한 보물에는 늘 파리때가 꼬이기마련";
        m_secretary_talk[6]="천공의 섬에는 보물을 지키기위해 수많은 함점,방어타워,가디언들이 항시 대기하고 있습니다.";
        m_secretary_talk[7]="보물을 얻기 위해서는 이러한 방해꾼들을 제압해야만 합니다";
        m_secretary_talk[8]="창너머로 천공의 섬이 보이네요 ";
        m_secretary_talk[9]="백문이 불어일견이라고 전투를 하면서 나머지 설명드리도록 하겠습니다.";
        m_secretary_talk[10]="보잘것 없는 가디언밖에 보이지 않지만 혹시 모르니";
        m_secretary_talk[11]=DBManager.getInstance().GetID()+"님 덱을 한번더 꼼꼼히 챙겨주세요";


    }

    public String getSara(int number)
    {
        return m_secretary_talk[number];
    }

}
