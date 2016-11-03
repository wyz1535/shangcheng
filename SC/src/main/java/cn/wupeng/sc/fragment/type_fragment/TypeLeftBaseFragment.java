package cn.wupeng.sc.fragment.type_fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Android on 2016/6/28.
 */
public abstract class TypeLeftBaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getResourcesLayout(), null);
        init(rootView);
        return rootView;
    }

    protected abstract void init(View view);

    public abstract int getResourcesLayout();
}
