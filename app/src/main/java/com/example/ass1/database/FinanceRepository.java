package com.example.ass1.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class FinanceRepository {

    private MutableLiveData<List<Finance>> searchResults =
            new MutableLiveData<>();
    private LiveData<List<Finance>> allFinance;
    private LiveData<Integer> getBalance;
    private LiveData<Integer> getAmountID;
    private LiveData<List<Finance>> findType;
    private FinanceDao financeDao;

    public FinanceRepository(Application application) {
        FinanceRoomDatabase db;
        db = FinanceRoomDatabase.getDatabase(application);
        financeDao = db.financeDao();
        allFinance = financeDao.getAllFinance();
        getBalance = financeDao.GetTotalAmount();
    }

    public void insertFinance(Finance newFinance) {
        InsertAsyncTask task = new InsertAsyncTask(financeDao);
        task.execute(newFinance);
    }

    public void deleteFinance(String title) {
        DeleteAsyncTask task = new DeleteAsyncTask(financeDao);
        task.execute(title);
    }

    public void findFinance(String title) {
        QueryAsyncTask task = new QueryAsyncTask(financeDao);
        task.delegate = this;
        task.execute(title);
    }

   public  LiveData<Integer> getBalance(){
       return getBalance;
   }

    public  LiveData<Integer> getAmountID(int id){

       getAmountID = financeDao.GetTotalAmountID(id);
        return getAmountID;
    }

    public LiveData<List<Finance>> getAllFinance() {
        return allFinance;
    }

    public LiveData<List<Finance>> getType(int id){
        findType = financeDao.findType(id);
        return findType;
    }

    public MutableLiveData<List<Finance>> getSearchResults() {
        return searchResults;
    }
    private void asyncFinished(List<Finance> results) {
        searchResults.setValue(results);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<Finance>> {

        private FinanceDao asyncTaskDao;
        private FinanceRepository delegate = null;

        //AsyncTask implementation for inserting products into the database:
        QueryAsyncTask(FinanceDao dao) {
            asyncTaskDao = dao;
        }

        //String containing the product name for which the search is to be performed,
        @Override
        protected List<Finance> doInBackground(final String... params){
            return  asyncTaskDao.findFinance(params[0]);
        }

        @Override
        protected void onPostExecute(List<Finance> result) {
            delegate.asyncFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Finance, Void, Void> {

        private FinanceDao asyncTaskDao;

        InsertAsyncTask(FinanceDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Finance... params) {
            asyncTaskDao.insertFinance(params[0]);
            return null;
        }
    }

    // will be used when deleting products from the database
    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void> {

        private FinanceDao asyncTaskDao;

        DeleteAsyncTask(FinanceDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.DeleteFinance(params[0]);
            return null;
        }
    }
}
