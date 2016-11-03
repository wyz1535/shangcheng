package cn.wupeng.sc.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.MoreAddressAdapter;
import cn.wupeng.sc.bean.moreAddressBean;
import cn.wupeng.sc.utils.UtilsNet;
import cn.wupeng.sc.utils.UtilsShaPrefe;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public class ConsigneeAddressFragment extends BaseFragment {

    private Button bt_collection_clear;
    private TextView top_head_text;
    private ListView listView;
    HttpUtils mHttpUtils;
    private List<moreAddressBean.AddressListBean> addressList;
    private SharedPreferences sp;
    Handler handler = new Handler();
    private MoreAddressAdapter adapter;
    private String useId;
    View view;
    @Override
    protected void init(View view) {
        this.view=view;
        listView = (ListView) view.findViewById(R.id.lv_address_manage);
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        sp = view.getContext().getSharedPreferences("more_useName", view.getContext().MODE_PRIVATE);
        useId = sp.getString("useId", null);
        click(view);
        if (!TextUtils.isEmpty(useId)) {
            mHttpUtils = new HttpUtils();
            mHttpUtils.configTimeout(3000);
            RequestParams params = new RequestParams();
            params.addHeader("userid", useId);
            mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/addresslist", params, mCallBack);
        }

    }

    moreAddressBean moreAddressBean;
    private RequestCallBack<String> mCallBack = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            moreAddressBean = new Gson().fromJson(result, cn.wupeng.sc.bean.moreAddressBean.class);
            String response = moreAddressBean.getResponse();
            if (response.equals("error")) {
                Toast.makeText(getActivity(), "需要重新登录哦!亲", Toast.LENGTH_SHORT).show();
                return;
            } else {
                addressList = moreAddressBean.getAddressList();
                Log.i("text", addressList.toString());
                listView.setEmptyView(view.findViewById(R.id.empty));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new MoreAddressAdapter(addressList, getActivity());
                        listView.setAdapter(adapter);
                    }
                }, 2000);

                listView.setOnItemClickListener(itemAddressClickListener);
                listView.setOnItemLongClickListener(itemAddressListener);
            }
        }

        //设置地址长按事件
        private AdapterView.OnItemLongClickListener itemAddressListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final moreAddressBean.AddressListBean addressListBean = addressList.get(position);
                Log.i("tttt", addressListBean.getId() + "");
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("你确定要删除吗?");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHttpUtils = new HttpUtils();
                        mHttpUtils.configTimeout(3000);
                        RequestParams params = new RequestParams();
                        params.addHeader("userid", useId);

                        params.addQueryStringParameter("id",addressListBean.getId()+"");
                        mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/addressdelete", params, meCallBack);

                        dialog.dismiss();

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        };

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    private void click(View view) {
        top_head_text.setText("地址列表");
        bt_collection_clear.setText("新增");
        bt_collection_clear.setTextSize(10);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        tv_main_back.setOnClickListener(backListener);
        bt_collection_clear.setOnClickListener(addAddressListener);


    }

    //点击返回键
    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.useCenterOnclick();
        }
    };

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_consignee_address;
    }

    //跳转到新增地址页面
    private View.OnClickListener addAddressListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.addAddressOnclick();

        }
    };

    //设置地址条目点击事件  点击跳转到结算中心并带回数据
    private AdapterView.OnItemClickListener itemAddressClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            cn.wupeng.sc.bean.moreAddressBean.AddressListBean addressListBean = moreAddressBean.getAddressList().get(position);
            String name = addressListBean.getName();
            String phoneNumber = addressListBean.getPhoneNumber();
            String city = addressListBean.getCity();
            String addressArea = addressListBean.getAddressArea();
            String addressDetail = addressListBean.getAddressDetail();
            String deliveryAddress = city + addressArea + addressDetail;
            MainActivity activityAccount = (MainActivity) getActivity();
            activityAccount.addressToAccountOnClick(name, phoneNumber, deliveryAddress);

            UtilsShaPrefe.sharedPreferences("addressToAccName", "addressToAccName", name, parent.getContext());
            UtilsShaPrefe.sharedPreferences("addressToAccPhone", "addressToAccPhone", phoneNumber, parent.getContext());
            UtilsShaPrefe.sharedPreferences("addressToAccAdd", "addressToAccAdd", deliveryAddress, parent.getContext());
        }
    };


    private RequestCallBack<String> meCallBack = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            Log.i("tttt", result);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // adapter.notifyDataSetChanged();
                    if (!TextUtils.isEmpty(useId)) {
                        mHttpUtils = new HttpUtils();
                        mHttpUtils.configTimeout(3000);
                        RequestParams params = new RequestParams();
                        params.addHeader("userid", useId);
                        mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/addresslist", params, mmCallBack);
                    }
                }
            });
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    private RequestCallBack<String> mmCallBack = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            moreAddressBean moreAddressBean = new Gson().fromJson(result, cn.wupeng.sc.bean.moreAddressBean.class);
            String response = moreAddressBean.getResponse();
            if (response.equals("error")) {
                Toast.makeText(getActivity(), "需要重新登录哦!亲", Toast.LENGTH_SHORT).show();
                return;
            } else {
                addressList = moreAddressBean.getAddressList();
                Log.i("text", addressList.toString());
                adapter = new MoreAddressAdapter(addressList, getActivity());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(itemAddressClickListener);
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
}
