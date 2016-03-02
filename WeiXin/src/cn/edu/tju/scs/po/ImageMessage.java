package cn.edu.tju.scs.po;

import cn.edu.tju.scs.po.base.BaseMessage;


/**
 * 图片消息
 * Created by jack on 2016/3/2.
 */
public class ImageMessage extends BaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
