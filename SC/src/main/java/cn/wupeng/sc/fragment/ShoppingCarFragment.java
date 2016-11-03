package cn.wupeng.sc.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;
import cn.wupeng.sc.adapter.ShoppingCarAdapter;
import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.dao.ShoppingCartDao;

/**
 * Created by hasee on 2016/6/30.
 */
public class ShoppingCarFragment extends BaseFragment implements ShoppingCarAdapter.OnItemDataClickListener {
    private static final int CHANGE = 0;
    protected Context mContext;
    private int i;
    private static final String TAG = "MainActivity";

    private HttpUtils mHttpUtils;
    private static String HOST = "http://10.0.2.2:8080/market";
    private boolean isEdit = false;
    private TextView tv_edit;
    private TextView tv_finish;
    private ListView lv;
    private List<ShoppingCartItemBean> list;
    private Map<Integer,Boolean> chooseItemMap ;
    private ShoppingCartDao dao;
    private ShoppingCartItemBean scBean;
    private ShoppingCarAdapter adapter;
    private Map<String, Boolean> selectMap;
    private double totalPrice = 0;
    private int jifen = 0;
    private View view;
    private boolean isChecked = false;

    private TextView tv_sum;

    private int sum;
    public TextView sumCount;
    private TextView mResultText;
    //    private CheckBox cb;
    //    private ImageView iv;
    private String picUrl;
    private ImageView ivtest;
    private CheckBox cb_select_all;

    //    private TextView tv_count;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE:

                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private TextView tv_jifen;
    private TextView continueShopping;
    private SharedPreferences sp;


    @Override
    protected void init(View view) {


//        iv = (ImageView) view.findViewById(R.id.iv_shoppingcart_item);
//        tv_count = (TextView) view.findViewById(R.id.tv_goods_count_shoppingcart);
    }

    @Override
    public int getResourcesLayout() {
        return 0;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mHttpUtils = new HttpUtils();

        // 不缓存服务器响应结果，方便调试。（真实应用里面不要这么做）
        mHttpUtils.configDefaultHttpCacheExpiry(0);

        // 设置http请求超时为3秒
        mHttpUtils.configTimeout(3000);

    }
//    public void  getTotalPrice(double totalPrice){
//        this.totalPrice = totalPrice;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shoppingcar, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        sp = getActivity().getSharedPreferences("car", Context.MODE_APPEND);
        chooseItemMap = new HashMap<>();
        ViewUtils.inject(this, view);
        tv_edit = (TextView) view.findViewById(R.id.shoppingcart_tv_edit);
        tv_finish = (TextView) view.findViewById(R.id.shoppingcart_tv_finish);
        continueShopping = (TextView) view.findViewById(R.id.continue_shopping);


//        mResultText = (TextView) view.findViewById(R.id.tv_shoppingcart_test);
        sumCount = (TextView) view.findViewById(R.id.tv_price_sum_count);
        tv_sum = (TextView) view.findViewById(R.id.tv_num_sum_count);
        tv_jifen = (TextView) view.findViewById(R.id.tv_jifen_shopping);
//        sumCount.setText(String.valueOf(totalPrice));


//        cb_select_all = (CheckBox) view.findViewById(R.id.cb_shopping_se_all);
        lv = (ListView) view.findViewById(R.id.shoppingcart_main_lv);
        adapter = new ShoppingCarAdapter(list, getActivity().getBaseContext());
        lv.setAdapter(adapter);
        adapter.setOnItemDataClickListener(this);
        loadData();

//        mResultText.setText("000");
    }


    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao = new ShoppingCartDao(getActivity());
                list = dao.queryAll();
                for (ShoppingCartItemBean shoppingCartItemBean : list) {
                    shoppingCartItemBean.setIsChecked(false);
//                    shoppingCartItemBean.getId();
                }
                adapter.setList(list);

                Message message = new Message();
                message.what = CHANGE;
                handler.sendMessage(message);

                if (list != null) {
                    selectMap = new HashMap<String, Boolean>();
                    for (ShoppingCartItemBean csib : list) {
                        selectMap.put(csib.getName(), false);
                        //Log.i("test", "初始化的isSelect:" + selectMap.get(cartInfo.getProid()));
                    }
                }
//                setTotalprice();

            }
        }).start();
        //--------------------------------------------------------------
//        RequestParams params = new RequestParams();
//
//
//        params.addBodyParameter("sku", "1:3:1,2,3,4|2:2:2,3");
//
//        mHttpUtils.send(HttpRequest.HttpMethod.POST, HOST + "/cart", params, mCallBack);

    }

