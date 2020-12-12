package com.lexas.expensemanager.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lexas.expensemanager.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String>
{
    private Context context;
    ArrayList<ExpenseClass> expenseClassArrayList;

    public ListAdapter(@NonNull Context context, ArrayList<ExpenseClass> expenseClassArrayList) {
        super(context, R.layout.customcard);
        this.expenseClassArrayList = expenseClassArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println("inside list adapter");
        View view=LayoutInflater.from(context).inflate(R.layout.customcard,null,true);
        TextView view1=(TextView)view.findViewById(R.id.custom_description);
        view1.setText(expenseClassArrayList.get(position).getName());
        return view;
    }
}
