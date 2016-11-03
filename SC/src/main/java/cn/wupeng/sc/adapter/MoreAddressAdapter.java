package cn.wupeng.sc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.bean.moreAddressBean;

/**
 * //地址管理列表显示的条目
 */
public class MoreAddressAdapter extends BaseAdapter {
    List<moreAddressBean.AddressListBean> addressList;
    Context context;

    public MoreAddressAdapter(List<moreAddressBean.AddressListBean> addressList, Context context) {
        this.addressList=addressList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return addressList.size() == 0 ? 0:addressList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.address_listing, null);
        }
        ViewHolder holder = ViewHolder.getViewHolder(convertView);
        moreAddressBean.AddressListBean addressListBean = addressList.get(position);
        holder.more_address_name.setText(addressListBean.getName());
        holder.more_address_telephone.setText(addressListBean.getPhoneNumber());
        holder.more_address_consignee.setText(addressListBean.getCity() + addressListBean.getAddressDetail()+addressListBean.getAddressArea());
        return convertView;
    }

    private static class ViewHolder {
        TextView more_address_name;
        TextView more_address_telephone;
        TextView more_address_consignee;

        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.more_address_name = ((TextView) convertView.findViewById(R.id.more_address_name));
                holder.more_address_telephone = ((TextView) convertView.findViewById(R.id.more_address_telephone));
                holder.more_address_consignee = ((TextView) convertView.findViewById(R.id.more_address_consignee));
            }
            convertView.setTag(holder);
            return holder;
        }
    }
}
