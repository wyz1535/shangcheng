package cn.wupeng.sc.fragment;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.utils.UtilsShaPrefe;

public class PayFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.ll_money_pay)
    LinearLayout ll_money_pay;

    @ViewInject(R.id.ll_zhifubao_pay)
    LinearLayout ll_zhifubao_pay;

    @ViewInject(R.id.ll_pos_pay)
    LinearLayout ll_pos_pay;

    @ViewInject(R.id.tv_door_service)
    TextView tv_door_service;

    @ViewInject(R.id.tv_third_party)
    TextView tv_third_party;

    @ViewInject(R.id.tv_nothing_time)
    TextView tv_nothing_time;

    @ViewInject(R.id.tv_weeked_time)
    TextView tv_weeked_time;

    @ViewInject(R.id.tv_work_time)
    TextView tv_work_time;

    @ViewInject(R.id.tv_money_pay)
    TextView tv_money_pay;

    @ViewInject(R.id.tv_zfb_pay)
    TextView tv_zfb_pay;

    @ViewInject(R.id.tv_pos_pay)
    TextView tv_pos_pay;

    @ViewInject(R.id.iv_pay_back)
    ImageView iv_pay_back;

    @ViewInject(R.id.payAndDeliveryClick)
    Button payAndDeliveryClick;

    @Override
    protected void init(View view) {
        ViewUtils.inject(this, view);
        //支付
        ll_money_pay.setOnClickListener(this);
//        ll_money_pay.setSelected(true);
        ll_zhifubao_pay.setOnClickListener(this);
        ll_pos_pay.setOnClickListener(this);
        //配送方式
        tv_door_service.setOnClickListener(this);
//        tv_door_service.setSelected(true);
        tv_third_party.setOnClickListener(this);
        //配送时间
        tv_nothing_time.setOnClickListener(this);
//        tv_nothing_time.setSelected(true);
        tv_weeked_time.setOnClickListener(this);
        tv_work_time.setOnClickListener(this);
        //getSharedPrederences 回显
        initPayAndDelivery(view);
        //给返回键设置点击监听
        iv_pay_back.setOnClickListener(backClickListener);
        //设置点击确定返回到支付中心监听
        payAndDeliveryClick.setOnClickListener(onPayAndDeliveryClick);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.activityu_pay_delivery;
    }

    private void initPayAndDelivery(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("payConfig", view.getContext().MODE_PRIVATE);
        String pay = sp.getString("pay", "");
        if ("1".equals(pay)) {
            currentPayState = PaySelectorState.LEFT;
            ll_money_pay.setSelected(true);

        } else if ("2".equals(pay)) {
            currentPayState = PaySelectorState.MIDDLE;
            ll_zhifubao_pay.setSelected(true);

        } else if ("3".equals(pay)) {
            currentPayState = PaySelectorState.RIGHT;
            ll_pos_pay.setSelected(true);

        }

        SharedPreferences sp2 = view.getContext().getSharedPreferences("deliveryTypeConfig", view.getContext().MODE_PRIVATE);
        String deliveryType = sp2.getString("deliveryType", "");
        if ("1".equals(deliveryType)) {
            tv_door_service.setSelected(true);
        } else if ("2".equals(deliveryType)) {
            tv_third_party.setSelected(true);
        }

        SharedPreferences sp3 = view.getContext().getSharedPreferences("delivertTimeConfig", view.getContext().MODE_PRIVATE);
        String delivertTime = sp3.getString("delivertTime", "");
        if ("1".equals(delivertTime)) {
            currentDeliverState = DeliverTimeState.NOTHING;
            tv_nothing_time.setSelected(true);
        } else if ("2".equals(delivertTime)) {
            currentDeliverState = DeliverTimeState.WEEKED;
            tv_weeked_time.setSelected(true);
        } else if ("3".equals(delivertTime)) {
            currentDeliverState = DeliverTimeState.WORK;
            tv_work_time.setSelected(true);
        }
    }

    //支付方式状态
    //    public PaySelectorState getcurrentPayState() {
