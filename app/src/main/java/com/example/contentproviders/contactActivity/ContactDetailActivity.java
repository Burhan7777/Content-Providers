package com.example.contentproviders.contactActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentproviders.MainActivity;
import com.example.contentproviders.R;

public class ContactDetailActivity extends AppCompatActivity {

    TextView getName, getNumber, getWork, getWorkMobile;
    String textName, textNumber, textWork, textHome, textNumberWithSpaces, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        getNumber = findViewById(R.id.getNumber);
        getName = findViewById(R.id.getName);
        getWork = findViewById(R.id.getWork);
        getWorkMobile = findViewById(R.id.getWorkMobile);

        Intent intent = getIntent();
        textName = intent.getStringExtra("name 1");
        textNumber = intent.getStringExtra("number");
        textWork = intent.getStringExtra("numberWork");
        textHome = intent.getStringExtra("numberHome");
        textNumberWithSpaces = intent.getStringExtra("numberWithSpaces");
        getName.setText(textName);
        getNumber.setText(textNumber);
        getWork.setText(textWork);
        getWorkMobile.setText(textHome);
    }

    public void deleteContact(View view) {
        int deletedRows = getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,
                ContactsContract.RawContacts._ID + "=?",
                new String[]{getContactId()});
        Log.i("DELETEd", String.valueOf(deletedRows));
        Toast.makeText(this, "Contact DELETED", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ContactDetailActivity.this, MainActivity.class));
    }

    public String getContactId() {
        String[] projection = {ContactsContract.RawContacts._ID};
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + "=?";
        String[] selectionArgs = new String[]{textName};
        Cursor cursor = getContentResolver().query(
                ContactsContract.RawContacts.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(ContactsContract.RawContacts._ID);
        String id = cursor.getString(index);
        cursor.close();
        return id;
    }

    public String getGroupIdOfBestie() {
        String[] projection = {ContactsContract.Groups._ID};
        String selection = ContactsContract.Groups.TITLE + "=?";
        String[] selectionArgs = new String[]{"Besties"};
        Cursor cursor = getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(ContactsContract.Groups._ID);
            id = cursor.getString(index);
        }
        cursor.close();
        return id;
    }


    public String getGroupIdOfFav() {
        String[] projection = {ContactsContract.Groups._ID};
        String selection = ContactsContract.Groups.TITLE + "=?";
        String[] selectionArgs = new String[]{"Starred in Android"};
        Cursor cursor = getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(ContactsContract.Groups._ID);
            id = cursor.getString(index);
        }
        cursor.close();
        return id;
    }


    public void goToUpdateActivity(View view) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("name", textName);
        intent.putExtra("number", textNumber);
        intent.putExtra("numberWithSpaces", textNumberWithSpaces);
        startActivity(intent);

    }

    public void addToBesties(View view) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, getContactId());
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, getGroupIdOfBestie());
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        Toast.makeText(this, "Added to besties", Toast.LENGTH_SHORT).show();
    }

    public void addToFav(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, getContactId());
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, getGroupIdOfFav());
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show();

    }
}