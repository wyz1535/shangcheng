package cn.wupeng.sc.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;

/**
 * Created by Android on 2016/6/28.
 */
public abstract class BaseFragment extends Fragment {
    public Gson gson;
    public HttpUtils httpUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getResourcesLayout(), null);
        gson=new Gson();
        httpUtils = new HttpUtils();
        init(rootView);
        return rootView;
    }
    protected abstract void init(View view);
    public abstract int getResourcesLayout();
}
