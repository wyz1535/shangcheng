package cn.wupeng.sc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.SalesPremotionBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/6/30.
 */
public class SalesPromotionAdapter extends BaseAdapter {

    private List<SalesPremotionBean> topic;


    public SalesPromotionAdapter(List<SalesPremotionBean> topic) {
        this.topic = topic;
    }

    public void setTopic(List<SalesPremotionBean> topic) {
        this.topic = topic;
    }

    @Override
    public int getCount() {
        return topic == null ? 0 : topic.size();
    }

    @Override
    public Object getItem(int position) {
        return topic.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SalesPremotionBean salesPremotionBean = topic.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_sales_promotion, null);
            ViewHolder holder = new ViewHolder();
            holder.tv = ((TextView) convertView.findViewById(R.id.tv_title));
            holder.iv = ((ImageView) convertView.findViewById(R.id.iv_sales_promotion));
            convertView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Picasso.with(parent.getContext()).load(UtilsNet.formatUrl(salesPremotionBean.getPic())).into(holder.iv);
        holder.tv.setText(salesPremotionBean.getName());
        holder.iv.setOnClickListener(onClickListener);
        return convertView;
    }

    private class ViewHolder {
        private TextView tv;
        private ImageView iv;
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.iv_sales_promotion){
                Toast.makeText(v.getContext(),"淡定",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
