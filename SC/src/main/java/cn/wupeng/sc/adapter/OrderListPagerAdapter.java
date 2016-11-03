package cn.wupeng.sc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.orderList.OrderList;
import cn.wupeng.sc.bean.orderList.OrderListBean;

/**
 * Created by admin on 2016/6/30.
 */
public class OrderListPagerAdapter extends BaseAdapter {

    private OrderList orderList;


    public OrderListPagerAdapter() {

    }

    public OrderListPagerAdapter(OrderList orderList) {
        this.orderList=orderList;
    }



    @Override
    public int getCount() {
        return orderList.getOrderList()==null?0:orderList.getOrderList().size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.getOrderList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(parent.getContext(), R.layout.item_order_list_pager,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_order_id= (TextView) convertView.findViewById(R.id.tv_order_id);
            viewHolder.tv_order_amount= (TextView) convertView.findViewById(R.id.tv_order_amount);
            viewHolder.tv_order_state= (TextView) convertView.findViewById(R.id.tv_order_state);
            viewHolder.tv_order_date= (TextView) convertView.findViewById(R.id.tv_order_date);
            viewHolder.tv_order_time= (TextView) convertView.findViewById(R.id.tv_order_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        OrderListBean orderListBean = orderList.getOrderList().get(position);
        viewHolder.tv_order_id.setText(orderListBean.getOrderId());
        viewHolder.tv_order_amount.setText(orderListBean.getPrice()+"");
        viewHolder.tv_order_state.setText(orderListBean.getStatus());
        String timeLong = orderListBean.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(Long.parseLong(timeLong));
        String date = format.substring(0, 10);
        String time = format.substring(11, format.length());
        viewHolder.tv_order_date.setText(date);
        viewHolder.tv_order_time.setText(time);
        return convertView;
    }

    private static class ViewHolder{
        TextView tv_order_id;
        TextView tv_order_amount;
        TextView tv_order_state;
        TextView tv_order_date;
        TextView tv_order_time;
    }


}
