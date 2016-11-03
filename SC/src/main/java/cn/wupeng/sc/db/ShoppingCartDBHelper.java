package cn.wupeng.sc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.wupeng.sc.db.info.ShoppingCartInfo;

/**
 * Created by hasee on 2016/6/30.
 */
public class ShoppingCartDBHelper extends SQLiteOpenHelper {
    public ShoppingCartDBHelper(Context context) {
        super(context, ShoppingCartInfo.DB_NAME, null, ShoppingCartInfo.START_VARSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ShoppingCartInfo.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
