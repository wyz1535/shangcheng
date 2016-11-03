package cn.itcast.wh20.sidepulldelete.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/6/26.
 */
public class SidePullDeleteLayout extends FrameLayout{

    private ViewDragHelper viewDragHelper;
    private ViewGroup menuView;
    private ViewGroup mainView;
    private int mWidth;
    private int mRange;

    public SidePullDeleteLayout(Context context) {
        this(context, null);
    }

    public SidePullDeleteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SidePullDeleteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public enum DragState{
        OPEN,CLOSE,DRAGGING
    }
    private DragState currentState=DragState.CLOSE;//当前状态
    private DragState preState=DragState.CLOSE;//上一次状态
    private void init() {
        //1、创建ViewDragHelper对象
        viewDragHelper = ViewDragHelper.create(this, callback);
    }
    public interface OnSidePullChangeListener{
        public void onOpen(SidePullDeleteLayout sidePullDeleteLayout);
        public void onDragging(SidePullDeleteLayout sidePullDeleteLayout);
        public void onClose();
        public void onStartOpen(SidePullDeleteLayout sidePullDeleteLayout);
    }
    private OnSidePullChangeListener onSidePullChangeListenerl;

    public void setOnSidePullChangeListenerl(OnSidePullChangeListener onSidePullChangeListenerl) {
        this.onSidePullChangeListenerl = onSidePullChangeListenerl;
    }

    //拖拽的回调方法
    private ViewDragHelper.Callback callback=new ViewDragHelper.Callback() {
        /**
         * 尝试捕获View
         * @param child  被拖拽的view
         * @param pointerId   多点触碰
         * @return  返回true表示允许被捕获
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 获取水平方向的拖拽范围，返回值大于0即可，当出现事件交叉的时候必须重写
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        /**
         *      期望下一次移动到哪个位置，真正的拖拽改变位置实在这个方法之后执行
         * @param child      被拖拽的view
         * @param left      移动位置的期望值  view.getLeft()+dx
         * @param dx        水平方向的偏移量
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child==mainView){
                if (left>0){
                    left=0;
                }else if (left<-mRange){
                    left=-mRange;
                }
            }else{
                if (left<mWidth-mRange){
                    left=mWidth-mRange;
                }else if (left>mWidth){
                    left=mWidth;
                }
            }
            return left;
        }

        /**
         * 当位置发生改变的时候调用
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView==mainView){
                menuView.offsetLeftAndRight(dx);//参数表示偏移量
            }else{
                mainView.offsetLeftAndRight(dx);
            }

            executeListaner();

            //为了兼容低版本，重绘界面
            invalidate();
        }

        /**
         * 当释放View的时候调用
         * @param releasedChild
         * @param xvel      x轴的速度
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mainView.getLeft()<-mRange*0.5){
                open(true);
            }else{
                close(true);
            }
        }
    };

    //执行接口回调
    private void executeListaner() {
        preState=currentState;
        //更新当前的拖拽状态
        currentState=updateDragState();
        if (onSidePullChangeListenerl!=null){
            if (currentState==DragState.CLOSE){
                onSidePullChangeListenerl.onClose();
            }else if (currentState==DragState.OPEN){
                onSidePullChangeListenerl.onOpen(this);
            }else{
                if (preState==DragState.CLOSE){
                    onSidePullChangeListenerl.onStartOpen(this);
                }else{
                    onSidePullChangeListenerl.onDragging(this);
                }
            }
        }
    }

    private DragState updateDragState() {
        if (mainView.getLeft()==-mRange){
            return DragState.OPEN;
        }else if (mainView.getLeft()==0){
            return  DragState.CLOSE;
        }
        return DragState.DRAGGING;
    }

    //关闭
    public void close(boolean isSmooth) {
        if (isSmooth){
            if (viewDragHelper.smoothSlideViewTo(mainView,0,0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else{
            layoutContent(false);
        }
    }
    //打开
    public void open(boolean isSmooth) {
        if (isSmooth){
            if (viewDragHelper.smoothSlideViewTo(mainView,-mRange,0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else{
            layoutContent(true);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //是否保持动画
        if (viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //2、把事件交给ViewDragHelper去拦截，true表示拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }
    //3、把事件交给ViewDragHelper去处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }
    //通过测量获取主界面和菜单的宽度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取主界面和最大拖拽范围
        mWidth = getMeasuredWidth();
        mRange = menuView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //摆放主界面和菜单的位置，有两情况 打开和关闭
       layoutContent(false);

    }
    //打开或者关闭菜单
    private void layoutContent(boolean isOpen) {
        //计算主界面的位置，保存到Rect矩形里面  false表示关闭状态
        Rect mainRect=computeMainRect(isOpen);
        //摆放主界面的位置
        mainView.layout(mainRect.left,mainRect.top,mainRect.right,mainRect.bottom);
        //计算菜单的位置，保存到Rect矩形里面，跟主界面的位置有关
        Rect menuRect=computeMuneRect(mainRect);
        //摆放菜单
        menuView.layout(menuRect.left,menuRect.top,menuRect.right,menuRect.bottom);
    }

    //保存菜单的位置
    private Rect computeMuneRect(Rect mainRect) {
        return new Rect(mWidth+mainRect.left,0,mainRect.right+mRange,menuView.getHeight());
    }
    //保存主界面的位置
    private Rect computeMainRect(boolean isOpen) {
        if (isOpen){
            return new Rect(-mRange,0,mainView.getWidth()-mRange,mainView.getHeight());
        }else{
            return new Rect(0,0,mainView.getWidth(),mainView.getHeight());
        }
    }
    //找到主界面View和菜单View
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //健壮性判断
        if (getChildCount()<2){
            throw new IllegalStateException("you must have two children,你必须包含两个孩子");
        }

        if (!(getChildAt(0) instanceof ViewGroup)||!(getChildAt(1) instanceof ViewGroup)){
            throw new IllegalArgumentException("your child must be instance of ViewGroup，你的孩子类型必须是ViewGroup");
        }
        menuView = (ViewGroup) getChildAt(0);
        mainView = (ViewGroup) getChildAt(1);
    }
}
