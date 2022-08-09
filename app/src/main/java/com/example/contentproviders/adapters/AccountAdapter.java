package com.example.contentproviders.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentproviders.accountActivity.AccountContactActivity;
import com.example.contentproviders.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.viewHolder> {

    List<String> myList, myList1;
    Context context;

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);
        }
    }

    public AccountAdapter(List<String> myList, Context context, List<String> myList1) {
        this.myList1 = myList1;
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_accounts, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.textView.setText(myList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AccountContactActivity.class);
                intent.putExtra("name", myList.get(holder.getAdapterPosition()));
                intent.putExtra("type", myList1.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}
