package cn.wupeng.sc.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.activity.NewbieGuideActivity;

/**
 * 帮组中心界面
 */
public class HelpCenterFragment extends BaseFragment {
    @InjectView(R.id.more_shopping_guide)
    RelativeLayout moreShoppingGuide;
    @InjectView(R.id.more_after_sales_service)
    RelativeLayout moreAfterSalesService;
    @InjectView(R.id.more_mode_of_distribution)
    RelativeLayout moreModeOfDistribution;
    @InjectView(R.id.MoretelephoneClick)
    Button MoretelephoneClick;
    private Button bt_collection_clear;
    private TextView top_head_text;


    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        click(view);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_help_center;
    }

    private void click(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        top_head_text.setText("帮助中心");
        bt_collection_clear.setVisibility(View.GONE);
        tv_main_back.setOnClickListener(backClickListener);
        moreShoppingGuide.setOnClickListener(shoppingClickListener);
        moreAfterSalesService.setOnClickListener(moreAfterClickListener);
        MoretelephoneClick.setOnClickListener(telephoneClickListener);
        moreModeOfDistribution.setOnClickListener(distributionClickListener);
    }
    //跳转到配送方式页面
    private View.OnClickListener distributionClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.modeofDistributionOnclick();
        }
    };
    //点击返回键
    private View.OnClickListener backClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.moreFragment();
        }
    };
    //点击拨打客服热线
    private View.OnClickListener telephoneClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel://" + "400-800-4752"));
            startActivity(intent);
        }
    };

    //跳转购物指南页面
    private View.OnClickListener shoppingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(), NewbieGuideActivity.class);
            startActivity(intent);
        }
    };
    //跳转售后服务页面
    private View.OnClickListener moreAfterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.moreAfterSalesServiceOnclick();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
