package cn.wupeng.sc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.GoodsCommentBean;


/**
 * Created by Android on 2016/7/1.
 */
public class CommentAdapter extends BaseAdapter {
    private  List<GoodsCommentBean.CommentBean> comment;

    public CommentAdapter() {
    }

    public CommentAdapter(List<GoodsCommentBean.CommentBean> comment) {
        this.comment=comment;
    }

    @Override
    public int getCount() {
        return comment.size()==0?0:comment.size();
    }

    @Override
    public Object getItem(int position) {
        return comment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (comment!=null) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_pager_comment, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment_content);
                viewHolder.tv_commentTime = (TextView) convertView.findViewById(R.id.tv_commentTime);
                viewHolder.tv_userName = (TextView) convertView.findViewById(R.id.tv_comment_content);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            GoodsCommentBean.CommentBean commentBean = comment.get(position);
            viewHolder.tv_comment.setText(commentBean.getContent());
            viewHolder.tv_userName.setText(commentBean.getUsername());
            viewHolder.tv_commentTime.setText(commentBean.getTime());
        return convertView;
        }else{
            View view = View.inflate(parent.getContext(), R.layout.item_null, null);
            return view;
        }
    }
    static class ViewHolder{
        TextView tv_userName;
        TextView tv_commentTime;
        TextView tv_comment;

    }
}
