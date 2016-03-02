package cn.edu.tju.scs.servlet;

import cn.edu.tju.scs.po.TextMessage;
import cn.edu.tju.scs.util.CheckUtil;
import cn.edu.tju.scs.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by jack on 2016/2/29.
 */
public class WeiXinServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        PrintWriter out = resp.getWriter();
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            out.print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try{
            Map<String,String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            // 回复消息
            String message = null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                if("1".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());
                }else if("2".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.secondMenu());
                }else if("3".equals(content)){
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                }else if("4".equals(content)){
                    message = MessageUtil.initImageMessage(toUserName,fromUserName);
                }else if("5".equals(content)){
                    message = MessageUtil.initMusicMessage(toUserName, fromUserName);
                }else if("?".equals(content)|| "？".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuTest());
                }
            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuTest());
                }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuTest());
                }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName,fromUserName,url);
                }else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
                    String key = map.get("EventKey");
                    message = MessageUtil.initText(toUserName,fromUserName,key);
                }else if(MessageUtil.MESSAGE_LOCATION.equals(eventType)){
                    String label = map.get("label");
                    message = MessageUtil.initText(toUserName,fromUserName,label);
                }
            }
            System.out.println(message);

            out.print(message);
        }catch (DocumentException e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
