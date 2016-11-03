package cn.itcast.wh20.sidepulldelete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import cn.itcast.wh20.sidepulldelete.adapter.MyAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //找到侧拉删除控件
//        SidePullDeleteLayout sidePullDeleteLayout= (SidePullDeleteLayout) findViewById(R.id.sidePullDeleteLayout);
//        sidePullDeleteLayout.setOnSidePullChangeListenerl(onSidePullChangeListener);
        //找到listview，设置适配器
        ListView listview= (ListView) findViewById(R.id.listview);
        listview.setAdapter(new MyAdapter());
    }

//    private SidePullDeleteLayout.OnSidePullChangeListener onSidePullChangeListener=new SidePullDeleteLayout.OnSidePullChangeListener() {
//        @Override
//        public void onOpen() {
//            Utils.showTaost(MainActivity.this,"onOpen");
//        }
//
//        @Override
//        public void onDragging() {
//            Utils.showTaost(MainActivity.this,"onDragging");
//        }
//
//        @Override
//        public void onClose() {
//            Utils.showTaost(MainActivity.this,"onClose");
//        }
//
//        @Override
//        public void onStartOpen() {
//            Utils.showTaost(MainActivity.this,"onStartOpen");
//        }
//    };
}
