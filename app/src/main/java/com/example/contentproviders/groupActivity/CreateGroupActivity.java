package com.example.contentproviders.groupActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import com.example.contentproviders.R;

public class CreateGroupActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        editText = findViewById(R.id.groupName);

    }

    public void createGroup(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Groups.TITLE, editText.getText().toString());
        contentValues.put(ContactsContract.Groups.SHOULD_SYNC, true);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(ContactsContract.Groups.CONTENT_URI, contentValues);
        startActivity(new Intent(CreateGroupActivity.this, GroupActivity.class));
        finish();
    }
}