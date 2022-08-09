package com.example.contentproviders.accountActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.contentproviders.R;
import com.example.contentproviders.adapters.AccountAdapter;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AccountAdapter accountAdapter;
    List<String> myList = new ArrayList<>();
    List<String> myList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        recyclerView = findViewById(R.id.accountRecycler);
        accountAdapter = new AccountAdapter(myList, this, myList1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accountAdapter);

        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            myList.add(account.name);
            myList1.add(account.type);
        }

    }
}