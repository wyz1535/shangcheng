package cn.wupeng.sc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
 * Created by heshun on 2016/6/30.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ProductListBean> hotProducts;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setHotProducts(List<ProductListBean> hotProducts) {
        this.hotProducts = hotProducts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_host_product, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

            final ProductListBean productListBean = hotProducts.get(position);
            viewHolder.tv_food_name.setText(productListBean.getName());
            viewHolder.tv_goods_price.setText(productListBean.getPrice() + "");
            Picasso.with(context).load(UtilsNet.formatUrl(productListBean.getPic())).into(viewHolder.iv);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //goodsdetial
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra("pId", productListBean.getId());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return hotProducts == null ? 0 : hotProducts.size() ;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        public ImageView iv;
        public TextView tv_food_name;
        public TextView tv_goods_price;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv = (ImageView) itemView.findViewById(R.id.iv_food);
            tv_food_name = (TextView) itemView.findViewById(R.id.tv_food_name);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_price);
            cardView = (CardView) itemView.findViewById(R.id.cardView_host_product);
        }
    }

}
