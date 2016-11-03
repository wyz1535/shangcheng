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
import cn.wupeng.sc.bean.more_favoriteBean;

/**
 * Created by Administrator on 2016/7/2 0002.
 */
public class moreFavoriteAdapter extends BaseAdapter {
    List<more_favoriteBean.ProductListBean> productList;
    Context context;
    public moreFavoriteAdapter(List<more_favoriteBean.ProductListBean> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size()== 0 ? 0:productList.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.more_item_favorite, null);
        }
        final ViewHolder holder=ViewHolder.getViewHolder(convertView);
        final more_favoriteBean.ProductListBean productListBean = productList.get(position);
        String HOST = "http://10.0.2.2:8080/market";
        String pic = productListBean.getPic();
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        bitmapUtils.display(holder.moreFavoritePic,HOST+pic);
        holder.moreTextName.setText(productListBean.getName());
        holder.moreTextPrice.setText(productListBean.getPrice() + "");
        holder.moreOriginalPrice.setText(productListBean.getMarketPrice()+"");
        holder.moreUndefined.setText("已有" + (235+position*3)+"人评价");

        return convertView;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        ImageView moreFavoritePic;
        TextView moreTextName;
        TextView moreTextPrice;
        TextView moreOriginalPrice;
        TextView moreUndefined;

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder holder= (ViewHolder) convertView.getTag();
            if (holder==null) {
                holder=new ViewHolder();
                holder.moreFavoritePic= ((ImageView) convertView.findViewById(R.id.more_favorite_pic));
                holder.moreTextName=((TextView) convertView.findViewById(R.id.more_text_name));
                holder.moreTextPrice=((TextView) convertView.findViewById(R.id.more_text_price));
                holder.moreOriginalPrice=((TextView) convertView.findViewById(R.id.more_original_price));
                holder.moreUndefined=((TextView) convertView.findViewById(R.id.more_undefined));
            }
            convertView.setTag(holder);
            return holder;
        }
    }
}
