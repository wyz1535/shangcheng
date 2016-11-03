package cn.wupeng.sc.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class BehalfPaymentFragment extends BaseFragment {
    private Button bt_collection_clear;
    private TextView top_head_text;


    @Override
    protected void init(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        top_head_text.setText("代付款");
        bt_collection_clear.setVisibility(View.GONE);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        ImageView favorite_pic = (ImageView) view.findViewById(R.id.more_favorite_pic);
        favorite_pic.setImageResource(R.drawable.b_4);
        tv_main_back.setOnClickListener(backClickListener);
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
        return R.layout.fragment__behalf_payment;
    }
}
