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
import cn.wupeng.sc.bean.orderList.MoreSearchBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Administrator on 2016/7/5 0005.
 */
public class SearchFragment extends BaseFragment {
    HttpUtils mHttpUtils;
    private Button bt_collection_clear;
    private TextView top_head_text;
    private TextView tv_hot_search_1;
    private TextView tv_hot_search_2;
    private TextView tv_hot_search_3;
    private TextView tv_hot_search_4;
    private EditText et_import_keywords;
    private Button bt_keywords;
    @Override
    protected void init(View view) {
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        top_head_text.setText("搜索");
        bt_collection_clear.setVisibility(View.GONE);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        tv_main_back.setOnClickListener(backListener);
        inOn(view);
    }

    private void inOn(View view) {
        tv_hot_search_1 = (TextView) view.findViewById(R.id.tv_hot_search_1);
        tv_hot_search_2 = (TextView) view.findViewById(R.id.tv_hot_search_2);
        tv_hot_search_3 = (TextView) view.findViewById(R.id.tv_hot_search_3);
        tv_hot_search_4 = (TextView) view.findViewById(R.id.tv_hot_search_4);
        et_import_keywords = (EditText) view.findViewById(R.id.et_import_keywords);
        bt_keywords = (Button) view.findViewById(R.id.bt_keywords);
        bt_keywords.setOnClickListener(bt_keywordsClickListener);
        mHttpUtils = new HttpUtils();
        mHttpUtils.configTimeout(3000);
        RequestParams params = new RequestParams();
        mHttpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.SERVER_URL + "/search/recommend", params, mCallBack);
    }

    //点击返回键
    private View.OnClickListener backListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.onBackPressed();
        }
    };
    //点击搜索按钮
    private View.OnClickListener bt_keywordsClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String import_keywords = et_import_keywords.getText().toString().trim();
            if (TextUtils.isEmpty(import_keywords)) {
                Toast.makeText(getActivity(), "搜索内容不能为空哦!亲", Toast.LENGTH_SHORT).show();
            }else {
                SharedPreferences sp= getActivity().getSharedPreferences("search",v.getContext().MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("keywords",import_keywords);
                edit.commit();
                MainActivity activity = (MainActivity) getActivity();
                activity.CommonOnclick(import_keywords);
            }
        }
    };
    private RequestCallBack<String> mCallBack = new RequestCallBack<String>(){

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            MoreSearchBean moreSearchBean = new Gson().fromJson(result, MoreSearchBean.class);
            String response = moreSearchBean.getResponse();
            if (response.equals("error")) {
                Toast.makeText(getActivity(), "需要重新登录哦!亲", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Log.i("test", "result==" + result);
                List<String> searchKeywords = moreSearchBean.getSearchKeywords();
                tv_hot_search_1.setText(searchKeywords.get(0));
                tv_hot_search_2.setText(searchKeywords.get(1));
                tv_hot_search_3.setText(searchKeywords.get(2));
                tv_hot_search_4.setText(searchKeywords.get(3));
                click(searchKeywords);
            }
        }

        private void click(final List<String> searchKeywords) {
            tv_hot_search_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= getActivity().getSharedPreferences("search", v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords", searchKeywords.get(0));
                    edit.commit();
                    MainActivity activity = (MainActivity) getActivity();
                    activity.CommonOnclick(searchKeywords.get(0));
                }
            });
            tv_hot_search_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= getActivity().getSharedPreferences("search", v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords", searchKeywords.get(1));
                    edit.commit();
                    MainActivity activity = (MainActivity) getActivity();
                    activity.CommonOnclick(searchKeywords.get(1));
                }
            });
            tv_hot_search_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= getActivity().getSharedPreferences("search", v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords",searchKeywords.get(2));
                    edit.commit();
                    MainActivity activity = (MainActivity) getActivity();
                    activity.CommonOnclick(searchKeywords.get(2));
                }
            });
            tv_hot_search_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= getActivity().getSharedPreferences("search", v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords",searchKeywords.get(3));
                    edit.commit();
                    MainActivity activity = (MainActivity) getActivity();
                    activity.CommonOnclick(searchKeywords.get(3));
                }
            });
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.activity_search;
    }
}
