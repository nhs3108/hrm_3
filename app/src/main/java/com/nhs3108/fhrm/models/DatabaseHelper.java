package com.nhs3108.fhrm.models;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hongson on 18/12/2015.
 */
public class DatabaseHelper {
    public static final int DATA_VERSION = 1;
    public static final String DATABASE_NAME = "fhrm.db";

    public static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE IF NOT EXISTS " + Department.DEPARTMENT_TABLE_NAME + "("
            + Department.DEPARTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Department.DEPARTMENT_NAME + " TEXT NOT NULL,"
            + Department.DEPARTMENT_DESCRIPTION + " TEXT DEFAULT '')";

    public static final String CREATE_TABLE_STAFF = "CREATE TABLE IF NOT EXISTS " + Staff.STAFF_TABLE_NAME + "("
            + Staff.STAFF_ID + " INTEGER NOT NULL, "
            + Staff.STAFF_NAME + " TEXT NOT NULL,"
            + Staff.STAFF_PLACE_OF_BIRTH + " TEXT DEFAULT '',"
            + Staff.STAFF_DATE_OF_BIRTH + " TEXT DEFAULT NULL,"
            + Staff.STAFF_PHONE + " TEXT DEFAULT '',"
            + Staff.STAFF_POSITION + " TEXT NOT NULL,"
            + Staff.STAFF_LEFT_JOB + " INTEGER NOT NULL,"
            + Staff.STAFF_DEPARTMENT_ID + " INTEGET NOT NULL,"
            + " FOREIGN KEY (" + Staff.STAFF_DEPARTMENT_ID + ") REFERENCES "
            + Department.DEPARTMENT_TABLE_NAME + "(" + Department.DEPARTMENT_ID + "))";
    public static SQLiteDatabase sDatabase;
    protected static Context sContext;
    protected OpenHelper mOpenHelper;

    public DatabaseHelper(Context context) {
        DatabaseHelper.sContext = context;
    }

    public DatabaseHelper open() throws SQLException {
        mOpenHelper = new OpenHelper(sContext);
        sDatabase = mOpenHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mOpenHelper.close();
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATA_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_DEPARTMENT);
            db.execSQL(CREATE_TABLE_STAFF);
        }

        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            db.execSQL("DROP TABLE IF EXISTS " + Department.DEPARTMENT_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Staff.STAFF_TABLE_NAME);
            onCreate(db);
        }
    }

}
