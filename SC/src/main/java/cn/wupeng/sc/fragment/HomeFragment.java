package cn.wupeng.sc.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.GoodsDetailActivity;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.bean.GoodsDetailBean;
import cn.wupeng.sc.bean.HotProduct;
import cn.wupeng.sc.bean.ProductListBean;
import cn.wupeng.sc.bean.TitleBean;
import cn.wupeng.sc.constant.Canstant;
import cn.wupeng.sc.utils.UtilsNet;
import cn.wupeng.sc.view.AutoRollLayout;
import cn.wupeng.sc.view.RollItem;


//import itcast.lib_arl.AutoRollLayout;

/**
 * Created by Android on 2016/6/28.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final int SET_PIG = 3;
    private ImageView iv_sellInTime;
    private ImageView iv_promptionNews;
    private ImageView iv_newGoods;
    private ImageView iv_newProduct;
    private ImageView iv_popularSingle;
    private TitleBean titleBean;
    private TextView pmdType;
    private TextView pmdTitle;
    private AutoRollLayout arl;
    private List<RollItem> items;
    private String title1;
    private String type1;
    private String title2;
    private String type2;
    private String title;
    private String type;
    private static final int CHANGE_SEC = 0;
    private static final int CHANGE_MIN = 1;
    private static final int CHANGE_HOUR = 2;
    private TextView tvhour;
    private TextView tvmin;
    private TextView tvsec;
    private int hour;
    private int min;
    private int sec;
    private ImageView iv_detail1;
    private ImageView iv_detail2;
    private ImageView iv_detail3;
    private ImageView iv_detail4;
    private ImageView iv_detail5;
    private ImageView iv_detail6;
    private GoodsDetailBean detailBean;
    private TextView tv_detail1;
    private TextView tv_detail2;
    private TextView tv_detail3;
    private TextView tv_detail4;
    private TextView tv_detail5;
    private TextView tv_detail6;
    private TextView tv_type1;
    private TextView tv_type2;
    private TextView tv_type3;
    private TextView tv_type4;
    private TextView tv_type5;
    private TextView tv_type6;
    private String name1;
    private String pic1;
    private String name2;
    private String pic2;
    private String name3;
    private String pic3;
    private String name4;
    private String pic4;
    private String name5;
    private String pic5;
    private String name6;
    private String pic6;
    private TextView tvSearch;
    private View rl_search;
    private View view;


    protected void init(View view) {
        this.view=view;
        iv_sellInTime = (ImageView) view.findViewById(R.id.iv_sellInTime);
        iv_promptionNews = (ImageView) view.findViewById(R.id.iv_promptionNews);
        iv_newProduct = (ImageView) view.findViewById(R.id.iv_newProduct);
        iv_popularSingle = (ImageView) view.findViewById(R.id.iv_popularSingle);
        rl_search = view.findViewById(R.id.rl_search);
        pmdType = (TextView) view.findViewById(R.id.tv_pmdType);
        pmdTitle = (TextView) view.findViewById(R.id.tv_pmdTitle);
//        (TextView)view.findViewById(R.id.tv_djs);
        iv_detail1 = (ImageView) view.findViewById(R.id.iv_detail1);
        iv_detail2 = (ImageView) view.findViewById(R.id.iv_detail2);
        iv_detail3 = (ImageView) view.findViewById(R.id.iv_detail3);
        iv_detail4 = (ImageView) view.findViewById(R.id.iv_detail4);
        iv_detail5 = (ImageView) view.findViewById(R.id.iv_detail5);
        iv_detail6 = (ImageView) view.findViewById(R.id.iv_detail6);

        tv_detail1 = (TextView) view.findViewById(R.id.tv_detail1);
        tv_detail2 = (TextView) view.findViewById(R.id.tv_detail2);
        tv_detail3 = (TextView) view.findViewById(R.id.tv_detail3);
        tv_detail4 = (TextView) view.findViewById(R.id.tv_detail4);
        tv_detail5 = (TextView) view.findViewById(R.id.tv_detail5);
        tv_detail6 = (TextView) view.findViewById(R.id.tv_detail6);

        tv_type1 = (TextView) view.findViewById(R.id.tv_type1);
        tv_type2 = (TextView) view.findViewById(R.id.tv_type2);
        tv_type3 = (TextView) view.findViewById(R.id.tv_type3);
        tv_type4 = (TextView) view.findViewById(R.id.tv_type4);
        tv_type5 = (TextView) view.findViewById(R.id.tv_type5);
        tv_type6 = (TextView) view.findViewById(R.id.tv_type6);

        arl = (AutoRollLayout) view.findViewById(R.id.arl_home_arl);

        tvhour = (TextView) view.findViewById(R.id.tv_hour);
        tvmin = (TextView) view.findViewById(R.id.tv_min);
        tvsec = (TextView) view.findViewById(R.id.tv_sec);
        tvSearch = (TextView) view.findViewById(R.id.tv_search);

        items = new ArrayList<>();

        items.add(new RollItem(" ", Canstant.HOST + "/images/home/topic01.jpg"));
        items.add(new RollItem(" ", Canstant.HOST + "/images/home/topic02.jpg"));
        items.add(new RollItem(" ", Canstant.HOST + "/images/home/topic03.jpg"));
        arl.setItems(items);
        click();
        getTitle();
        loadPic();
        TimeCaculator();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setpic(final ImageView iv, final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = setImage(path);

                iv.post(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bitmap);
                    }
                });

            }
        }).start();
    }

    private void TimeCaculator() {
        hour = 99;
        min = 59;
        sec = 59;
        tvhour.setText(hour + "");
        tvmin.setText(min + "");
        tvsec.setText(sec + "");
        dec();
    }

    private void click() {
        iv_sellInTime.setOnClickListener(this);
        iv_promptionNews.setOnClickListener(this);
        iv_newProduct.setOnClickListener(this);
        iv_popularSingle.setOnClickListener(this);
        rl_search.setOnClickListener(this);
        iv_detail1.setOnClickListener(this);
        iv_detail2.setOnClickListener(this);
        iv_detail3.setOnClickListener(this);
        iv_detail4.setOnClickListener(this);
        iv_detail5.setOnClickListener(this);
        iv_detail6.setOnClickListener(this);
        final MainActivity activity = (MainActivity) getActivity();

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.SearchOnclick();
            }
        });
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.SearchOnclick();
            }
        });
    }

    @Override
    public int getResourcesLayout() {

        return R.layout.fragment_home;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sellInTime:
                MainActivity activity = (MainActivity) getActivity();
                activity.turnToLimitBuyFragment();
                break;
            case R.id.iv_promptionNews:
                MainActivity activity1 = (MainActivity) getActivity();
                activity1.turnToSalesPromotionFragment();
                break;
            case R.id.iv_newProduct:
                MainActivity activity2 = (MainActivity) getActivity();
                activity2.turnToNewProductFragment();
                break;
            case R.id.iv_popularSingle:
                MainActivity activity3 = (MainActivity) getActivity();
                activity3.turnToHotProductFragment();
                break;
            case R.id.iv_detail1:
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pId",listId.get(0));
                startActivity(intent);
                break;
            case R.id.iv_detail2:
                Intent intent2 = new Intent(getActivity(), GoodsDetailActivity.class);
                intent2.putExtra("pId",listId.get(1));
                startActivity(intent2);
                break;
            case R.id.iv_detail3:
                Intent intent3 = new Intent(getActivity(), GoodsDetailActivity.class);
                intent3.putExtra("pId",listId.get(2));
                startActivity(intent3);
                break;
            case R.id.iv_detail4:
                Intent intent4 = new Intent(getActivity(), GoodsDetailActivity.class);
                intent4.putExtra("pId",listId.get(3));
                startActivity(intent4);
                break;
            case R.id.iv_detail5:
                Intent intent5 = new Intent(getActivity(), GoodsDetailActivity.class);
                intent5.putExtra("pId",listId.get(4));
                startActivity(intent5);
                break;
            case R.id.iv_detail6:
                Intent intent6 = new Intent(getActivity(), GoodsDetailActivity.class);
                intent6.putExtra("pId",listId.get(5));
                startActivity(intent6);
                break;
        }
    }

    HttpUtils mHttpUtils;

    private static String HOST = UtilsNet.SERVER_URL;


    public void getTitle() {
        mHttpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        mHttpUtils.send(HttpRequest.HttpMethod.GET, HOST + "/title", params, mcallback);
    }

    private RequestCallBack<String> mcallback = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            Log.i("test", "我打印出来了" + result);
            detailBean = new Gson().fromJson(result, GoodsDetailBean.class);

            titleBean = new Gson().fromJson(result, TitleBean.class);

            List<TitleBean.TitlesBean> titlesBeans = titleBean.getTitle();

            for (int i = 0; i < titlesBeans.size(); i++) {
                title = titlesBeans.get(0).getTitle();
                type = titlesBeans.get(0).getType();

                title1 = titlesBeans.get(1).getTitle();
                type1 = titlesBeans.get(1).getType();

                title2 = titlesBeans.get(2).getTitle();
                type2 = titlesBeans.get(2).getType();

            }
            String titles = title + title1 + title2;
            String types = type + type1 + type2;
            pmdType.setText(types);
            pmdTitle.setText(titles);
          
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
    private void loadPic() {
        mHttpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("page", "1");
        params.addQueryStringParameter("pageNum", "10");
        params.addQueryStringParameter("orderby", "priceUp");
        mHttpUtils.send(HttpRequest.HttpMethod.GET, Canstant.HOST + "/hotproduct", params, loadPicCallBack);
    }

    List<String> listName = new ArrayList<>();
    List<String> listPic = new ArrayList<>();
    List<Float> listprice = new ArrayList<>();
    List<Integer> listId = new ArrayList<>();
    RequestCallBack<String> loadPicCallBack = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String json = responseInfo.result;
            HotProduct hotProduct = new Gson().fromJson(json, HotProduct.class);
            Log.i("test", json);
            List<ProductListBean> productList = hotProduct.getProductList();
            for (int i = 0; i < productList.size(); i++) {
                String name = productList.get(i).getName();
                listName.add(name);
                String pic = productList.get(i).getPic();
                listPic.add(pic);
                float price = productList.get(i).getPrice();
                listprice.add(price);
                int id = productList.get(i).getId();
                listId.add(id);
            }
            setPicture(listName, listprice, listPic);
        }

        private void setPicture(List<String> listName, List<Float> listprice, List<String> listPic) {
            Picasso.with(view.getContext()).load(UtilsNet.formatUrl(listPic.get(0))).into(iv_detail1);
            Picasso.with(view.getContext()).load(UtilsNet.formatUrl(listPic.get(1))).into(iv_detail2);
            Picasso.with(view.getContext()).load(UtilsNet.formatUrl(listPic.get(2))).into(iv_detail3);
            Picasso.with(view.getContext()).load(UtilsNet.formatUrl(listPic.get(3))).into(iv_detail4);
            Picasso.with(view.getContext()).load(UtilsNet.formatUrl(listPic.get(4))).into(iv_detail5);
            Picasso.with(view.getContext()).load(UtilsNet.formatUrl(listPic.get(5))).into(iv_detail6);

            tv_detail1.setText(listName.get(0));
            tv_detail2.setText(listName.get(1));
            tv_detail3.setText(listName.get(2));
            tv_detail4.setText(listName.get(3));
            tv_detail5.setText(listName.get(4));
            tv_detail6.setText(listName.get(5));

            tv_type1.setText(listprice.get(0) + "");
            tv_type2.setText(listprice.get(1) + "");
            tv_type3.setText(listprice.get(2) + "");
            tv_type4.setText(listprice.get(3) + "");
            tv_type5.setText(listprice.get(4) + "");
            tv_type6.setText(listprice.get(5) + "");
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e("exception", s);
        }
    };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_SEC:
                    addZeroSec(sec);
                    sec = sec - 1;
//                    Log.i("test", "sec" + sec + "");
                    if (sec == 0) {
                        min = min - 1;
                        addZeroMin(min);
//                        message.what = CHANGE_MIN;
//                        handler.sendMessage(message);
                        sec = 60;

                    }
                    if (min == 0) {
                        hour = hour - 1;
                        addZeroHour(hour);
//                        message.what = CHANGE_HOUR;
//                        handler.sendMessage(message);
                        min = 60;

                    }
                    if (hour == 0 && min == 0 && sec == 0) {
//                        Toast.makeText(MainActivity.this, "计时结束", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    break;
                case SET_PIG:
                    setpic(iv_detail1, pic1);
                    setpic(iv_detail2, pic2);
                    setpic(iv_detail3, pic3);
                    setpic(iv_detail4, pic4);
                    setpic(iv_detail5, pic5);
                    setpic(iv_detail6, pic6);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Bitmap bitmap = setImage("http://10.0.2.2:8080/market/images/home/image1.jpg");
//
//                            iv_detail1.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    iv_detail1.setImageBitmap(bitmap);
//                                }
//                            });
//
//                        }
//                    }).start();
                    break;

            }
        }
    };


    private void dec() {
        handler.postDelayed(downTimeRunnable, 1000);
    }


    private Runnable downTimeRunnable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(this);
            Message message = Message.obtain();
            message.what = CHANGE_SEC;
            handler.sendMessage(message);
            handler.postDelayed(this, 1000);
        }
    };

    private void addZeroSec(int t) {
        if (t < 10) {
            tvsec.setText("0" + t + "");
        } else {
            tvsec.setText(t + "");
        }
    }

    private void addZeroMin(int t) {
        if (t < 10) {
            tvmin.setText("0" + t + "");
        } else {
            tvmin.setText(t + "");
        }
    }

    private void addZeroHour(int t) {
        if (t < 10) {
            tvhour.setText("0" + t + "");
        } else {
            tvhour.setText(t + "");
        }
    }

    public Bitmap setImage(String path) {
        Bitmap bm = null;
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            bm = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
        }
        return bm;
    }

    @Override
    public void onPause() {
        super.onPause();

    }


}
