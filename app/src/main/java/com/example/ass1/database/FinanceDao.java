package com.example.ass1.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FinanceDao
{
    @Insert
    void insertFinance(Finance finance);

    @Query("SELECT * FROM finance WHERE Title IN (:title)")
    List<Finance> findFinance(String title);

    @Query("SELECT * FROM finance WHERE type =:id")
    LiveData<List<Finance>> findType(int id);

    @Query("DELETE FROM finance WHERE Title =:title")
   public void DeleteFinance(String title);

    @Query("SELECT * FROM finance")
    LiveData<List<Finance>> getAllFinance();

    @Query("SELECT SUM(amount) FROM finance")
    LiveData<Integer> GetTotalAmount();

    @Query("SELECT SUM(amount) FROM finance WHERE type =:id")
    LiveData<Integer> GetTotalAmountID(int id);
}
