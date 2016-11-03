package cn.itcast.wh20.sidepulldelete.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.wh20.sidepulldelete.MainActivity;
import cn.itcast.wh20.sidepulldelete.R;
import cn.itcast.wh20.sidepulldelete.domain.Cheeses;
import cn.itcast.wh20.sidepulldelete.domain.GoodMan;
import cn.itcast.wh20.sidepulldelete.utils.Utils;
import cn.itcast.wh20.sidepulldelete.view.SidePullDeleteLayout;

/**
 * Created by Administrator on 2016/6/26.
 */
public class MyAdapter extends BaseAdapter {
    private List<GoodMan> goodManList=new ArrayList<GoodMan>();
    private SidePullDeleteLayout preSidePullDeleteLayout;//保存上一个拖拽的条目
    List<SidePullDeleteLayout> list=new ArrayList<SidePullDeleteLayout>();
    public MyAdapter() {
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            goodManList.add(new GoodMan(Cheeses.NAMES[i]));
        }
    }

    @Override
    public int getCount() {
        return goodManList==null?0:goodManList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(parent.getContext(),R.layout.items,null);
        }
        final ViewHolder holder=ViewHolder.getViewHolder(convertView);
        //获取好汉数据
        final GoodMan goodMan = goodManList.get(position);
        holder.tv_name.setText(goodMan.getName());
        holder.sidePullDeleteLayout.close(false);
        holder.sidePullDeleteLayout.setOnSidePullChangeListenerl(new SidePullDeleteLayout.OnSidePullChangeListener() {
            @Override
            public void onOpen(SidePullDeleteLayout sidePullDeleteLayout) {
                Utils.showTaost(parent.getContext(), "onOpen");
                list.add(sidePullDeleteLayout);
                preSidePullDeleteLayout=sidePullDeleteLayout;
            }

            @Override
            public void onDragging(SidePullDeleteLayout sidePullDeleteLayout) {
                Utils.showTaost(parent.getContext(),"onDragging");
                if (list!=null&&list.size()>0){
                    for (SidePullDeleteLayout pullDeleteLayout : list) {
                        pullDeleteLayout.close(true);
                    }
                }
            }

            @Override
            public void onClose() {
                Utils.showTaost(parent.getContext(),"onClose");
            }

            @Override
            public void onStartOpen(SidePullDeleteLayout sidePullDeleteLayout) {
                Utils.showTaost(parent.getContext(), "onStartOpen");
                if (list!=null&&list.size()>0){
                    for (SidePullDeleteLayout pullDeleteLayout : list) {
                        pullDeleteLayout.close(true);
                    }
                }
            }
        });
        //点击删除
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation ta=new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_SELF,-1f,
                        Animation.RELATIVE_TO_SELF,0f,
                        Animation.RELATIVE_TO_PARENT,0f

                );
                ta.setDuration(1000);
//                ta.setFillAfter(false);
                holder.sidePullDeleteLayout.startAnimation(ta);
                ta.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        goodManList.remove(goodMan);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        private TextView tv_name;
        private TextView tv_delete;
        private SidePullDeleteLayout sidePullDeleteLayout;
        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder holder= (ViewHolder) convertView.getTag();
            if (holder==null){
                holder=new ViewHolder();
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                holder.tv_delete= (TextView) convertView.findViewById(R.id.tv_delete);
                holder.sidePullDeleteLayout= (SidePullDeleteLayout) convertView.findViewById(R.id.sidePullDeleteLayout);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
