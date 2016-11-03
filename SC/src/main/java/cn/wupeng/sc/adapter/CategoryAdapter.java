package cn.wupeng.sc.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.SimpleCategoryBean;

/**
 * Created by heshun on 2016/7/5.
 */
public class CategoryAdapter extends BaseAdapter {

    private List<SimpleCategoryBean> categoryList;
    private int selected;
    private Context context;
    private final Resources resources;

    public List<SimpleCategoryBean> getCategoryList() {
        return categoryList;
    }

    public CategoryAdapter(List<SimpleCategoryBean> categoryList,Context context) {
        this.categoryList = categoryList;
        this.context=context;
        resources = context.getResources();
    }

    public void setCategoryList(List<SimpleCategoryBean> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,null);
            ViewHolder holder=new ViewHolder();
            holder.tv_category= ((TextView) convertView.findViewById(R.id.tv_category));
            convertView.setTag(holder);
        }
        ViewHolder holder= (ViewHolder) convertView.getTag();
        SimpleCategoryBean simpleCategoryBean = categoryList.get(position);

        if (position==selected){
            holder.tv_category.setBackgroundColor(resources.getColor(R.color.colorWhite));
            holder.tv_category.setTextColor(resources.getColor(R.color.colorOragge));
        }else {
            holder.tv_category.setTextColor(resources.getColor(R.color.colorWhite));
            holder.tv_category.setBackgroundColor(resources.getColor(R.color.colorOragge));
        }
        holder.tv_category.setText(simpleCategoryBean.getName());
        return convertView;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    private static class ViewHolder{
        private TextView tv_category;
    }
}
