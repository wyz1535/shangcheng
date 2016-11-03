package cn.wupeng.sc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.BrandBean;
import cn.wupeng.sc.bean.BrandListInfo;

/**
 * Created by admin on 2016/6/30.
 */
public class BrandListPagerAdapter extends BaseAdapter {
    private BrandListInfo brandListInfo;
    public BrandListPagerAdapter(BrandListInfo brandListInfo) {
        this.brandListInfo=brandListInfo;
    }

    @Override
    public int getCount() {
        return brandListInfo.getBrand()==null?0:brandListInfo.getBrand().size();
    }

    @Override
    public Object getItem(int position) {
        return brandListInfo.getBrand().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(parent.getContext(), R.layout.item_brand_list,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_brand= (TextView) convertView.findViewById(R.id.tv_brand);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        BrandBean brandBean = brandListInfo.getBrand().get(position);
        viewHolder.tv_brand.setText(brandBean.getKey());
        return convertView;
    }

    private static class ViewHolder{
        TextView tv_brand;
    }
}
