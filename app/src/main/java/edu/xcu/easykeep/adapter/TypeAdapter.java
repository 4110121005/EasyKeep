package edu.xcu.easykeep.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Objects;

import edu.xcu.easykeep.R;
import edu.xcu.easykeep.bean.TypeBean;

/**
 * 用于显示类型网格的适配器
 */
public class TypeAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private final Context context;
    /**
     * 类型数据列表
     */
    private final List<TypeBean> typeList;

    /**
     * 构造函数
     *
     * @param typeList 类型数据列表
     * @param context  上下文对象
     */

    /**
     * 选中项的位置
     */
    private int selectPos = 0;
    public TypeAdapter(List<TypeBean> typeList, Context context) {
        this.typeList = typeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public TypeBean getItem(int position) {
        return typeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        convertView = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        viewHolder = (ViewHolder) convertView.getTag();

        // 获取当前 item 的数据
        TypeBean item = typeList.get(position);

        //判断当前位置是否为选中位置，如果是选中的位置就显示成有颜色的图片
        if (selectPos == position) {
            viewHolder.ivTypeImage.setImageResource(item.getSelected());
        }else{
            viewHolder.ivTypeImage.setImageResource(item.getImageId());
        }

        //TextView 的内容
        viewHolder.tvTypeName.setText(item.getName());

        return convertView;
    }

    /**
     * ViewHolder 用于缓存 View，提高列表滑动时的性能
     */
    private static class ViewHolder {
        ImageView ivTypeImage;
        TextView tvTypeName;

        /**
         * 构造函数
         *
         * @param view item 布局的 View 对象
         */
        ViewHolder(View view) {
            ivTypeImage = view.findViewById(R.id.iv_type_image);
            tvTypeName = view.findViewById(R.id.tv_type_name);
        }
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }
}
