package cn.wupeng.sc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.wupeng.sc.bean.ShoppingCartItemBean;
import cn.wupeng.sc.db.ShoppingCartDBHelper;
import cn.wupeng.sc.db.info.ShoppingCartInfo;

/**
 * Created by hasee on 2016/6/30.
 */
public class ShoppingCartDao {
    private ShoppingCartDBHelper helper;
    public ShoppingCartDao(Context ctx){
        helper = new ShoppingCartDBHelper(ctx);
    }
    public ShoppingCartItemBean find(String name){
        SQLiteDatabase db = helper.getReadableDatabase();
        ShoppingCartItemBean shoppingCartItemBean =null;
        if(db.isOpen()){
          Cursor cursor = db.query(ShoppingCartInfo.TABLE_CART,
                  new String[]{ShoppingCartInfo.ID, ShoppingCartInfo.NAME,
                          ShoppingCartInfo.PRICE, ShoppingCartInfo.COUNT,ShoppingCartInfo.PIC},
                    ShoppingCartInfo.NAME + "=?", new String[]{name}, null, null, null);
//            if(cursor.moveToNext()){
//              String title_ =  cursor.getString(0);
//                int truePrice = cursor.getInt(1);
//                int oldPrice = cursor.getInt(2);
//                int buyNum = cursor.getInt(3);
//                shoppingCartItemBean = new ShoppingCartItemBean();
//                shoppingCartItemBean.setTitle(title_);
//                shoppingCartItemBean.setTruePrice(truePrice);
//                shoppingCartItemBean.setOldPrice(oldPrice);
//                shoppingCartItemBean.setBuyNum(buyNum);
//            }
            if(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name_ = cursor.getString(1);
                double price = cursor.getDouble(2);
                int count = cursor.getInt(3);
                String pic = cursor.getString(4);
                shoppingCartItemBean = new ShoppingCartItemBean();
                shoppingCartItemBean.setId(id);
                shoppingCartItemBean.setName(name_);
                shoppingCartItemBean.setPrice(price);
                shoppingCartItemBean.setCount(count);
                shoppingCartItemBean.setPic(pic);

            }
            cursor.close();
            db.close();
        }
        return shoppingCartItemBean;
    }
    public void add(ShoppingCartItemBean shoppingCartItemBean){
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues contentValues = new ContentValues();
//            contentValues.put(ShoppingCartInfo.TITLE,shoppingCartItemBean.getTitle());
//            contentValues.put(ShoppingCartInfo.TRUE_PRICE,shoppingCartItemBean.getTruePrice());
//            contentValues.put(ShoppingCartInfo.OLD_PRICE,shoppingCartItemBean.getOldPrice());
//            contentValues.put(ShoppingCartInfo.BUY_NUM,shoppingCartItemBean.getBuyNum());
            contentValues.put(ShoppingCartInfo.ID,shoppingCartItemBean.getId());
            contentValues.put(ShoppingCartInfo.NAME,shoppingCartItemBean.getName());
            contentValues.put(ShoppingCartInfo.PRICE,shoppingCartItemBean.getPrice());
            contentValues.put(ShoppingCartInfo.COUNT,shoppingCartItemBean.getCount());
            contentValues.put(ShoppingCartInfo.PIC,shoppingCartItemBean.getPic());
            db.insert(ShoppingCartInfo.TABLE_CART, null, contentValues);


            db.close();
        }
    }
    public void upDate(ShoppingCartItemBean shoppingCartItemBean){
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()){
            ContentValues contentValues = new ContentValues();
//            contentValues.put(ShoppingCartInfo.TRUE_PRICE,shoppingCartItemBean.getTruePrice());
//            contentValues.put(ShoppingCartInfo.OLD_PRICE,shoppingCartItemBean.getOldPrice());
//            contentValues.put(ShoppingCartInfo.BUY_NUM, shoppingCartItemBean.getBuyNum());
            contentValues.put(ShoppingCartInfo.ID,shoppingCartItemBean.getId());
            contentValues.put(ShoppingCartInfo.NAME,shoppingCartItemBean.getName());
            contentValues.put(ShoppingCartInfo.PRICE,shoppingCartItemBean.getPrice());
            contentValues.put(ShoppingCartInfo.COUNT,shoppingCartItemBean.getCount());
            contentValues.put(ShoppingCartInfo.PIC,shoppingCartItemBean.getPic());
            db.update(ShoppingCartInfo.TABLE_CART, contentValues, ShoppingCartInfo.NAME + "=?", new String[]{shoppingCartItemBean.getName()});
        }
    }
    public void remove(ShoppingCartItemBean shoppingCartItemBean){
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()){
            db.delete(ShoppingCartInfo.TABLE_CART,
                    ShoppingCartInfo.NAME + "=?",
                    new String[]{shoppingCartItemBean.getName()});
            db.close();
        }
    }
public List<ShoppingCartItemBean>queryAll(){
    SQLiteDatabase db = helper.getWritableDatabase();
    List<ShoppingCartItemBean>list = null;
    if(db.isOpen()){
        list = new ArrayList<>();
       Cursor cursor = db.query(ShoppingCartInfo.TABLE_CART,
              new String[]{ShoppingCartInfo.ID,ShoppingCartInfo.NAME,ShoppingCartInfo.PRICE,ShoppingCartInfo.COUNT,ShoppingCartInfo.PIC},
               null,null,null,null,null);
        while (cursor.moveToNext()){
//            String title =  cursor.getString(0);
//            int truePrice = cursor.getInt(1);
//            int oldPrice = cursor.getInt(2);
//            int buyNum = cursor.getInt(3);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            int count = cursor.getInt(3);
            String pic = cursor.getString(4);
            ShoppingCartItemBean info = new ShoppingCartItemBean(id,name,price,count,pic);
            list.add(info);
        }
        db.close();
    }
return  list;
}

}
