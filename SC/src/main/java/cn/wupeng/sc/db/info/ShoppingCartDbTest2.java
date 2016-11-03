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
        for(int i=0;i<10;i++){
            ShoppingCartItemBean item = new ShoppingCartItemBean();
            item.setName("吾儿黄凡"+i);
            item.setPrice(20 + i);

            item.setCount(i);
            item.setPic("http://10.0.2.2:8080/market/photo_2.png");

            dao.add(item);
        }
    }


}
