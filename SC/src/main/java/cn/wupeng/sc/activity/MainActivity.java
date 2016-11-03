package cn.wupeng.sc.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.fragment.AccountFragment;
import cn.wupeng.sc.fragment.AddAddressFragment;
import cn.wupeng.sc.fragment.BaseFragment;
import cn.wupeng.sc.fragment.BehalfDeliveryFragment;
import cn.wupeng.sc.fragment.BehalfEvaluateFragment;
import cn.wupeng.sc.fragment.BehalfPaymentFragment;
import cn.wupeng.sc.fragment.BrandFragment;
import cn.wupeng.sc.fragment.CommonProductListFragment;
import cn.wupeng.sc.fragment.ConsigneeAddressFragment;
import cn.wupeng.sc.fragment.HelpCenterFragment;
import cn.wupeng.sc.fragment.HomeFragment;
import cn.wupeng.sc.fragment.HotProductFragment;
import cn.wupeng.sc.fragment.InvoiceFragment;
import cn.wupeng.sc.fragment.LimitBuyFragment;
import cn.wupeng.sc.fragment.ModeofDistributionFragment;
import cn.wupeng.sc.fragment.MoreAfterSalesServiceFragment;
import cn.wupeng.sc.fragment.MoreFragment;
import cn.wupeng.sc.fragment.MoreSettingFragment;
import cn.wupeng.sc.fragment.MorebalanceFragment;
import cn.wupeng.sc.fragment.NewProductFragment;
import cn.wupeng.sc.fragment.OrderDetailFragment;
import cn.wupeng.sc.fragment.OrderListFragment;
import cn.wupeng.sc.fragment.PayFragment;
import cn.wupeng.sc.fragment.PrivilegeFragment;
import cn.wupeng.sc.fragment.SalesPromotionFragment;
import cn.wupeng.sc.fragment.SearchFragment;
import cn.wupeng.sc.fragment.ShoppingCarFragment;
import cn.wupeng.sc.fragment.SubmitFragment;
import cn.wupeng.sc.fragment.TypeFragment;
import cn.wupeng.sc.fragment.UserCenterFragment;
import cn.wupeng.sc.fragment.UserEnterFragment;
import cn.wupeng.sc.fragment.UserFeedbackFragment;
import cn.wupeng.sc.fragment.UserRegistrationFragment;
import cn.wupeng.sc.fragment.moreFavoriteFragment;


