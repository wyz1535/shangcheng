package cn.wupeng.sc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.GoodsDetailActivity;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.CommonProductAdapter;
import cn.wupeng.sc.bean.HotProduct;
import cn.wupeng.sc.bean.ProductListBean;
import cn.wupeng.sc.utils.UtilsNet;
import me.maxwin.view.XListView;


/**
 * Created by heshun on 2016/6/30.
 */
public class CommonProductListFragment extends BaseFragment {


    private List<ProductListBean> productList=new ArrayList<>();
    private CommonProductAdapter adapter;
    private XListView lv;
    private TextView tv_by_commentDown;
    private TextView tv_by_saleNum;
    private TextView tv_by_salePrice;
    private TextView tv_by_shelvesDown;
    private String keyword;
    private String orderBy="saleDown";
    private View view;
    private Context context;

    @Override
    protected void init(View view) {
//        ImageView backToPre = (ImageView) view.findViewById(R.id.backToPre);
//        backToPre.setOnClickListener(clickBackToPre);
        initView(view);
        this.view=view;
        Bundle bundle = getArguments();
        keyword=bundle.getString("key");
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);

        tvOrderByOnClick();
        context = view.getContext();
        SharedPreferences sp = view.getContext().getSharedPreferences("search", context.MODE_PRIVATE);
        keyword = sp.getString("keywords", null);
        initData(keyword, 0, 10, orderBy);
    }

//    private View.OnClickListener clickBackToPre=new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            MainActivity activity = (MainActivity) getActivity();
//            activity.onBackPressed();
//        }
//    };

    //给排序textView设置点击事件
    private void tvOrderByOnClick() {
        tv_by_commentDown.setOnClickListener(clickOrderBy);
        tv_by_saleNum.setOnClickListener(clickOrderBy);
        tv_by_salePrice.setOnClickListener(clickOrderBy);
        tv_by_shelvesDown.setOnClickListener(clickOrderBy);

    }

    private void initView(View view) {
        lv = (XListView) view.findViewById(R.id.common_product_list_lv);
        tv_by_commentDown = (TextView) view.findViewById(R.id.orderByCommentDown);
        tv_by_saleNum = (TextView) view.findViewById(R.id.orderBySaleNum);
        tv_by_salePrice = (TextView) view.findViewById(R.id.orderBySalePrice);
        tv_by_shelvesDown = (TextView) view.findViewById(R.id.orderByShelvesDown);
        ImageView  iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.onBackPressed();
            }
        });
    }

    //下拉刷新和上拉加载
    private XListView.IXListViewListener xListViewListener=new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {
            lv.stopRefresh();
        }

        @Override
        public void onLoadMore() {
            lv.stopLoadMore();
        }
    };


    private void initData(String keyword, int page, int pageNum, String orderby) {
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("keyword",keyword);
        params.addQueryStringParameter("page",page+"");
        params.addQueryStringParameter("pageNum",pageNum+"");
        params.addQueryStringParameter("orderby", orderby);
        params.addQueryStringParameter("filter","t1-s1-p8");
        httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/search"), params, commonProductCallBack);

    }
    //排序
    private View.OnClickListener clickOrderBy=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String orderby = v.getTag().toString();
            productList.clear();
            initData(keyword, 0, 10, orderby);
        }
    };


    private RequestCallBack<String> commonProductCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            Log.i("test", result);
            HotProduct hotProduct = gson.fromJson(result, HotProduct.class);
            String response = hotProduct.getResponse();
            List<ProductListBean> getDataList = hotProduct.getProductList();
            productList=getDataList;
            adapter = new CommonProductAdapter(productList);
            lv.setAdapter(adapter);
            lv.setXListViewListener(xListViewListener);
            lv.setOnItemClickListener(onItemClickListener);
            productList.addAll(getDataList);
            adapter.setProductList(productList);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProductListBean productListBean = productList.get(position);
            Intent intent = new Intent(view.getContext(), GoodsDetailActivity.class);
            intent.putExtra("pId", productListBean.getId());
            view.getContext().startActivity(intent);
        }
    };
    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_common_product_list;
    }

}
