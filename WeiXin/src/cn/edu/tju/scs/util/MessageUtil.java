package cn.edu.tju.scs.util;

import cn.edu.tju.scs.po.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * 微信的消息是用 xml 格式发送给后台的，所以需要实现以下 xml —— object 的转化
 * Created by jack on 2016/2/29.
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_SCANCODE = "scancode_push";


    /**
     * xml 转成 map 集合
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map <String,String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for(Element e:list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 将文本消息对象转换为 xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(MESSAGE_TEXT);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setContent(content);
        return textMessageToXml(textMessage);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuTest(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("欢迎您的关注，请按照菜单提示操作：\n\n");
        stringBuffer.append("回复‘1’：风土人情介绍\n");
        stringBuffer.append(("回复‘2’：旅游景点介绍\n\n"));
        stringBuffer.append(("回复‘3’：图文消息\n\n"));
        stringBuffer.append(("回复‘4’：图片消息\n\n"));
        stringBuffer.append(("回复‘5’：音乐消息\n\n"));
        stringBuffer.append("回复 '?' 号，调出此菜单");
        return stringBuffer.toString();
    }


    /**
     * 回复 1
     * @return
     */
    public static String firstMenu(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("我们这里介绍了河北省石家庄市的一些风土人情，还有那些许儿时的回忆！");
        return stringBuffer.toString();
    }

    /**
     * 回复 2
     * @return
     */
    public static String secondMenu(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("大家印象\n" +
                "\n" +
                "对石家庄印象还不错，古代称“常山、真定”，历史上曾与北京、保定并称“北方三雄镇”；近几年污染的确严重，整个城市很破旧的感觉。\n" +
                "走进石家庄\n" +
                "\n" +
                "石家庄市为河北省省会，位于河北省西南部，古代称“常山、真定”，历史上曾与北京、保定并称“北方三雄镇”；中国优秀旅游城市。中国新兴城市50强。石家庄历史却颇悠久：在郊区的村庄里有大量的殷周文物出土，证明在三四千年以前，就有先民生息在这块土地上；战国初期，市区内曾经出现过两座古城，先后为中山国都城，后为赵国所吞并。直至现在，河北仍被称作为“燕赵大地”。 石家庄的旅游资源也很丰富。古城正定的隆兴寺（内有全国最高铜制大佛）、临济寺（佛教临济宗的发祥地）、赵云庙、赵县的安济桥（世界最早的石拱桥）、柏林寺及国家级风景区井陉县的苍岩山、赞皇县的嶂石岩、著名革命圣地西柏坡等遍布于市区周围。 官网：http://www.sjz.gov.cn\n" +
                "\uE613\n" +
                "\n" +
                "最佳季节：9-11月，秋季最佳。受季风气候影响，冬季南北温差很大，夏季普遍高温，秋季最宜人。\uE613\n" +
                "\n" +
                "建议游玩：1-2天");
        return stringBuffer.toString();
    }


    /**
     * 将图文消息对象转换为 xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item",new News().getClass());
        return xStream.toXML(newsMessage);
    }


    /**
     * 图文消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("美人鱼");
        news.setDescription("《美人鱼》是由周星驰执导，由江玉仪监制的喜剧爱情片，邓超、林允、张雨绮、罗志祥等领衔出演。该片讲述了富豪刘轩和为了拯救同族前往刺杀他的美人鱼珊珊坠入爱河，谱写了一段人鱼爱情童话的故事。该片于2016年2月8日在中国上映。");
        news.setPicUrl("http://115.28.65.140/WeiXin/image/beauty.jpg");
        news.setUrl("http://baike.baidu.com/link?url=WwQ0naoargQfE27wwoqbJ4HcyCiXz5pJJN0vJSYRpp4_iHKqzNDuaLgyXSR4Q59J3ga6JLnRKrOU-2ICiqJ2N7i4C7Q-QbUwqm_2sJN22OO");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }


    /**
     * 将图片消息对象转换为 xml
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",imageMessage.getClass());
//        xStream.alias("Image",new Image().getClass());

        return xStream.toXML(imageMessage);
    }


    /**
     * 图片消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        String message = null;
        Image image = new Image();
        image.setMediaId("q5jrEF4F1WHLZbIb_ka4qID8GwUsL8KkbPtz30mckR-KBM6VTSE1oytSTMHRFBJY");
//        image.setMsgId(Long.valueOf("1234567890123456"));
//        image.setPicUrl("https://api.weixin.qq.com/cgi-bin/media/get?access_token=3n6QflsoEgT9LHJwNxXoP98AeMhW8fZw3zl2l8_N4m1Br7G_we2i9k42n8WvQ3YYX6IHCWraNyfZ7UTrN1Z59KJuGXZrG7M1XIgJTaVTlCYZITdAGASBM&media_id=q5jrEF4F1WHLZbIb_ka4qID8GwUsL8KkbPtz30mckR-KBM6VTSE1oytSTMHRFBJY");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);

        message = imageMessageToXml(imageMessage);
        return message;
    }


    /**
     * 将音乐消息对象转换为 xml
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",musicMessage.getClass());

        return xStream.toXML(musicMessage);
    }

    /**
     * 音乐消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initMusicMessage(String toUserName,String fromUserName){
        String message = null;
        Music music = new Music();
        music.setThumbMediaId("ou_bi_EkgwpfBmvyuEBv1FuMkQh3W-toro6TZeVMOsfaRfUbITXRxMGWklLrAFEE");
        music.setTitle("《红颜旧》");
        music.setDescription("电视剧琅琊榜插曲");
        music.setMusicUrl("http://115.28.65.140/WeiXin/music/红颜旧笛子.mp3");
        music.setHQMusicUrl("http://115.28.65.140/WeiXin/music/红颜旧笛子.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);

        message = musicMessageToXml(musicMessage);
        return message;
    }




}
