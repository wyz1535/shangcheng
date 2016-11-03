package cn.wupeng.sc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.wupeng.sc.R;
import cn.wupeng.sc.fragment.CommonProductListFragment;
import cn.wupeng.sc.fragment.HotProductFragment;
import cn.wupeng.sc.fragment.LimitBuyFragment;
import cn.wupeng.sc.fragment.SalesPromotionFragment;

/**
 * Created by heshun on 2016/6/30.
 */
public class ProductListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_product_list);


    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.limit_buy:
                getFragmentManager().beginTransaction().replace(R.id.fl, new LimitBuyFragment()).commit();
                break;
            case R.id.sales_promotion:
                getFragmentManager().beginTransaction().replace(R.id.fl, new SalesPromotionFragment()).commit();
                break;
            case R.id.hot_product:
                getFragmentManager().beginTransaction().replace(R.id.fl, new HotProductFragment()).commit();
                break;
            case R.id.common_product:
                getFragmentManager().beginTransaction().replace(R.id.fl, new CommonProductListFragment()).commit();
                break;
        }
    }
}