//    @Override
//    public int getResourcesLayout() {
//        return R.layout.fragment_shoppingcar;
//    }

    @OnClick(R.id.shoppingcart_tv_edit)
    public void edit(View view) {
        if (!isChecked) {
            tv_edit.setText("取消全选");
        } else {
            tv_edit.setText("全选");
        }
        isAllChoose(!isChecked);
        isChecked = !isChecked;


    }
    @OnClick(R.id.continue_shopping)
    public void continueShopping(View v){
        MainActivity mainActivity= (MainActivity) getActivity();
        mainActivity.turnToHotProductFragment();
    }

    /**
     * 结算
     * @param view
     */
    @OnClick(R.id.shoppingcart_tv_finish)
    public void finish(View view) {
        SharedPreferences sp8 = view.getContext().getSharedPreferences("more_useName", view.getContext().MODE_PRIVATE);
        String useId7 = sp8.getString("useId", null);
        if (!TextUtils.isEmpty(useId7)) {
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.accountOnClick();
        }else {
            Toast.makeText(view.getContext(), "亲,请先登录哦", Toast.LENGTH_SHORT).show();
            MainActivity activity4 = (MainActivity) getActivity();
            activity4.userEnteronOnclick();
        }



    }

//    @OnClick(R.id.qqqqqqqq)
//    public void testAddGoods(View view) {
//        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("sku", "1:3:1,2,3,4|2:2:2,3");
//        mHttpUtils.send(HttpRequest.HttpMethod.POST, HOST + "/cart", params, mCallBack);
//    }


    //清空购物车
    @OnClick(R.id.clean_all_cart)
    public void cleanCart(View v) {
        if (list != null) {
            cleanAllGoods();
        }
    }

    ShoppingCartItemBean bean = null;

    private void cleanAllGoods() {
        for (ShoppingCartItemBean sb : list) {
            ShoppingCartItemBean removeB = new ShoppingCartItemBean();
            removeB.setName(sb.getName());
            dao.remove(removeB);

        }
//        list.remove(bean);
        list.clear();
        adapter.notifyDataSetChanged();
        totalPrice = 0;
        jifen = 0;
        sumCount.setText("0.0");
        sum = 0;
        tv_sum.setText(sum+"");

        File file= new File("/data/data/"+getActivity().getPackageName().toString()+"/shared_prefs","car.xml");
        if(file.exists()){
            file.delete();
        }
//        Toast.makeText(getActivity(),getActivity().getPackageName().toString(),Toast.LENGTH_SHORT).show();
    }


    //测试 不用管
//    @OnClick(R.id.sc_add)
    public void addItem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());
        final AlertDialog dialog = builder.create();
        View contentView = View.inflate(getActivity(), R.layout.shoppingcart_add_item,
                null);
        builder.setTitle("添加商品");
        final EditText et_enter_title = (EditText) contentView
                .findViewById(R.id.et_enter_title);
        final EditText et_enter_trueprice = (EditText) contentView
                .findViewById(R.id.et_enter_trueprice);
        final EditText et_enter_oldprice = (EditText) contentView
                .findViewById(R.id.et_enter_oldprice);
        Button bt_confirm = (Button) contentView.findViewById(R.id.bt_confirm);
        Button bt_cancle = (Button) contentView.findViewById(R.id.bt_cancle);
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            private ShoppingCartItemBean scib;

            @Override
            public void onClick(View v) {

                String name = et_enter_title.getText().toString().trim();
                double price = Double.parseDouble(et_enter_trueprice.getText().toString().trim());
                double oldprice = Double.parseDouble(et_enter_oldprice.getText().toString().trim());
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(String.valueOf(price))) {
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(String.valueOf(oldprice))) {
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                } else {
//                    scib = new ShoppingCartItemBean(title, Integer.parseInt(et_enter_trueprice.getText()), Integer.parseInt(et_enter_oldprice.getText()),0);
                    scib = new ShoppingCartItemBean(0, name, price, 1, "");
                    dao.add(scib);
                    list.add(scib);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), name + "+" + price + "+" + oldprice, Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }


            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "取消！！！", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setView(contentView);
        dialog.show();

    }


