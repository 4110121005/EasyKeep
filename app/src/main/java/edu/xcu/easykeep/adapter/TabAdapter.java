package edu.xcu.easykeep.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.xcu.easykeep.fragment.IncomeFragment;
import edu.xcu.easykeep.fragment.PayFragment;

/**
 * 用于 ViewPager2 的 Tab 适配器，管理支出和收入两个 Fragment。
 */
public class TabAdapter extends FragmentStateAdapter {

    /**
     * Tab 的数量
     */
    private static final int TAB_COUNT = 2;

    /**
     * 构造函数
     *
     * @param fragment 宿主 Fragment
     */
    public TabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    /**
     * 根据位置创建对应的 Fragment，为 ViewPager2 提供需要显示的 Fragment 实例。
     *
     * @param position 位置
     * @return 对应的 Fragment
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PayFragment(); // 返回支出 Fragment
            case 1:
                return new IncomeFragment(); // 返回收入 Fragment
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    /**
     * 获取 Tab 的数量
     *
     * @return Tab 的数量
     */
    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
}
