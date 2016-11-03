package cn.wupeng.sc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.orderDetail.OrderDetail;
import cn.wupeng.sc.bean.orderDetail.ProductListBean;


/**
 * Created by admin on 2016/7/5.
 */
public class ProductInfoListAdapter extends BaseAdapter{
    OrderDetail orderDetail;
    public ProductInfoListAdapter(OrderDetail orderDetail) {
        this.orderDetail=orderDetail;
    }

    @Override
    public int getCount() {
        return orderDetail.getProductList()==null?0:orderDetail.getProductList().size();
    }

    @Override
    public Object getItem(int position) {
        return orderDetail.getProductList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(parent.getContext(), R.layout.item_product_info_list,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            viewHolder.tv_total_number = (TextView) convertView.findViewById(R.id.tv_Total_number);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_freight = (TextView) convertView.findViewById(R.id.tv_freight);
            viewHolder.tv_privilege = (TextView) convertView.findViewById(R.id.tv_privilege);
            viewHolder.tv_should_pay = (TextView) convertView.findViewById(R.id.tv_should_pay);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ProductListBean productListBean = orderDetail.getProductList().get(position);
        viewHolder.tv_goods_name.setText(productListBean.getProduct().getName());
        viewHolder.tv_total_number.setText(orderDetail.getCheckoutAddup().getTotalCount()+"");
        viewHolder.tv_price.setText(productListBean.getProduct().getPrice()+"");
        viewHolder.tv_freight.setText(orderDetail.getCheckoutAddup().getFreight()+"");
        viewHolder.tv_privilege.setText(orderDetail.getProm().get(1));
        viewHolder.tv_should_pay.setText(orderDetail.getCheckoutAddup().getTotalPrice()+"");
        return convertView;
    }

    private static class ViewHolder{
        private TextView tv_goods_name;
        private TextView tv_total_number;
        private TextView tv_price;
        private TextView tv_freight;
        private TextView tv_privilege;
        private TextView tv_should_pay;
    }
}
