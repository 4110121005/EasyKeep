package edu.xcu.easykeep.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {

    private MutableLiveData<Boolean> daily; //记账日报
    private MutableLiveData<Boolean> monthly; //记账月报

    public SettingViewModel() {
        daily = new MutableLiveData<>();
        monthly = new MutableLiveData<>();
        daily.setValue(true);//默认开启日报
        monthly.setValue(true);//默认开启月报
    }

    public MutableLiveData<Boolean> getDaily() {
        return daily;
    }

    public void setDaily(MutableLiveData<Boolean> daily) {
        this.daily = daily;
    }

    public MutableLiveData<Boolean> getMonthly() {
        return monthly;
    }

    public void setMonthly(MutableLiveData<Boolean> monthly) {
        this.monthly = monthly;
    }
}