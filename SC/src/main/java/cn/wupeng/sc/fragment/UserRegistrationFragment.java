package cn.wupeng.sc.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
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
import cn.wupeng.sc.bean.UserRegistrationBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Administrator on 2016/6/30 0030.
 */
public class UserRegistrationFragment extends BaseFragment {
    @InjectView(R.id.et_account)
    EditText etAccount;
    @InjectView(R.id.cb_sex_man)
    CheckBox cbSexMan;
    @InjectView(R.id.cb_sex_woman)
    CheckBox cbSexWoman;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.et_password_again)
    EditText etPasswordAgain;
    @InjectView(R.id.more_verification_code)
    TextView moreVerificationCode;
    @InjectView(R.id.more_click_verification_code)
    Button moreClickVerificationCode;
    @InjectView(R.id.more_bt_register)
    Button moreBtRegister;
    @InjectView(R.id.more_tv_enter)
    TextView moreTvEnter;
    private Button bt_collection_clear;
    private TextView top_head_text;
    // Android模拟器访问电脑地址
    private HttpUtils mHttpUtils;
    Handler handler=new Handler();
    private EditText et_verification;


    @Override
    protected void init(View view) {
        ButterKnife.inject(this,view);
        mHttpUtils = new HttpUtils();
        mHttpUtils.configTimeout(3000);
        click(view);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_user_registration;
    }
    private void click(View view) {
        moreBtRegister.setOnClickListener(registerClickListener);
        moreTvEnter.setOnClickListener(enterClicxListener);
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        et_verification = (EditText) view.findViewById(R.id.et_verification);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        top_head_text.setText("用户注册");
        moreClickVerificationCode.setOnClickListener(ClickListener);
        bt_collection_clear.setVisibility(View.GONE);
        tv_main_back.setOnClickListener(backClickListener);
    }
    private View.OnClickListener ClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moreClickVerificationCode.setText("cao");
            moreClickVerificationCode.setTextColor(Color.BLACK);
            moreClickVerificationCode.setBackgroundColor(Color.YELLOW);
        }
    };
    //点击返回键
    private View.OnClickListener backClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.useCenterOnclick();
        }
    };
    //点击注册按钮
    private View.OnClickListener registerClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String account = etAccount.getText().toString();
            String password = etPassword.getText().toString();
            String passwordAgain =etPasswordAgain.getText().toString();
            String verification =et_verification.getText().toString();
            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain) || TextUtils.isEmpty(verification)){
                Toast.makeText(v.getContext(),"内容不能为空哦!",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(passwordAgain)) {
                Toast.makeText(v.getContext(),"两次密码输入不一样哦!",Toast.LENGTH_SHORT).show();
                return;
            }
            if (cbSexMan.isChecked() && cbSexWoman.isChecked()) {
                Toast.makeText(v.getContext(),"性别不能多选哦!",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!cbSexMan.isChecked() && !cbSexWoman.isChecked()) {
                Toast.makeText(v.getContext(),"性别不能不选哦!",Toast.LENGTH_SHORT).show();
                return;
            }
            if (!"cao".equals(verification)) {
                Toast.makeText(v.getContext(),"验证码输入错误!",Toast.LENGTH_SHORT).show();
                return;
            }
            RequestParams params = new RequestParams();

            // GET类型请求，使用addQueryStringParameter添加参数
            params.addQueryStringParameter("username", account);
            params.addQueryStringParameter("password", password);
            mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/register", params, mCallBack);

        }
    };
    //点击已有账号,去登录...跳转到登录界面
    private View.OnClickListener enterClicxListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.userEnteronOnclick();
        }
    };
    private RequestCallBack<String> mCallBack = new RequestCallBack<String>(){

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            UserRegistrationBean userRegistrationBean = new Gson().fromJson(result, UserRegistrationBean.class);
            String response = userRegistrationBean.getResponse();
            if (response.equals("error")) {
                Toast.makeText(getActivity(),"用户名已经注册过了哦!亲",Toast.LENGTH_SHORT).show();
                return;
            }
            MainActivity activity = (MainActivity) getActivity();
            activity.userEnteronOnclick();
        }

        @Override
        public void onFailure(HttpException error, String msg) {

        }
    };
}