public class MainActivity extends Activity {
    List<BaseFragment> fragments = new ArrayList<>();
    private RadioGroup rg;
    private AccountFragment accountFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg = (RadioGroup) findViewById(R.id.main_radiogroup);
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new BrandFragment());
        fragments.add(new ShoppingCarFragment());
        fragments.add(new MoreFragment());


        rg.setOnCheckedChangeListener(onCheckedChangeListener);
        ((RadioButton) rg.getChildAt(0)).setChecked(true);

        goodsDetailForMainActivity();

    }
    /**
     * 商品详情跳转立即购物携带过来的数据，并传递给购物中心
     */
    private void goodsDetailForMainActivity() {
        Intent intent = getIntent();
        int pId = intent.getIntExtra("pId", -1);
        String count = intent.getStringExtra("count");
        if (pId>=0){
            Log.i("pId", pId + "");
            Log.i("count", count);
            accountFragment = new AccountFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_main, accountFragment).commit();
            Bundle bundle = new Bundle();
            bundle.putInt("pId",pId);
            bundle.putInt("count",Integer.parseInt(count));
            accountFragment.setArguments(bundle);
        }
    }

    FragmentManager fragmentManager = getFragmentManager();
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.home:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(0)).commit();
                    break;
                case R.id.type:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(1)).commit();
                    break;
                case R.id.brand:
                    FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(2));
                    transaction.addToBackStack(null);
                    transaction.commit();

                    break;
                case R.id.shoppingcat:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(3)).commit();
                    break;
                case R.id.more:
                    fragmentManager.beginTransaction().replace(R.id.fragment_main, fragments.get(4)).commit();
                    break;
            }
        }
    };

    //跳转到更多界面
    public void moreFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new MoreFragment()).commit();
    }

    //从首页跳到商品列表
    public void turnToHotProductFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.fragment_main, new HotProductFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void turnToNewProductFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.fragment_main, new NewProductFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    //调到促销快报
    public void turnToSalesPromotionFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.fragment_main, new SalesPromotionFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


    //传递数据到fragment

    public void sendData(String data) {

    }


    //跳转到limitbuyfragment
    public void turnToLimitBuyFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new LimitBuyFragment()).commit();
    }


    //跳转用户中心
    public void useCenterOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new UserCenterFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转到帮组中心
    public void helpCenterOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new HelpCenterFragment()).commit();
    }

    //跳转售后服务页面
    public void moreAfterSalesServiceOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new MoreAfterSalesServiceFragment()).commit();
    }


    //点击进去结算中心 （暂用,不要改代码）
    public void accountOnClick() {
        AccountFragment accountFragment = new AccountFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("key1", "呵呵");
        bundle.putInt("key2", 1553435424);
        bundle.putString("key3", "湖北省武汉市金融港");
        accountFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_main, accountFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //点击计入发票类型界面
    public void invoideOnClick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new InvoiceFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //点击进入支付界面
    public void payAndDeliveryType() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new PayFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //点击进入提交界面

    public void submitOnClick(String orderId, int price, String data) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SubmitFragment submitFragment = new SubmitFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", orderId);
        bundle.putInt("key2", price);
        bundle.putString("key3", data);
        submitFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_main, submitFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //点击进入优惠券界面
    public void privilegeOnClick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new PrivilegeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
//        onBackPressed();
    }

    //跳转到首页
    public void submitToHomeOnClick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new HomeFragment());
        transaction.commit();
    }

    //从地址详情界面跳转到结算中心并带回数据
    public void addressToAccountOnClick(String name, String phoneNumber, String deliveryAddress) {
//        fragmentManager.beginTransaction().replace(R.id.fragment_main,new AccountFragment()).commit();
        AccountFragment accountFragment = new AccountFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("k1", name);
        bundle.putString("k2", phoneNumber);
        bundle.putString("k3", deliveryAddress);
        accountFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_main, accountFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    //跳转到用户反馈
    public void userFeedbackOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new UserFeedbackFragment()).commit();
    }

    //跳转注册界面
    public void userRegistrationOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new UserRegistrationFragment()).commit();
    }

    //跳转登录界面
    public void userEnteronOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new UserEnterFragment()).commit();
    }

    //跳转收货地址界面
    public void consigneeAddressOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new ConsigneeAddressFragment()).commit();
    }

    //跳转新增地址界面
    public void addAddressOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new AddAddressFragment()).commit();

    }

    //跳转代付款界面
    public void behalfPaymentOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new BehalfPaymentFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转搜索界面
    public void SearchOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new SearchFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //跳转搜索结果界面
    public void CommonOnclick(String data) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        CommonProductListFragment commonProductListFragment = new CommonProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", data);
        commonProductListFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_main, commonProductListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转代收货界面
    public void behalfDeliveryOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new BehalfDeliveryFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转代评价界面
    public void behalfEvaluateOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new BehalfEvaluateFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转设置界面
    public void moreSettingOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new MoreSettingFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转我的余额界面
    public void morebalanceOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new MorebalanceFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转配送方式界面
    public void modeofDistributionOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new ModeofDistributionFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //跳转我的收藏界面
    public void moreFavoriteOnclick() {
        fragmentManager.beginTransaction().replace(R.id.fragment_main, new moreFavoriteFragment()).commit();

    }


    //跳转到订单列表界面

    public void myOrderOnclick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new OrderListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void orderDetailOnclick(String data, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", data);
        bundle.putString("TAG", tag);
        orderDetailFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_main, orderDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = this.getSharedPreferences("more_useName", this.MODE_PRIVATE);
        boolean isAutoEnter = sp.getBoolean("isAutoEnter", false);
        String useId = sp.getString("useId", null);
        Log.i("ttttt", isAutoEnter + useId);
        if (!isAutoEnter) {
            SharedPreferences sp1 = this.getSharedPreferences("more_useName", this.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp1.edit();
            edit.putString("useId", null);
            edit.commit();
        }
    }
    public void shoppingCarOnClick() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(R.id.fragment_main, new ShoppingCarFragment());
        transaction.commit();
    }
}
