package edu.xcu.easykeep.bean;

import android.content.Context;

import java.util.ArrayList;

import edu.xcu.easykeep.db.TypeDBManger;

/**
 * 列表项数据模型，继承自 BillBean，增加了图片资源信息。
 */
public class ItemBean extends BillBean {
    /**
     * 选中的图片资源 ID
     */
    private int selected;

    /**
     * 构造函数
     *
     * @param id       记录 ID
     * @param name     类型名称
     * @param note     备注信息
     * @param money    金额
     * @param time     时间字符串
     * @param year     年份
     * @param month    月份
     * @param day      日期
     * @param kind     收入/支出类型
     * @param selected 选中的图片资源 ID
     */
    public ItemBean(int id, String name, String note, float money, String time, int year, int month, int day, int kind, int selected) {
        super(id, name, note, money, time, year, month, day, kind);
        this.selected = selected;
    }

    /**
     * 获取选中的图片资源 ID
     *
     * @return 选中的图片资源 ID
     */
    public int getSelected() {
        return selected;
    }

    /**
     * 设置选中的图片资源 ID
     *
     * @param selected 选中的图片资源 ID
     */
    public void setSelected(int selected) {
        this.selected = selected;
    }

    /**
     * 将 ArrayList<BillBean>; 转换为 ArrayList<ItemBean>
     *
     * @param billList     需要转换的 BillBean 列表
     * @param typeDBManger TypeDBManger 实例，用于查询选中图片 ID
     * @return 转换后的 ItemBean 列表
     */
    public static ArrayList<ItemBean> convertToItemList(ArrayList<BillBean> billList, TypeDBManger typeDBManger) {
        ArrayList<ItemBean> itemList = new ArrayList<>();
        for (BillBean billBean : billList) {
            // 根据类型名称获取选中图片 ID
            int selected = typeDBManger.getSelectedByName(billBean.getName());

            // 创建 ItemBean 对象
            ItemBean itemBean = new ItemBean(
                    billBean.getId(),
                    billBean.getName(),
                    billBean.getNote(),
                    billBean.getMoney(),
                    billBean.getTime(),
                    billBean.getYear(),
                    billBean.getMonth(),
                    billBean.getDay(),
                    billBean.getKind(),
                    selected
            );
            itemList.add(itemBean);
        }
        return itemList;
    }
}
