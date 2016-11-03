package cn.wupeng.sc.db.info;

/**
 * Created by hasee on 2016/6/30.
 */
public interface ShoppingCartInfo {
    String DB_NAME = "cart.db";
    int START_VARSION =1 ;
    String CREATE_TABLE_SQL =
            "create table cartmsg(_id integer primary key autoincrement , id integer, name varchar,price varchar,count integer,pic varchar)";
    String TABLE_CART ="cartmsg" ;
    String ID = "id";
    String NAME ="name" ;
    String PRICE ="price" ;
    String COUNT ="count" ;
    String PIC = "pic";
}
