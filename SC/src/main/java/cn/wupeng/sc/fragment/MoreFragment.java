package cn.wupeng.sc.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.activity.MoreAboutActivity;


/**
 * 更多界面
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.user_center)
    RelativeLayout userCenter;
    @InjectView(R.id.browsing_history)
    RelativeLayout browsingHistory;
    @InjectView(R.id.help_center)
    RelativeLayout helpCenter;
    @InjectView(R.id.user_feedback)
    RelativeLayout userFeedback;
    @InjectView(R.id.about)
    RelativeLayout about;
    @InjectView(R.id.bt_telephone)
    Button btTelephone;
    private Button bt_collection_clear;
    private TextView top_head_text;

    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        click(view);

    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_more;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void click(View view) {
        userCenter.setOnClickListener(this);
        helpCenter.setOnClickListener(this);
        userFeedback.setOnClickListener(this);
        btTelephone.setOnClickListener(this);
        about.setOnClickListener(this);
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        top_head_text.setText("更多");
        bt_collection_clear.setVisibility(View.GONE);
        tv_main_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击跳转用户中心
            case R.id.user_center:
                MainActivity activity = (MainActivity) getActivity();
                activity.useCenterOnclick();
                break;
            //跳转到帮组中心
            case R.id.help_center:
                MainActivity activity1 = (MainActivity) getActivity();
                activity1.helpCenterOnclick();
                break;
            //跳转到用户反馈
            case R.id.user_feedback:
                MainActivity activity2 = (MainActivity) getActivity();
                activity2.userFeedbackOnclick();
                break;
            //跳转到关于界面
            case R.id.about:
                Intent intent3=new Intent(v.getContext(), MoreAboutActivity.class);
                startActivity(intent3);
                break;
            //更多界面客服电话按钮,跳转到拨号盘
            case R.id.bt_telephone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel://" + "400-800-4752"));
                startActivity(intent);
                break;
            case R.id.tv_main_back:

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}
