package com.example.contentproviders.accountActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.contentproviders.R;
import com.example.contentproviders.adapters.AccountContactAdapter;
import com.example.contentproviders.models.AccountModel;

import java.util.ArrayList;
import java.util.List;

public class AccountContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AccountContactAdapter accountContactAdapter;
    List<AccountModel> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_contact);
        recyclerView = findViewById(R.id.accountContactRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountContactAdapter = new AccountContactAdapter(myList);
        recyclerView.setAdapter(accountContactAdapter);

        Intent intent = getIntent();
        String textName = intent.getStringExtra("name");
        String textType = intent.getStringExtra("type");
        String selection = ContactsContract.RawContacts.ACCOUNT_NAME + "=?";
        String[] selectionArgs = {textName};

        Cursor cursor = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " COLLATE NOCASE ASC"
        );

        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY);
            String name = cursor.getString(index);
            AccountModel accountModel = new AccountModel();
            accountModel.setName(name);
            myList.add(accountModel);
        }
        cursor.close();


    }
}