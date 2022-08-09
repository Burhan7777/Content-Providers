package com.example.contentproviders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentproviders.R;
import com.example.contentproviders.models.AccountModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountContactAdapter extends RecyclerView.Adapter<AccountContactAdapter.viewHolder> {

    List<AccountModel> myList;

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView3);
        }
    }

    public AccountContactAdapter(List<AccountModel> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public AccountContactAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_account_contact, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountContactAdapter.viewHolder holder, int position) {
        AccountModel accountModel = myList.get(position);
        holder.textView.setText(accountModel.getName());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
