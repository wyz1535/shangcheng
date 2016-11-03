package cn.wupeng.sc.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2016/7/5.
 */
public class Utils {

    public static final String MEG_TYPE_BUDDY_LIST="buddy_list";
    public static final String MEG_TYPE_TO_CHAT="to_chat";
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    //在子线程中执行
    public static void runInThread(Runnable task){
        executorService.execute(task);
    }
    //在主线程中执行
    private static Handler handler=new Handler(Looper.getMainLooper());
    public static void runOnUIThread(Runnable task){
        handler.post(task);
    }
    //万能的吐司
    public static void showToast(final Context context, final String text){
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
