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
        insertType(db);
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
    private void insertType(SQLiteDatabase db){
        String sql = "insert into type(name, imageId, selected, kind) values (?, ?, ?, ?)";

        //支出
        db.execSQL(sql, new Object[]{"飞来横祸，嘤嘤嘤", R.drawable.ic_type_angry, R.drawable.ic_type_angry_green, -1});

        db.execSQL(sql, new Object[]{"餐饮", R.drawable.ic_type_catering, R.drawable.ic_type_catering_green, -1});
        db.execSQL(sql, new Object[]{"交通", R.drawable.ic_type_traffic,  R.drawable.ic_type_traffic_green, -1});
        db.execSQL(sql, new Object[]{"服饰", R.drawable.ic_type_clothes, R.drawable.ic_type_clothes_green, -1});
        db.execSQL(sql, new Object[]{"购物", R.drawable.ic_type_shopping, R.drawable.ic_type_shopping_green, -1});
        db.execSQL(sql, new Object[]{"服务", R.drawable.ic_type_service, R.drawable.ic_type_service_green, -1});
        db.execSQL(sql, new Object[]{"教育", R.drawable.ic_type_education, R.drawable.ic_type_education_green, -1});

        db.execSQL(sql, new Object[]{"娱乐", R.drawable.ic_type_recreation, R.drawable.ic_type_recreation, -1});
        db.execSQL(sql, new Object[]{"运动", R.drawable.ic_type_exercise, R.drawable.ic_type_exercise_green, -1});
        db.execSQL(sql, new Object[]{"生活缴费", R.drawable.ic_type_living, R.drawable.ic_type_living_green, -1});
        db.execSQL(sql, new Object[]{"旅行", R.drawable.ic_type_travel, R.drawable.ic_type_travel_green, -1});
        db.execSQL(sql, new Object[]{"宠物", R.drawable.ic_type_pet, R.drawable.ic_type_pet_green, -1});
        db.execSQL(sql, new Object[]{"医疗", R.drawable.ic_type_medical, R.drawable.ic_type_medical_green, -1});

        db.execSQL(sql, new Object[]{"保险", R.drawable.ic_type_insure, R.drawable.ic_type_insure_green, -1});
        db.execSQL(sql, new Object[]{"公益", R.drawable.ic_type_public, R.drawable.ic_type_public_green, -1});

        //收入
        db.execSQL(sql, new Object[]{"天降横财，鹅鹅鹅", R.drawable.ic_type_happy, R.drawable.ic_type_happy_green, 1});
        db.execSQL(sql, new Object[]{"交易", R.drawable.ic_type_transaction, R.drawable.ic_type_transaction_green, 1});
        db.execSQL(sql, new Object[]{"工资", R.drawable.ic_type_salary, R.drawable.ic_type_salary_green, 1});
        db.execSQL(sql, new Object[]{"奖金", R.drawable.ic_type_bonus, R.drawable.ic_type_bonus_green, 1});

        //收入或支出
        db.execSQL(sql, new Object[]{"其他", R.drawable.ic_type_rests, R.drawable.ic_type_rests_green, 0});
        db.execSQL(sql, new Object[]{"理财", R.drawable.ic_type_management, R.drawable.ic_type_management_green, 0});
        db.execSQL(sql, new Object[]{"人情往来", R.drawable.ic_type_relation, R.drawable.ic_type_relation_green, 0});
    }

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
