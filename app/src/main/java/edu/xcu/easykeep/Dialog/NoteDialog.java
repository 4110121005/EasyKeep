package edu.xcu.easykeep.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import edu.xcu.easykeep.databinding.DialogNoteBinding;

/**
 * 自定义备注对话框
 */
public class NoteDialog extends Dialog {

    private DialogNoteBinding binding; // 视图绑定对象
    private OnNoteSubmitListener onNoteSubmitListener; // 提交监听器
    private String note = ""; // 初始备注内容

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public NoteDialog(@NonNull Context context) {
        super(context);
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    /**
     * 当对话框创建时调用
     *
     * @param savedInstanceState 保存的状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化视图绑定
        binding = DialogNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 设置初始备注内容
        if (!TextUtils.isEmpty(note)) {
            binding.etNote.setText(note);
            // 将光标移动到文本末尾
            binding.etNote.setSelection(note.length());
        }

        // 设置按钮点击监听器
        binding.btnNoteCancel.setOnClickListener(view -> dismiss());

        binding.btnNoteEnsure.setOnClickListener(view -> {
            if (onNoteSubmitListener != null) {
                note = binding.etNote.getText().toString().trim();
                onNoteSubmitListener.onNoteSubmit();
            }
            dismiss();
        });

        // 设置对话框尺寸和样式
        setDialogSizeAndStyle();

        // 显示软键盘
        showKeyboard();
    }

    /**
     * 设置对话框尺寸和样式
     */
    private void setDialogSizeAndStyle() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.gravity = Gravity.BOTTOM;
            window.setAttributes(wlp);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    /**
     * 显示软键盘
     */
    private void showKeyboard() {
        binding.etNote.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(binding.etNote, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 设置备注提交监听器
     *
     * @param listener 监听器
     */
    public void setOnNoteSubmitListener(OnNoteSubmitListener listener) {
        this.onNoteSubmitListener = listener;
    }

    /**
     * 备注提交监听器接口
     */
    public interface OnNoteSubmitListener {
        void onNoteSubmit();
    }
}
