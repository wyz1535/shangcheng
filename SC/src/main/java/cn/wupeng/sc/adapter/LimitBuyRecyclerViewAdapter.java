package cn.wupeng.sc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.GoodsDetailActivity;
import cn.wupeng.sc.bean.ProductListBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/7/1.
 */
public class LimitBuyRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<ProductListBean> productList;
    private  Context context;


    public LimitBuyRecyclerViewAdapter(List<ProductListBean> productList,Context context) {
        this.productList = productList;
        this.context=context;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_limit_buy, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductListBean productListBean = productList.get(position);
        Picasso.with(context).load(UtilsNet.formatUrl(productListBean.getPic())).into(((ViewHolder) holder).iv);
        ((ViewHolder) holder).tv_goods_name.setText(productListBean.getName());
        ((ViewHolder) holder).tv_old_price.setText(productListBean.getPrice()+"");
        ((ViewHolder) holder).tv_new_price.setText(productListBean.getLimitPrice()+"");
        ((ViewHolder) holder).tv_time.setText(productListBean.getLeftTime()+"");
        ((ViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("pId", productListBean.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_goods_name;
        private final TextView tv_new_price;
        private final TextView tv_old_price;
        private TextView tv_time;
        View itemView;
        public ImageView iv;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv = (ImageView) itemView.findViewById(R.id.iv_food);
            tv_time = ((TextView) itemView.findViewById(R.id.tv_time));
            tv_goods_name = ((TextView) itemView.findViewById(R.id.tv_goods_name));
            tv_new_price = ((TextView) itemView.findViewById(R.id.tv_food_new_price));
            tv_old_price = ((TextView) itemView.findViewById(R.id.tv_food_old_price));
            cardView = (CardView) itemView.findViewById(R.id.limit_buy_cardView);
        }
    }



}
