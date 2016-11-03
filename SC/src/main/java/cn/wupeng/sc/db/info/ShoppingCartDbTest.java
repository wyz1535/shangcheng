package cn.wupeng.sc.db.info;

import android.test.AndroidTestCase;

import cn.wupeng.sc.db.ShoppingCartDBHelper;

/**
 * Created by hasee on 2016/6/30.
 */
public class ShoppingCartDbTest extends AndroidTestCase{
    public void testHp(){
        ShoppingCartDBHelper hp = new ShoppingCartDBHelper(getContext());
        hp.getWritableDatabase();
    }


}
