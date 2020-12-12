package com.lexas.expensemanager.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lexas.expensemanager.NewExpense;
import com.lexas.expensemanager.R;
import com.lexas.expensemanager.Utility.ExpenseClass;

import java.util.ArrayList;
import java.util.Calendar;


public class Home extends Fragment {

    PieChart pieChart;
    int colors[]={R.color.c1,R.color.c2,R.color.c3,R.color.c4,R.color.c5,R.color.c6,R.color.c7,R.color.c8,R.color.c9,R.color.c10,R.color.c11};
    View root;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_home, container, false);
        pieChart=(PieChart)root.findViewById(R.id.pieChart);
        feedData();
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        feedData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void  feedData()
    {
        ArrayList<PieEntry> list=new ArrayList<>();
        ExpenseClass expenseClass=new ExpenseClass();
        ArrayList<ExpenseClass> expenseClassArrayList=expenseClass.getExpenseRecords(root.getContext(),root);
        System.out.println("size of list: "+expenseClassArrayList.size());
        TextView textView=(TextView)root.findViewById(R.id.accbasics);
        if (expenseClassArrayList.size()!=0)
        {
            textView.setVisibility(View.INVISIBLE);
            int counts[]=expenseClass.getCounts(expenseClassArrayList,root);
            String expense_type[]=root.getResources().getStringArray(R.array.expense_type);
            System.out.println("Count data from feedData\t"+counts.length+"\tlength of expense array\t"+expense_type.length);
            if (counts!=null) {
                for (int i = 0; i < counts.length; i++) {
                    if (counts[i]!=0)
                    {
                        list.add(new PieEntry(counts[i], expense_type[i]));
                    }
                }
                PieDataSet dataSet = new PieDataSet(list, "Expense OverView");
                dataSet.setColors(colors,root.getContext());
                PieData data = new PieData(dataSet);
                pieChart.setData(data);
                pieChart.animateY(700);
                pieChart.setCenterText("Expense Overview");
                pieChart.setUsePercentValues(true);
                pieChart.invalidate();
                LinearLayout linearLayout=(LinearLayout)root.findViewById(R.id.expense_layout);
                linearLayout.setVisibility(View.VISIBLE);
                try {
                    System.out.println(expenseClass.getNetExpense(Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.YEAR),root.getContext()));
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        }
        else
        {
            System.out.println("inside else");
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Expense Data Available");
        }
    }
}
