package com.example.contentproviders.contactActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contentproviders.MainActivity;
import com.example.contentproviders.R;

public class ContactAddActivity extends AppCompatActivity {

    EditText name, number, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        name = findViewById(R.id.editName);
        number = findViewById(R.id.editNumber);
        email = findViewById(R.id.editEmail);

    }

    public void addContact(View view) {
        String textName = name.getText().toString();
        String textNumber = number.getText().toString();
        String textEmail = email.getText().toString();

        long contactID = getContactID();
        addDisplayName(ContactsContract.Data.CONTENT_URI, contactID, textName);
        addPhone(ContactsContract.Data.CONTENT_URI, contactID, textNumber);
        addEmail(ContactsContract.Data.CONTENT_URI, contactID, textEmail);

        Toast.makeText(this, "New Contact Added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ContactAddActivity.this, MainActivity.class));
        finish();

    }

    private void addEmail(Uri contentUri, long contactID, String textEmail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactID);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        contentValues.put(ContactsContract.CommonDataKinds.Email.DATA, textEmail);
        getContentResolver().insert(contentUri, contentValues);

    }

    private void addPhone(Uri contentUri, long contactID, String textNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactID);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, textNumber);
        getContentResolver().insert(contentUri, contentValues);
    }

    private void addDisplayName(Uri contentUri, long contactID, String textName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactID);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, textName);
        getContentResolver().insert(contentUri, contentValues);
    }

    private long getContactID() {
        ContentValues contentValues = new ContentValues();
        Uri getContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        return ContentUris.parseId(getContactUri);
    }













      /*  // Through ContentProviderOperation , Not working currently

        ArrayList<ContentProviderOperation> myOperations = new ArrayList<>();


        if (textEmail.isEmpty() || textName.isEmpty() || textNumber.isEmpty()) {
            Toast.makeText(this, "Please Enter Details", Toast.LENGTH_SHORT).show();
        } else {
            myOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, myOperations.size())
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, textName)
                    .build());

            myOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, myOperations.size())
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, textNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());

            myOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, myOperations.size())
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, textName)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, myOperations);
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Contact", e.getMessage());
            Toast.makeText(this, "Could not add", Toast.LENGTH_SHORT).show();
        }

    } */
}
