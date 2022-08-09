package com.example.contentproviders.contactActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentproviders.MainActivity;
import com.example.contentproviders.R;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    EditText name, number;
    String textName, textNumber, textNumberWithSpaces;
    String id1, id2;
    int phoneContactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name = findViewById(R.id.editNewName);
        number = findViewById(R.id.editNewNumber);

        Intent intent = getIntent();
        textName = intent.getStringExtra("name");
        textNumber = intent.getStringExtra("number");
        textNumberWithSpaces = intent.getStringExtra("numberWithSpaces");

        name.setText(textName);
        number.setText(textNumber);
    }

    public void getUpdate(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name.getText().toString());
        int rowsUpdated = getContentResolver().update(ContactsContract.Data.CONTENT_URI, contentValues,
                ContactsContract.CommonDataKinds.StructuredName._ID + "=?", new String[]{getContactId()});

     /*   ContentValues contentValues1 = new ContentValues();
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        contentValues1.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues1.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number.getText().toString());
        int rowsUpdated1 = getContentResolver().update(ContactsContract.Data.CONTENT_URI, contentValues1,
                ContactsContract.CommonDataKinds.Phone._ID + "=?", new String[]{getContactByNumber()});
        Log.i("Updated", String.valueOf(rowsUpdated)); */


        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
        contentProviderOperations.add(ContentProviderOperation.
                newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.CommonDataKinds.Phone._ID + "=? AND "
                                + ContactsContract.Data.MIMETYPE + "='"
                                + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
                        new String[]{getContactIdByNumber()})
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number.getText().toString())
                .build());

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(UpdateActivity.this, MainActivity.class));
        finish();

    }

    public String getContactIdByNumber() {
        String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + "=?";
        String[] selectionArgs = new String[]{textNumberWithSpaces};
        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
            id2 = cursor.getString(index);
            cursor.close();
        } else
            Toast.makeText(this, "Some Error in Number", Toast.LENGTH_SHORT).show();
        return id2;
    }

    public String getContactId() {
        String selection = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{textName};
        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName._ID);
            id1 = cursor.getString(index);
            cursor.close();
        } else
            Toast.makeText(this, "Some Error in Name", Toast.LENGTH_SHORT).show();
        return id1;
    }
}