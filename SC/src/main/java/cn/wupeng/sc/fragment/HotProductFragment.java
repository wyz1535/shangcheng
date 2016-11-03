package cn.wupeng.sc.fragment;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import cn.wupeng.sc.adapter.RecyclerViewAdapter;
import cn.wupeng.sc.bean.HotProduct;
import cn.wupeng.sc.bean.ProductListBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/6/29.
 */
public class HotProductFragment extends BaseFragment {
    private List<ProductListBean> hotProducts;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    private LinearLayoutManager linearLayoutManager;




    @Override
    protected void init(View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.textView_title);
        tv_title.setText("热门单品");
        ImageView iv_backToPre = (ImageView) view.findViewById(R.id.backToPre);
        iv_backToPre.setOnClickListener(backTOPreListener);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.host_product_srl);
        srl.setOnRefreshListener(onRefreshListener);

        rv = (RecyclerView) view.findViewById(R.id.host_product_rv);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3, GridLayoutManager.VERTICAL, false));
        adapter = new RecyclerViewAdapter(view.getContext());
        rv.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", "1");
        params.addQueryStringParameter("pageNum", "10");
        params.addQueryStringParameter("orderby", "saleDown");
        httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/hotproduct"), params, hotProductCallBack);
        rv.setOnScrollListener(scrollListener);
        initData("1", "12", "saleDown");
    }


    private int lastVisibleItem;
    private RecyclerView.OnScrollListener scrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==adapter.getItemCount()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       initData(page+"","12","saleDown");
                    }
                },2000);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
        }
    };

    private static int page=0;
    private void initData(String s, String s1, String orderby) {

            page++;
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("page", s);
            params.addQueryStringParameter("pageNum", s1);
            params.addQueryStringParameter("orderby", orderby);
            httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/hotproduct"), params, hotProductCallBack);
    }

    private RequestCallBack<String> hotProductCallBack = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            HotProduct hotProduct = gson.fromJson(result, HotProduct.class);
            if (hotProducts!=null&&hotProducts.size()>0){
                List<ProductListBean> productListBeanList=hotProduct.getProductList();
                hotProducts.addAll(productListBeanList);
            }else{
                hotProducts = hotProduct.getProductList();
            }
            adapter.setHotProducts(hotProducts);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e("test", e.getMessage());
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    srl.setRefreshing(false);
                }
            },2000);
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
