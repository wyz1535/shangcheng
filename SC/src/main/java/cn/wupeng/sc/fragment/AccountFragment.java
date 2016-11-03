package cn.wupeng.sc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.GoodsDetailActivity;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.AccountGoodsAdapter;
import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.bean.accountBean.AccountProductBean;
import cn.wupeng.sc.bean.accountBean.ProductBean;
import cn.wupeng.sc.bean.accountBean.ProductListAcBean;
import cn.wupeng.sc.bean.orderDetail.OrderInfoBean;
import cn.wupeng.sc.bean.toSubmitbean.Submitbean;
import cn.wupeng.sc.dao.ShoppingCartDao;
import cn.wupeng.sc.utils.UtilsNet;


public class AccountFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.tv_account_submit)
    TextView tv_account_submit;

    @ViewInject(R.id.rl_address)
    RelativeLayout rl_address;

    @ViewInject(R.id.rl_pay)
    RelativeLayout rl_pay;

    @ViewInject(R.id.rl_invoice)
    RelativeLayout rl_invoice;

//    @ViewInject(R.id.rl_privilege)
//    RelativeLayout rl_privilege;


    @ViewInject(R.id.tv_type_invoice)
    TextView tv_type_invoice;

    @ViewInject(R.id.tv_belong_invoice)
    TextView tv_belong_invoice;

    @ViewInject(R.id.tv_thing_invoice)
    TextView tv_thing_invoice;

    @ViewInject(R.id.tv_money_pay)
    TextView tv_money_pay;

    @ViewInject(R.id.tv_delivery_type)
    TextView tv_delivery_type;

    @ViewInject(R.id.tv_delivery_time)
    TextView tv_delivery_time;

    @ViewInject(R.id.iv_account_back)
    ImageView iv_account_back;

    @ViewInject(R.id.lv_goods_infom)
    ListView lv_goods_infom;

    @ViewInject(R.id.tv_name)
    TextView tv_name;

    @ViewInject(R.id.tv_number)
    TextView tv_number;

    @ViewInject(R.id.tv_address)
    TextView tv_address;

//    @ViewInject(R.id.tv_acount_should_money)
//    TextView tv_acount_should_money;
    AccountProductBean accountProductBean;
    /**
     * orderId : 915178
     * paymentType : 1
     * price : 450
     */

    private List<ShoppingCartItemBean> dbList = new ArrayList<>();
    private OrderInfoBean orderInfo;



    /**
     * orderInfo : {"orderId":"915178","paymentType":1,"price":450}
     * response : orderSubmit
     */

    private String response;
    private String invoiceType;
    private String delivertTime;
    private String belong;
