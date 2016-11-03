package cn.wupeng.sc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;


/**
 * Created by Administrator on 2016-6-28.
 */
public class GuideActivity extends Activity{
    @ViewInject(R.id.guide_vp)
    ViewPager vp;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        ViewUtils.inject(this);
        vp.setAdapter(pagerAdapter);
        vp.setOnPageChangeListener(pageListener);
        pageListener.onPageSelected(0);
    }

    int[] picRes = new int[]{R.drawable.lead1,R.drawable.lead2,R.drawable.lead3};

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return picRes == null?0:picRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            iv = new ImageView(container.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(picRes[position]);
            container.addView(iv);

            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
    private ViewPager.OnPageChangeListener pageListener = new ViewPager.SimpleOnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (position == pagerAdapter.getCount()-1){
                iv.setClickable(true);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(GuideActivity.this, "我准备跳转到主页了",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

}
