package cn.wupeng.sc.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/6/29.
 */
public class GoodsDetailPagerAdapter extends PagerAdapter {

    List<View> cachesView;
    private  List<View> views;
	public GoodsDetailPagerAdapter(){
        cachesView=new ArrayList<View>();
		}


    public GoodsDetailPagerAdapter(List<View> views) {
        this.views=views;

    }

    @Override
    public int getCount() {
        return views==null?0:views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        if (cachesView.isEmpty()){
//            View view = new View(container.getContext());
//            cachesView.add(view);
//        }
//        View view = cachesView.remove(0);
       View view = views.get(position);
        container.addView(view);
        return  view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}
