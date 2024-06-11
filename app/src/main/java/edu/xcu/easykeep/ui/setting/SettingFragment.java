package edu.xcu.easykeep.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

import edu.xcu.easykeep.activity.LoginActivity;
import edu.xcu.easykeep.activity.MainActivity;
import edu.xcu.easykeep.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingViewModel settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SwitchMaterial switchDaily = binding.switchDaily;
        SwitchMaterial switchMonthly = binding.switchMonthly;
        ImageView ivClear = binding.ivClear;
        ImageView ivLogout = binding.ivLogout;
        LinearLayout layoutShare = binding.layoutShare;

        //监听点击事件，清空记录
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = requireActivity().getSharedPreferences(LoginActivity.data, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                editor.clear();//清空登录记录
                editor.commit();
                startActivity(intent);
            }
        });

        //监听点击事件，分享app链接
        layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "分享给你一个记账软件：https://github.com/4110121005/EasyKeep");
                shareIntent.setType("text/plain");
                //向系统发送隐式意图，打开系统分享选择器
                startActivity(Intent.createChooser(shareIntent, "分享到..."));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}