package cn.wupeng.sc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.dao.ShoppingCartDao;
import cn.wupeng.sc.fragment.ShoppingCarFragment;
import cn.wupeng.sc.utils.UtilsNet;

/**
 * Created by hasee on 2016/7/3.
 */
public class ShoppingCarAdapter extends BaseAdapter {

    private double totalPrice = 1;

    private Context context;

    //private ShoppingCarFragment shoppingCarFragment;
    private List<ShoppingCartItemBean> list;
    private ViewHolder viewHolder;
    //    private TextView tvSumPrice;

    public void setList(List<ShoppingCartItemBean> list) {
        this.list = list;
    }

    private static ShoppingCarFragment sf = new ShoppingCarFragment();

    public ShoppingCarAdapter(List<ShoppingCartItemBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //    public void  getView(){
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        LinearLayout linearLayout = (LinearLayout)layoutInflater.inflate(R.layout.fragment_shoppingcar,null);
//        tvSumPrice = (TextView) linearLayout.findViewById(R.id.tv_price_sum_count);
//    }
    public interface OnItemDataClickListener {

      public  void OnItemCheckBoxClick(ShoppingCartItemBean spcartb);
      public  void OnCountAddClick(ShoppingCartItemBean spcartb);
      public  void OnCountCutClick(ShoppingCartItemBean spcartb);
        public void OnItemDelete(ShoppingCartItemBean spcartb);
    }

    private OnItemDataClickListener mlistener;

    public void setOnItemDataClickListener(OnItemDataClickListener mlistener) {
        this.mlistener = mlistener;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_shoppingcart, null);
            viewHolder = new ViewHolder();

            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_shoppingcart_item_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_shoppingcart_item_true_price);
            viewHolder.oldprice = (TextView) convertView.findViewById(R.id.tv_shoppingcart_item_old_price);
            viewHolder.count = (TextView) convertView.findViewById(R.id.tv_goods_count_shoppingcart);
            viewHolder.cb = (ImageView) convertView.findViewById(R.id.cb_item_shoppingcart);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_shoppingcart_item);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.sc_delete);
            viewHolder.add = (TextView) convertView.findViewById(R.id.tv_add);
            viewHolder.cut = (TextView) convertView.findViewById(R.id.tv_cut);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ShoppingCartItemBean scbean = list.get(position);
//        viewHolder.tv_sum.setText("1");
        viewHolder.name.setText(scbean.getName());
        viewHolder.price.setText(String.valueOf(scbean.getPrice()));
        viewHolder.oldprice.setText(String.valueOf(scbean.getPrice() + 20));
        viewHolder.count.setText(String.valueOf(scbean.getCount()));



        Picasso.with(parent.getContext()).load(UtilsNet.formatUrl(scbean.getPic())).into(viewHolder.iv);
        final ShoppingCartDao dao = new ShoppingCartDao(parent.getContext());



//
//        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
//                builder.setTitle("确定删除吗?");
//                builder.setMessage("确定删除吗？");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ShoppingCartItemBean removebean = new ShoppingCartItemBean();
//                        removebean.setName(scbean.getName());
//
//                        dao.remove(removebean);
//                        list.remove(scbean);
//                        setList(list);
//                        notifyDataSetChanged();
//                    }
//                });
//                builder.setNegativeButton("取消", null);
//                builder.show();
//            }
//        });


//        final ViewHolder finalViewHolder = viewHolder;
//        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            //设置checkbox的点击事件
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                ShoppingCartItemBean bean = new ShoppingCartItemBean();
//                bean.getCount();
//                bean.getPrice();
//                Toast.makeText(parent.getContext(),
//                        finalViewHolder.cb.isChecked()+"+"+dao.queryAll().get(position).getPrice()*(dao.queryAll().get(position).getCount()),
//                        Toast.LENGTH_SHORT).show();
//dao.queryAll().get(position).getName() 可以获得当前条目的name
//                sf.refreshTotalPrice();
//
//            }
//        });




        viewHolder.cb.setTag(position);
        viewHolder.cb.setOnClickListener(onRefreshListener);

        viewHolder.add.setTag(position);
        viewHolder.add.setOnClickListener(onCountAddLIstener);

        viewHolder.cut.setTag(position);
        viewHolder.cut.setOnClickListener(onCountCutLIstener);

        viewHolder.delete.setTag(position);
        viewHolder.delete.setOnClickListener(onItemDeleteListener);
        //设置选择器
        viewHolder.cb.setTag(position);
        if(scbean.isChecked()){
            viewHolder.cb.setImageResource(R.drawable.isclick);
        }else{
            viewHolder.cb.setImageResource(R.drawable.noclick);
        }


//        //添加数量事件
//        viewHolder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShoppingCartItemBean scbean = list.get(position);
//
//
//                if (scbean.getCount() > 0 && scbean.getCount() < 10) {
//                    scbean.setCount(scbean.getCount() + 1);
//                    dao.upDate(scbean);
//                    notifyDataSetChanged();
//                }
//
//            }
//        });
//        //减少数量事件
//        viewHolder.cut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ShoppingCartItemBean scbean = list.get(position);
//
//
//                if (scbean.getCount() >= 2 && scbean.getCount() <= 10) {
//                    scbean.setCount(scbean.getCount() - 1);
//                    dao.upDate(scbean);
//                    notifyDataSetChanged();
//                }
//            }
//        });


//            viewHolder.cb.setChecked(isChecked);

//          加减号的点击事件
        viewHolder.add.setTag(position);

//            viewHolder.add.setOnClickListener(numAddOnClickListener);


        viewHolder.cut.setTag(position);
//            viewHolder.cut.setOnClickListener(numCutOnClickListener);


        return convertView;
    }
//已经移动到shoppingcartfragment
//    public void isAllChoose(boolean isAllChoose) {
//        for (ShoppingCartItemBean shoppingCartItemBean : list) {
//            shoppingCartItemBean.setIsChecked(isAllChoose);
//        }
//
//        setList(list);
//        notifyDataSetChanged();
//    }

    class ViewHolder {

        TextView name;
        TextView price;
        TextView oldprice;
        TextView count;

        ImageView iv;
        TextView delete;
        ImageView cb;
        TextView cut;
        TextView add;

    }

    private View.OnClickListener onRefreshListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            ShoppingCartItemBean shoppingCartItemBean = list.get(position);
//            Toast.makeText(context,"点击checkbox成功"+position,Toast.LENGTH_SHORT).show();
            shoppingCartItemBean.setIsChecked(!shoppingCartItemBean.isChecked());
            if (mlistener != null) {
                mlistener.OnItemCheckBoxClick(shoppingCartItemBean);
            }
            notifyDataSetChanged();
        }
    };

    private View.OnClickListener onCountAddLIstener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            ShoppingCartItemBean shoppingCartItemBean = list.get(position);
            if (mlistener != null) {
                mlistener.OnCountAddClick(shoppingCartItemBean);
            }
            notifyDataSetChanged();
        }
    };

    private View.OnClickListener onCountCutLIstener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            ShoppingCartItemBean shoppingCartItemBean = list.get(position);
            if (mlistener != null) {
                mlistener.OnCountCutClick(shoppingCartItemBean);
            }
            notifyDataSetChanged();
        }
    };

    private  View.OnClickListener onItemDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            ShoppingCartItemBean shoppingCartItemBean = list.get(position);
            if (mlistener != null) {
                mlistener.OnItemDelete(shoppingCartItemBean);
            }
            notifyDataSetChanged();
        }
    };
}
