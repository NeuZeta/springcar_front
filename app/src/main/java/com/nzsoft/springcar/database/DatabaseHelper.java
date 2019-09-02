package com.nzsoft.springcar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nzsoft.springcar.model.Contact;
import com.nzsoft.springcar.model.Location;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdata.db";
    private static final String USER_TABLE = "USER";
    private static final String COL_1 = "CODIGO";
    private static final String COL_2 = "NOMBRE";
    private static final String COL_3 = "APELLIDO";
    private static final String COL_4 = "DNI";
    private static final String COL_5 = "DIRECCION";
    private static final String COL_6 = "CONTACTO";
    private static final String COL_7 = "USUARIO";
    private static final String COL_8 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder strSQL = new StringBuilder();

        /*
        private Long id;
        private String firstName;
        private String lastName;
        private String idNumber;
        private Location location;
        private Contact contact;
        private String userName;
        private String password;
        */

        strSQL.append("CREATE TABLE ").append(USER_TABLE).append(" (")
                .append(COL_1).append("  REAL NOT NULL,");

        db.execSQL(strSQL.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }
}
