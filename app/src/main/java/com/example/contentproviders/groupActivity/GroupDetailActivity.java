package com.example.contentproviders.groupActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.contentproviders.R;
import com.example.contentproviders.adapters.GroupContactAdapter;
import com.example.contentproviders.models.GroupContactModel;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailActivity extends AppCompatActivity {

    String textTitle;
    String id;
    List<GroupContactModel> myList = new ArrayList<>();
    RecyclerView recyclerView;
    GroupContactAdapter groupContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        recyclerView = findViewById(R.id.groupContact);
        groupContactAdapter = new GroupContactAdapter(myList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(groupContactAdapter);
        Intent intent = getIntent();
        textTitle = intent.getStringExtra("title");
        Toast.makeText(this, textTitle, Toast.LENGTH_SHORT).show();


        String selection = ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + "=?"
                + " AND "
                + ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'";
        String[] selectionArgs = new String[]{getGroupId()};
        String[] projection = new String[]{ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, ContactsContract.Data.DISPLAY_NAME_PRIMARY};

        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                ContactsContract.Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        while (cursor.moveToNext()) {
            GroupContactModel item = new GroupContactModel();
            int index = (cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            item.setName(cursor.getString(index));
            int index1 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID);
            item.setId(cursor.getString(index1));
            myList.add(item);
        }
        cursor.close();
    }

    public String getGroupId() {
        String[] projection = {ContactsContract.Groups._ID};
        String selection = ContactsContract.Groups.TITLE + "=?";
        String[] selectionArgs = new String[]{textTitle};
        Cursor cursor = getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(ContactsContract.Groups._ID);
            id = cursor.getString(index);
        }
        cursor.close();
        return id;
    }

}