//    private int totalPrice;

    Handler handler = new Handler();
    private AccountGoodsAdapter adapter;
    private int id;
    private int count;
    private boolean isSelect = true;
    private String idString;
    private String user_id;



    @Override
    protected void init(View view) {

        ViewUtils.inject(this, view);

        tv_account_submit.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_pay.setOnClickListener(this);
        rl_invoice.setOnClickListener(this);
//        rl_privilege.setOnClickListener(this);

        //显示发票类型
        initInvoiceType(view);
        //支付及送货方式
        initPayAndDelivery(view);
        //设置返回监听
        iv_account_back.setOnClickListener(backClickListener);
        //从服务器获取数据
        initNetData();
        //初始化网上信息
        initAddressAndProductor(view);

        SharedPreferences sp=view.getContext().getSharedPreferences("more_useName", Context.MODE_PRIVATE);
        user_id = sp.getString("useId", "");
        Log.i("user_id",user_id);
    }


    @Override
    public int getResourcesLayout() {
        return R.layout.activity_account;
    }

    private void initPayAndDelivery(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("payConfig", view.getContext().MODE_PRIVATE);
        String pay = sp.getString("pay", "");
        if ("1".equals(pay)) {
            tv_money_pay.setText("到付-现金");
        } else if ("2".equals(pay)) {
            tv_money_pay.setText("支付宝");
        } else if ("3".equals(pay)) {
            tv_money_pay.setText("到付-POS");
        }else{
            tv_money_pay.setText("");
        }

        SharedPreferences sp2 = view.getContext().getSharedPreferences("deliveryTypeConfig", view.getContext().MODE_PRIVATE);
        String deliveryType = sp2.getString("deliveryType", "");
        if ("1".equals(deliveryType)) {
            tv_delivery_type.setText("物流配送上门");
        } else if ("2".equals(deliveryType)) {
            tv_delivery_type.setText("第三方物流配送");
        }else{
            tv_delivery_type.setText("");
        }

        SharedPreferences sp3 = view.getContext().getSharedPreferences("delivertTimeConfig", view.getContext().MODE_PRIVATE);
        delivertTime = sp3.getString("delivertTime", "");
        String text = "送货时间:";
        if ("1".equals(delivertTime)) {
            tv_delivery_time.setText(text + "不限送货时间");
        } else if ("2".equals(delivertTime)) {
            tv_delivery_time.setText(text + "只周末送货");
        } else if ("3".equals(delivertTime)) {
            tv_delivery_time.setText(text + "只工作日送货");
        }else{
            tv_delivery_time.setText(text);
        }
    }


    private void initInvoiceType(View view) {
        SharedPreferences sp = view.getContext().getSharedPreferences("invoiceType", view.getContext().MODE_PRIVATE);
        invoiceType = sp.getString("invoiceType", "");
        if ("1".equals(invoiceType)) {
            tv_type_invoice.setText("纸质发票");
        } else if ("2".equals(invoiceType)) {
            tv_type_invoice.setText("电子发票");
        } else if ("3".equals(invoiceType)) {
            tv_type_invoice.setText("增值税发票");
        }else{
            tv_type_invoice.setText("");
        }

        SharedPreferences sp2 = view.getContext().getSharedPreferences("belong", view.getContext().MODE_PRIVATE);
        belong = sp2.getString("belong", "");
        if ("1".equals(belong)) {
            tv_belong_invoice.setText("不要发票");
        } else if ("2".equals(belong)) {
            tv_belong_invoice.setText("个人");
        } else if ("3".equals(belong)) {
            tv_belong_invoice.setText("单位");
        }else{
            tv_belong_invoice.setText("");
        }

        SharedPreferences sp3 = view.getContext().getSharedPreferences("radio", view.getContext().MODE_PRIVATE);
        String radio = sp3.getString("radio", "");
        tv_thing_invoice.setText(radio);

    }


    // //初始化网上信息
    private void initAddressAndProductor(final View view) {
        SharedPreferences spcart = view.getContext().getSharedPreferences("car", Context.MODE_APPEND);

        StringBuilder sb = new StringBuilder();

        //------------------------------sqlite
        ShoppingCartItemBean bean = new ShoppingCartItemBean();
        ShoppingCartDao dao = new ShoppingCartDao(getActivity());

        dbList =dao.queryAll();
        if(dbList.size()!=0){
            for(ShoppingCartItemBean dbbean : dbList){

                id = dbbean.getId();
                count = dbbean.getCount();
                isSelect = dbbean.isChecked();
                //spcart.getBoolean(id+"",false)读取sp里的boolean值
                //没勾选则不进行此次拼接
                if(spcart.getBoolean(id+"",false)) {
                    idString = id + ":" + count + ":1,3|";
                    idString = sb.append(idString).toString();
                }else {
                    continue;
                }
            }
        }else{
            int id = getArguments().getInt("pId");
            int count = getArguments().getInt("count");
            idString=id + ":" + count + ":1,3|";
        }


//        Toast.makeText(getActivity(),dbList.size()+"++++++"+idString+"++++++"+isSelect,Toast.LENGTH_LONG).show();
        //------------------------------
//        StringBuilder sb = new StringBuilder();
//        ShoppingCartItemBean bean = new ShoppingCartItemBean();
//        ShoppingCartDao dao = new ShoppingCartDao(getActivity());
//        dbList = dao.queryAll();
//        for (ShoppingCartItemBean dbbean : dbList) {
//
//            id = dbbean.getId();
//            count = dbbean.getCount();
//            idString = id + ":" + count + ":1,3|";
//            idString = sb.append(idString).toString();
//        }
//        Toast.makeText(getActivity(),dbList.size()+"++++++"+idString,Toast.LENGTH_LONG).show();

        RequestParams params3 = new RequestParams();
        // 添加请求头
        params3.addHeader("userid", "20428");

        params3.addBodyParameter("sku", idString);


        mHttpUtils.send(HttpRequest.HttpMethod.POST, UtilsNet.SERVER_URL + "/checkout", params3, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                Class<AccountProductBean> accountProduct = AccountProductBean.class;
                accountProductBean = new Gson().fromJson(json, accountProduct);
                //设置商品adapter
                adapter = new AccountGoodsAdapter(accountProductBean, view.getContext());
                lv_goods_infom.setAdapter(adapter);

                //设置地址
                if (tv_name.getText() == null) {
                    String name = getArguments().getString("k1");
                    Log.i("12121", name);
                    String phoneNumber = getArguments().getString("k2");
                    String deliveryAddress = getArguments().getString("k3");
                    tv_name.setText(name);
                    tv_number.setText(phoneNumber);
                    tv_address.setText(deliveryAddress);
                } else {
                    SharedPreferences addressToAcc = view.getContext().getSharedPreferences("addressToAccName", Context.MODE_PRIVATE);
                    SharedPreferences addressToAccPhone = view.getContext().getSharedPreferences("addressToAccPhone", Context.MODE_PRIVATE);
                    SharedPreferences addressToAccAdd = view.getContext().getSharedPreferences("addressToAccAdd", Context.MODE_PRIVATE);
                    String addressToAccName = addressToAcc.getString("addressToAccName", null);
                    String addressToAccPhone1 = addressToAccPhone.getString("addressToAccPhone", null);
                    String addressToAccAdd1 = addressToAccAdd.getString("addressToAccAdd", null);
                    tv_name.setText(addressToAccName);
                    tv_number.setText(addressToAccPhone1);
                    tv_address.setText(addressToAccAdd1);
                }
                //网上获取地址
//                 AddressInfoBean address = accountProductBean.getAddressInfo();
//                            tv_name.setText(address.getName());
//                            tv_number.setText(address.getPhoneNumber());
//                            String addressDetail = address.getProvince() + address.getCity() + address.getAddressArea()
//                                    + address.getAddressDetail();
//                            tv_address.setText(addressDetail);

                lv_goods_infom.setOnItemClickListener(onItemClickListener);
                //解决listview跟scrollview冲突问题
                setListViewHeight(lv_goods_infom);
            }

            @Override
            public void onFailure(HttpException e, String s) {
            }
        });

    }

    //给adapter设置点击事件
    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProductListAcBean productListAcBean = (ProductListAcBean) adapter.getItem(position);
            ProductBean product = productListAcBean.getProduct();
            Intent intent = new Intent(parent.getContext(), GoodsDetailActivity.class);
            intent.putExtra("pId", product.getId());


            Log.d("1111", product.getId() + "");


            view.getContext().startActivity(intent);
        }
    };

    //点击跳转到相应界面
    @Override
    public void onClick(final View v) {
        switch (v.getId()) {

            case R.id.tv_account_submit:

                //提交携带数据
                RequestParams params4 = new RequestParams();

                // 添加参数到请求头使用addHeader
                // 注意：USER_ID需要改成login接口返回的userId值！！

                params4.addHeader("userid", "20428");
                // post请求添加参数使用addBodyParameter
                params4.addBodyParameter("sku", idString);
                params4.addBodyParameter("addressId", "139");
                //支付方式
                params4.addBodyParameter("paymentType", invoiceType);
                //送货时间
                params4.addBodyParameter("deliveryType", delivertTime);
                //发票类型
                params4.addBodyParameter("invoiceType", belong);
                params4.addBodyParameter("invoiceTitle", "传智慧播客教育科技有限公司");
                params4.addBodyParameter("invoiceContent", "传智慧播客教育科技有限公司");
                mHttpUtils.send(HttpRequest.HttpMethod.POST, UtilsNet.SERVER_URL + "/ordersumbit", params4, new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json = responseInfo.result;
                        Submitbean submitbean = new Gson().fromJson(json, Submitbean.class);
                        String orderId = submitbean.getOrderInfo().getOrderId();
                        int price = submitbean.getOrderInfo().getPrice();
                        String text = (String) tv_money_pay.getText();
//                        int paymentType = submitbean.getOrderInfo().getPaymentType();
                        MainActivity activity3 = (MainActivity) getActivity();
                        activity3.submitOnClick(orderId, price, text);

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                        Toast.makeText(v.getContext(), "请选择送货方式及发票类型", Toast.LENGTH_SHORT).show();

                    }
                });


                break;
            case R.id.rl_address:
                MainActivity activityAddress = (MainActivity) getActivity();
                activityAddress.consigneeAddressOnclick();

                break;
            case R.id.rl_pay:
                MainActivity activity2 = (MainActivity) getActivity();
                activity2.payAndDeliveryType();
                break;
            case R.id.rl_invoice:
                MainActivity activity = (MainActivity) getActivity();
                activity.invoideOnClick();
                break;
        }
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.shoppingCarOnClick();
//            activity.onBackPressed();
        }
    };


    //从服务器获取数据
//    private static final String TAG = "MainActivity";
    // Android模拟器访问电脑地址
//    private static String HOST = "http://10.0.2.2:8080/market";
    // 当前登录的用户ID，根据login接口的响应结果设置
//    private static final String USER_ID = "20428";




    HttpUtils mHttpUtils;

    private void initNetData() {
        mHttpUtils = new HttpUtils();
        // 不缓存服务器响应结果，方便调试。（真实应用里面不要这么做）
        mHttpUtils.configDefaultHttpCacheExpiry(0);
        // 设置http请求超时为3秒
        mHttpUtils.configTimeout(3000);
    }

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     *
     * @param listView
     */
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



}
