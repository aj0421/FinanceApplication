package com.example.ass1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Finance.class}, version = 1)
public abstract class FinanceRoomDatabase extends RoomDatabase
{
    public  abstract FinanceDao financeDao();

    private  static FinanceRoomDatabase INSTANCE;

    public static FinanceRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (FinanceRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FinanceRoomDatabase.class, "finance_database").build();
                }
            }
        }
        return  INSTANCE;
    }
}
