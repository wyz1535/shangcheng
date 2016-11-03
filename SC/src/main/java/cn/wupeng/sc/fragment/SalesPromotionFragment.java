package cn.wupeng.sc.fragment;

import android.util.Log;
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
import cn.wupeng.sc.adapter.SalesPromotionAdapter;
import cn.wupeng.sc.bean.SalesPremotion;
import cn.wupeng.sc.bean.SalesPremotionBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/6/29.
 */
public class SalesPromotionFragment extends BaseFragment {


    private SalesPromotionAdapter adapter;
    private List<SalesPremotionBean> topic;


    @Override
    protected void init(View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.textView_title);
        tv_title.setText("促销专题");
        ListView lv = (ListView) view.findViewById(R.id.sales_promotion_lv);
        adapter = new SalesPromotionAdapter(topic);
        lv.setAdapter(adapter);
        ImageView iv_backToPre = (ImageView) view.findViewById(R.id.backToPre);
        iv_backToPre.setOnClickListener(backTOPreListener);
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("page","0");
        params.addQueryStringParameter("pageNum","8");
        httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/topic"),params,salesPromotionCallBack);
    }


    private RequestCallBack<String> salesPromotionCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            SalesPremotion salesPremotion = gson.fromJson(result, SalesPremotion.class);
            topic = salesPremotion.getTopic();
            adapter.setTopic(topic);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e("test",e.getMessage());
        }
    };


    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_sales_promotion;
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
