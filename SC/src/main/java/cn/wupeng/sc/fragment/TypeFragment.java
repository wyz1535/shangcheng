package cn.wupeng.sc.fragment;


import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.R;
import cn.wupeng.sc.adapter.CategoryAdapter;
import cn.wupeng.sc.adapter.CategoryRecyclerViewAdapter;
import cn.wupeng.sc.bean.CategoryBean;
import cn.wupeng.sc.bean.SimpleCategoryBean;
import cn.wupeng.sc.utils.UtilsNet;

;

/**
 * Created by Android on 2016/6/28.
 */
public class TypeFragment extends BaseFragment {

    private ListView lv;
    private CategoryAdapter categoryAdapter;
    private List<SimpleCategoryBean> list;
    private RecyclerView rv;
    private List<SimpleCategoryBean> categoryList;
    private List<SimpleCategoryBean> recyclerViewDataList=new ArrayList<>();
    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;

    @Override
    protected void init(View view) {
        initView(view);
        initData();
        categoryAdapter = new CategoryAdapter(list,view.getContext());
        lv.setAdapter(categoryAdapter);
        categoryAdapter.setSelected(0);
        lv.setOnItemClickListener(categoryClickListener);
        categoryRecyclerViewAdapter=new CategoryRecyclerViewAdapter(recyclerViewDataList,view.getContext());
        rv.setLayoutManager(new GridLayoutManager(view.getContext(),2,GridLayoutManager.VERTICAL, false));
        rv.setAdapter(categoryRecyclerViewAdapter);
    }

    private AdapterView.OnItemClickListener categoryClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            categoryAdapter.setSelected(position);
            categoryAdapter.notifyDataSetChanged();
            int categoryId = list.get(position).getId();
            initRecyclerViewData(categoryId);

        }
    };

    boolean isFirstTime=true;

    private void initRecyclerViewData(int categoryId) {
        recyclerViewDataList.clear();
        for (SimpleCategoryBean simpleCategoryBean : categoryList) {
            if (simpleCategoryBean.getParentId()==categoryId){
                recyclerViewDataList.add(simpleCategoryBean);
            }
        }
        categoryRecyclerViewAdapter.notifyDataSetChanged();
//        new CategoryAsyncTask().execute(Integer.parseInt(String.valueOf(categoryId)));
    }

    private class CategoryAsyncTask extends AsyncTask<Integer,Void,List<SimpleCategoryBean>>{

        @Override
        protected List<SimpleCategoryBean> doInBackground(Integer... params) {
            Integer[] clone = params.clone();
            Integer categoryId = clone[0];
            for (SimpleCategoryBean simpleCategoryBean : categoryList) {
                if (simpleCategoryBean.getParentId()==categoryId){
                    recyclerViewDataList.add(simpleCategoryBean);
                }
            }
            return recyclerViewDataList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<SimpleCategoryBean> simpleCategoryBeans) {
            super.onPostExecute(simpleCategoryBeans);
            categoryRecyclerViewAdapter.setRecyclerViewDataList(simpleCategoryBeans);
            categoryRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        httpUtils.send(HttpRequest.HttpMethod.GET, UtilsNet.formatUrl("/category"),categoryCallBack);
    }

    private RequestCallBack<String> categoryCallBack=new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String result = responseInfo.result;
            CategoryBean categoryBean = gson.fromJson(result, CategoryBean.class);
            categoryList = categoryBean.getCategory();
            list = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++) {
                SimpleCategoryBean simpleCategoryBean = categoryList.get(i);
                if (!simpleCategoryBean.isIsLeafNode()){
                    list.add(simpleCategoryBean);
                }
            }
            categoryAdapter.setCategoryList(list);
            if (isFirstTime){
                isFirstTime=!isFirstTime;
                int categoryId = list.get(0).getId();
                initRecyclerViewData(categoryId);
            }
            categoryAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv_category);
        rv = (RecyclerView) view.findViewById(R.id.category_recyclerView);
    }

    @Override
    public int getResourcesLayout() {
        return R.layout.fragment_type;
    }






}
