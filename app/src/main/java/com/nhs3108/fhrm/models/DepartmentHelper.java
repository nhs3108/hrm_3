package com.nhs3108.fhrm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hongson on 22/12/2015.
 */
public class DepartmentHelper extends DatabaseHelper implements ModelDao<Department> {
    private String[] mColumns = {Department.DEPARTMENT_ID, Department.DEPARTMENT_NAME, Department.DEPARTMENT_DESCRIPTION};

    public DepartmentHelper(Context context) {
        super(context);
    }

    @Override
    public int insert(ArrayList<Department> departments) throws SQLException {
        open();
        int numOfRowsInserted = 0;
        ContentValues insertValues;
        sDatabase.beginTransaction();
        try {
            for (Department department : departments) {
                insertValues = new ContentValues();
                insertValues.put(Department.DEPARTMENT_NAME, department.getName());
                insertValues.put(Department.DEPARTMENT_DESCRIPTION, department.getDescription());
                if (sDatabase.insert(Department.DEPARTMENT_TABLE_NAME, null, insertValues) > -1) {
                    numOfRowsInserted++;
                }
            }
            sDatabase.setTransactionSuccessful();
        } finally {
            sDatabase.endTransaction();
            close();
            return numOfRowsInserted;
        }
    }

    @Override
    public int update(ArrayList<Department> departments) throws SQLException {
        open();
        int numOfRowsUpdated = 0;
        ContentValues insertValues;
        sDatabase.beginTransaction();
        try {
            for (Department department : departments) {
                insertValues = new ContentValues();
                insertValues.put(Department.DEPARTMENT_NAME, department.getName());
                insertValues.put(Department.DEPARTMENT_DESCRIPTION, department.getDescription());
                String whereClause = Department.DEPARTMENT_ID + " = ?";
                String[] whereArgs = {String.valueOf(department.getId())};
                numOfRowsUpdated += sDatabase.update(Department.DEPARTMENT_TABLE_NAME, insertValues, whereClause, whereArgs);
            }
            sDatabase.setTransactionSuccessful();
        } finally {
            sDatabase.endTransaction();
            close();
            return numOfRowsUpdated;
        }
    }

    @Override
    public int delete(int[] ids) throws SQLException {
        this.open();
        int numOfRowsDeleted = 0;
        String selectionArgs = TextUtils.join(", ", Arrays.toString(ids).split("[\\[\\]]")[1].split(", "));
        String whereClause = Department.DEPARTMENT_ID + " IN ( " + selectionArgs + " )";
        numOfRowsDeleted = sDatabase.delete(Department.DEPARTMENT_TABLE_NAME, whereClause, null);
        this.close();
        return numOfRowsDeleted;
    }

    @Override
    public int deleteAll() throws SQLException {
        this.open();
        int numOfRowsDeleted = 0;
        numOfRowsDeleted = sDatabase.delete(Department.DEPARTMENT_TABLE_NAME, null, null);
        this.close();
        return numOfRowsDeleted;
    }

    @Override
    public Department getById(int index) throws SQLException {
        open();
        Department department = null;
        String selection = Department.DEPARTMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(index)};
        Cursor cursor = sDatabase.query(Department.DEPARTMENT_TABLE_NAME, mColumns, selection, selectionArgs, null, null, "1");
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(Department.DEPARTMENT_ID));
            String name = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_NAME));
            String description = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_DESCRIPTION));
            department = new Department(name, description);
            department.setId(id);
        }
        close();
        return department;
    }

    @Override
    public ArrayList<Department> getAll() throws SQLException {
        open();
        ArrayList<Department> list = new ArrayList<Department>();
        Cursor cursor = sDatabase.query(Department.DEPARTMENT_TABLE_NAME, mColumns, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Department.DEPARTMENT_ID));
            String name = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_NAME));
            String description = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_DESCRIPTION));
            Department department = new Department(name, description);
            department.setId(id);
            list.add(department);
        }
        close();
        return list;
    }

    public Department getDepartmentContainsStaff(Staff staff) {
        open();
        Department department = null;
        String selection = Department.DEPARTMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(staff.getDepartment().getId())};
        Cursor cursor = sDatabase.query(Department.DEPARTMENT_TABLE_NAME, mColumns, selection, selectionArgs, null, null, "1");
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(Department.DEPARTMENT_ID));
            String name = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_NAME));
            String description = cursor.getString(cursor.getColumnIndex(Department.DEPARTMENT_DESCRIPTION));
            department = new Department(name, description);
            department.setId(id);
        }
        close();
        return department;
    }
}
