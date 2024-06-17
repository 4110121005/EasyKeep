package edu.xcu.easykeep.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置广播
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.billList;
        itemList = getAllItem();
        billAdapter = new BillAdapter(itemList, getContext());

        listView.setAdapter(billAdapter);

        //添加列表项长按事件
        binding.billList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 创建数据库管理对象
                BillDBManger billDBManger = new BillDBManger(getContext());

                // 创建确认对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setMessage("确定要删除这条账单吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 删除账单数据
                                billDBManger.deleteBillById(itemList.get(position).getId());
                                itemList.remove(position);
                                billAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        return root;
    }
    private ArrayList<ItemBill> getAllItem() {
        billDBManger = new BillDBManger(getContext());
        ArrayList<BillBean> billList = billDBManger.selectAllBill();
        return ItemBill.convertToItemList(billList, new TypeDBManger(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}