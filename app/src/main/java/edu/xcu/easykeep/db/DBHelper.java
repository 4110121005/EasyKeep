package edu.xcu.easykeep.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Random;

import edu.xcu.easykeep.R;

/**
 * 数据库帮助类，用于管理数据库连接，并提供获取数据库连接的单例方法。
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * 数据库名称
     */
    private static final String DB_NAME = "EasyKeep.db"; // 数据库文件名
    private static final int DB_VERSION = 3; // 数据库版本号

    private static DBHelper instance; // 单例实例
    private static SQLiteDatabase db; // 数据库连接对象

    /**
     * 私有构造函数，防止外部实例化。
     *
     * @param context 上下文对象
     */
    private DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 获取数据库帮助类的单例实例。
     *
     * @param context 上下文对象
     * @return 数据库帮助类的单例实例
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
            db = instance.getWritableDatabase();
        }
        return instance;
    }

    /**
     * 获取数据库连接对象。
     *
     * @return 数据库连接对象
     */
    public SQLiteDatabase getDB() {
        return db;
    }

    /**
     * 关闭数据库连接。
     */
    public void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * 在数据库第一次创建时调用，用于创建数据库表
     *
     * @param db SQLiteDatabase 对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        createUserTable(db);
        // 插入初始用户数据
        insertRootUser(db);

        // 创建类型表
        createTypeTable(db);
        // 插入初始类型数据
        insertType(db);

        // 创建账单表
        createBillTable(db);
        // 插入初始账单数据
        insertBill(db);
    }

    /**
     * 当数据库版本号升级时调用，用于更新数据库结构
     *
     * @param db         SQLiteDatabase 对象
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: 处理数据库升级逻辑
    }

    /**
     * 创建用户表
     * 表结构：
     * - uid: 用户ID，文本类型，主键，非空
     * - upassword: 用户密码，文本类型，非空
     *
     * @param db SQLiteDatabase 对象
     */
    public void createUserTable(SQLiteDatabase db) {
        final String sql = "create table user( " +
                "uid text primary key not null, " +
                "upassword text not null)";
        db.execSQL(sql);
    }

    /**
     * 创建消费类型表
     * 表结构：
     * - id: 类型ID，整型，主键，自增
     * - name: 类型名称，文本类型
     * - imageId: 类型图标资源ID，整型
     * - selected: 类型选中状态，整型
     * - kind: 类型种类，整型，-1表示支出，1表示收入，0表示其他
     *
     * @param db SQLiteDatabase 对象
     */
    public void createTypeTable(SQLiteDatabase db) {
        final String sql = "create table type(" +
                "id integer primary key autoincrement," +
                "name text," +
                "imageId integer," +
                "selected integer," +
                "kind integer)";
        db.execSQL(sql);
    }

    /**
     * 创建账单表
     * 表结构：
     * - id: 账单ID，整型，主键，自增
     * - uid: 用户ID，字符串类型，非空
     * - name: 账单名称，文本类型
     * - note: 账单备注，文本类型
     * - money: 账单金额，浮点型
     * - time: 账单时间，字符串类型
     * - year: 账单年份，整型
     * - month: 账单月份，整型
     * - day: 账单日期，整型
     * - kind: 账单类型，整型，-1表示支出，1表示收入
     *
     * @param db SQLiteDatabase 对象
     */
    public void createBillTable(SQLiteDatabase db) {
        final String sql = "create table bill(" +
                "id integer primary key autoincrement," +
                "uid string not null," +
                "name text," +
                "note text," +
                "money float," +
                "time varchar(60)," +
                "year integer," +
                "month integer," +
                "day integer," +
                "kind integer)";
        db.execSQL(sql);
    }

    /**
     * 向用户表为 root 用户插入初始用户数据
     *
     * @param db SQLiteDatabase 对象
     */
    private void insertRootUser(SQLiteDatabase db) {
        String uid = "0";
        String upassword = "0";
        String sql = "insert into user values(?,?)";
        db.execSQL(sql, new Object[]{uid, upassword});
    }

    /**
     * 向账单表插入初始账单数据
     *
     * @param db SQLiteDatabase 对象
     */
    private void insertBill(SQLiteDatabase db) {
        String sql = "INSERT INTO bill (uid, name, note, money, time, year, month, day, kind) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1); // 设置起始日期为2023年1月1日

        String[] names = {"餐饮", "服饰", "购物", "旅行", "交通", "交通", "娱乐", "购物", "生活缴费", "生活缴费", "工资", "奖金"};
        String[] notes = {"", "好吃", "好贵", "人多", "方便", "快", "好看", "漂亮", "贵", "便宜", "努力", "开心"};
        int[] kinds = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1}; // -1: 支出, 1: 收入

        for (int i = 0; i < 20; i++) {
            int nameIndex = random.nextInt(names.length);
            int noteIndex = random.nextInt(notes.length);
            int kind = kinds[nameIndex];
            float money = random.nextFloat() * 100;

            calendar.add(Calendar.DAY_OF_MONTH, random.nextInt(3)); // 随机增加1-3天

            @SuppressLint("DefaultLocale") String time = String.format("%d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

            db.execSQL(sql, new Object[]{
                    "0",
                    names[nameIndex],
                    notes[noteIndex],
                    money,
                    time,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    kind
            });
        }
    }

    /**
     * 向类型表插入初始类型数据
     *
     * @param db SQLiteDatabase 对象
     */
    private void insertType(SQLiteDatabase db) {
        String sql = "insert into type(name, imageId, selected, kind) values (?, ?, ?, ?)";

        // 收入或支出
        db.execSQL(sql, new Object[]{"其他", R.drawable.ic_type_rests, R.drawable.ic_type_rests_green, 0});
        db.execSQL(sql, new Object[]{"理财", R.drawable.ic_type_management, R.drawable.ic_type_management_green, 0});
        db.execSQL(sql, new Object[]{"人情往来", R.drawable.ic_type_relation, R.drawable.ic_type_relation_green, 0});

        // 支出
        db.execSQL(sql, new Object[]{"飞来横祸", R.drawable.ic_type_angry, R.drawable.ic_type_angry_green, -1});
        db.execSQL(sql, new Object[]{"餐饮", R.drawable.ic_type_catering, R.drawable.ic_type_catering_green, -1});
        db.execSQL(sql, new Object[]{"交通", R.drawable.ic_type_traffic, R.drawable.ic_type_traffic_green, -1});
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

        // 收入
        db.execSQL(sql, new Object[]{"天降横财", R.drawable.ic_type_happy, R.drawable.ic_type_happy_green, 1});
        db.execSQL(sql, new Object[]{"交易", R.drawable.ic_type_transaction, R.drawable.ic_type_transaction_green, 1});
        db.execSQL(sql, new Object[]{"工资", R.drawable.ic_type_salary, R.drawable.ic_type_salary_green, 1});
        db.execSQL(sql, new Object[]{"奖金", R.drawable.ic_type_bonus, R.drawable.ic_type_bonus_green, 1});
    }
}
