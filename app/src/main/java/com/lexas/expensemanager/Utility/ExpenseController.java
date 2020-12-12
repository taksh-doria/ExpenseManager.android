package com.lexas.expensemanager.Utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ExpenseController extends SQLiteOpenHelper
{

    public ExpenseController(@Nullable Context context) {
        super(context,"expense.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table expense ( id INTEGER PRIMARY KEY AUTOINCREMENT "+
                ",name varchar(20),expense_date date,expense_type varchar(20),amount int,note varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}