package cn.wupeng.sc.fragment;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.BrandListPagerAdapter;
import cn.wupeng.sc.bean.BrandBean;
import cn.wupeng.sc.bean.BrandListInfo;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Android on 2016/6/28.
 */
public class BrandFragment extends BaseFragment {

    private static String HOST = UtilsNet.SERVER_URL;
    private String brandId;
    HttpUtils mHttpUtils;
    private ListView lv_brand;
    private BrandListInfo brandListInfo;


    @Override
    protected void init(View view) {
        mHttpUtils = new HttpUtils();
        // 不缓存服务器响应结果，方便调试。（真实应用里面不要这么做）
        mHttpUtils.configDefaultHttpCacheExpiry(0);
        // 设置http请求超时为3秒
        mHttpUtils.configTimeout(3000);

        lv_brand = (ListView) view.findViewById(R.id.lv_brand);
        ImageView iv_back= (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.onBackPressed();
            }
        });
        requestData();

    }



    private AdapterView.OnItemClickListener brandNameClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BrandBean brandBean = brandListInfo.getBrand().get(position);
            String key = brandBean.getKey();
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.CommonOnclick(key);
        }
    };



    public void requestData(){
        RequestParams params = new RequestParams();
        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/brand", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json=responseInfo.result;
                brandListInfo = new Gson().fromJson(json, BrandListInfo.class);
                BrandListPagerAdapter adapter = new BrandListPagerAdapter(brandListInfo);
                lv_brand.setAdapter(adapter);
                lv_brand.setOnItemClickListener(brandNameClickListener);
                //Log.i("brand",json);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_brand;
    }
}
