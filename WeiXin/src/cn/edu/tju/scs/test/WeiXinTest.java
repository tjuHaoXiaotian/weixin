package cn.edu.tju.scs.test;

import cn.edu.tju.scs.po.AccessTocken;
import cn.edu.tju.scs.util.WeiXinUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Created by jack on 2016/3/1.
 */
public class WeiXinTest {

    public static void main(String []args){
        AccessTocken accessTocken = WeiXinUtil.getAccessToken();
        System.out.println("票据："+ accessTocken.getToken());
        System.out.println("有效时间："+ accessTocken.getExpiresIn());

        // 上传临时素材
        
//        String path = "D:\\2016Project\\WeiXin\\web\\image\\langyabang.jpg";
//        String mediaId = null;
//        try {
//            mediaId = WeiXinUtil.upload(path, accessTocken.getToken(), "thumb");
//            System.out.println(mediaId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(new Date().getTime());


        // 创建菜单
        String memu = JSONObject.fromObject(WeiXinUtil.initMenu()).toString();
        int result = WeiXinUtil.createMenu(accessTocken.getToken(),memu);
        if(result == 0){
            System.out.println("创建菜单成功！");
        }else {
            System.out.println("错误码：" + result);
        }

        // 查询菜单
        System.out.println(WeiXinUtil.queryMenu(accessTocken.getToken()).toString());


        // 删除菜单
        int deleteResult = WeiXinUtil.deleteMenu(accessTocken.getToken());
        if(deleteResult == 0){
            System.out.println("删除成功！");
        }else{
            System.out.println("错误码：" + result);
        }



        /**
         * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdf350bf1a55079d9&secret=159bff7f5666146c77d3bbff6e01c3d8
         票据：mlAEywyXZCLbEX3cOYU_B6GIXJHNDFaD21pv0huFS_QSSPCX3SE-88WTNMw5bbXbD5aYwhrZP6Sm7K8dZUUuln5z40DI3Ybrr9XezOOcCLqKf66_b95ESU5_VLZgTnC4LZZjAHATHH
         有效时间：7200
         上传的路径为：https://api.weixin.qq.com/cgi-bin/media/upload?access_token=mlAEywyXZCLbEX3cOYU_B6GIXJHNDFaD21pv0huFS_QSSPCX3SE-88WTNMw5bbXbD5aYwhrZP6Sm7K8dZUUuln5z40DI3Ybrr9XezOOcCLqKf66_b95ESU5_VLZgTnC4LZZjAHATHH&type=thumb
         {"type":"thumb","thumb_media_id":"ou_bi_EkgwpfBmvyuEBv1FuMkQh3W-toro6TZeVMOsfaRfUbITXRxMGWklLrAFEE","created_at":1456899927}
         ou_bi_EkgwpfBmvyuEBv1FuMkQh3W-toro6TZeVMOsfaRfUbITXRxMGWklLrAFEE
         */
    }
}
