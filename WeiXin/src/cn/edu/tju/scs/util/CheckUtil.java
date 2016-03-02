package cn.edu.tju.scs.util;

import java.util.Arrays;

/**
 * Created by jack on 2016/2/29.
 */
public class CheckUtil {

    private static final String token = "haoxiaotian";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String [] arr = new String[]{token,timestamp,nonce};
        // 排序(字典序)
        Arrays.sort(arr);

        // 生成字符串
        StringBuffer content = new StringBuffer();
        for(int i=0;i < arr.length;i++){
            content.append(arr[i]);
        }

        // sha1 加密
        String temp = EncodeUtil.SHA1(content.toString());

        return temp.equals(signature);
    }
}
