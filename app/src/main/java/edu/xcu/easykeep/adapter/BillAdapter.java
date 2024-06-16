package edu.xcu.easykeep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.xcu.easykeep.R;
import edu.xcu.easykeep.bean.ItemBill;

/**
 * 用于展示账单列表的适配器
 */
public class BillAdapter extends BaseAdapter {

    /**
     * 账单数据列表
     */
    private final List<ItemBill> itemList;
    /**
     * 上下文对象
     */
    private final Context context;

    /**
     * 当前年份
     */
    int currentYear;
    /**
     * 当前月份
     */
    int currentMonth;
    /**
     * 当前日期
     */
    int currentDay;

    /**
     * 构造函数
     *
     * @param itemList 账单数据列表
     * @param context  上下文对象
     */
    public BillAdapter(List<ItemBill> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取列表项数量
     *
     * @return 列表项数量
     */
    @Override
    public int getCount() {
        return itemList.size();
    }

    /**
     * 获取指定位置的列表项数据
     *
     * @param position 列表项位置
     * @return 列表项数据
     */
    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    /**
     * 获取指定位置的列表项ID
     *
     * @param position 列表项位置
     * @return 列表项ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取指定位置的列表项视图
     *
     * @param position    列表项位置
     * @param convertView 可复用的视图
     * @param parent      父视图
     * @return 列表项视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // 复用 convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemBill item = itemList.get(position); // 获取当前item的数据

        viewHolder.iv_item_image.setImageResource(item.getSelected());
        viewHolder.tv_item_name.setText(item.getName());
        viewHolder.tv_item_note.setText(item.getNote());
        viewHolder.tv_item_money.setText(String.format(Locale.CHINA, "￥ %.2f", item.getMoney()));

        // 判断日期是否为今天，如果是则显示“今天 XX:XX”格式，否则显示完整日期时间
        if (item.getYear() == currentYear && item.getMonth() == currentMonth && item.getDay() == currentDay) {
            viewHolder.tv_item_date.setText(String.format("今天 %s", item.getTime().split(" ")[1]));
        } else {
            viewHolder.tv_item_date.setText(item.getTime());
        }
        return convertView;
    }

    /**
     * ViewHolder类，用于缓存列表项视图中的控件
     */
    private static class ViewHolder {
        ImageView iv_item_image;
        TextView tv_item_name;
        TextView tv_item_note;
        TextView tv_item_money;
        TextView tv_item_date;

        /**
         * 构造函数
         *
         * @param view 列表项视图
         */
        public ViewHolder(View view) {
            iv_item_image = view.findViewById(R.id.iv_item_image);
            tv_item_name = view.findViewById(R.id.tv_item_name);
            tv_item_note = view.findViewById(R.id.tv_item_note);
            tv_item_money = view.findViewById(R.id.tv_item_money);
            tv_item_date = view.findViewById(R.id.tv_item_date);
        }
    }
}
