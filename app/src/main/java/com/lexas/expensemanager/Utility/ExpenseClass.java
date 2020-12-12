package com.lexas.expensemanager.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.lexas.expensemanager.R;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseClass
{
    int id;
    String name;
    String expense_date;
    String expense_type;
    int amount;
    String note;

    public ExpenseClass() { }

    public ExpenseClass(int id, String name, String expense_date, String expense_type, int amount, String note) {
        this.id = id;
        this.name = name;
        this.expense_date = expense_date;
        this.expense_type = expense_type;
        this.amount = amount;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean addResponseToDataBase(ExpenseClass new_expense, Context context)
    {
        ExpenseController controller=new ExpenseController(context);
        SQLiteDatabase database=controller.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",new_expense.name);
        contentValues.put("expense_type",new_expense.expense_type);
        contentValues.put("expense_date",expense_date);
        contentValues.put("amount",new_expense.amount);
        contentValues.put("note",new_expense.note);
        long return_value=database.insert("expense",null,contentValues);
        if (return_value!=-1)
        {
            return true;
        }
        return false;
    }

    public ArrayList<ExpenseClass> getExpenseRecords(Context context,View root)
    {
        ArrayList<ExpenseClass> expenseClassArrayList;
        ExpenseController controller=new ExpenseController(root.getContext());
        SQLiteDatabase database=controller.getReadableDatabase();
        Cursor result=database.query("expense",root.getResources().getStringArray(R.array.expense),null,null,null,null,"expense_date "+"ASC",null);
        if (result==null)
        {
            System.out.println("no expense records available");
            return null;
        }
        else
        {
            expenseClassArrayList =new ArrayList<>();
            try {
                while (result.moveToNext())
                {
                    System.out.println(new ExpenseClass(result.getInt(0),result.getString(1),result.getString(2),result.getString(3),result.getInt(4),result.getString(5)).toString());
                    expenseClassArrayList.add(new ExpenseClass(result.getInt(0),result.getString(1),result.getString(2),result.getString(3),result.getInt(4),result.getString(5)));
                }
                result.close();
            }
            catch (CursorIndexOutOfBoundsException e)
            {
                System.out.println(e);
                return null;
            }
            return  expenseClassArrayList;
        }
    }
    @Override
    public String toString() {
        return "ExpenseClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expense_date='" + expense_date + '\'' +
                ", expense_type='" + expense_type + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }
    public int[] getCounts(ArrayList<ExpenseClass> expenseClassArrayList,View root)
    {
        String expense_type[]=root.getResources().getStringArray(R.array.expense_type);
        int counts[]=new int[expense_type.length];
        for (int i=0;i<expense_type.length;i++)
        {
            for (ExpenseClass expenseClass:expenseClassArrayList)
            {
                if (expenseClass.getExpense_type().equals(expense_type[i]))
                {
                    counts[i]++;
                }
            }
        }
        return counts;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getNetExpense(int mm,int yy, Context context) throws ParseException {
        ExpenseController controller=new ExpenseController(context);
        SQLiteDatabase database=controller.getReadableDatabase();
        String max_exp_date=Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+mm+"/"+yy;
        System.out.println("max date: "+max_exp_date);
        String min_expense_date=Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH)+"/"+mm+"/"+yy;
        System.out.println("min date: "+min_expense_date);
        Cursor cursor=database.rawQuery("select sum(amount) as total from expense where expense_date<=DATE_FORMAT("+max_exp_date+",'dd/mm/yy') and expense_date >="+min_expense_date,null);
        cursor.moveToFirst();
        int expense=cursor.getInt(0);
        return expense;
    }
}
