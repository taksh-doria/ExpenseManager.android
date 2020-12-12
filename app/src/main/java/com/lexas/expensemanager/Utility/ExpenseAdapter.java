package com.lexas.expensemanager.Utility;

import android.animation.Animator;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lexas.expensemanager.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    Context context;
    ArrayList<ExpenseClass> expenseClassArrayList;

    public ExpenseAdapter(Context context, ArrayList<ExpenseClass> expenseClassArrayList) {
        //System.out.print("print of passed arraylist: "+expenseClassArrayList);
        this.context = context;
        this.expenseClassArrayList = expenseClassArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customcard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        String description,date,inr,type;
        inr=expenseClassArrayList.get(position).getAmount()+" INR";
        description=expenseClassArrayList.get(position).getName();
        date=expenseClassArrayList.get(position).getExpense_date();
        type=expenseClassArrayList.get(position).getExpense_type();
        holder.description.setText(description);
        holder.date.setText(date);
        holder.inr.setText(inr);
        holder.type.setText(type);

    }

    @Override
    public int getItemCount() {
        return expenseClassArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView description,date,inr,type;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            description=(TextView) itemView.findViewById(R.id.custom_description);
            date=(TextView)itemView.findViewById(R.id.custom_date);
            inr=(TextView)itemView.findViewById(R.id.custom_amount);
            type=(TextView)itemView.findViewById(R.id.custom_type);
            cardView=(CardView)itemView.findViewById(R.id.customcard);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YoYo.with(Techniques.Pulse).duration(500).playOn(cardView);
                }
            });
        }
    }
}
