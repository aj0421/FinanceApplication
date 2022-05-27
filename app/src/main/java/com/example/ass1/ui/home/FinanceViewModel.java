package com.example.ass1.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ass1.database.Finance;
import com.example.ass1.database.FinanceRepository;

import java.util.List;

public class FinanceViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private FinanceRepository repository;
    private LiveData<List<Finance>> allFinance, findType;
    private MutableLiveData<List<Finance>> searchResults;
    LiveData<Integer> findAmount;
    LiveData<Integer> getAmountID;
    public FinanceViewModel(Application application)
    {
        super(application);
        repository = new FinanceRepository(application);
        allFinance = repository.getAllFinance();
        findAmount = repository.getBalance();
        searchResults = repository.getSearchResults();
        mText = new MutableLiveData<>();
        mText.setValue("This is finance fragment");
    }

    LiveData<Integer> getAmount(){
        return findAmount;
    }
    LiveData<Integer> getAmountID(int id){
        getAmountID = repository.getAmountID(id);
        return getAmountID;
    }

    MutableLiveData<List<Finance>> getSearchResults() {
        return searchResults;
    }

    LiveData<List<Finance>> getAllFinance() {
        return allFinance;
    }

    LiveData<List<Finance>> getType(int id) {
        findType = repository.getType(id);
        return findType;
    }

}