package cn.wupeng.sc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wupeng.sc.R;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
public class Toggle extends LinearLayout {
    private boolean isToggleOn;
    private ImageView ivToggle;

    public Toggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_toggle, null);
        this.addView(view);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Toggle);
        String string = typedArray.getString(0);
        String string1 = typedArray.getString(1);
        TextView textView = (TextView)findViewById(R.id.tv_title);
        ivToggle = (ImageView) findViewById(R.id.iv_toggle);
        textView.setText(string);
        if ("top".equals(string1)) {
            setBackgroundResource(R.drawable.setting_center_top_selector);
        } else if ("mid".equals(string1)) {
            setBackgroundResource(R.drawable.setting_center_mid_selector);
        } else if ("bottom".equals(string1)) {
            setBackgroundResource(R.drawable.setting_center_bottom_selector);
        }
    }
    public void setToggleOn(boolean isToggleOn) {
        this.isToggleOn = isToggleOn;
        if (isToggleOn) {
            ivToggle.setImageResource(R.drawable.on);
        } else {
            ivToggle.setImageResource(R.drawable.off);
        }
    }

    public boolean isToggleOn() {
        return isToggleOn;
    }
    public void toggle() {
        setToggleOn(!isToggleOn);
    }
}
