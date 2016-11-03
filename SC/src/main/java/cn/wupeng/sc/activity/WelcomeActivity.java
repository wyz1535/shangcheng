package cn.wupeng.sc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;


/**
 * Created by Administrator on 2016-6-28.
 */
public class WelcomeActivity extends Activity {
    @ViewInject(R.id.welcome_iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ViewUtils.inject(this);
        playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(goNextUIRunnable);
    }

    private void playAnimation() {
        AnimationSet animationSet = new AnimationSet(false);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2000);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setAnimationListener(animationListener);
        iv.startAnimation(animationSet);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playAnimation();
    }

    static Handler handler = new Handler();
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            //发送延期任务,过一会儿跳转页面
            handler.postDelayed(goNextUIRunnable, 2000);
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Runnable goNextUIRunnable = new Runnable() {
        @Override
        public void run() {
            goNextUI();
        }
    };

    private void goNextUI() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }
}
