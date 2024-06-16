package edu.xcu.easykeep.bean;

import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.xcu.easykeep.R;
import edu.xcu.easykeep.db.TypeDBManger;

/**
 * 列表项数据模型，继承自 BillBean，增加了图片资源信息。
 */
public class ItemBill extends BillBean {
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
    public ItemBill(int id, String name, String note, float money, String time, int year, int month, int day, int kind, int selected) {
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
     * 将 ArrayList<BillBean>; 转换为 ArrayList<ItemBill>
     *
     * @param billList     需要转换的 BillBean 列表
     * @param typeDBManger TypeDBManger 实例，用于查询选中图片 ID
     * @return 转换后的 ItemBill 列表
     */
//    public static ArrayList<ItemBill> convertToItemList(ArrayList<BillBean> billList, TypeDBManger typeDBManger) {
//        ArrayList<ItemBill> itemList = new ArrayList<>();
//        for (BillBean billBean : billList) {
//            // 根据类型名称获取选中图片 ID
//            int selected = typeDBManger.getSelectedByName(billBean.getName());
//
//            // 处理无效的图片 ID
//            if (selected == -1) {
//                // 使用默认图片 ID
//                selected = R.drawable.ic_type_defalut;
//            }
//            // 创建 ItemBill 对象
//            ItemBill itemBean = new ItemBill(
//                    billBean.getId(),
//                    billBean.getName(),
//                    billBean.getNote(),
//                    billBean.getMoney(),
//                    billBean.getTime(),
//                    billBean.getYear(),
//                    billBean.getMonth(),
//                    billBean.getDay(),
//                    billBean.getKind(),
//                    selected
//            );
//            itemList.record(itemBean);
//        }
//        return itemList;
//    }
    public static ArrayList<ItemBill> convertToItemList(ArrayList<BillBean> billList, TypeDBManger typeDBManger) {
        //使用 Stream API 避免了显式的循环，代码更紧凑易读。
        //Stream API 可以轻松实现并行处理，如果 billList 很大，可以提高效率。
        return billList.stream()
                .map(billBean -> {
                    int selected = typeDBManger.getSelectedByName(billBean.getName());
                    if (selected == -1) {
                        selected = R.drawable.ic_type_defalut; // 使用默认图片 ID 或抛出异常
                    }
                    return new ItemBill(
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
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
