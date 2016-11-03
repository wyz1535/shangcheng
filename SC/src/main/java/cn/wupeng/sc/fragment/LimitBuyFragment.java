package cn.wupeng.sc.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.LimitBuyRecyclerViewAdapter;
import cn.wupeng.sc.bean.HotProduct;
import cn.wupeng.sc.bean.ProductListBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/6/29.
 */
public class LimitBuyFragment extends BaseFragment {


    private LimitBuyRecyclerViewAdapter adapter;
    private List<ProductListBean> productList;


    @Override
    protected void init(View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.textView_title);
        tv_title.setText("限时抢购");
        ImageView iv_backToPre = (ImageView) view.findViewById(R.id.backToPre);
        iv_backToPre.setOnClickListener(backTOPreListener);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.host_product_rv);
        rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
        adapter = new LimitBuyRecyclerViewAdapter(productList,view.getContext());
        rv.setAdapter(adapter);
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("page","0");
        params.addQueryStringParameter("pageNum","12");
        httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/limitbuy"),params,limitBuyCallBack);
    }



    private RequestCallBack<String> limitBuyCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            HotProduct hotProduct = gson.fromJson(result, HotProduct.class);
            productList = hotProduct.getProductList();
            adapter.setProductList(productList);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_host_product;
    }

    private View.OnClickListener backTOPreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.backToPre) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onBackPressed();
            }
        }
    };

}
