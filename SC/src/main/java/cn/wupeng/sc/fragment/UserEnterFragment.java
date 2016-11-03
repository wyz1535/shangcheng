package cn.wupeng.sc.fragment;

import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.bean.UseidBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public class UserEnterFragment extends BaseFragment {
    @InjectView(R.id.et_import_account)
    EditText etImportAccount;
    @InjectView(R.id.et_import_password)
    EditText etImportPassword;
    @InjectView(R.id.more_remember_password)
    CheckBox moreRememberPassword;
    @InjectView(R.id.more_enter)
    Button moreEnter;
    @InjectView(R.id.more_immediately_register)
    TextView moreImmediatelyRegister;
    @InjectView(R.id.more_forget_password)
    TextView moreForgetPassword;
    private HttpUtils mHttpUtils;
    // Android模拟器访问电脑地址
    private Button bt_collection_clear;
    private TextView top_head_text;
    Handler handler=new Handler();
    @Override
    protected void init(View view) {
        ButterKnife.inject(this,view);
        mHttpUtils = new HttpUtils();
        mHttpUtils.configTimeout(3000);

        click(view);
    }
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_user_enter;
    }
    private void click(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        top_head_text.setText("登录");
        bt_collection_clear.setVisibility(View.GONE);
        tv_main_back.setOnClickListener(backListener);
        moreImmediatelyRegister.setOnClickListener(registerClickListener);
        moreEnter.setOnClickListener(enterClickListener);

    }
    //点击返回键
    private View.OnClickListener backListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.useCenterOnclick();
        }
    };
    //点击立即注册,跳转到注册界面
    private View.OnClickListener registerClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.userRegistrationOnclick();
        }
    };
    //点击登录
    private View.OnClickListener enterClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String importAccount = etImportAccount.getText().toString();
            String importPassword = etImportPassword.getText().toString();
            if (TextUtils.isEmpty(importAccount) || TextUtils.isEmpty(importPassword)){
                Toast.makeText(v.getContext(), "内容不能为空哦!", Toast.LENGTH_SHORT).show();
                return;
            }

            //dbUtils.findAll("et_shop",)
            RequestParams params = new RequestParams();

            // GET类型请求，使用addQueryStringParameter添加参数
            params.addQueryStringParameter("username", importAccount);
            params.addQueryStringParameter("password", importPassword);

            mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/login", params, mCallBack);

        }
    };
    private RequestCallBack<String> mCallBack = new RequestCallBack<String>(){

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            Log.i("text","result==" + result);
            UseidBean useidBean = new Gson().fromJson(result, UseidBean.class);
            String response = useidBean.getResponse();
            if(response.equals("error")){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "用户名或密码错误哦!", Toast.LENGTH_SHORT).show();
                    }
                });


                return;
            }else{
                UseidBean.UserInfoBean userInfo = useidBean.getUserInfo();
                String userid = userInfo.getUserid();
                SharedPreferences sp = getActivity().getSharedPreferences("more_useName", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("useId", userid);
                edit.commit();
                if (moreRememberPassword.isChecked()) {
                    SharedPreferences  sp1 = getActivity().getSharedPreferences("more_useName", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor edit1 = sp1.edit();
                    edit1.putBoolean("isAutoEnter",true);
                    Log.i("eeee", moreRememberPassword.isChecked()+"");
                    edit1.commit();
                }else {
                    SharedPreferences  sp2 = getActivity().getSharedPreferences("more_useName", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor edit2 = sp2.edit();
                    edit2.putBoolean("isAutoEnter",false);
                    Log.i("eeee", moreRememberPassword.isChecked()+"");
                    edit2.commit();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.useCenterOnclick();
                    }
                });
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {

        }
    };
}
