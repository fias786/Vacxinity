package com.sharnoxz.vacxinity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<VerticalData>> data;

    public HomeViewModel() {
        data = new MutableLiveData<>();
        DataSources DataSources = new DataSources();
        data.setValue(DataSources.loadVerticalData());
    }

    public LiveData<ArrayList<VerticalData>> getData() {
        return data;
    }
}
