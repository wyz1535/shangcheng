package cn.wupeng.sc.fragment;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class AddAddressFragment extends BaseFragment {
    private Button bt_collection_clear;
    private TextView top_head_text;
    @ViewInject(R.id.et_consignee)
    EditText et_consignee;
    @ViewInject(R.id.et_phone_num)
    EditText et_phone_num;
    @ViewInject(R.id.et_province)
    EditText et_province;
    @ViewInject(R.id.et_account)
    EditText et_account;
    HttpUtils mHttpUtils;
    private SharedPreferences sp;
    private String useId;

    @Override
    protected void init(View view) {
        click(view);
    }

    private void click(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ViewUtils.inject(this, view);
        top_head_text.setText("地址列表");
        bt_collection_clear.setText("保存");
        bt_collection_clear.setTextSize(10);
        sp = view.getContext().getSharedPreferences("more_useName", view.getContext().MODE_PRIVATE);
        useId = sp.getString("useId", null);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        tv_main_back.setOnClickListener(backClickListener);
        bt_collection_clear.setOnClickListener(preserveClickListener);
    }
    //点击返回键
    private View.OnClickListener backClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.consigneeAddressOnclick();
        }
    };
    private View.OnClickListener preserveClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String et_consignee = AddAddressFragment.this.et_consignee.getText().toString();
            String et_phone_num = AddAddressFragment.this.et_phone_num.getText().toString();
            String et_province = AddAddressFragment.this.et_province.getText().toString();
            String et_account = AddAddressFragment.this.et_account.getText().toString();
            if (TextUtils.isEmpty(et_consignee) ||TextUtils.isEmpty(et_phone_num)||TextUtils.isEmpty(et_province)||TextUtils.isEmpty(et_account)){
                Toast.makeText(v.getContext(),"内容不能为空哦!亲",Toast.LENGTH_SHORT).show();
                return;
            }
            //保存并且跳转到地址列表
            if (!TextUtils.isEmpty(useId)) {
                mHttpUtils = new HttpUtils();
                mHttpUtils.configTimeout(3000);
                RequestParams params = new RequestParams();
                params.addHeader("userid", useId);
                // post请求添加参数使用addBodyParameter
                params.addBodyParameter("name",et_consignee);
                params.addBodyParameter("phoneNumber",et_phone_num);
                params.addBodyParameter("province","");
                params.addBodyParameter("city","");
                params.addBodyParameter("addressArea",et_province);
                params.addBodyParameter("addressDetail",et_account);
                params.addBodyParameter("zipCode","430070");
                params.addBodyParameter("isDefault", "0");
                mHttpUtils.send(HttpRequest.HttpMethod.POST,  UtilsNet.SERVER_URL + "/addresssave", params, meCallBack);
                MainActivity activity = (MainActivity) getActivity();
                activity.consigneeAddressOnclick();
            }else {
                Toast.makeText(v.getContext(),"请先登录哦!亲",Toast.LENGTH_SHORT).show();
            }
            //保存并且更新地址列表

        }
    };
    private RequestCallBack<String> meCallBack = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            Log.i("test", "result==" + result);
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_add_address;
    }
}
