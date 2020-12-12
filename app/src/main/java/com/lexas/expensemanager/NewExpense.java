package com.lexas.expensemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lexas.expensemanager.Utility.DataValidator;
import com.lexas.expensemanager.Utility.ExpenseClass;
import com.lexas.expensemanager.ui.main.Expense;

import java.util.ArrayList;
import java.util.Calendar;

public class NewExpense extends AppCompatActivity {

    EditText expenseDescription,expense_amount,expense_note;
    Spinner ExpenseType;
    TextView calendarView;
    Button save_response;
    int dd,mm,yy;
    private String[] expense_type_list;
    private ExpenseClass new_expense =new ExpenseClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        expenseDescription=(EditText)findViewById(R.id.description);
        expense_amount=(EditText)findViewById(R.id.amount);
        expense_note=(EditText)findViewById(R.id.expense_note);
        calendarView=(TextView) findViewById(R.id.date);

        //creating a datepicker to select date
        calendarView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                DatePickerDialog datePickerDialog=new DatePickerDialog(NewExpense.this,android.R.style.ThemeOverlay_Material_Dark_ActionBar,new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendarView.setText(dayOfMonth+"/"+month+"/"+year);
                        dd=dayOfMonth;
                        mm=month;
                        yy=year;
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                datePickerDialog.getWindow().setLayout(800,1024);
                datePickerDialog.show();
            }
        });


        save_response=(Button)findViewById(R.id.save_expense);
        ExpenseType=(Spinner)findViewById(R.id.type_spinner);
        expense_type_list=getResources().getStringArray(R.array.expense_type);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,expense_type_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ExpenseType.setAdapter(adapter);
        ExpenseType.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ExpenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new_expense.setExpense_type(expense_type_list[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(400).playOn(save_response);
                System.out.println(expenseDescription.getText().toString()+":description "+expense_amount.getText().toString()+":amount");
                if (!expenseDescription.getText().toString().isEmpty())
                {
                    //System.out.println("inside if of Description");
                    new_expense.setName(expenseDescription.getText().toString());
                    if (!expense_amount.getText().toString().isEmpty())
                    {
                        new_expense.setAmount(Integer.parseInt(expense_amount.getText().toString()));
                        if (new DataValidator().validateDate(dd,mm,yy))
                        {
                            new_expense.setExpense_date(dd+"/"+mm+"/"+yy);
                            new_expense.setNote(expense_note.getText().toString());
                            //System.out.println(new_expense.toString());
                            if (new_expense.addResponseToDataBase(new_expense,getApplicationContext())&&new_expense.getExpense_type().equals("Select Expense Type")==false)
                            {
                                Toast.makeText(getApplicationContext(),"Expense Data Recorded",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Please Select Expense Type",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Enter a valid Date",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        expense_amount.setError("Field Required!");
                    }
                }
                else
                {
                    expenseDescription.setError("Field Required!");
                }
            }
        });

    }
}