package com.example.contentproviders.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentproviders.contactActivity.ContactDetailActivity;
import com.example.contentproviders.R;
import com.example.contentproviders.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {

    List<ContactModel> myList;
    Context context;


    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }


    public ContactAdapter(ArrayList<ContactModel> myList, Context context) {
        this.context = context;
        this.myList = myList;
        notifyDataSetChanged();
    }

    public void filteredList(ArrayList<ContactModel> filteredList) {
        this.myList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_xml, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ContactModel contactModel = myList.get(position);
        holder.name.setText(contactModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("name", contactModel.getName());
                intent.putExtra("number", contactModel.getPhone());
                intent.putExtra("numberWork", contactModel.getPhoneWork());
                intent.putExtra("numberHome", contactModel.getPhoneWorkMobile());
                intent.putExtra("numberWithSpaces", contactModel.getNumberWithSpaces());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}

