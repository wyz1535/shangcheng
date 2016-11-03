package cn.wupeng.sc.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import cn.wupeng.sc.bean.orderList.MoreSearchBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * 搜索界面
 */
public class SearchActivity extends Activity{
    private static String HOST = UtilsNet.SERVER_URL;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        top_head_text = (TextView) findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) findViewById(R.id.bt_collection_clear);
        top_head_text.setText("搜索");
        bt_collection_clear.setVisibility(View.GONE);
        ImageView tv_main_back = (ImageView) findViewById(R.id.tv_main_back);
        tv_main_back.setOnClickListener(backListener);
        init();
    }
    //点击返回键
    private View.OnClickListener backListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SearchActivity.this.finish();
        }
    };
    private void init() {
        tv_hot_search_1 = (TextView) findViewById(R.id.tv_hot_search_1);
        tv_hot_search_2 = (TextView) findViewById(R.id.tv_hot_search_2);
        tv_hot_search_3 = (TextView) findViewById(R.id.tv_hot_search_3);
        tv_hot_search_4 = (TextView) findViewById(R.id.tv_hot_search_4);
        et_import_keywords = (EditText) findViewById(R.id.et_import_keywords);
        bt_keywords = (Button) findViewById(R.id.bt_keywords);
        bt_keywords.setOnClickListener(bt_keywordsClickListener);
        mHttpUtils = new HttpUtils();
        mHttpUtils.configTimeout(3000);
        RequestParams params = new RequestParams();
        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/search/recommend", params, mCallBack);
    }
    //点击搜索按钮
    private View.OnClickListener bt_keywordsClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String import_keywords = et_import_keywords.getText().toString().trim();
            if (TextUtils.isEmpty(import_keywords)) {
                Toast.makeText(SearchActivity.this, "搜索内容不能为空哦!亲", Toast.LENGTH_SHORT).show();
            }else {
                SharedPreferences sp= SearchActivity.this.getSharedPreferences("search",v.getContext().MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("keywords",import_keywords);
                edit.commit();
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
                Toast.makeText(SearchActivity.this, "需要重新登录哦!亲", Toast.LENGTH_SHORT).show();
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
                    SharedPreferences sp= SearchActivity.this.getSharedPreferences("search",v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords", searchKeywords.get(0));
                    edit.commit();
                }
            });
            tv_hot_search_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= SearchActivity.this.getSharedPreferences("search",v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords", searchKeywords.get(1));
                    edit.commit();
                    FragmentManager fragmentManager = getFragmentManager();

                }
            });
            tv_hot_search_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= SearchActivity.this.getSharedPreferences("search",v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords",searchKeywords.get(2));
                    edit.commit();
                }
            });
            tv_hot_search_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp= SearchActivity.this.getSharedPreferences("search",v.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("keywords",searchKeywords.get(3));
                    edit.commit();
                }
            });
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
}
