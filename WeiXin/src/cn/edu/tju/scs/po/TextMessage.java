package cn.edu.tju.scs.po;

import cn.edu.tju.scs.po.base.BaseMessage;

/**
 * 微信传来的消息文本对象
 * Created by jack on 2016/2/29.
 */
public class TextMessage extends BaseMessage{


    private String Content;
    private String MsgId;


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
