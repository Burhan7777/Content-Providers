package com.example.contentproviders.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentproviders.groupActivity.GroupDetailActivity;
import com.example.contentproviders.R;
import com.example.contentproviders.models.GroupModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.viewHolder> {

    List<GroupModel> myList = new ArrayList<>();
    Context context;

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.showFav);
        }
    }

    public GroupAdapter(List<GroupModel> myList, Context context) {
        this.context = context;
        this.myList = myList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_group, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        GroupModel groupModel = myList.get(position);
        holder.textView.setText(groupModel.getFavourites());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GroupDetailActivity.class);
                intent.putExtra("title", groupModel.getFavourites());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}
