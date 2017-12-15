package com.example.milde.sqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milde on 15.12.17.
 */

public class DatabasHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kontaktDatenbank";
    public static final String TABLE_CONTACTS = "kontakte";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE_NUMBER = "phone";


    public DatabasHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STATEMENT =
                "CREATE TABLE " + TABLE_CONTACTS
                        + " ("
                        + KEY_ID + " INTEGER PRIMARY KEY, "
                        + KEY_NAME + " TEXT, "
                        + KEY_PHONE_NUMBER + " TEXT"
                        + ")";

        Log.d("Jan", CREATE_STATEMENT);

        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // loeschen, falls Table existiert
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // wieder erzeugen
        onCreate(sqLiteDatabase);
    }


    // CRUD Methoden

    public void addContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, c.get_name());
        values.put(KEY_PHONE_NUMBER, c.get_phone_number());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_ID, KEY_NAME, KEY_PHONE_NUMBER
        };

        String pattern = KEY_ID + "=?";

        String[] values = {
                String.valueOf(id)
        };

        Cursor cursor = db.query(TABLE_CONTACTS,
                columns, pattern, values,
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );


        // TODO muss der cursor hier geschlossen werden ????

        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact c = new Contact();
                c.set_id(Integer.parseInt(cursor.getString(0)));
                c.set_name(cursor.getString(1));
                c.set_phone_number(cursor.getString(2));
                contactList.add(c);
            } while (cursor.moveToNext());
        }

        // TODO muss der cursor hier geschlossen werden ?
        // cursor.close(); ??????

        return contactList;
    }

    public int getContactsCount() {
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();


        return cursor.getCount();
    }

    public int updateContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, c.get_name());
        values.put(KEY_PHONE_NUMBER, c.get_phone_number());

        return db.update (
                TABLE_CONTACTS,
                values,
                KEY_ID + "=?",
                new String[] {String.valueOf(c.get_id())}
        );

    }

    public void deleteContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_CONTACTS,
                KEY_ID + "=?",
                new String[] {String.valueOf(c.get_id())}
        );
        db.close();
    }

}
