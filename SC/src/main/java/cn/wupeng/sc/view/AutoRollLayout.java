package cn.wupeng.sc.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.R;

/**
 * 对外提供的方法：
 * <p/>
 * 一：要显示什么内容（图片、标题） :
 * public void  setItems(List<RollItem> items)
 * public class RollItem{
 * String title;
 * String imageUrl;
 * }
 * 二：开启或关闭自动滚动效果
 * public void setAutoRoll(boolean  roll)
 * 定时滚动
 *
 *
 * 需求：
 * 1 当点击了轮播图，能够显示土司
 * 2 当用户手指触摸到轮播图，轮播图要停止自动滚动，当离开时，恢复自动滚动
 *
 *
 *
 *
 *
 * 一
 * 分发触摸事件
 * dispatchTouchEvent
 *
 * dispatchTouchEvent 遍历孩子 调用 dispatchTransformedTouchEvent
 * 谁来调用此方法的？ 父控件的 dispatchTouchEvent
 *
 *
 *
 * 二
 * 拦截触摸事件
 * onInterceptTouchEvent
 *
 *谁来调用此方法的？  自己的 dispatchTouchEvent 调用的
 *
 if (actionMasked == MotionEvent.ACTION_DOWN
 || mFirstTouchTarget != null) {
 final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
 if (!disallowIntercept) {
 intercepted = onInterceptTouchEvent(ev);
 ev.setAction(action); // restore action in case it was changed
 } else {
 intercepted = false;
 }
 }
 *
 三
 * 处理(消费)触摸事件
 * onTouchEvent
 *谁来调用此方法的？ 自己的 dispatchTouchEvent 调用的
 *
 ListenerInfo li = mListenerInfo;
 if (li != null && li.mOnTouchListener != null
 && (mViewFlags & ENABLED_MASK) == ENABLED
 && li.mOnTouchListener.onTouch(this, event)) {
 result = true;
 }

 if (!result && onTouchEvent(event)) {
 result = true;
 }
结论
 如果给View设置了触摸监听，并且在监听的onTouch方法中返回了true，那么View的onTouchEvent方法就不会被调用了，
 如果返回false，才会被调用

 View.onTouchEvent  -> up -> View.performClick()   会导致设置到view的点击监听的onClick方法调用


 三个触摸事件相关方法
 U形管
 分发 ：被谁调用  调用时机  作用

 拦截 ：被谁调用  调用时机  作用

 处理 ：被谁调用  调用时机  作用

 performClick   onTouchEvent->up->performClick-->onClick

 setOnTouchListener

 GestureDetector  三个步骤

 */
public class AutoRollLayout extends FrameLayout {
    private LinearLayout dotContainer;
    private ViewPager vp;
    private TextView tv;
    List<? extends IRollItem> items;
    private GestureDetector gestureDetector;


    public AutoRollLayout(Context context) {
        this(context, null);
//        init();
    }

