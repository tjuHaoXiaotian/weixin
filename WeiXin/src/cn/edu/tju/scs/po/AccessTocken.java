package cn.edu.tju.scs.po;

/**
 * 微信 AccessToken
 * Created by jack on 2016/3/1.
 */
public class AccessTocken {
    private String token;
    private int expiresIn;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
