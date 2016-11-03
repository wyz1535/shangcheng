package cn.wupeng.sc.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import cn.wupeng.sc.R;
import cn.wupeng.sc.adapter.CommentAdapter;
import cn.wupeng.sc.adapter.GoodsDetailPagerAdapter;
import cn.wupeng.sc.bean.GoodsCommentBean;
import cn.wupeng.sc.bean.GoodsDetailBean;
import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.dao.ShoppingCartDao;
import cn.wupeng.sc.utils.Utils;
import cn.wupeng.sc.utils.UtilsNet;


/**
 * Created by Android on 2016/6/29.
 */
public class GoodsDetailActivity extends Activity implements View.OnClickListener {

    private static final String HOST = UtilsNet.SERVER_URL;
    private ViewPager viewPager;
    private float preposition;
    private View tab;
    private TextView tv_cut;
    private TextView tv_add;
    private TextView tv_goods_count;
    private int pId;
    private HttpUtils httpUtils;
    private RequestParams requestParams;
    private int buyLimit;
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        init();
    }
    /**
     *
     * 初始化
     */
    private void init() {
        Intent intent = getIntent();
        pId = intent.getIntExtra("pId",-1);
        httpUtils = new HttpUtils();
        requestParams = new RequestParams();
        requestParams.addQueryStringParameter("pId", "" + pId);
        requestParams.addQueryStringParameter("page", "0");
        requestParams.addQueryStringParameter("pageNum", "4");
        httpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/product", requestParams, detailCallBack);
        httpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/product/comment", requestParams, commentCallBack);
        ImageView iv_return = (ImageView) findViewById(R.id.iv_return);
        inittab();
        initData();
        RadioGroup goods_detail_radiogroup = (RadioGroup) findViewById(R.id.goods_detail_radiogroup);
        goods_detail_radiogroup.setOnCheckedChangeListener(onCheckedChangeListener);
        iv_return.setOnClickListener(onClickListener);
        getGoodsCount();
    }



    private GoodsDetailBean goodsDetailBean;
    private GoodsDetailBean.ProductBean product;
    /**
     * 获取商品基本信息
     */
    RequestCallBack<String> detailCallBack=new RequestCallBack<String>() {
        private BitmapUtils bitmapUtils;
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String json = responseInfo.result;
            Log.i("json", json);
            goodsDetailBean = new Gson().fromJson(json, GoodsDetailBean.class);
            product = goodsDetailBean.getProduct();
            setData(product);
        }
        private void setData(GoodsDetailBean.ProductBean product) {
            TextView tv_name = (TextView) views.get(0).findViewById(R.id.tv_name);
            TextView tv_goods_trueprice = (TextView) views.get(0).findViewById(R.id.tv_goods_trueprice);
            TextView tv_maketprice = (TextView) views.get(0).findViewById(R.id.tv_maketprice);
            TextView tv_area = (TextView) views.get(0).findViewById(R.id.tv_area);
            TextView tv_commentCount = (TextView) views.get(0).findViewById(R.id.tv_commentCount);
            ImageView iv_area = (ImageView) views.get(0).findViewById(R.id.iv_area);

            String name = product.getName();
            float price = product.getPrice();
//            String pic = product.getPics().get(0);
            float marketPrice = product.getMarketPrice();
            int score = product.getScore();
            String area = product.getInventoryArea();
            buyLimit = product.getBuyLimit();
            int commentCount = product.getCommentCount();
            getStarCount(score);
            tv_name.setText(name);
            tv_goods_trueprice.setText(price+"");
            tv_maketprice.setText(marketPrice+"");
            tv_area.setText(area);
            tv_commentCount.setText("共有"+commentCount+"人评论");

        }


        @Override
        public void onFailure(HttpException e, String s) {
      //  Log.e("test",s);
        }
    };
        private List<GoodsCommentBean.CommentBean> comment;
    /**
     * 获取评论内容
     */
    RequestCallBack<String> commentCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String json = responseInfo.result;
           // Log.i("json",json);
            GoodsCommentBean goodsCommentBean = new Gson().fromJson(json, GoodsCommentBean.class);
            comment = goodsCommentBean.getComment();
            getItem();
        }
        @Override
        public void onFailure(HttpException e, String s) {
       // Log.e("test",s);

        }
    };

    /**
     * return goodsList
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GoodsDetailActivity.this.finish();

        }
    };
    /**
     * 底部buttom的点击
     */
    private ShoppingCartDao dao;
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.customer:
                    Toast.makeText(GoodsDetailActivity.this, "客服", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.shop:
                    Toast.makeText(GoodsDetailActivity.this, "店铺", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.attention:
                    Toast.makeText(GoodsDetailActivity.this, "关注", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.join_shoppingcat:
                    SharedPreferences sp=getSharedPreferences("more_useName", Context.MODE_PRIVATE);
                    String useId = sp.getString("useId", "");
                    if (TextUtils.isEmpty(useId)){
                        Utils.showToast(GoodsDetailActivity.this, "亲！请先登录哦！");
                    }else {


                        String count = tv_goods_count.getText().toString();
                        int id = product.getId();
                        double price = product.getPrice();
                        List<String> pics = product.getPics();
                        String name = product.getName();
                        String pic = pics.get(0);
                        ShoppingCartItemBean shoppingCatBean = new ShoppingCartItemBean(id, name, price, Integer.parseInt(count), pic);
                        //TODO存数据
                        dao = new ShoppingCartDao(GoodsDetailActivity.this);
                        dao.add(shoppingCatBean);
                    }
                    break;
                case R.id.immediately_buy:
                    int pId = product.getId();
                    Intent intent=new Intent(GoodsDetailActivity.this,MainActivity.class);
                   // intent.putExtra("")
                    startActivity(intent);
                    Toast.makeText(GoodsDetailActivity.this, "立即购买", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public static List<View> views;
    public static int[] pagers = {R.layout.pager_goods,R.layout.pager_detail, R.layout.pager_comment};
    /**
     * 初始化商品详情中间的内容
     */
    private void initData() {
        views = new ArrayList<>();
        for (int i = 0; i < pagers.length; i++) {
            View pager = View.inflate(GoodsDetailActivity.this,pagers[i], null);
            views.add(pager);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager_details);
        viewPager.setAdapter(new GoodsDetailPagerAdapter(views));
        viewPager.addOnPageChangeListener(onPagerChangeListener);

    }
    /**
     * 头部的初始化
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void inittab() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int winWidth = outMetrics.widthPixels;
        tab = findViewById(R.id.view_tab);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tab.getLayoutParams();
        params.width = (int) (winWidth * 0.6f / 3);
        tab.setLayoutParams(params);
    }
    /**
     * 页面平滑滑动监听
     */
    ViewPager.OnPageChangeListener onPagerChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, preposition,
                    TranslateAnimation.RELATIVE_TO_SELF, position,
                    TranslateAnimation.RELATIVE_TO_SELF, 0,
                    TranslateAnimation.RELATIVE_TO_SELF, 0);
            animation.setDuration(200);
            animation.setFillAfter(true);
            tab.startAnimation(animation);
            preposition = position;
        }
    };
    /**
     *商品数量的加减
     */
    public void getGoodsCount(){
        tv_cut = (TextView)views.get(0).findViewById(R.id.tv_cut);
        tv_add = (TextView) views.get(0).findViewById(R.id.tv_add);
        tv_goods_count = (TextView) views.get(0).findViewById(R.id.tv_goods_count);
        tv_goods_count.setText("1");
        tv_add.setOnClickListener(GoodsDetailActivity.this);
        tv_cut.setOnClickListener(GoodsDetailActivity.this);
    }
    private int i;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cut:
                Toast.makeText(GoodsDetailActivity.this,"点击—",Toast.LENGTH_SHORT).show();
                String goodsCount = tv_goods_count.getText().toString();
                i=Integer.parseInt(goodsCount);
                if (i>1&&i<=buyLimit){
                    i=i-1;
                tv_goods_count.setText("" + i);
                }

                break;
            case R.id.tv_add:
                Toast.makeText(GoodsDetailActivity.this,"点击+",Toast.LENGTH_SHORT).show();
                String goodsCounts = tv_goods_count.getText().toString();
                i = Integer.parseInt(goodsCounts);
                if(i<buyLimit&&i>=1){
                    i = i +1;
                }else if(i>10){
                    i=buyLimit;
                }
                tv_goods_count.setText(""+i);
                break;
        }
    }
    /**
     * 评分星的加减
     */
    public void getStarCount(int score){
        ImageView iv_star = (ImageView) findViewById(R.id.iv_start);
        ImageView iv_star1 = (ImageView) findViewById(R.id.iv_start1);
        ImageView iv_star2 = (ImageView) findViewById(R.id.iv_start2);
        ImageView iv_star3 = (ImageView) findViewById(R.id.iv_start3);
        ImageView iv_star4 = (ImageView) findViewById(R.id.iv_start4);
         if (score==1) {
            iv_star.setVisibility(View.VISIBLE);
        }else if (score==2){
             iv_star.setVisibility(View.VISIBLE);
             iv_star1.setVisibility(View.VISIBLE);
         }else if(score==3){
             iv_star.setVisibility(View.VISIBLE);
             iv_star1.setVisibility(View.VISIBLE);
             iv_star2.setVisibility(View.VISIBLE);
         }else if(score==4){
             iv_star.setVisibility(View.VISIBLE);
             iv_star1.setVisibility(View.VISIBLE);
             iv_star2.setVisibility(View.VISIBLE);
             iv_star3.setVisibility(View.VISIBLE);
         }else if (score==5){
             iv_star.setVisibility(View.VISIBLE);
             iv_star1.setVisibility(View.VISIBLE);
             iv_star2.setVisibility(View.VISIBLE);
             iv_star3.setVisibility(View.VISIBLE);
             iv_star4.setVisibility(View.VISIBLE);
         }
    }
    /**
     * 加载评论中的listview
     */
    public void getItem(){
            ListView lv_comment = (ListView) views.get(2).findViewById(R.id.lv_comment);
            TextView tv_commentToatalCount = (TextView) views.get(2).findViewById(R.id.tv_commentTotalCount);
            TextView tv_goodsCommentCount = (TextView) views.get(2).findViewById(R.id.tv_goodCommentCount);
        if (comment!=null) {
            tv_commentToatalCount.setText(comment.size()+"");
            tv_goodsCommentCount.setText(comment.size() + "");
            lv_comment.setAdapter(new CommentAdapter(comment));

        }else{
            tv_commentToatalCount.setText(0+"");
            tv_goodsCommentCount.setText(0 + "");

           lv_comment.setAdapter(new CommentAdapter());
        }
    }
}
