package cn.wupeng.sc.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.wupeng.sc.R;
import cn.wupeng.sc.activity.MainActivity;

/**
 * Created by Administrator on 2016/7/1.
 */
public class PrivilegeFragment extends BaseFragment implements View.OnClickListener{

    @ViewInject(R.id.iv_account_back)
    ImageView iv_account_back;

    @ViewInject(R.id.iv_privilege_01)
    ImageView iv_privilege_01;

    @ViewInject(R.id.iv_privilege_02)
    ImageView iv_privilege_02;

    @ViewInject(R.id.iv_privilege_03)
    ImageView iv_privilege_03;

    @Override
    protected void init(View view) {
        ViewUtils.inject(this,view);

        iv_privilege_01.setOnClickListener(this);
        iv_privilege_02.setOnClickListener(this);
        iv_privilege_03.setOnClickListener(this);
        //设置返回监听
        iv_account_back.setOnClickListener(backClickListener);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_privilege;
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            activity.onBackPressed();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_privilege_01:
                Toast.makeText(v.getContext(), "亲！已经领取过了额，做人别这么贪心额", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_privilege_02:
                Toast.makeText(v.getContext(), "亲！领取成功额，记得快去使用额", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_privilege_03:
                Toast.makeText(v.getContext(), "亲！领取成功额，记得快去使用额", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
