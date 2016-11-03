package cn.wupeng.sc.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.view.Toggle;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class MoreSettingFragment extends BaseFragment implements View.OnClickListener {
    private Button bt_collection_clear;
    private TextView top_head_text;
    private Toggle toggle1;
    private Toggle toggle2;
    private Toggle toggle3;
    private Toggle toggle4;
    private Toggle toggle5;

    @Override
    protected void init(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        top_head_text.setText("设置");
        bt_collection_clear.setVisibility(View.GONE);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        tv_main_back.setOnClickListener(backClickListener);
        toggle1 = (Toggle) view.findViewById(R.id.Toggle1);
        toggle2 = (Toggle) view.findViewById(R.id.Toggle2);
        toggle3 = (Toggle) view.findViewById(R.id.Toggle3);
        toggle4 = (Toggle) view.findViewById(R.id.Toggle4);
        toggle5 = (Toggle) view.findViewById(R.id.Toggle5);
        toggle1.setOnClickListener(this);
        toggle2.setOnClickListener(this);
        toggle3.setOnClickListener(this);
        toggle4.setOnClickListener(this);
        toggle5.setOnClickListener(this);
    }
    private View.OnClickListener backClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.onBackPressed();
        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_more_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Toggle1:
                toggle1.toggle();
                break;
            case R.id.Toggle2:
                toggle2.toggle();
                break;
            case R.id.Toggle3:
                toggle3.toggle();
                break;
            case R.id.Toggle4:
                toggle4.toggle();
                break;
            case R.id.Toggle5:
                toggle5.toggle();
                break;
        }
    }
}
