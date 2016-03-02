package cn.edu.tju.scs.po;

import cn.edu.tju.scs.po.base.BaseMessage;

/**
 * 音乐消息
 * Created by jack on 2016/3/2.
 */
public class MusicMessage extends BaseMessage{
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
