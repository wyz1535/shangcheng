package cn.wupeng.sc.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.bean.UserCenterBean;
import cn.wupeng.sc.utils.UtilsNet;
import cn.wupeng.sc.utils.UtilsRound;

/**
 * 账户中心
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener {
    @ViewInject(R.id.more_account_register)
    TextView more_account_register;
    @ViewInject(R.id.more_account_enter)
    TextView more_account_enter;
    @ViewInject(R.id.more_account_back)
    ImageView more_account_back;
    @ViewInject(R.id.more_account_setting)
    ImageView more_account_setting;
    //头像
    @ViewInject(R.id.more_account_portrait)
    ImageView more_account_portrait;
    //登录后显示的标签
    @ViewInject(R.id.more_account_label)
    ImageView more_account_label;
    @ViewInject(R.id.more_stay_payment)
    RadioButton more_stay_payment;
    @ViewInject(R.id.more_stay_consignee)
    RadioButton more_stay_consignee;
    @ViewInject(R.id.more_stay_evaluate)
    RadioButton more_stay_evaluate;
    @ViewInject(R.id.more_indent_indent)
    RadioButton more_indent_indent;
    @ViewInject(R.id.moew_my_indent)
    RelativeLayout moew_my_indent;
    @ViewInject(R.id.more_my_collect)
    RelativeLayout more_my_collect;
    @ViewInject(R.id.more_me_balance)
    RelativeLayout more_me_balance;
    @ViewInject(R.id.more_receive_address)
    RelativeLayout more_receive_address;
    @ViewInject(R.id.more_me_coupon)
    RelativeLayout more_me_coupon;
    // Android模拟器访问电脑地址
    HttpUtils mHttpUtils;
    private RelativeLayout more_write_off;
    Handler handler=new Handler();


    @Override
    protected void init(View view) {
        ViewUtils.inject(this, view);
        more_account_label.setVisibility(View.GONE);
        more_account_portrait.setImageResource(R.drawable.pic_22);
        more_write_off = (RelativeLayout) view.findViewById(R.id.more_write_off);
        more_write_off.setOnClickListener(writeOffClickListener);
        SharedPreferences sp = view.getContext().getSharedPreferences("more_useName", view.getContext().MODE_PRIVATE);

        String useId = sp.getString("useId", null);

        if (!TextUtils.isEmpty(useId)) {
            mHttpUtils = new HttpUtils();
            mHttpUtils.configTimeout(3000);
            RequestParams params = new RequestParams();
            params.addHeader("userid", useId);
            mHttpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.SERVER_URL + "/userinfo", params, mCallBack);
        }
        onTheClick(view);
    }
    private RequestCallBack<String> mCallBack = new RequestCallBack<String>(){

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            UserCenterBean userCenterBean = new Gson().fromJson(result, UserCenterBean.class);
            String response = userCenterBean.getResponse();
            if (response.equals("error")) {
                Log.i("sada",result + response);
                Toast.makeText(getActivity(),"需要重新登录哦!亲",Toast.LENGTH_SHORT).show();
                SharedPreferences sp1 = getActivity().getSharedPreferences("more_useName", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor edit = sp1.edit();
                edit.putString("useId", null);
                edit.commit();
                return;
            }else {
                UserCenterBean.UserInfoBean userInfo = userCenterBean.getUserInfo();
                //所下的订单
                int orderCount = userInfo.getOrderCount();
                //收藏总数
                int favoritesCount = userInfo.getFavoritesCount();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b_1);
                        more_account_portrait.setImageBitmap(UtilsRound.toRoundBitmap(bitmap));
                    }
                });




                more_account_label.setVisibility(View.VISIBLE);
            }

           // Log.i("test", "result--" + result);
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    //登出账号按钮
    private View.OnClickListener writeOffClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sp = getActivity().getSharedPreferences("more_useName", getActivity().MODE_PRIVATE);
            String useId = sp.getString("useId", null);
            mHttpUtils = new HttpUtils();
            mHttpUtils.configTimeout(3000);
            RequestParams params = new RequestParams();
            params.addHeader("userid", useId);
            mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/logout", params, meeCallBack);
        }
    };
    private RequestCallBack<String> meeCallBack = new RequestCallBack<String>(){

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            Log.i("qqq",result);
            SharedPreferences sp = getActivity().getSharedPreferences("more_useName", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("useId", null);
            edit.commit();
            MainActivity activity = (MainActivity) getActivity();
            activity.userEnteronOnclick();
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_user_center;
    }
    private void onTheClick(View view) {

        more_account_register.setOnClickListener(this);
        more_account_enter.setOnClickListener(this);
        more_account_back.setOnClickListener(this);
        more_account_setting.setOnClickListener(this);
        more_stay_payment.setOnClickListener(this);
        more_stay_consignee.setOnClickListener(this);
        more_stay_evaluate.setOnClickListener(this);
        more_indent_indent.setOnClickListener(this);
        moew_my_indent.setOnClickListener(this);
        more_my_collect.setOnClickListener(this);
        more_me_balance.setOnClickListener(this);
        more_receive_address.setOnClickListener(this);
        more_me_coupon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转注册界面
            case R.id.more_account_register:
                MainActivity activity = (MainActivity) getActivity();
                activity.userRegistrationOnclick();

                break;
            //跳转登录界面
            case R.id.more_account_enter:
                MainActivity activity1 = (MainActivity) getActivity();
                activity1.userEnteronOnclick();
                break;
            //跳转返回界面
            case R.id.more_account_back:
                MainActivity activity2 = (MainActivity) getActivity();
                activity2.moreFragment();
                break;
            //跳转设置界面
            case R.id.more_account_setting:
                    MainActivity activity3 = (MainActivity) getActivity();
                    activity3.moreSettingOnclick();
                break;
            //跳转代付款界面
            case R.id.more_stay_payment:
                SharedPreferences sp = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId = sp.getString("useId", null);
                if (!TextUtils.isEmpty(useId)) {
                    MainActivity activity7 = (MainActivity) getActivity();
                    activity7.behalfPaymentOnclick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                break;
            //跳转代收货界面
            case R.id.more_stay_consignee:
                SharedPreferences sp4 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId3 = sp4.getString("useId", null);
                if (!TextUtils.isEmpty(useId3)) {
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.behalfDeliveryOnclick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                break;
            //跳转代评价界面
            case R.id.more_stay_evaluate:
                SharedPreferences sp5 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId4 = sp5.getString("useId", null);
                if (!TextUtils.isEmpty(useId4)) {
                    MainActivity activity5 = (MainActivity) getActivity();
                    activity5.behalfEvaluateOnclick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                break;
            //跳转订单查询界面
            case R.id.more_indent_indent:
                Toast.makeText(v.getContext(), "跳转订单查询界面", Toast.LENGTH_SHORT).show();
                break;
            //跳转我的订单界面
            case R.id.moew_my_indent:
                SharedPreferences sp8 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId7 = sp8.getString("useId", null);
                if (!TextUtils.isEmpty(useId7)) {
                    MainActivity orderListActivity = (MainActivity) getActivity();
                    orderListActivity.myOrderOnclick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                //Toast.makeText(v.getContext(), "跳转我的订单界面", Toast.LENGTH_SHORT).show();
                break;
            //跳转我的收藏界面
            case R.id.more_my_collect:
                SharedPreferences sp3 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId2 = sp3.getString("useId", null);
                if (!TextUtils.isEmpty(useId2)) {
                    MainActivity activity6 = (MainActivity) getActivity();
                    activity6.moreFavoriteOnclick();
                }else {
                Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                MainActivity activity4 = (MainActivity) getActivity();
                activity4.userEnteronOnclick();
                }
                break;
            //跳转我的余额界面
            case R.id.more_me_balance:
                SharedPreferences sp7 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId6 = sp7.getString("useId", null);
                if (!TextUtils.isEmpty(useId6)) {
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.morebalanceOnclick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                break;
            //跳转收货地址界面
            case R.id.more_receive_address:
                SharedPreferences sp1 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId1 = sp1.getString("useId", null);
                if (!TextUtils.isEmpty(useId1)) {
                    MainActivity activity9 = (MainActivity) getActivity();
                    activity9.consigneeAddressOnclick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                break;
            //跳转我的优惠券界面
            case R.id.more_me_coupon:
                SharedPreferences sp9 = v.getContext().getSharedPreferences("more_useName", v.getContext().MODE_PRIVATE);
                String useId8 = sp9.getString("useId", null);
                if (!TextUtils.isEmpty(useId8)) {
                    MainActivity activityPrivilege = (MainActivity) getActivity();
                    activityPrivilege.privilegeOnClick();
                }else {
                    Toast.makeText(v.getContext(), "请先登录哦!亲", Toast.LENGTH_SHORT).show();
                    MainActivity activity4 = (MainActivity) getActivity();
                    activity4.userEnteronOnclick();
                }
                break;
        }
    }

}
