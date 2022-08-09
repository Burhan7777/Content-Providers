package com.example.contentproviders.groupActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.example.contentproviders.R;
import com.example.contentproviders.adapters.GroupAdapter;
import com.example.contentproviders.models.GroupModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    List<GroupModel> myList = new ArrayList<>();
    RecyclerView recyclerView;
    GroupAdapter groupAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        recyclerView = findViewById(R.id.groupRecycler);
        fab = findViewById(R.id.createGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new GroupAdapter(myList, this);
        recyclerView.setAdapter(groupAdapter);
        Cursor cursor = getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        ArrayList<String> groupTitle = new ArrayList<>();
        while (cursor.moveToNext()) {
            GroupModel groupModel = new GroupModel();
            int index = cursor.getColumnIndex(ContactsContract.Groups._ID);
            groupModel.setId(cursor.getString(index));
            int index1 = cursor.getColumnIndex(ContactsContract.Groups.TITLE);
            String favourites = cursor.getString(index1);
            if (favourites.contains("Group:"))
                favourites = favourites.substring(favourites.indexOf("Group:") + "Group:".length()).trim();

            if (favourites.contains("Favorite_"))
                favourites = "Favorite";

            if (favourites.contains("My Contacts"))
                continue;

            if (groupTitle.contains(favourites)) {
                for (GroupModel group : myList) {
                    if (group.getFavourites().equals(favourites)) {
                        group.setId(groupModel.getId());
                        break;
                    }
                }
            } else {
                groupTitle.add(favourites);
                groupModel.setFavourites(favourites);
                myList.add(groupModel);
            }
        }
        cursor.close();
        Collections.sort(myList, new Comparator<GroupModel>() {
            public int compare(GroupModel item1, GroupModel item2) {
                return item2.getFavourites().compareTo(item1.getFavourites()) < 0
                        ? 0 : -1;
            }
        });
    }

    public void startCreateGroupActivity(View view) {
        startActivity(new Intent(GroupActivity.this, CreateGroupActivity.class));
    }
}