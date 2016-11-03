package cn.wupeng.sc.db.info;

import android.test.AndroidTestCase;

import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.dao.ShoppingCartDao;

/**
 * Created by hasee on 2016/6/30.
 */
public class ShoppingCartDbTest2 extends AndroidTestCase{
    public void testDao(){
        ShoppingCartDao dao = new ShoppingCartDao(getContext());
        for(int i=0;i<100;i++){
            ShoppingCartItemBean item = new ShoppingCartItemBean();
            item.setTitle("嘎嘎"+i);
            item.setTruePrice(20+i);
            item.setOldPrice(30+i);
            item.setBuyNum(i);
            dao.add(item);
        }
    }


}
