package cn.wupeng.sc.utils;

/**
 * Created by heshun on 2016/6/30.
 */
public class UtilsNet {

    public static final String SERVER_URL = "http://10.0.2.2:8080/market";
    public static String formatUrl(String url){
        return SERVER_URL+url;
    }
}
