package edu.xcu.easykeep.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import edu.xcu.easykeep.databinding.DialogCalendarBinding;

/**
 * 自定义时间选择对话框，允许用户选择日期和时间。
 */
public class CalendarDialog extends Dialog {

    private DialogCalendarBinding binding; // ViewBinding对象，用于访问布局中的视图

    // 定义回调接口，用于通知调用者用户选择的日期和时间
    public interface OnEnsureListener {
        /**
         * 当用户点击确认按钮时调用此方法。
         *
         * @param time  格式化后的时间字符串，格式为 "yyyy年MM月dd日 HH:mm"
         * @param year  选择的年份
         * @param month 选择的月份 (1-12)
         * @param day   选择的日期
         */
        void onEnsure(String time, int year, int month, int day);
    }

    private OnEnsureListener onEnsureListener; // 回调监听器

    /**
     * 设置确认按钮点击事件的回调监听器。
     *
     * @param onEnsureListener 回调监听器
     */
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    /**
     * 构造函数。
     *
     * @param context 上下文对象
     */
    public CalendarDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogCalendarBinding.inflate(LayoutInflater.from(getContext())); // 初始化ViewBinding对象
        setContentView(binding.getRoot()); // 设置对话框布局

        // 设置按钮点击监听
        binding.btnCalendarEnsure.setOnClickListener(v -> handleEnsureButtonClick());
        binding.btnCalendarCancel.setOnClickListener(v -> dismiss());

        // 隐藏DatePicker的头部
        hideDatePickerHeader();
    }

    /**
     * 处理确认按钮点击事件：获取用户选择的日期和时间，并通知回调监听器。
     */
    @SuppressLint("DefaultLocale")
    private void handleEnsureButtonClick() {
        // 获取日期选择器选择的日期
        DatePicker datePicker = binding.dpCalendar;
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // 月份从0开始，需要加1
        int dayOfMonth = datePicker.getDayOfMonth();

        // 格式化月份和日期
        String monthStr = String.format("%02d", month);
        String dayStr = String.format("%02d", dayOfMonth);

        // 获取输入的小时和分钟
        String hourStr = binding.etHour.getText().toString();
        String minuteStr = binding.etMinute.getText().toString();

        // 解析小时和分钟，并进行合法性校验
        int hour = parseTimeValue(hourStr, 24);
        int minute = parseTimeValue(minuteStr, 60);

        // 格式化小时和分钟
        hourStr = String.format("%02d", hour);
        minuteStr = String.format("%02d", minute);

        // 拼接时间字符串
        String time = hourStr + ":" + minuteStr;

        // 回调监听器
        if (onEnsureListener != null) {
            onEnsureListener.onEnsure(time, year, month, dayOfMonth);
        }

        // 关闭对话框
        dismiss();
    }

    /**
     * 解析时间值，并进行合法性校验。
     *
     * @param timeStr  时间字符串
     * @param maxValue 最大值
     * @return 解析后的时间值
     */
    private int parseTimeValue(String timeStr, int maxValue) {
        int time = 0;
        if (!TextUtils.isEmpty(timeStr)) {
            time = Integer.parseInt(timeStr);
            time = time % maxValue; // 对最大值取余，确保值在合法范围内
        }
        return time;
    }

    /**
     * 隐藏 DatePicker 的头部布局。
     */
    private void hideDatePickerHeader() {
        ViewGroup rootView = (ViewGroup) binding.dpCalendar.getChildAt(0);
        if (rootView == null) {
            return;
        }
        View headerView = rootView.getChildAt(0);
        if (headerView == null) {
            return;
        }
        // 6.0+ 版本的DatePicker
        @SuppressLint("DiscouragedApi") int headerId = getContext().getResources().getIdentifier("date_picker_header", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }
}
