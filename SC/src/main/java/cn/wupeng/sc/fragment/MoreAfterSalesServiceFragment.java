package cn.wupeng.sc.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;

/**
 * 售后服务界面
 */
public class MoreAfterSalesServiceFragment extends BaseFragment {
    @InjectView(R.id.online_customer_service)
    LinearLayout onlineCustomerService;
    @InjectView(R.id.Phone_Customer)
    LinearLayout PhoneCustomer;
    @InjectView(R.id.more_bt_confirm)
    Button moreBtConfirm;
    private Button bt_collection_clear;
    private TextView top_head_text;
    @Override
    protected void init(View view) {
        ButterKnife.inject(this,view);
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        top_head_text.setText("售后服务");
        bt_collection_clear.setVisibility(View.GONE);
        tv_main_back.setOnClickListener(backClickListener);
    }
    //点击返回键
    private View.OnClickListener backClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.helpCenterOnclick();
        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_more_aftersales_service;
    }
}
