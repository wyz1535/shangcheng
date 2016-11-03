package cn.wupeng.sc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;
import cn.wupeng.sc.fragment.TypeFragment;

/**
 * Created by Administrator on 2016/6/30.
 */
public class ScreenedActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.screened_iv)
    private ImageView screened_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screened);
        ViewUtils.inject(this);

        init();
    }

    private void init() {

        screened_iv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
          case R.id.screened_iv:
              Intent intent=new Intent();
              intent.setClass(this,TypeFragment.class);
              startActivity(intent);
              break;
        }
    }
}
