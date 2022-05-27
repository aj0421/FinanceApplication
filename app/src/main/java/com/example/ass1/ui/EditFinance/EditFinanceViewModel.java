package com.example.ass1.ui.EditFinance;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ass1.database.Finance;
import com.example.ass1.database.FinanceRepository;

import java.util.List;

public class EditFinanceViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private FinanceRepository repository;
    private LiveData<List<Finance>> allFinance;
    private MutableLiveData<List<Finance>> searchResults;
    private LiveData<Integer> findAmount;
    public EditFinanceViewModel(Application application)
    {
        super(application);
        repository = new FinanceRepository(application);
        allFinance = repository.getAllFinance();
        searchResults = repository.getSearchResults();
        findAmount = repository.getBalance();
        mText = new MutableLiveData<>();
        mText.setValue("This is finance fragment");
    }

    public LiveData<Integer> getAmount(){
        return findAmount;
    }

    MutableLiveData<List<Finance>> getSearchResults() {
        return searchResults;
    }

    public void insertFinance(Finance finance) {
        repository.insertFinance(finance);
    }

    public void findFinance(String title) {
        repository.findFinance(title);
    }

    public void deleteFinance(String title) {
        repository.deleteFinance(title);

    }
}