//
//   @OnClick(R.id.sc_delete)
//    public void sc_delete(View view) {
//        dao.remove(scBean);
//        list.remove(scBean);
//        adapter.notifyDataSetChanged();
//    }

    public void reSetAdapter() {

    }


    private RequestCallBack<String> mCallBack = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            int statusCode = responseInfo.statusCode;
            String result = responseInfo.result;

            Log.d(TAG, "onSuccess: result - " + result);

            if (statusCode == 200) {
                mResultText.setText(result);
            } else {
                mResultText.setText("返回的错误码：" + statusCode);
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            mResultText.setText(msg);
        }
    };

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


    public void isAllChoose(boolean isAllChoose) {
        for (ShoppingCartItemBean scib : list) {
            scib.setIsChecked(isAllChoose);
            selectMap.put(scib.getName(), isAllChoose);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(scib.getId()+"",isAllChoose);
            editor.commit();
        }
        adapter.setList(list);

        adapter.notifyDataSetChanged();
        setTotalprice();
        setTotalNum();
        setJifen();
    }


    //    获得总价格
    private void setTotalprice() {
        totalPrice = 0;

        if (list != null) {
            for (ShoppingCartItemBean csib : list) {
                if (selectMap.get(csib.getName())) {
                    totalPrice = totalPrice + (csib.getPrice() * csib.getCount());
                }
            }
            sumCount.setText(totalPrice + "");

        }
    }

    private void setTotalNum() {
        sum = 0;

        if (list != null) {
            for (ShoppingCartItemBean csib : list) {
                if (selectMap.get(csib.getName())) {
                    sum = sum + csib.getCount();
                }
            }
            tv_sum.setText(sum + "");

        }
    }
    private void setJifen() {
        jifen = 0;

        if (list != null) {
            for (ShoppingCartItemBean csib : list) {
                if (selectMap.get(csib.getName())) {
                    jifen = jifen + csib.getCount()*2;
                }
            }
            tv_jifen.setText(jifen + "");

        }
    }

//    public  void refreshTotalPrice(){
//        mResultText.setText("hhh");
//        loadData();
//    }


    @Override
    public void OnItemCheckBoxClick(ShoppingCartItemBean spcartb) {
        if (spcartb.isChecked()) {
//            sumCount.setText("233");
            totalPrice += spcartb.getPrice() * spcartb.getCount();
            sumCount.setText(totalPrice + "");
            sum += spcartb.getCount();
            tv_sum.setText(sum + "");
            jifen+=spcartb.getCount()*2;
            tv_jifen.setText(jifen + "");
            chooseItemMap.put(spcartb.getId(), true);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(spcartb.getId()+"",true);
            editor.commit();
        } else {
            totalPrice -= spcartb.getPrice() * spcartb.getCount();
            sumCount.setText(totalPrice + "");
            sum -= spcartb.getCount();
            tv_sum.setText(sum + "");
            jifen-=spcartb.getCount()*2;
            tv_jifen.setText(jifen+"");
            chooseItemMap.put(spcartb.getId(),false);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(spcartb.getId() + "", false);
            editor.commit();
        }
//        Toast.makeText(getActivity(),spcartb.getId()+"+"+chooseItemMap.get(spcartb.getId()).toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnCountAddClick(ShoppingCartItemBean spcartb) {
        Toast.makeText(getActivity(), "add", Toast.LENGTH_SHORT).show();

        if (spcartb.getCount() > 0 && spcartb.getCount() < 10) {
            spcartb.setCount(spcartb.getCount() + 1);
            dao.upDate(spcartb);
            if (spcartb.isChecked()) {
                totalPrice += spcartb.getPrice();
                sumCount.setText(totalPrice + "");
                sum++;
                tv_sum.setText(sum + "");
                jifen=jifen+2;
                tv_jifen.setText(jifen+"");
            }
        }

    }

    @Override
    public void OnCountCutClick(ShoppingCartItemBean spcartb) {
        Toast.makeText(getActivity(), "cut", Toast.LENGTH_SHORT).show();
        if (spcartb.getCount() >= 2 && spcartb.getCount() <= 10) {
            spcartb.setCount(spcartb.getCount() - 1);
            dao.upDate(spcartb);
            if (spcartb.isChecked()) {
                totalPrice -= spcartb.getPrice();
                sumCount.setText(totalPrice + "");

                sum--;
                tv_sum.setText(sum + "");

                jifen=jifen-2;
                tv_jifen.setText(jifen+"");
            }
        }

    }

    @Override
    public void OnItemDelete(final ShoppingCartItemBean spcartb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确定删除吗?");
        builder.setMessage("确定删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShoppingCartItemBean removebean = new ShoppingCartItemBean();
                removebean.setName(spcartb.getName());

                dao.remove(removebean);
                list.remove(spcartb);
//                setList(list);

                if (spcartb.isChecked()) {
                    totalPrice -= spcartb.getPrice() * spcartb.getCount();
                    sumCount.setText(totalPrice + "");

                    sum -= spcartb.getCount();
                    tv_sum.setText(sum + "");

                    jifen -= spcartb.getCount()*2;
                    tv_jifen.setText(jifen + "");

                }
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("取消", null);
        builder.show();

    }

}
