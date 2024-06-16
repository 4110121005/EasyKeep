package edu.xcu.easykeep.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Objects;

import edu.xcu.easykeep.adapter.BillAdapter;
import edu.xcu.easykeep.bean.BillBean;
import edu.xcu.easykeep.bean.ItemBill;
import edu.xcu.easykeep.databinding.FragmentBillBinding;
import edu.xcu.easykeep.db.BillDBManger;
import edu.xcu.easykeep.db.TypeDBManger;

public class BillFragment extends Fragment {

    private FragmentBillBinding binding;
    BillAdapter billAdapter;
    ArrayList<ItemBill> itemList;
    BillDBManger billDBManger;
    BroadcastReceiver billInsertReceiver;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置广播
        super.onCreate(savedInstanceState);
        billInsertReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 收到广播，更新 ListView 数据
                billAdapter.notifyDataSetChanged();
            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.billList;
        itemList = getAllItem();
        billAdapter = new BillAdapter(itemList, getContext());

        listView.setAdapter(billAdapter);

        return root;
    }
    private ArrayList<ItemBill> getAllItem() {
        billDBManger = new BillDBManger(getContext());
        ArrayList<BillBean> billList = billDBManger.selectAllBill();
        return ItemBill.convertToItemList(billList, new TypeDBManger(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        // 注册广播接收器
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(billInsertReceiver,
                new IntentFilter("INSERT_BILL"));
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(billInsertReceiver,
                new IntentFilter("DELETE_BILL"));
    }

    @Override
    public void onPause() {
        super.onPause();
        // 取消注册广播接收器
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(billInsertReceiver);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}