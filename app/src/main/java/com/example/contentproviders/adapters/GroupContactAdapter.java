package com.example.contentproviders.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentproviders.R;
import com.example.contentproviders.models.GroupContactModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupContactAdapter extends RecyclerView.Adapter<GroupContactAdapter.viewHolder> {

    List<GroupContactModel> myList;

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public GroupContactAdapter(List<GroupContactModel> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_group_contacts, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        GroupContactModel contactModel = myList.get(position);
        holder.textView.setText(contactModel.getName());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}
