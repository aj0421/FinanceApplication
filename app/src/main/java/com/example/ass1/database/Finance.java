package com.example.ass1.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "finance")
public class Finance
{
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name="idPR")
    private int idPr;
    @ColumnInfo(name="type")
    private int id;
    @ColumnInfo(name="Date")
    private String date;
    @ColumnInfo(name="Title")
    private String title;
    @ColumnInfo(name = "Category")
    private  String category;
    @ColumnInfo(name = "Amount")
    private int amount;
    @ColumnInfo(name = "months")
    public int month;

    public Finance(int id, String date, String title, String category, int amount)
    {
        this.id = id;
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }
    @Ignore
    public Finance(int id, String date, String title, String category, int amount, int month)
    {
        this.id = id;
        this.date = date;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.month = month;
    }
    public int getId(){
        return this.id;
    }

    public int getIdPr(){
        return this.idPr;
    }

    public String getDate(){return this.date;}

    public String getTitle(){
        return this.title;
    }

    public String getCategory(){
        return this.category;
    }

    public int getAmount()
    {
        return this.amount;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setIdPr(int idpr){
        this.idPr=idpr;
    }


    @NonNull
    @Override
    public String toString() {
        return "ID= " + id+ "date = " + date+", title = "+ title + ", category = " + category + ", amount = " + amount;
    }
}
