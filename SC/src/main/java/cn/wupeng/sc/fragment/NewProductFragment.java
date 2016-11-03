package cn.wupeng.sc.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.NewProductAdapter;
import cn.wupeng.sc.bean.HotProduct;
import cn.wupeng.sc.bean.ProductListBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/7/1.
 */
public class NewProductFragment extends BaseFragment {

    private ListView lv;
    private List<ProductListBean> productList;
    private NewProductAdapter newProductAdapter;


    @Override
    protected void init(View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.textView_title);
        tv_title.setText("新品上架");
        ImageView iv_backToPre = (ImageView) view.findViewById(R.id.backToPre);
        iv_backToPre.setOnClickListener(backTOPreListener);
        lv = (ListView) view.findViewById(R.id.lv_new_product);
        newProductAdapter = new NewProductAdapter(productList);
        lv.setAdapter(newProductAdapter);
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("page","0");
        params.addQueryStringParameter("pageNum", "10");
        params.addQueryStringParameter("orderby","saleDown");
        httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/newproduct"),params,newProductCallBack);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_new_product;
    }

    private RequestCallBack<String> newProductCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            HotProduct hotProduct = gson.fromJson(result, HotProduct.class);
            productList = hotProduct.getProductList();
            newProductAdapter.setProductList(productList);
            newProductAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

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
