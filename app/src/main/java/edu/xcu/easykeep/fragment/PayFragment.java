package edu.xcu.easykeep.fragment;

import java.util.ArrayList;

import edu.xcu.easykeep.bean.TypeBean;
import edu.xcu.easykeep.db.BillDBManger;
import edu.xcu.easykeep.db.TypeDBManger;
import edu.xcu.easykeep.utils.SimpleDialog;

public class PayFragment extends BaseTypeFragment {
    @Override
    public ArrayList<TypeBean> getAllItem() {
        typeDBManger = new TypeDBManger(getContext());
        ArrayList<TypeBean> list = typeDBManger.getTypeListByKind(0);
        list.addAll(typeDBManger.getTypeListByKind(-1));
        return list;
    }

    @Override
    public void saveBillTODB() {
        bill.setKind(-1);
        Float money = getInputMoney();
        if(money == null){
            SimpleDialog.showHintDialog(getContext(), "请输入金额！");
            return;
        }
        bill.setMoney(money*-1);
        BillDBManger billDBManger = new BillDBManger(getContext());
        billDBManger.insertBill(bill);
        SimpleDialog.showHintDialog(getContext(), "添加成功！");
    }
}

