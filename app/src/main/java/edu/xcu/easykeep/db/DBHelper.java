package edu.xcu.easykeep.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import edu.xcu.easykeep.R;

/*
* 为 app 创建一个 SQLite 数据库，包含用户表、类型表......
* */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EasyKeep.db"; //数据库名

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        createUserTable(db);
        insertRootUser(db);

        //创建类型表
        createTypeTable(db);
//        insertType(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /*
    * 创建用户表
    * */
    public void createUserTable(SQLiteDatabase db){
        //创建表的 SQL 语句
        final String sql = "create table user( " +
                "uid text primary key not null, " +
                "upassword text not null)";
        db.execSQL(sql);
    }
    /*
     * 创建消费类型表
     * */
    public void createTypeTable(SQLiteDatabase db){
        //创建表的 SQL 语句
        final String sql = "create table type(" +
                "id integer primary key autoincrement," +
                "name text," +
                "imageId integer," +
                "selected integer," +
                "kind integer)";
        db.execSQL(sql);
    }

    /*
    * 向 type 表当中插入元素
    * */
//    private void insertType(SQLiteDatabase db){
//        String sql = "insert into type(name, imageId, selected, kind) values (?, ?, ?, ?)";
//
//        //支出
//        db.execSQL(sql, new Object[]{"飞来横祸，嘤嘤嘤", R.drawable.ic_type_angry, R.drawable.ic_type_angry_green, -1});
//
//        db.execSQL(sql, new Object[]{"餐饮", R.mipmap.ic_canyin,R.mipmap.ic_canyin_fs,0});
//        db.execSQL(sql, new Object[]{"交通", R.mipmap.ic_jiaotong,R.mipmap.ic_jiaotong_fs,0});
//        db.execSQL(sql, new Object[]{"服饰", R.mipmap.ic_fushi,R.mipmap.ic_fushi_fs,0});
//        db.execSQL(sql, new Object[]{"购物", R.mipmap.ic_gouwu,R.mipmap.ic_gouwu_fs,0});
//        db.execSQL(sql, new Object[]{"服务", R.mipmap.ic_gouwu,R.mipmap.ic_gouwu_fs,0});
//        db.execSQL(sql, new Object[]{"教育", R.mipmap.ic_gouwu,R.mipmap.ic_gouwu_fs,0});
//
//        db.execSQL(sql, new Object[]{"娱乐", R.mipmap.ic_yule,R.mipmap.ic_yule_fs,0});
//        db.execSQL(sql, new Object[]{"运动", R.mipmap.ic_lingshi,R.mipmap.ic_lingshi_fs,0});
//        db.execSQL(sql, new Object[]{"生活缴费", R.mipmap.ic_yanjiu,R.mipmap.ic_yanjiu_fs,0});
//        db.execSQL(sql, new Object[]{"旅行", R.mipmap.ic_xuexi,R.mipmap.ic_xuexi_fs,0});
//        db.execSQL(sql, new Object[]{"宠物", R.mipmap.ic_xuexi,R.mipmap.ic_xuexi_fs,0});
//        db.execSQL(sql, new Object[]{"医疗", R.mipmap.ic_yiliao,R.mipmap.ic_yiliao_fs,0});
//
//        db.execSQL(sql, new Object[]{"保险", R.mipmap.ic_zhufang,R.mipmap.ic_zhufang_fs,0});
//        db.execSQL(sql, new Object[]{"公益", R.mipmap.ic_shuidianfei,R.mipmap.ic_shuidianfei_fs,0});
//        db.execSQL(sql, new Object[]{"发红包", R.mipmap.ic_tongxun,R.mipmap.ic_tongxun_fs,0});
//        db.execSQL(sql, new Object[]{"转账", R.mipmap.ic_tongxun,R.mipmap.ic_tongxun_fs,0});
//        db.execSQL(sql, new Object[]{"人情往来", R.mipmap.ic_renqingwanglai,R.mipmap.ic_renqingwanglai_fs,0});
//        db.execSQL(sql, new Object[]{"其他", R.mipmap.in_qt,R.mipmap.in_qt_fs,0});
//
//        //支出
//        db.execSQL(sql, new Object[]{"天降横财，嘿嘿嘿", R.mipmap.in_yiwai,R.mipmap.in_yiwai_fs,1});
//        db.execSQL(sql, new Object[]{"交易", R.mipmap.in_xinzi,R.mipmap.in_xinzi_fs,1});
//        db.execSQL(sql, new Object[]{"工资", R.mipmap.in_xinzi,R.mipmap.in_xinzi_fs,1});
//        db.execSQL(sql, new Object[]{"奖金", R.mipmap.in_jiangjin,R.mipmap.in_jiangjin_fs,1});
//        db.execSQL(sql, new Object[]{"人情往来", R.mipmap.in_jieru,R.mipmap.in_jieru_fs,1});
//        db.execSQL(sql, new Object[]{"收红包", R.mipmap.in_shouzhai,R.mipmap.in_shouzhai_fs,1});
//        db.execSQL(sql, new Object[]{"利息收入", R.mipmap.in_lixifuji,R.mipmap.in_lixifuji_fs,1});
//        db.execSQL(sql, new Object[]{"投资回报", R.mipmap.in_touzi,R.mipmap.in_touzi_fs,1});
//        db.execSQL(sql, new Object[]{"其他", R.mipmap.in_yiwai,R.mipmap.in_yiwai_fs,1});
//    }

    /*
    * 向 user表添加初始用户，便于前期开发
    * */
    private void insertRootUser(SQLiteDatabase db){
        String uid = "0";
        String upassword = "0";
        String sql = "insert into user values(?,?)";
        db.execSQL(sql, new Object[]{uid, upassword});
    }
}
