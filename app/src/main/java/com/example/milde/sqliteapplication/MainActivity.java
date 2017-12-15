package com.example.milde.sqliteapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private DatabasHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DatabasHandler(this);

        Button btnAddContact = (Button)findViewById(R.id.btnAddRecord);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.addContact(new Contact("Alexandra", "0123 456789"));
            }
        });


        Button btnListRecords = (Button)findViewById(R.id.btnListRecords);
        btnListRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO code für auflisten der records einfügen
            }
        });

    }
}
