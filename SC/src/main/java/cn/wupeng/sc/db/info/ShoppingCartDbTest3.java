package cn.wupeng.sc.db.info;

import android.test.AndroidTestCase;

import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.dao.ShoppingCartDao;

/**
 * Created by hasee on 2016/6/30.
 */
public class ShoppingCartDbTest3 extends AndroidTestCase{
    public void testHp(){
        ShoppingCartDao dao = new ShoppingCartDao(getContext());
        ShoppingCartItemBean item = new ShoppingCartItemBean();
        item.setName("嘎嘎0");
        dao.remove(item);
    }


}
