package com.nzsoft.springcar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.Contact;
import com.nzsoft.springcar.model.Location;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdata.db";

    private static final String USER_TABLE = "USER";


    private static final String COL_1 = "CODIGO";
    private static final String COL_2 = "NOMBRE";
    private static final String COL_3 = "APELLIDO";
    private static final String COL_4 = "DNI";

    private static final String COL_5 = "ADDRESS";
    private static final String COL_6 = "ZIPCODE";
    private static final String COL_7 = "CITY";
    private static final String COL_8 = "COUNTRY";

    private static final String COL_9 = "PHONE";
    private static final String COL_10 = "EMAIL";

    private static final String COL_11 = "USUARIO";
    private static final String COL_12 = "PASSWORD";


    private static final String[] FIELDS = {COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9, COL_10, COL_11, COL_12};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder strSQL = new StringBuilder();

        strSQL.append("CREATE TABLE ").append(USER_TABLE).append(" (")
                .append(COL_1).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(COL_2).append(" TEXT NOT NULL,")
                .append(COL_3).append(" TEXT NOT NULL,")
                .append(COL_4).append(" TEXT NOT NULL,")
                .append(COL_5).append(" TEXT NOT NULL,")
                .append(COL_6).append(" TEXT NOT NULL,")
                .append(COL_7).append(" TEXT NOT NULL,")
                .append(COL_8).append(" TEXT NOT NULL,")
                .append(COL_9).append(" TEXT NOT NULL,")
                .append(COL_10).append(" TEXT NOT NULL,")
                .append(COL_11).append(" TEXT NOT NULL,")
                .append(COL_12).append(" TEXT NOT NULL)");

        db.execSQL(strSQL.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }


    public Client createClient (Client client){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, client.getFirstName());
        contentValues.put(COL_3, client.getLastName());
        contentValues.put(COL_4, client.getIdNumber());
        contentValues.put(COL_5, client.getLocation().getAddress());
        contentValues.put(COL_6, client.getLocation().getZipCode());
        contentValues.put(COL_7, client.getLocation().getCity());
        contentValues.put(COL_8, client.getLocation().getCountry());
        contentValues.put(COL_9, client.getContact().getPhoneNumber());
        contentValues.put(COL_10, client.getContact().getEmail());
        contentValues.put(COL_11, client.getUserName());
        contentValues.put(COL_12, client.getPassword());


        long resultado = db.insert(USER_TABLE, null, contentValues);

        client.setId(resultado);

        return resultado == -1 ? null : client;
    }

    public Client getClient(){

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(USER_TABLE, FIELDS, null, null, null, null, COL_1 + " DESC");

        Client client = new Client();
        client.setContact(new Contact());
        client.setLocation(new Location());

        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                client.setId(cursor.getLong(0));
                client.setFirstName(cursor.getString(1));
                client.setLastName(cursor.getString(2));
                client.setIdNumber(cursor.getString(3));
                client.getLocation().setAddress(cursor.getString(4));
                client.getLocation().setZipCode(cursor.getString(5));
                client.getLocation().setCity(cursor.getString(6));
                client.getLocation().setCountry(cursor.getString(7));
                client.getContact().setPhoneNumber(cursor.getString(8));
                client.getContact().setEmail(cursor.getString(9));
                client.setUserName(cursor.getString(10));
                client.setPassword(cursor.getString(11));
            }
        }

        return client;
    }

}
