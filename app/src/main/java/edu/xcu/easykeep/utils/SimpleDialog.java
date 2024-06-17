package edu.xcu.easykeep.utils;

import android.app.AlertDialog;
import android.content.Context;

public class SimpleDialog {
    /**
     *  显示消息提示框
     * @param context 上下文内容
     * @param message 提示的消息内容
     */
    public static void showHintDialog(Context context, String message){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(message);
        dialog = builder.create();
        dialog.show();;
    }
}
