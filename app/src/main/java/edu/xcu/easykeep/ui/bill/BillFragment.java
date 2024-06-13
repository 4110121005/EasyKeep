package edu.xcu.easykeep.ui.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.xcu.easykeep.adapter.ItemAdapter;
import edu.xcu.easykeep.bean.BillBean;
import edu.xcu.easykeep.bean.ItemBean;
import edu.xcu.easykeep.databinding.FragmentBillBinding;
import edu.xcu.easykeep.db.BillDBManger;
import edu.xcu.easykeep.db.TypeDBManger;

public class BillFragment extends Fragment {

    private FragmentBillBinding binding;
    ItemAdapter itemAdapter;
    ArrayList<ItemBean> itemList;
    BillDBManger billDBManger;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView billList = binding.billList;
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList, getContext());
        //getAllItem();
        billList.setAdapter(itemAdapter);

        return root;
    }
/*    private ArrayList<ItemBean> getAllItem() {
        billDBManger = new BillDBManger(getContext());
        TypeDBManger typeDBManger = new TypeDBManger(getContext());
        ArrayList<BillBean> billList = billDBManger.selectAllBill();
        itemList.addAll((Collection<? extends ItemBean>) billList);
        itemList.forEach((item)->{
            item.setSelected(typeDBManger.getSelectedByName(item.getName()));
        });
        return itemList;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}