package cn.wupeng.sc.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.ProductInfoListAdapter;
import cn.wupeng.sc.bean.orderDetail.OrderDetail;
import cn.wupeng.sc.utils.UtilsNet;


public class OrderDetailFragment extends BaseFragment implements View.OnClickListener {

    // Android模拟器访问电脑地址
    private static String HOST = UtilsNet.SERVER_URL;

    // 当前登录的用户ID，根据login接口的响应结果设置
    HttpUtils mHttpUtils;
    private TextView tv_order_id;
    private TextView tv_phone_number;
    private TextView tv_address;
    private TextView tv_order_state;
    private TextView tv_pay_style;
    private TextView tv_order_time;
    private TextView tv_invoice;
    private TextView tv_invoice_head;
    private TextView tv_send_require;

    private View view;

    private String orderId;
    private Button bt_cancle_order;
    private ListView lv_product_info;
    private String user_id;
    private TextView tv_detail_null;
    private LinearLayout ll_order_detail;


    @Override
    protected void init(View view) {
        this.view=view;
        SharedPreferences sp=view.getContext().getSharedPreferences("more_useName", Context.MODE_PRIVATE);
        user_id = sp.getString("useId", "");
        tv_order_id = (TextView) view.findViewById(R.id.tv_order_id);
        tv_phone_number = (TextView) view.findViewById(R.id.tv_phone_number);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_order_state = (TextView)view. findViewById(R.id.tv_order_state);
        tv_pay_style = (TextView) view.findViewById(R.id.tv_pay_style);
        tv_order_time = (TextView)view. findViewById(R.id.tv_order_time);
        tv_invoice = (TextView) view.findViewById(R.id.tv_invoice);
        tv_invoice_head = (TextView)view. findViewById(R.id.tv_invoice_head);
        tv_send_require = (TextView) view.findViewById(R.id.tv_send_require);
        tv_detail_null = (TextView) view.findViewById(R.id.tv_detail_null);
        ll_order_detail = (LinearLayout) view.findViewById(R.id.ll_order_detail);

        lv_product_info = (ListView) view.findViewById(R.id.lv_product_info);
        ImageView iv_back = (ImageView) view.findViewById(R.id.iv_back);
        bt_cancle_order = (Button) view.findViewById(R.id.bt_cancle_order);
        iv_back.setOnClickListener(this);


        mHttpUtils = new HttpUtils();
        // 不缓存服务器响应结果，方便调试。（真实应用里面不要这么做）
        mHttpUtils.configDefaultHttpCacheExpiry(0);
        // 设置http请求超时为3秒
        mHttpUtils.configTimeout(3000);

        Bundle bundle = getArguments();
        String orderId1 = bundle.getString("orderId");
        String tag=bundle.getString("TAG");
        if ("OrderListFragment".equals(tag)){
            bt_cancle_order.setVisibility(View.GONE);
        }else {
            bt_cancle_order.setOnClickListener(this);
        }
        orderId=orderId1;
        requestData(orderId);

    }

    private void cancelOrder(String orderId) {
        RequestParams params = new RequestParams();
        // 添加参数到请求头使用addHeader
        // 注意：USER_ID需要改成login接口返回的userId值！！
        params.addHeader("userid", user_id);
        params.addBodyParameter("orderId", orderId);

        mHttpUtils.send(HttpRequest.HttpMethod.POST, HOST + "/ordercancel", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;

        }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("test", "onFailure" + s);
            }
        });
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_order_detail;
    }

    public void requestData(String orderId) {
        RequestParams params = new RequestParams();
        // 添加参数到请求头使用addHeader
        // 注意：USER_ID需要改成login接口返回的userId值！！
        params.addHeader("userid", user_id);
        params.addQueryStringParameter("orderId", orderId);

        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/orderdetail", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                OrderDetail orderDetail = new Gson().fromJson(json, OrderDetail.class);
                if ("error".equals(orderDetail.getResponse())){
                    tv_detail_null.setVisibility(View.VISIBLE);
                    ll_order_detail.setVisibility(View.INVISIBLE);
                    return;
                }else {
                    tv_detail_null.setVisibility(View.INVISIBLE);
                    ll_order_detail.setVisibility(View.VISIBLE);
                }
                ProductInfoListAdapter productInfoListAdapter = new ProductInfoListAdapter(orderDetail);
                lv_product_info.setAdapter(productInfoListAdapter);


                SharedPreferences addressToAcc = view.getContext().getSharedPreferences("addressToAccName", Context.MODE_PRIVATE);
                SharedPreferences addressToAccPhone = view.getContext().getSharedPreferences("addressToAccPhone", Context.MODE_PRIVATE);
                SharedPreferences addressToAccAdd = view.getContext().getSharedPreferences("addressToAccAdd", Context.MODE_PRIVATE);
                String addressToAccName = addressToAcc.getString("addressToAccName", null);
                String addressToAccPhone1 = addressToAccPhone.getString("addressToAccPhone", null);
                String addressToAccAdd1 = addressToAccAdd.getString("addressToAccAdd", null);
                tv_phone_number.setText(addressToAccName);
                tv_address.setText(addressToAccAdd1);



                tv_order_id.setText(orderDetail.getOrderInfo().getOrderId());
                tv_phone_number.setText(orderDetail.getAddressInfo().getName());
                tv_address.setText(orderDetail.getAddressInfo().getAddressArea() + orderDetail.getAddressInfo().getAddressDetail());
                tv_order_state.setText(orderDetail.getOrderInfo().getStatus());
                //tv_pay_style.setText(orderDetail.getPaymentInfo().getType());
                tv_order_time.setText(orderDetail.getOrderInfo().getTime());
                tv_invoice.setText(orderDetail.getInvoiceInfo().getInvoiceContent());
                tv_invoice_head.setText(orderDetail.getInvoiceInfo().getInvoiceTitle());
                tv_send_require.setText(orderDetail.getDeliveryInfo().getType());
               // List<ProductListBean> productList = orderDetail.getProductList();

//                tv_goods_name.setText(orderDetail.getProductList().get(2).getProduct().getName());
//                tv_Total_number.setText(orderDetail.getCheckoutAddup().getTotalCount());
//                tv_price.setText(orderDetail.getProductList().get(2).getProduct().getPrice());
//                tv_freight.setText(orderDetail.getCheckoutAddup().getFreight());
                //               tv_privilege.setText(orderDetail.getProm().get(1));
                //               tv_should_pay.setText(orderDetail.getCheckoutAddup().getTotalPrice());
                Log.i("tt", "商品详情 " + json);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("test", "onFailure" + s);
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.iv_back){
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.onBackPressed();
        }

        if (v.getId()==R.id.bt_cancle_order){
            cancelOrder(orderId);
        }
    }
}
