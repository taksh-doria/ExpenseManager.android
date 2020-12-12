package com.lexas.expensemanager.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lexas.expensemanager.NewExpense;
import com.lexas.expensemanager.R;
import com.lexas.expensemanager.Utility.ExpenseAdapter;
import com.lexas.expensemanager.Utility.ExpenseClass;
import com.lexas.expensemanager.Utility.ExpenseController;

import java.util.ArrayList;

public class Expense extends Fragment {

    View root;
    private  ArrayList<ExpenseClass> expenseClassArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_expense, container, false);
        FloatingActionButton floatingActionButton=(FloatingActionButton)root.findViewById(R.id.new_record);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), NewExpense.class));
            }
        });
        expenseClassArrayList=new ExpenseClass().getExpenseRecords(getContext(),root);
        if (expenseClassArrayList!=null)
        {
            System.out.println("before adapter created,current state of arraylist: "+expenseClassArrayList);
            ExpenseAdapter adapter=new ExpenseAdapter(getContext(),expenseClassArrayList);
            System.out.println("After adapter created!");
            RecyclerView recyclerView=(RecyclerView)root.findViewById(R.id.expenserecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(adapter);
        }
        else
        {
            System.out.println("no expense records available");
            TextView textView=(TextView)root.findViewById(R.id.errortext);
            textView.setText("No Expense Records Available!.");
            textView.setVisibility(View.VISIBLE);
        }
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        RecyclerView recyclerView=(RecyclerView)root.findViewById(R.id.expenserecycler);
        expenseClassArrayList=new ExpenseClass().getExpenseRecords(getContext(),root);
        if (expenseClassArrayList!=null)
        {
            System.out.println("before adapter created,current state of arraylist: "+expenseClassArrayList);
            ExpenseAdapter adapter=new ExpenseAdapter(getContext(),expenseClassArrayList);
            System.out.println("After adapter created!");
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(adapter);
        }
        else
        {
            System.out.println("no expense records available");
            TextView textView=(TextView)root.findViewById(R.id.errortext);
            textView.setText("No Expense Records Available!.");
            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
