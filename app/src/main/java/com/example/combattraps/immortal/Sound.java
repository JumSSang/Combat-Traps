package com.example.combattraps.immortal;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by GyungMin on 2015-02-03.
 */
public class Sound {
    SoundPool m_Soundpool;
    Context m_context;
    AudioManager m_Audio;
    private HashMap m_SoundPoolMap;
    private HashMap m_backgroundMap;
    public MediaPlayer m_MediaPlayer;
    public void Init(Context context)
    {

        m_SoundPoolMap=new HashMap();
        m_Soundpool=new SoundPool(4,m_Audio.STREAM_MUSIC,0);
        m_Audio=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        m_context=context;
    }
    public void addList(int index,int soundID)
    {
        int id=m_Soundpool.load(m_context,soundID,1);
        m_SoundPoolMap.put(index,id);
    }
    public void backgroundPlay(int soundID)
    {
        m_MediaPlayer=MediaPlayer.create(m_context, soundID);
        m_MediaPlayer.setLooping(true);
        m_MediaPlayer.setVolume(0.4f,0.4f);
        m_MediaPlayer.start();

    }

    public void play(int index)
    {
        float volume =m_Audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
       volume=volume/m_Audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_Soundpool.play((Integer)m_SoundPoolMap.get(index),volume,volume,1,0,1.0f);
    }
    public void loopplay(int index)
    {
        float volume =m_Audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume=volume/m_Audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        m_Soundpool.play((Integer)m_SoundPoolMap.get(index),volume,volume,1,-1,1.0f);
    }
    private static Sound s_instace;

    public static Sound getInstance()
    {
        if(s_instace==null)
        {
            s_instace=new Sound();
        }
        return s_instace;
    }




}
