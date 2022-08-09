package com.example.contentproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.contentproviders.accountActivity.AccountActivity;
import com.example.contentproviders.adapters.ContactAdapter;
import com.example.contentproviders.contactActivity.ContactAddActivity;
import com.example.contentproviders.groupActivity.GroupActivity;
import com.example.contentproviders.models.ContactModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ContactModel> myList = new ArrayList<>();
    ContactAdapter contactAdapter;
    FloatingActionButton fab;
    String numberMobile;
    String numberHome;
    String numberWork;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder stringBuilder3 = new StringBuilder();
    String numbers;
    String numbers1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerContact);
        fab = findViewById(R.id.FAB);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(myList, this);
        recyclerView.setAdapter(contactAdapter);
        Toast.makeText(this, "Hello this is new commit", Toast.LENGTH_SHORT).show();
        checkPermission();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContactAddActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(null);
        for (Account account : accounts) {
            Log.i("ACCOUNTS", account.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s.toLowerCase());
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        ArrayList<ContactModel> filteredList = new ArrayList<>();
        for (ContactModel model : myList) {
            if (model.getName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(model);
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Results Found", Toast.LENGTH_SHORT).show();
        } else {
            contactAdapter.filteredList(filteredList);
        }

    }

    public void goToAccountActivity(View view) {
        startActivity(new Intent(MainActivity.this, AccountActivity.class));
    }

    public void goToGroupsActivity(View view) {
        startActivity(new Intent(MainActivity.this, GroupActivity.class));
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 110);
        } else
            getContactList();
    }

    private void getContactList() {
        String sort = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE NOCASE ASC";
        Cursor nameCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                sort);

        if (nameCursor.getCount() > 0) {
            while (nameCursor.moveToNext()) {
                int index = nameCursor.getColumnIndex(ContactsContract.Contacts._ID);
                String id = nameCursor.getString(index);
                int index1 = nameCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
                String name = nameCursor.getString(index1);
                int index4 = nameCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        selection,
                        new String[]{id},
                        null);
                while (phoneCursor.moveToNext()) {
                    int index3 = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int type = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int type1 = phoneCursor.getInt(type);

                    switch (type1) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            numberWork = phoneCursor.getString(index3);
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            numberMobile = phoneCursor.getString(index3);

                            stringBuilder.append(numberMobile).append("-").append("\n");
                            numbers = stringBuilder.toString();
                            numbers1 = numbers.replaceAll("\\s", "");

                            String[] token = numbers1.split("-");
                            stringBuilder3 = new StringBuilder();
                            Set<String> alreadyPresent = new HashSet<String>();

                            boolean first = true;
                            for (String tokens : token) {
                                if (!alreadyPresent.contains(tokens)) {
                                    if (first)
                                        first = false;
                                    else stringBuilder3.append("\n");

                                    if (!alreadyPresent.contains(tokens))
                                        stringBuilder3.append(tokens);
                                }

                                alreadyPresent.add(tokens);


                            }

                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            numberHome = phoneCursor.getString(index3);
                            break;
                    }
                }
                ContactModel contactModel = new ContactModel();
                contactModel.setName(name);
                contactModel.setNumberWithSpaces(numberMobile);

                contactModel.setPhone(stringBuilder3.toString());
                contactModel.setPhoneWork(numberWork);
                contactModel.setPhoneWorkMobile(numberHome);
                myList.add(contactModel);
                numberMobile = "";
                numberWork = "";
                numberHome = "";
                stringBuilder.setLength(0);
                phoneCursor.close();
            }

            nameCursor.close();

        } else
            Toast.makeText(this, "Cursor Zero", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContactList();
        } else {
            //   checkPermission();
        }
    }


}
