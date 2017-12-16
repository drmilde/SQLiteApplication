package com.example.milde.sqliteapplication;

import android.support.annotation.NonNull;
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


        // Kontakt einfügen und Liste aktualisieren
        Button btnAddContact = (Button) findViewById(R.id.btnAddRecord);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.addContact(new Contact("Rebecca", "0123 456789"));
                updateAusgabeListe();
            }
        });


        // Auflisten der aktuellen Records
        Button btnListRecords = (Button) findViewById(R.id.btnListRecords);
        btnListRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Reading all contacts
                updateAusgabeListe();
            }
        });

        // Count number of records
        Button btnCountRecords = (Button)findViewById(R.id.btnCountRecords);
        btnCountRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = dbh.getContactsCount();
                setAusgabe(count+"");
            }
        });

    }

    private void updateAusgabeListe() {
        String log = getRecordString();
        setAusgabe(log);
    }

    public void setAusgabe(String ausgabe) {
        tvAusgabe.setText(ausgabe);
    }

    @NonNull
    private String getRecordString() {
        List<Contact> contacts = dbh.getAllContacts();
        String log = "";

        for (Contact cn : contacts) {
            log += "Id: " + cn.get_id()
                    + ", Name: " + cn.get_name()
                    + ", Phone: " + cn.get_phone_number()
                    + "\n";
        }
        return log;
    }

}