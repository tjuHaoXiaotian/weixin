package cn.edu.tju.scs.util;

import cn.edu.tju.scs.menu.Button;
import cn.edu.tju.scs.menu.ClickButton;
import cn.edu.tju.scs.menu.Menu;
import cn.edu.tju.scs.menu.ViewButton;
import cn.edu.tju.scs.po.AccessTocken;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取以及发送 accessToken
 * Created by jack on 2016/3/1.
 */
public class WeiXinUtil {


    private static final String APPID = "wxdf350bf1a55079d9";
    private static final String APPSECRET = "159bff7f5666146c77d3bbff6e01c3d8";

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    /**
     * Get 请求
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url){

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try{
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Post 请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url,String outStr){

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try{
           httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(),"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取 accessToken
     * @return
     */
    public static AccessTocken getAccessToken(){
        AccessTocken accessTocken = new AccessTocken();
        String url = null;
        url = ACCESS_TOKEN_URL.replace("APPID",APPID);
        url = url.replace("APPSECRET",APPSECRET);
        System.out.println(url);

        JSONObject jsonObject = doGetStr(url);
        if(jsonObject != null){
            if(jsonObject.has("access_token")) {
                accessTocken.setToken(jsonObject.getString("access_token"));
                accessTocken.setExpiresIn(jsonObject.getInt("expires_in"));
            }else if (jsonObject.has("errcode")){
                System.out.println(jsonObject);
            }
        }
        return accessTocken;
    }


    public static String upload(String filePath,String accessToken,String type) throws IOException {
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN",accessToken).replace("TYPE",type);
        System.out.println("上传的路径为："+url);

        URL urlObj = new URL(url);

        //连接
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        // 设置请求头信息
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Charset","UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--");
        stringBuilder.append(BOUNDARY);
        stringBuilder.append("\r\n");
        stringBuilder.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        stringBuilder.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = stringBuilder.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        // 输出表头
        outputStream.write(head);

        // 文件正文部分
        // 吧文件以流文件的方式 堆入到 url 中
        DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = inputStream.read(bufferOut))!=-1){
            outputStream.write(bufferOut,0,bytes);
        }
        inputStream.close();

        // 结尾部分
        byte[]foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8"); // 定义最后数据分隔线

        outputStream.write(foot);

        outputStream.flush();
        outputStream.close();


        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try{
            // 定义 BufferedReader 输入流来读取 URL 的响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while((line = reader.readLine())!= null){
                buffer.append(line);
            }
            if(result == null){
                result = buffer.toString();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                reader.close();
            }
        }

        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject);

        String typeName = "media_id";
        if(!"image".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObject.getString(typeName);
        return mediaId;
    }


    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button1 = new ClickButton();
        button1.setName("click菜单");
        button1.setType("click");
        button1.setKey("1");

        ViewButton button2 = new ViewButton();
        button2.setName("view菜单");
        button2.setType("view");
        button2.setUrl("http://e.tju.edu.cn/Main/init.do");

        ClickButton button3 = new ClickButton();
        button3.setName("扫码事件");
        button3.setType("scancode_push");
        button3.setKey("2");

        ClickButton button3_2 = new ClickButton();
        button3_2.setName("地理位置");
        button3_2.setType("location_select");
        button3_2.setKey("3");


        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button3,button3_2});

        menu.setButton(new Button[]{button1,button2,button});
        return menu;
    }

    /**
     * 新建菜单
     * @param token
     * @param menu
     * @return
     */
    public static int createMenu(String token,String menu){
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = doPostStr(url,menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    /**
     * 查询菜单
     * @param token
     * @return
     */
    public static JSONObject queryMenu(String token){
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN",token);
        return doGetStr(url);
    }


    /**
     * 删除菜单
     * @param token
     * @return
     */
    public static int deleteMenu(String token){
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = doGetStr(url);
        int result = 0;
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }
}
