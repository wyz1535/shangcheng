package cn.wupeng.sc.adapter;

import android.content.Context;
import android.content.Intent;
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
import cn.wupeng.sc.bean.SimpleCategoryBean;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by heshun on 2016/7/5.
 */
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private List<SimpleCategoryBean> recyclerViewDataList;
    private Context context;

    public CategoryRecyclerViewAdapter(List<SimpleCategoryBean> recyclerViewDataList,Context context) {
        this.recyclerViewDataList = recyclerViewDataList;
        this.context=context;
    }


    public void setRecyclerViewDataList(List<SimpleCategoryBean> recyclerViewDataList) {
        this.recyclerViewDataList = recyclerViewDataList;
    }

    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_recyclerview, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SimpleCategoryBean simpleCategoryBean = recyclerViewDataList.get(position);
        holder.tv_goods.setText(simpleCategoryBean.getName());
        Picasso.with(context).load(UtilsNet.formatUrl(simpleCategoryBean.getPic())).into(holder.iv_goods);
        holder.iv_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailActivity.class);
                intent.putExtra("pId", simpleCategoryBean.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerViewDataList == null ? 0 : recyclerViewDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_goods;
        private TextView tv_goods;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_goods = (ImageView) itemView.findViewById(R.id.iv_goods);
            tv_goods = ((TextView) itemView.findViewById(R.id.tv_goods));
        }
    }
}
