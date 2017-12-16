package com.example.milde.sqliteapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabasHandler dbh;
    private TextView tvAusgabe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DatabasHandler(this);
        tvAusgabe = (TextView) findViewById(R.id.tvAusgabe);
        tvAusgabe.setMovementMethod(new ScrollingMovementMethod());


        Button btnAddContact = (Button) findViewById(R.id.btnAddRecord);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.addContact(new Contact("Alexandra", "0123 456789"));
            }
        });


        Button btnListRecords = (Button) findViewById(R.id.btnListRecords);
        btnListRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reading all contacts
                List<Contact> contacts = dbh.getAllContacts();
                String log = "";

                for (Contact cn : contacts) {
                    log += "Id: " + cn.get_id()
                            + ", Name: " + cn.get_name()
                            + ", Phone: " + cn.get_phone_number()
                            + "\n";

                    // Writing Contacts to log
                }
                tvAusgabe.setText(log);
            }
        });
    }
}