package edu.xcu.easykeep.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.xcu.easykeep.bean.BillBean;

/**
 * 账单数据管理类，负责对账单表进行操作
 */
public class BillDBManger {
    private final SQLiteDatabase db;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public BillDBManger(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 插入账单数据
     *
     * @param bill 账单对象
     */
    public void insertBill(BillBean bill) {
        ContentValues values = new ContentValues();
        values.put("name", bill.getName());
        values.put("note", bill.getNote());
        values.put("money", bill.getMoney());
        values.put("time", bill.getTime());
        values.put("year", bill.getYear());
        values.put("month", bill.getMonth());
        values.put("day", bill.getDay());
        values.put("kind", bill.getKind());

        db.insert("bill", null, values);
    }

    /**
     * 查询所有账单
     *
     * @return 所有账单列表
     */
    public ArrayList<BillBean> selectAllBill() {
        ArrayList<BillBean> list = new ArrayList<>();

        String sql = "select * from bill";
        Cursor cursor = db.rawQuery(sql, null);

        // 遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("note"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));

            BillBean billBean = new BillBean(id, name, note, money, time, year, month, day, kind);
            list.add(billBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询某一天的所有账单
     *
     * @param year  年份
     * @param month 月份
     * @param day   日期
     * @return 指定日期的账单列表
     */
    public ArrayList<BillBean> selectBillListByDay(int year, int month, int day) {
        ArrayList<BillBean> list = new ArrayList<>();

        String sql = "select * from bill where year = ? and month= ? and day= ? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});

        // 遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("note"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));

            BillBean billBean = new BillBean(id, name, note, money, time, year, month, day, kind);
            list.add(billBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 获取某一天的支出或收入总金额
     *
     * @param year  年份
     * @param month 月份
     * @param day   日期
     * @param kind  收入或支出类型，-1 表示支出，1 表示收入
     * @return 总金额
     */
    @SuppressLint("Range")
    public float selectSumMoneyByDay(int year, int month, int day, int kind) {
        float sum = 0.0f;
        String sql = "select sum(money) from bill where year = ? and month = ? and day = ? and kind = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return sum;
    }

    /**
     * 获取某一月的支出或收入总金额
     *
     * @param year  年份
     * @param month 月份
     * @param kind  收入或支出类型，-1 表示支出，1 表示收入
     * @return 总金额
     */
    @SuppressLint("Range")
    public float selectSumMoneyByMonth(int year, int month, int kind) {
        float sum = 0.0f;
        String sql = "select sum(money) from bill where year = ? and month = ? and kind = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return sum;
    }

    /**
     * 统计某月份支出或收入的记录条数
     *
     * @param year  年份
     * @param month 月份
     * @param kind  收入或支出类型，-1 表示支出，1 表示收入
     * @return 记录条数
     */
    @SuppressLint("Range")
    public int selectSumBillByMonth(int year, int month, int kind) {
        int sum = 0;
        String sql = "select count(money) from bill where year = ? and month = ? and kind = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("count(money)"));
        }
        cursor.close();
        return sum;
    }

    /**
     * 获取某一年的支出或收入总金额
     *
     * @param year 年份
     * @param kind 收入或支出类型，-1 表示支出，1 表示收入
     * @return 总金额
     */
    @SuppressLint("Range")
    public float selectSumMoneyByYear(int year, int kind) {
        float sum = 0.0f;
        String sql = "select sum(money) from bill where year = ? and kind = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        cursor.close();
        return sum;
    }

    /**
     * 根据 ID 删除账单记录
     *
     * @param id 账单记录 ID
     * @return 删除的行数
     */
    public int deleteBillById(int id) {
        return db.delete("bill", "id = ?", new String[]{id + ""});
    }

    /**
     * 根据备注搜索账单列表
     *
     * @param nt 备注关键词
     * @return 包含备注关键词的账单列表
     */
    public ArrayList<BillBean> selectBillListByNote(String nt) {
        ArrayList<BillBean> list = new ArrayList<>();
        String sql = "select * from bill where note like '%" + nt + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("note"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));

            BillBean billBean = new BillBean(id, name, note, money, time, year, month, day, kind);
            list.add(billBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 获取某一月的所有支出或收入记录
     *
     * @param year  年份
     * @param month 月份
     * @return 指定月份的账单列表
     */
    public ArrayList<BillBean> selectBillListByMonth(int year, int month) {
        ArrayList<BillBean> list = new ArrayList<>();
        String sql = "select * from bill where year = ? and month = ? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});
        // 遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("note"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));

            BillBean billBean = new BillBean(id, name, note, money, time, year, month, day, kind);
            list.add(billBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询记账表中的年份信息
     *
     * @return 年份列表
     */
    public ArrayList<Integer> selectYearList() {
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select distinct(year) from bill order by year asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        cursor.close();
        return list;
    }

    /**
     * 删除账单表中的所有数据
     */
    public void deleteAllBill() {
        String sql = "delete from bill";
        db.execSQL(sql);
    }
}

