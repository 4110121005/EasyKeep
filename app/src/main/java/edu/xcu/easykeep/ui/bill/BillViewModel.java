package edu.xcu.easykeep.ui.bill;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.xcu.easykeep.bean.BillBean;
import edu.xcu.easykeep.db.BillDBManger;

public class BillViewModel extends ViewModel {

    private LiveData<List<BillBean>> billList; //账单列表
    private BillDBManger billDBManger;

    public BillViewModel(Application application) {
        billDBManger = new BillDBManger(application);
    }
}