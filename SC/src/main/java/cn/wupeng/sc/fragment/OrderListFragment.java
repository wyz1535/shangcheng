package cn.wupeng.sc.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.OrderListPagerAdapter;
import cn.wupeng.sc.bean.orderList.OrderList;
import cn.wupeng.sc.bean.orderList.OrderListBean;
import cn.wupeng.sc.utils.Utils;
import cn.wupeng.sc.utils.UtilsNet;


public class OrderListFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_first;
    private TextView tv_mid;
    private TextView tv_last;
    // Android模拟器访问电脑地址
    private static String HOST = UtilsNet.SERVER_URL;
    private static final String TAG = "OrderListFragment";

    // 当前登录的用户ID，根据login接口的响应结果设置
    HttpUtils mHttpUtils;
    private ListView lv_order_list;
    private OrderList orderList;
    private ImageView iv_order_list_back;
//    private Button bt_login;
//    private Button bt_submit;
//    private Button bt_cancle;
    private TextView tv_null;
    private String user_id;


    private void init() {
        tv_first.setSelected(true);
        tv_mid.setSelected(false);
        tv_last.setSelected(false);
        withinOneMouth();

    }

    @Override
    protected void init(View view) {
        SharedPreferences sp=view.getContext().getSharedPreferences("more_useName", Context.MODE_PRIVATE);
        user_id = sp.getString("useId", "");
        if (TextUtils.isEmpty(user_id)){
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.userEnteronOnclick();
            Utils.showToast(view.getContext(),"亲！请先登录哦！");
            return;
        }
        mHttpUtils = new HttpUtils();
        // 不缓存服务器响应结果，方便调试。（真实应用里面不要这么做）
        mHttpUtils.configDefaultHttpCacheExpiry(0);
        // 设置http请求超时为3秒
        mHttpUtils.configTimeout(3000);

        tv_first = (TextView)view. findViewById(R.id.tv_first);
        tv_mid = (TextView)view. findViewById(R.id.tv_mid);
        tv_last = (TextView)view. findViewById(R.id.tv_last);
        iv_order_list_back = (ImageView) view.findViewById(R.id.iv_order_list_back);
//        bt_login = (Button) view.findViewById(R.id.bt_login);
//        bt_submit = (Button) view.findViewById(R.id.bt_submit);
//        bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
        tv_first.setOnClickListener(this);
        tv_mid.setOnClickListener(this);
        tv_last.setOnClickListener(this);
        iv_order_list_back.setOnClickListener(this);
//        bt_login.setOnClickListener(this);
//        bt_submit.setOnClickListener(this);
//        bt_cancle.setOnClickListener(this);
        lv_order_list = (ListView) view.findViewById(R.id.lv_order_list);
       tv_null = (TextView) view.findViewById(R.id.tv_null);

        lv_order_list.setOnItemClickListener(onItemClickListener);
        init();

    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_order_list;
    }


    private enum SegmentState {
        FIRST, MID, LAST
    }

    private SegmentState currentState = SegmentState.FIRST;

    public void setSegment(SegmentState currentState) {
        if (currentState == SegmentState.FIRST) {
            tv_first.setSelected(true);
            tv_mid.setSelected(false);
            tv_last.setSelected(false);
            this.currentState = currentState;
        } else if (currentState == SegmentState.MID) {
            tv_first.setSelected(false);
            tv_mid.setSelected(true);
            tv_last.setSelected(false);
            this.currentState = currentState;
        } else if (currentState == SegmentState.LAST) {
            tv_first.setSelected(false);
            tv_mid.setSelected(false);
            tv_last.setSelected(true);
            this.currentState = currentState;
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_first) {
            setSegment(SegmentState.FIRST);
            withinOneMouth();
        }
        if (v.getId() == R.id.tv_mid) {
            setSegment(SegmentState.MID);
            beforeOneMouth();
        }
        if (v.getId() == R.id.tv_last) {
            setSegment(SegmentState.LAST);
            cancelOrder();
        }

        if (v.getId()==R.id.iv_order_list_back){
            MainActivity activity= (MainActivity) getActivity();
            activity.onBackPressed();
            Toast.makeText(v.getContext(),"我被点击了",Toast.LENGTH_SHORT).show();
        }

//        //登陆
//        if (v.getId()==R.id.bt_login){
//            RequestParams params = new RequestParams();
//
//            // GET类型请求，使用addQueryStringParameter添加参数
//            params.addQueryStringParameter("username", "fan");
//            params.addQueryStringParameter("password", "fan");
//
//            mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/login", params, new RequestCallBack<String>() {
//                @Override
//                public void onSuccess(ResponseInfo<String> responseInfo) {
//                    String result = responseInfo.result;
//                    Log.i("ii", "登陆成功 " + result);
//                }
//
//                @Override
//                public void onFailure(HttpException e, String s) {
//                    Log.i("test", "onFailure" + s);
//                }
//            });
//        }
//
//        //提交
//        if (v.getId()==R.id.bt_submit){
//            RequestParams params = new RequestParams();
//
//            // 添加参数到请求头使用addHeader
//            // 注意：USER_ID需要改成login接口返回的userId值！！
//            params.addHeader("userid", USER_ID);
//
//            // post请求添加参数使用addBodyParameter
//            params.addBodyParameter("sku", "1:3:1,2,3,4|2:2:2,3");
//            params.addBodyParameter("addressId", "139");
//            params.addBodyParameter("paymentType", "1");
//            params.addBodyParameter("deliveryType", "1");
//            params.addBodyParameter("invoiceType", "2");
//            params.addBodyParameter("invoiceTitle", "传智慧播客教育科技有限公司");
//            params.addBodyParameter("invoiceContent", "传智慧播客教育科技有限公司");
//
//            mHttpUtils.send(HttpRequest.HttpMethod.POST, HOST + "/ordersumbit", params, new RequestCallBack<String>() {
//                @Override
//                public void onSuccess(ResponseInfo<String> responseInfo) {
//                    String result = responseInfo.result;
//                    Log.i("ii", "提交成功 " + result);
//                }
//
//                @Override
//                public void onFailure(HttpException e, String s) {
//                    Log.i("test", "onFailure" + s);
//                }
//            });
//        }
//
//        //取消
//        if (v.getId()==R.id.bt_cancle){
//            RequestParams params = new RequestParams();
//            params.addHeader("userid", USER_ID);    // USER_ID需要使用login接口返回的userId值
//
//            // 这个对应提交订单时返回的orderId，需要修改!!!
//            params.addBodyParameter("orderId", "103499");
//            mHttpUtils.send(HttpRequest.HttpMethod.POST, HOST + "/ordercancel", params, new RequestCallBack<String>() {
//                @Override
//                public void onSuccess(ResponseInfo<String> responseInfo) {
//                    String result = responseInfo.result;
//                    Log.i("ii", "取消成功 " + result);
//                }
//
//                @Override
//                public void onFailure(HttpException e, String s) {
//                    Log.i("test", "onFailure" + s);
//                }
//            });
//        }
    }


    public void withinOneMouth() {
        RequestParams params = new RequestParams();
        // 添加参数到请求头使用addHeader
        // 注意：USER_ID需要改成login接口返回的userId值！！
        params.addHeader("userid", user_id);
        params.addQueryStringParameter("type", "1");
        params.addQueryStringParameter("page", "0");
        params.addQueryStringParameter("pageNum", "10");

        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/orderlist", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                OrderList withinList = new Gson().fromJson(json, OrderList.class);
                orderList=withinList;
                if (orderList.getOrderList().size()==0){
                    lv_order_list.setVisibility(View.INVISIBLE);
                    tv_null.setVisibility(View.VISIBLE);
                }else {
                    lv_order_list.setVisibility(View.VISIBLE);
                    tv_null.setVisibility(View.INVISIBLE);
                }
                OrderListPagerAdapter withinListAdapter = new OrderListPagerAdapter(withinList);
                lv_order_list.setAdapter(withinListAdapter);
                withinListAdapter.notifyDataSetChanged();
                Log.i("ii", "一个月之内 " + json);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("test", "onFailure" + s);
            }
        });
    }

    public void beforeOneMouth() {
        RequestParams params = new RequestParams();
        // 添加参数到请求头使用addHeader
        // 注意：USER_ID需要改成login接口返回的userId值！！
        params.addHeader("userid", user_id);
        params.addQueryStringParameter("type", "2");
        params.addQueryStringParameter("page", "0");
        params.addQueryStringParameter("pageNum", "10");

        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/orderlist", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                OrderList beforeList = new Gson().fromJson(json, OrderList.class);
                orderList=beforeList;
                if (orderList.getOrderList().size()==0){
                    lv_order_list.setVisibility(View.INVISIBLE);
                    tv_null.setVisibility(View.VISIBLE);
                }else {
                    lv_order_list.setVisibility(View.VISIBLE);
                    tv_null.setVisibility(View.INVISIBLE);
                }
                OrderListPagerAdapter beforeListAdapter = new OrderListPagerAdapter(beforeList);
                lv_order_list.setAdapter(beforeListAdapter);
                beforeListAdapter.notifyDataSetChanged();

                Log.i("ii", "一个月以前 " + json);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("test", "onFailure" + s);
            }
        });
    }

    public void cancelOrder() {
        RequestParams params = new RequestParams();
        // 添加参数到请求头使用addHeader
        // 注意：USER_ID需要改成login接口返回的userId值！！
        params.addHeader("userid", user_id);
        params.addQueryStringParameter("type", "3");
        params.addQueryStringParameter("page", "0");
        params.addQueryStringParameter("pageNum", "10");

        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/orderlist", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                OrderList cancelList = new Gson().fromJson(json, OrderList.class);
                orderList=cancelList;
                if (orderList.getOrderList().size()==0){
                    lv_order_list.setVisibility(View.INVISIBLE);
                    tv_null.setVisibility(View.VISIBLE);
                }else {
                    lv_order_list.setVisibility(View.VISIBLE);
                    tv_null.setVisibility(View.INVISIBLE);
                }
                OrderListPagerAdapter cancelListAdapter = new OrderListPagerAdapter(cancelList);
                lv_order_list.setAdapter(cancelListAdapter);
                cancelListAdapter.notifyDataSetChanged();

                Log.i("ii", "已取消 " + json);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("test", "onFailure" + s);
            }
        });
    }

    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            OrderListBean orderListBean = orderList.getOrderList().get(position);
            String orderId = orderListBean.getOrderId();
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.orderDetailOnclick(orderId,TAG);
        }
    };

}
