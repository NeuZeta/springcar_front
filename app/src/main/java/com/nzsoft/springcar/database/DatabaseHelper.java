package com.nzsoft.springcar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.Contact;
import com.nzsoft.springcar.model.Location;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdata.db";

    private static final String USER_TABLE = "USER";
    private static final String LOCATION_TABLE = "LOCATIONS";
    private static final String CONTACT_TABLE = "CONTACTS";


    private static final String USER_COL_1 = "CODIGO";
    private static final String USER_COL_2 = "NOMBRE";
    private static final String USER_COL_3 = "APELLIDO";
    private static final String USER_COL_4 = "DNI";
    private static final String USER_COL_5 = "LOCATION_ID";
    private static final String USER_COL_6 = "CONTACT_ID";
    private static final String USER_COL_7 = "USUARIO";
    private static final String USER_COL_8 = "PASSWORD";

    private static final String LOC_COL_1 = "CODIGO";
    private static final String LOC_COL_2 = "ADDRESS";
    private static final String LOC_COL_3 = "ZIPCODE";
    private static final String LOC_COL_4 = "CITY";
    private static final String LOC_COL_5 = "COUNTRY";

    private static final String CON_COL_1 = "CODIGO";
    private static final String CON_COL_2 = "PHONE";
    private static final String CON_COL_3 = "EMAIL";

    private static final String[] FIELDS = {USER_COL_1, USER_COL_2, USER_COL_3, USER_COL_4, USER_COL_5, USER_COL_6, USER_COL_7, USER_COL_8};




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder strContactSQL = new StringBuilder();

        strContactSQL.append("CREATE TABLE ").append(CONTACT_TABLE).append(" (")
                .append(CON_COL_1).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(CON_COL_2).append(" TEXT NOT NULL,")
                .append(CON_COL_3).append(" TEXT NOT NULL,");


        StringBuilder strLocationSQL = new StringBuilder();

        strLocationSQL.append("CREATE TABLE ").append(LOCATION_TABLE).append(" (")
                .append(LOC_COL_1).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(LOC_COL_2).append(" TEXT NOT NULL,")
                .append(LOC_COL_3).append(" TEXT NOT NULL,")
                .append(LOC_COL_4).append(" TEXT NOT NULL,")
                .append(LOC_COL_5).append(" TEXT NOT NULL)");

        StringBuilder strUserSQL = new StringBuilder();

        strUserSQL.append("CREATE TABLE ").append(USER_TABLE).append(" (")
                .append(USER_COL_1).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(USER_COL_2).append(" TEXT NOT NULL,")
                .append(USER_COL_3).append(" TEXT NOT NULL,")
                .append(USER_COL_4).append(" TEXT NOT NULL,")
                .append(USER_COL_5).append(" REAL NOT NULL,")
                .append(USER_COL_6).append(" REAL NOT NULL,")
                .append(USER_COL_7).append(" TEXT NOT NULL,")
                .append(USER_COL_8).append(" TEXT NOT NULL)");

        db.execSQL(strContactSQL.toString());
        db.execSQL(strLocationSQL.toString());
        db.execSQL(strUserSQL.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE);
        onCreate(db);
    }

    public Contact createContact(Contact contact){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CON_COL_2, contact.getPhoneNumber());
        contentValues.put(CON_COL_3, contact.getEmail());

        long resultado = db.insert(CONTACT_TABLE, null, contentValues);

        contact.setId(resultado);

        return resultado == -1 ? null : contact;

    }

    public Location createContact(Location location){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(LOC_COL_2, location.getAddress());
        contentValues.put(LOC_COL_3, location.getZipCode());
        contentValues.put(LOC_COL_4, location.getCity());
        contentValues.put(LOC_COL_5, location.getCountry());

        long resultado = db.insert(LOCATION_TABLE, null, contentValues);

        location.setId(resultado);

        return resultado == -1 ? null : location;

    }

    public Client createClient (Client client){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COL_2, client.getFirstName());
        contentValues.put(USER_COL_3, client.getLastName());
        contentValues.put(USER_COL_4, client.getIdNumber());
        contentValues.put(USER_COL_5, client.getLocation().getId());
        contentValues.put(USER_COL_6, client.getContact().getId());
        contentValues.put(USER_COL_7, client.getUserName());
        contentValues.put(USER_COL_8, client.getPassword());

        long resultado = db.insert(USER_TABLE, null, contentValues);

        client.setId(resultado);

        return resultado == -1 ? null : client;
    }

    public Location getLocationById(long id){
        Location location = null;

        SQLiteDatabase db = getWritableDatabase();

        String[] campos = new String[] {LOC_COL_1, LOC_COL_2, LOC_COL_3, LOC_COL_4, LOC_COL_5};
        String[] args = new String[] {String.valueOf(id)};
        Cursor cursor = db.query(LOCATION_TABLE, campos, "CODIGO = ?", args, null, null, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToNext();
            location.setAddress(cursor.getString(1));
            location.setZipCode(cursor.getString(2));
            location.setCity(cursor.getString(3));
            location.setCountry(cursor.getString(4));
        }

        return location;
    }

    public Contact getContactById (long id) {
        Contact contact = null;

        SQLiteDatabase db = getWritableDatabase();

        String[] campos = new String[]{CON_COL_1, CON_COL_2, CON_COL_3};
        String[] args = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(CONTACT_TABLE, campos, "CODIGO = ?", args, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            contact.setPhoneNumber(cursor.getString(1));
            contact.setEmail(cursor.getString(2));
        }

        return contact;
    }


    public Client getClient(){

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(USER_TABLE, FIELDS, null, null, null, null, USER_COL_1 + " DESC");

        Client client = new Client();
        Long locationId;
        Long contactId;

        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                client.setId(cursor.getLong(0));
                client.setFirstName(cursor.getString(1));
                client.setLastName(cursor.getString(2));
                client.setIdNumber(cursor.getString(3));

                locationId = cursor.getLong(4);
                client.setLocation(getLocationById(locationId));

                contactId = cursor.getLong(5);
                client.setContact(getContactById(contactId));

                client.setUserName(cursor.getString(6));
                client.setPassword(cursor.getString(7));
            }
        }

        return client;
    }

}
