package edu.xcu.easykeep.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.xcu.easykeep.EasyKeepApp;
import edu.xcu.easykeep.activity.LoginActivity;
import edu.xcu.easykeep.databinding.FragmentSettingBinding;
import edu.xcu.easykeep.db.BillDBManger;

/**
 * 设置页面Fragment
 */
public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // 初始化视图绑定
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 注销登录点击事件
        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清空登录记录
                EasyKeepApp app = (EasyKeepApp) requireActivity().getApplicationContext();
                SharedPreferences.Editor editor = app.getSharedPreferences().edit();
                editor.clear();
                editor.apply();

                // 跳转到登录页面
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // 清空数据点击事件
        binding.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建数据库管理对象
                BillDBManger billDBManger = new BillDBManger(getContext());

                // 创建确认对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("大傻春你要干什么！")
                        .setMessage("确定要删除所有数据吗？") // 添加确认信息
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("删光跑路...", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 删除所有账单数据
                                billDBManger.deleteAllBill();
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // 分享App链接点击事件
        binding.layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建分享意图
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "分享给你一个记账软件：https://github.com/4110121005/EasyKeep");
                shareIntent.setType("text/plain");

                // 启动系统分享选择器
                startActivity(Intent.createChooser(shareIntent, "分享到..."));
            }
        });

        // 意见反馈点击事件
        binding.tvAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到GitHub Issues页面
                String url = "https://github.com/4110121005/EasyKeep/issues";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 清理视图绑定
        binding = null;
    }
}
