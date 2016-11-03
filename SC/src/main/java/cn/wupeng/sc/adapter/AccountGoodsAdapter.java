package cn.wupeng.sc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.accountBean.AccountProductBean;
import cn.wupeng.sc.bean.accountBean.ProductListAcBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by Administrator on 2016/7/1.
 */
public class AccountGoodsAdapter extends BaseAdapter {

    private AccountProductBean accountProductBean;
    private Context context;
    private List<ProductListAcBean> productList;

    public AccountGoodsAdapter(AccountProductBean accountProductBean, Context context) {
        this.accountProductBean = accountProductBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        productList = accountProductBean.getProductList();
        return productList == null ? 0 : productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_account_goods, null);
        }
        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);

        ProductListAcBean productListAcBean = productList.get(position);
        //商品图片
        String url= UtilsNet.SERVER_URL;
        String picPath = productListAcBean.getProduct().getPic();
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        bitmapUtils.display(viewHolder.iv_goods, url+picPath);

        //设置圆形图片
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.h_7);
//        viewHolder.iv_goods.setImageBitmap(UtilsRound.toRoundBitmap(bitmap));
//        Drawable.createFromStream(new URL(url+picPath).openStream(), "00.png");

        //商品名称
        viewHolder.tv_goods_title.setText(productListAcBean.getProduct().getName());
        //单件商品数量
        int num = productListAcBean.getProdNum();
        viewHolder.tv_goods_total.setText(num + "件");
        //单价
        double aGoodsMOney = productListAcBean.getProduct().getPrice();
        viewHolder.tv_ori_money.setText("¥" + aGoodsMOney);
        //运费
        double deliveryMoney = 0.0;
        int a=position%3;
         if (a == 1) {
            deliveryMoney = 10.0;
            viewHolder.tv_delivery_money.setText("¥" + deliveryMoney);
        } else {
            deliveryMoney = 0.0;
            viewHolder.tv_delivery_money.setText("¥" + deliveryMoney);
        }
        viewHolder.tv_privilege_moeny.setText("¥0.0");
        //应付金额
        double shouleMoney = aGoodsMOney * num + deliveryMoney;
        viewHolder.tv_should_money.setText("¥" + shouleMoney);
        return convertView;
    }

    private static class ViewHolder {
        ImageView iv_goods;
        TextView tv_goods_title;
        TextView tv_goods_total;
        TextView tv_ori_money;
        TextView tv_delivery_money;
        TextView tv_privilege_moeny;
        TextView tv_should_money;

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
                viewHolder.tv_goods_title = (TextView) convertView.findViewById(R.id.tv_goods_title);
                viewHolder.tv_goods_total = (TextView) convertView.findViewById(R.id.tv_goods_total);
                viewHolder.tv_ori_money = (TextView) convertView.findViewById(R.id.tv_ori_money);
                viewHolder.tv_delivery_money = (TextView) convertView.findViewById(R.id.tv_delivery_money);
                viewHolder.tv_privilege_moeny = (TextView) convertView.findViewById(R.id.tv_privilege_money);
                viewHolder.tv_should_money = (TextView) convertView.findViewById(R.id.tv_should_money);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
