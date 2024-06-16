package edu.xcu.easykeep.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.xcu.easykeep.R;
import edu.xcu.easykeep.adapter.TabAdapter;
import edu.xcu.easykeep.databinding.FragmentRecordBinding;


/**
 * 记录Fragment，包含收入和支出两个选项卡
 */
public class RecordFragment extends Fragment {

    private FragmentRecordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 初始化 ViewPager2 和 TabLayout
        initViewPagerAndTabLayout();

        return root;
    }

    /**
     * 初始化 ViewPager2 和 TabLayout，并将它们关联起来
     */
    private void initViewPagerAndTabLayout() {
        ViewPager2 viewPager2 = binding.vpRecord;
        TabLayout tabLayout = binding.layoutRecordTabs;

        // 初始化 TabAdapter 并设置给 ViewPager2
        TabAdapter tabAdapter = new TabAdapter(this);
        viewPager2.setAdapter(tabAdapter);

        // 将 TabLayout 和 ViewPager2 关联起来
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            // 设置每个 Tab 的标题
            switch (position) {
                case 0:
                    tab.setText("支出");
                    break;
                case 1:
                    tab.setText("收入");
                    break;
            }
        }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