//        return currentPayState;
//    }
    private PaySelectorState currentPayState = PaySelectorState.LEFT;


    //支付方式状态
    public enum PaySelectorState {
        LEFT, MIDDLE, RIGHT
    }

    //送货时间状态
    private DeliverTimeState currentDeliverState = DeliverTimeState.NOTHING;

    public enum DeliverTimeState {
        NOTHING, WEEKED, WORK
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //支付方式
            case R.id.ll_money_pay:
                currentPayState = PaySelectorState.LEFT;
                ll_money_pay.setSelected(true);
                ll_zhifubao_pay.setSelected(false);
                ll_pos_pay.setSelected(false);
                break;
            case R.id.ll_zhifubao_pay:
                currentPayState = PaySelectorState.MIDDLE;
                ll_money_pay.setSelected(false);
                ll_zhifubao_pay.setSelected(true);
                ll_pos_pay.setSelected(false);
                break;
            case R.id.ll_pos_pay:
                currentPayState = PaySelectorState.RIGHT;
                ll_money_pay.setSelected(false);
                ll_zhifubao_pay.setSelected(false);
                ll_pos_pay.setSelected(true);
                break;
            //送货方式
            case R.id.tv_door_service:
                setSelectorType(true);
                break;
            case R.id.tv_third_party:
                setSelectorType(false);
                break;
            //送货时间
            case R.id.tv_nothing_time:
                currentDeliverState = DeliverTimeState.NOTHING;
                tv_nothing_time.setSelected(true);
                tv_weeked_time.setSelected(false);
                tv_work_time.setSelected(false);
                break;
            case R.id.tv_weeked_time:
                currentDeliverState = DeliverTimeState.WEEKED;
                tv_nothing_time.setSelected(false);
                tv_weeked_time.setSelected(true);
                tv_work_time.setSelected(false);
                break;
            case R.id.tv_work_time:
                currentDeliverState = DeliverTimeState.WORK;
                tv_nothing_time.setSelected(false);
                tv_weeked_time.setSelected(false);
                tv_work_time.setSelected(true);
                break;
        }
    }

    //送货方式状态
    boolean isLeftSelector;

    public void setSelectorType(boolean isLeftSelector) {
        this.isLeftSelector = isLeftSelector;
        if (isLeftSelector == true) {
            tv_door_service.setSelected(true);
            tv_third_party.setSelected(false);
        } else {
            tv_door_service.setSelected(false);
            tv_third_party.setSelected(true);
        }
    }

    private View.OnClickListener onPayAndDeliveryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentPayState == PaySelectorState.LEFT) {
                UtilsShaPrefe.sharedPreferences("payConfig", "pay", "1", v.getContext());
            } else if (currentPayState == PaySelectorState.MIDDLE) {
                UtilsShaPrefe.sharedPreferences("payConfig", "pay", "2", v.getContext());
            } else if (currentPayState == PaySelectorState.RIGHT) {
                UtilsShaPrefe.sharedPreferences("payConfig", "pay", "3", v.getContext());
            }

            if (isLeftSelector) {
                UtilsShaPrefe.sharedPreferences("deliveryTypeConfig", "deliveryType", "1", v.getContext());
            } else {
                UtilsShaPrefe.sharedPreferences("deliveryTypeConfig", "deliveryType", "2", v.getContext());
            }

            if (currentDeliverState == DeliverTimeState.NOTHING) {
                UtilsShaPrefe.sharedPreferences("delivertTimeConfig", "delivertTime", "1", v.getContext());
            } else if (currentDeliverState == DeliverTimeState.WEEKED) {
                UtilsShaPrefe.sharedPreferences("delivertTimeConfig", "delivertTime", "2", v.getContext());
            } else if (currentDeliverState == DeliverTimeState.WORK) {
                UtilsShaPrefe.sharedPreferences("delivertTimeConfig", "delivertTime", "3", v.getContext());
            }

            MainActivity activity = (MainActivity) getActivity();
            activity.accountOnClick();

        }
    };

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.onBackPressed();
        }
    };
}