    public AutoRollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
//        init();
    }

    public AutoRollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setAutoRoll(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAutoRoll(false);
    }

    private void init() {
        View.inflate(getContext(), R.layout.arl_arl_layout, this);
        dotContainer = (LinearLayout) findViewById(R.id.arl_arl_dot_container);
        vp = (ViewPager) findViewById(R.id.arl_arl_vp);
        tv = (TextView) findViewById(R.id.arl_arl_tv);

        vp.setOnPageChangeListener(pageChangeListener);
        vp.setOnTouchListener(touchListener);
        // 手势识别器 GestureDetector
        // 类比 间谍
        // GestureDetector 步骤
        // 1 构造对象
        // 2 把触摸事件交给GestureDetector去分析
        // 3 享用分析的结果
        gestureDetector = new GestureDetector(getContext(),gestureListener);
    }

    public void setItems(List<? extends IRollItem> items) {
        this.items = items;
        // Viewpager
        vp.setAdapter(pagerAdapter);
        // textview
        tv.setText(null);
        // dots
        dotContainer.removeAllViews();
        addDots();
        // 初始状态
        pageChangeListener.onPageSelected(0);
    }

    public int getCurrentItem(){
        return vp.getCurrentItem();
    }

    static Handler handler = new Handler();

    public void setAutoRoll(boolean roll) {
        Log.d("AutoRollLayout","setAutoRoll " + roll);
        if (roll) {
            handler.postDelayed(rollRunnable, 2000);
        } else {
            handler.removeCallbacks(rollRunnable);
        }
    }

    Runnable rollRunnable = new Runnable() {
        @Override
        public void run() {
            // 防止重复进行任务，
            handler.removeCallbacks(this);
            if(!isTouchingVp){
                goNextPage();
            }

            handler.postDelayed(this, 2000);
        }
    };

    boolean fromLeftToRight = true;
    private void goNextPage() {

        int currentItem = vp.getCurrentItem();
        if(currentItem ==0 ){
            fromLeftToRight = true;
        }else if(currentItem == pagerAdapter.getCount() -1){
            fromLeftToRight = false;
        }
        int nextItem = fromLeftToRight ? currentItem + 1: currentItem -1;
        vp.setCurrentItem(nextItem);
        Log.d("AutoRollLayout", "goNextPage " + items.get(nextItem).getRollItemTitle());
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (cache.isEmpty()) {
                ImageView iv = new ImageView(container.getContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                cache.add(iv);
            }

            ImageView iv = cache.remove(0);
            Picasso.with(getContext())
                    .load(items.get(position).getRollItemImageUrl())
                    .fit()
                    .into(iv);
            container.addView(iv);
            return iv;
        }

        List<ImageView> cache = new ArrayList<>();

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView iv = (ImageView) object;
            iv.setImageBitmap(null);
            cache.add(iv);
            container.removeView(iv);
        }
    };


    private void addDots() {
        if (items == null) {
            return;
        }
        int pxFor10Dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        for (IRollItem item : items) {

            View dot = new View(getContext());
            dot.setBackgroundResource(R.drawable.arl_dot_selector);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pxFor10Dp, pxFor10Dp);
            lp.rightMargin = pxFor10Dp;
//            dot.setLayoutParams(lp);
            dotContainer.addView(dot, lp);

            dot.setOnClickListener(goThisPositionOcl);
        }
    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (items == null || items.isEmpty()) {
                return;
            }
            String text = items.get(position).getRollItemTitle();
            tv.setText(text);

            for (int i = 0; i < pagerAdapter.getCount(); i++) {
                dotContainer.getChildAt(i).setEnabled(i != position);
            }
        }
    };

    private OnClickListener goThisPositionOcl = new OnClickListener() {
        @Override
        public void onClick(View v) {
//            Toast.makeText(v.getContext(), "go this", Toast.LENGTH_SHORT).show();


            int index = dotContainer.indexOfChild(v);
            vp.setCurrentItem(index);
        }
    };

    boolean isTouchingVp = false;

    private OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    // 停下来
                    isTouchingVp = true;
//                    requestDisallowInterceptTouchEvent(true);
//                    handler.removeCallbacks(rollRunnable);
                    break;
                // cancel 约等于 up， 表示后续没有触摸事件了
                case MotionEvent.ACTION_CANCEL:
                    Log.e("onTouch", "ACTION_CANCEL");
                case MotionEvent.ACTION_UP:

                    // 恢复滚动
                    isTouchingVp = false;
                    break;
            }
            gestureDetector.onTouchEvent(event);
//            boolean match = gestureDetector.onTouchEvent(event);
//            Log.d("onTouch" , " "+ match);
            return false;
        }
    };
    private GestureDetector.OnGestureListener gestureListener =new GestureDetector.OnGestureListener() {
        // 方法的返回值表示 手势命中了，会把返回值告诉  gestureDetector.onTouchEvent 的 onTouchEvent可以继续利用返回值
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("OnGestureListener","onSingleTapUp");
            AutoRollLayout.this.performClick();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };


}
