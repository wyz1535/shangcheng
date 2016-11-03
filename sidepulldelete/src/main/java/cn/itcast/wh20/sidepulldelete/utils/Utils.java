package cn.itcast.wh20.sidepulldelete.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/25.
 */
public class Utils {
    private static Toast toast;
    public static void showTaost(Context context,String text){
        if (toast==null){
            toast=Toast.makeText(context,"",Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
