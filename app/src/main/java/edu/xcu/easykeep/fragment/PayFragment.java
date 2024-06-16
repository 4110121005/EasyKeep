package edu.xcu.easykeep.fragment;

import java.util.ArrayList;

import edu.xcu.easykeep.bean.TypeBean;
import edu.xcu.easykeep.db.BillDBManger;
import edu.xcu.easykeep.db.TypeDBManger;

public class PayFragment extends BaseTypeFragment {
    public ArrayList<TypeBean> getAllItem() {
        typeDBManger = new TypeDBManger(getContext());
        ArrayList<TypeBean> list = typeDBManger.getTypeListByKind(0);
        list.addAll(typeDBManger.getTypeListByKind(-1));
        return list;
    }

    @Override
    public void saveBillTODB() {
        bill.setKind(-1);
        bill.setMoney(Float.parseFloat(binding.etMoney.getText().toString()));
        BillDBManger billDBManger = new BillDBManger(getContext());
        billDBManger.insertBill(bill);
        billDBManger.billUpdateBroadcast(getContext(), "BILL_INSERT");
        successAddDialog(getContext());
    }

}

