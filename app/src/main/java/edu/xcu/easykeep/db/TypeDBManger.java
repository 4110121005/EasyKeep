package edu.xcu.easykeep.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.nio.file.WatchEvent;
import java.util.ArrayList;

import edu.xcu.easykeep.bean.TypeBean;

/**
 * 类型数据管理类，负责对类型表进行操作
 */
public class TypeDBManger {
    private final SQLiteDatabase db;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public TypeDBManger(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 读取 type 表中指定 kind 类型的记录，并将其封装成 TypeBean 对象列表返回
     *
     * @param kind 类型，-1 表示支出，1 表示收入，0 表示其他
     * @return 指定 kind 类型的 TypeBean 对象列表
     */
    public ArrayList<TypeBean> getTypeListByKind(int kind) {
        ArrayList<TypeBean> list = new ArrayList<>();

        // 读取 type 表中 kind 类型的数据
        String sql = "select * from type where kind = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{kind + ""});

        // 循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));

            TypeBean typeBean = new TypeBean(id, name, imageId, selected, kind);
            list.add(typeBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 获取指定名称的类型的选中状态图片 ID
     *
     * @param name 类型名称
     * @return 选中状态图片 ID
     */
    @SuppressLint("Range")
    public int getSelectedByName(String name) {
        int selected = 0;

        String sql = "select selected from type where name = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});

        if (cursor.moveToNext()) {
            selected = cursor.getInt(cursor.getColumnIndex("selected"));
        }
        cursor.close();
        return selected;
    }
}
