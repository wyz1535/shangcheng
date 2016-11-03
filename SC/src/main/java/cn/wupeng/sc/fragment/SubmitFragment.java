package cn.wupeng.sc.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;

public class SubmitFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.iv_submit_back)
    ImageView iv_submit_back;

    @ViewInject(R.id.bt_go_shop)
    Button bt_go_shop;

    @ViewInject(R.id.bt_look_goods)
    Button bt_look_goods;

    @ViewInject(R.id.tv_number)
    TextView tv_number;

    @ViewInject(R.id.tv_goods_number)
    TextView tv_goods_number;

    @ViewInject(R.id.tv_should_money)
    TextView tv_should_money;

    @ViewInject(R.id.tv_pay_type)
    TextView tv_pay_type;

    private static final String TAG = "SubmitFragment";
    private String orderId;

    @Override
    protected void init(View view) {

        ViewUtils.inject(this, view);
        //给返回键设置点击监听
        bt_go_shop.setOnClickListener(this);
        bt_look_goods.setOnClickListener(this);
        iv_submit_back.setOnClickListener(this);
        tv_number.setOnClickListener(this);
        //获取从accountFragment传递过来的数据
        orderId = getArguments().getString("key");
        int price = getArguments().getInt("key2");
        String text = getArguments().getString("key3");
        tv_goods_number.setText("订单号:"+ orderId);
        tv_should_money.setText("实付金额:¥"+price +".0");
        tv_pay_type.setText("支付方式:" + text);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.activity_submit;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_go_shop:
                MainActivity activityHome = (MainActivity) getActivity();
                activityHome.submitToHomeOnClick();
                break;
            case R.id.bt_look_goods:
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.orderDetailOnclick(orderId,TAG);
                Toast.makeText(v.getContext(), "跳转到查看订单界面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_submit_back:
                MainActivity activity = (MainActivity) getActivity();
                activity.onBackPressed();
                break;
            case R.id.tv_number:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel://" + "400_008_2422"));
                startActivity(intent);
                break;
        }
    }
}
