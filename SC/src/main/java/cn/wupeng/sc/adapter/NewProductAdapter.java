package cn.wupeng.sc.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class NewProductAdapter extends BaseAdapter {

    private List<ProductListBean> productList;

    public NewProductAdapter(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ProductListBean productListBean = productList.get(position);
        if (convertView==null){
            convertView=View.inflate(parent.getContext(), R.layout.item_new_product,null);
            ViewHolder holder=new ViewHolder();
            holder.iv_food= (ImageView) convertView.findViewById(R.id.iv_food);
            holder.tv_goods_name= (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.tv_goods_price= (TextView) convertView.findViewById(R.id.tv_goods_price);
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tv_goods_price.setText(productListBean.getPrice()+"");
        holder.tv_goods_name.setText(productListBean.getName());
        Picasso.with(parent.getContext()).load(UtilsNet.formatUrl(productListBean.getPic())).into(holder.iv_food);
        holder.iv_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), GoodsDetailActivity.class);
                intent.putExtra("pId", productListBean.getId());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder{
        private ImageView iv_food;
        private TextView tv_goods_name;
        private TextView tv_goods_price;
    }
}
