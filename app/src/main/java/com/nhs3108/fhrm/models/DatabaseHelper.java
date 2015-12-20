package com.nhs3108.fhrm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hongson on 18/12/2015.
 */
public class DatabaseHelper {
    public static final int DATA_VERSION = 1;
    public static final String DATABASE_NAME = "fhrm.db";

    public static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE IF NOT EXISTS " + Department.DEPARTMENT_TABLE_NAME + "("
            + Department.DEPARTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Department.DEPARTMENT_NAME + " VARCHAR(50) NOT NULL,"
            + Department.DEPARTMENT_DESCRIPTION + " VARCHAR(256) DEFAULT NULL)";

    private static Context sContext;
    private OpenHelper mOpenHelper;
    public static SQLiteDatabase sDatabase;

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

    public Object getRecord(String tableName, int index) {
        this.open();
        Object object = null;
        Cursor cursor;
        switch (tableName) {
            case Department.DEPARTMENT_TABLE_NAME: {
                Department department = null;
                String[] columns = {Department.DEPARTMENT_ID, Department.DEPARTMENT_NAME, Department.DEPARTMENT_DESCRIPTION};
                String selection = Department.DEPARTMENT_ID + " = ?";
                String[] selectionArgs = {String.valueOf(index)};
                cursor = sDatabase.query(Department.DEPARTMENT_TABLE_NAME, columns, selection, selectionArgs, null, null, "1");
                if (cursor != null && cursor.moveToFirst()) {
                    int id = cursor.getInt(cursor.getColumnIndex(Department.DEPARTMENT_ID));
                    String name = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_DESCRIPTION));
                    department = new Department(name, description);
                    department.setId(id);
                }
                object = department;
                break;
            }
            default:
                break;
        }
        return object;
    }

    public ArrayList<Department> getAllDepartments() {
        this.open();
        ArrayList<Department> list = new ArrayList<Department>();
        String[] columns = {Department.DEPARTMENT_ID, Department.DEPARTMENT_NAME, Department.DEPARTMENT_DESCRIPTION};
        Cursor cursor = sDatabase.query(Department.DEPARTMENT_TABLE_NAME, columns, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Department.DEPARTMENT_ID));
            String name = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_NAME));
            String description = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_DESCRIPTION));
            Department department = new Department(name, description);
            department.setId(id);
            list.add(department);
        }
        this.close();
        return list;
    }

    public void insertRecords(ArrayList<Department> departments) throws SQLException {
        this.open();
        ContentValues insertValues;
        sDatabase.beginTransaction();
        for (Department department : departments) {
            insertValues = new ContentValues();
            insertValues.put(Department.DEPARTMENT_NAME, department.getName());
            insertValues.put(Department.DEPARTMENT_DESCRIPTION, department.getDescription());
            sDatabase.insert(Department.DEPARTMENT_TABLE_NAME, null, insertValues);
        }
        sDatabase.setTransactionSuccessful();
        sDatabase.endTransaction();
        this.close();
    }

    public void updateRecords(ArrayList<Department> departments) throws SQLException {
        this.open();
        ContentValues insertValues;
        sDatabase.beginTransaction();
        for (Department department : departments) {
            insertValues = new ContentValues();
            insertValues.put(Department.DEPARTMENT_NAME, department.getName());
            insertValues.put(Department.DEPARTMENT_DESCRIPTION, department.getDescription());
            String whereClause = Department.DEPARTMENT_ID + " = ?";
            String[] whereArgs = {String.valueOf(department.getId())};
            sDatabase.update(Department.DEPARTMENT_TABLE_NAME, insertValues, whereClause, whereArgs);
        }
        sDatabase.setTransactionSuccessful();
        sDatabase.endTransaction();
        this.close();
    }

    public int deleteRecords(String tableName, int[] ids) throws SQLException {
        this.open();
        int result = 0;
        String selectionArgs = TextUtils.join(", ", Arrays.toString(ids).split("[\\[\\]]")[1].split(", "));
        switch (tableName) {
            case Department.DEPARTMENT_TABLE_NAME: {
                result = sDatabase.delete(Department.DEPARTMENT_TABLE_NAME, Department.DEPARTMENT_ID + " IN ( " + selectionArgs + " )", null);
                break;
            }
            default:
                break;
        }
        this.close();
        return result;
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATA_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_DEPARTMENT);
        }

        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            db.execSQL("DROP TABLE IF EXISTS " + Department.DEPARTMENT_TABLE_NAME);
            onCreate(db);
        }
    }

}
