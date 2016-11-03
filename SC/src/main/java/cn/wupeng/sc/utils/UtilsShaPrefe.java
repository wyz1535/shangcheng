package cn.wupeng.sc.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 首选项存储
 * Created by Administrator on 2016/7/1.
 */
public class UtilsShaPrefe {
    public static void sharedPreferences(String name, String infom, String type,Context context) {
        SharedPreferences sp = context.getSharedPreferences(name, context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(infom, type);
        edit.commit();
    }
}
