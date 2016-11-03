package cn.wupeng.sc.fragment;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import cn.wupeng.sc.adapter.moreFavoriteAdapter;
import cn.wupeng.sc.bean.more_favoriteBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class moreFavoriteFragment extends BaseFragment {
    HttpUtils mHttpUtils;
    private List<more_favoriteBean.ProductListBean> productList;
    private ListView listView;
    Handler handler=new Handler();
    private Button bt_collection_clear;
    private TextView top_head_text;
    private ImageView empty;


    @Override
    protected void init(View view) {
        listView = (ListView) view.findViewById(R.id.more_favorite);
        empty = (ImageView) view.findViewById(R.id.empty);
        SharedPreferences sp = view.getContext().getSharedPreferences("more_useName", view.getContext().MODE_PRIVATE);
        String useId = sp.getString("useId", null);
        mHttpUtils = new HttpUtils();
        mHttpUtils.configTimeout(3000);
        RequestParams params = new RequestParams();
        params.addHeader("userid", useId);
        params.addQueryStringParameter("page", "0");
        params.addQueryStringParameter("pageNum", "10");
        mHttpUtils.send(HttpRequest.HttpMethod.GET,  UtilsNet.SERVER_URL + "/favorites", params, meCallBack);
        top_head_text = (TextView) view.findViewById(R.id.tv__top_head_text);
        bt_collection_clear = (Button) view.findViewById(R.id.bt_collection_clear);
        ImageView tv_main_back = (ImageView) view.findViewById(R.id.tv_main_back);
        tv_main_back.setOnClickListener(backClickListener);
        bt_collection_clear.setOnClickListener(cleanClickListener);
    }
    //清空按钮
    private View.OnClickListener cleanClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listView.setVisibility(View.GONE);
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
    private RequestCallBack<String> meCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;

            more_favoriteBean more_favoriteBean = new Gson().fromJson(result, cn.wupeng.sc.bean.more_favoriteBean.class);
            String response = more_favoriteBean.getResponse();
            if (response.equals("error")) {
                Toast.makeText(getActivity(), "!亲", Toast.LENGTH_SHORT).show();
                return;
            }else {
                productList = more_favoriteBean.getProductList();
            final moreFavoriteAdapter adapter = new moreFavoriteAdapter(productList, getActivity());
                listView.setEmptyView(empty);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(adapter);
                    }
                },2000);


                Log.i("test", "result--" + result);
                Log.i("test", "result--" + productList.toString());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_more_favorite;
    }
}
