package com.lexas.expensemanager.Utility;

import android.widget.Toast;

import java.util.Calendar;

public class DataValidator
{
    public boolean validateDate(int dd,int mm,int yy)
    {
        if (yy<=Calendar.getInstance().get(Calendar.YEAR))
        {
            System.out.println("year is correct: "+yy);
            if (mm<Calendar.getInstance().get(Calendar.MONTH))
            {
                return true;
            }
            else if (mm==Calendar.getInstance().get(Calendar.MONTH))
            {
                if (dd<=Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    return true;
                else
                    return false;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
