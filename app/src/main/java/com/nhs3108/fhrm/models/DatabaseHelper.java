package com.nhs3108.fhrm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hongson on 18/12/2015.
 */
public class DatabaseHelper {
    public static final int DATA_VERSION = 1;
    public static final String DATABASE_NAME = "fhrm.db";

    public static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE " + Department.TABLE_NAME + "("
            + Department.DEPARTMENT_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Department.DEPARTMENT_NAME + " VARCHAR(50) NOT NULL,"
            + Department.DEPARTMENT_DESCRIPTION + " VARCHAR(256) DEFAULT NULL)";

    private static Context context;
    private OpenHelper openHelper;
    public static SQLiteDatabase db;

    public DatabaseHelper(Context context){
        DatabaseHelper.context = context;
    }

    public DatabaseHelper open() throws SQLException{
        openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
        db = openHelper.getReadableDatabase();
        return this;
    }

    public void close(){
        openHelper.close();
    }

    public Object get(String tableName, int index){
        switch (tableName) {
            case Department.TABLE_NAME : {
                Department department = null;
                Cursor cursor = db.rawQuery("SELECT * FROM " + Department.TABLE_NAME + " WHERE id = ?",
                        new String[]{String.valueOf(index)});
                if(cursor != null && cursor.moveToFirst()){
                    int id = cursor.getInt(cursor.getColumnIndex(Department.DEPARTMENT_KEY_ID));
                    String name = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_NAME));
                    String description = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_DESCRIPTION));
                    department = new Department(name, description);
                    department.setId(id);
                }
                return department;
            }
            default : return null;
        }
    }

    public long save(Department department){
        ContentValues cv = new ContentValues();
        cv.put(Department.DEPARTMENT_NAME, department.getName());
        cv.put(Department.DEPARTMENT_DESCRIPTION, department.getDescription());
        if (department.getId() == Department.FLAG_NOT_SAVED){
            return db.insert(Department.TABLE_NAME, null, cv);
        } else {
            return db.update(Department.TABLE_NAME, cv, Department.DEPARTMENT_KEY_ID + " = ?",
                    new String[]{String.valueOf(department.getId())});
        }
    }

    public int delete(String tableName, int index){
        switch (tableName) {
            case Department.TABLE_NAME : {
                return db.delete(Department.TABLE_NAME, Department.DEPARTMENT_KEY_ID + " = ?",
                        new String[]{String.valueOf(index)});
            }
            default : return 0;
        }
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATA_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_DEPARTMENT);
        }

        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            db.execSQL("DROP TABLE IF EXISTS " + Department.TABLE_NAME);
            onCreate(db);
        }
    }

}
