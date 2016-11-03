package cn.wupeng.sc.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by Android on 2016/7/5.
 */
public class TimeService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public TimeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //创建一个Handler
    Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int currentTime = msg.arg1;
            Intent broadcast = new Intent("timeBroadcast");
            sendBroadcast(broadcast);
        }
    };
    //创建一个线程
    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
                Message msg = mHandler.obtainMessage();
                mHandler.postDelayed(updateThread, 1000);
        }
    };



    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        //在这里判断是否是启动服务的那个Action
        if (intent.getAction().equals("timeService")) {
            mHandler.postDelayed(updateThread, 1000);
        }
    }
}
