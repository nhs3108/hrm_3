package com.nhs3108.fhrm.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;

import com.nhs3108.fhrm.constants.TypesFormat;
import com.nhs3108.fhrm.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by hongson on 22/12/2015.
 */
public class StaffHelper extends DatabaseHelper implements ModelDao<Staff> {
    String[] mColumns = {Staff.STAFF_ID, Staff.STAFF_NAME, Staff.STAFF_PLACE_OF_BIRTH,
            Staff.STAFF_DATE_OF_BIRTH, Staff.STAFF_PHONE, Staff.STAFF_POSITION,
            Staff.STAFF_PHONE, Staff.STAFF_LEFT_JOB, Staff.STAFF_DEPARTMENT_ID};

    public StaffHelper(Context context) {
        super(context);
    }

    @Override
    public long insert(Staff staff) throws SQLException {
        open();
        long rowIdInserted = -1;
        ContentValues insertValues;
        insertValues = new ContentValues();
        insertValues.put(Staff.STAFF_NAME, staff.getName());
        insertValues.put(Staff.STAFF_PLACE_OF_BIRTH, staff.getPlaceOfBirth());
        insertValues.put(Staff.STAFF_DATE_OF_BIRTH, DateUtils.convertToString(staff.getDateOfBirth(),
                TypesFormat.DATE_FORMAT));
        insertValues.put(Staff.STAFF_PHONE, staff.getPhone());
        insertValues.put(Staff.STAFF_POSITION, staff.getPosition());
        insertValues.put(Staff.STAFF_LEFT_JOB, staff.isLeftJob());
        insertValues.put(Staff.STAFF_DEPARTMENT_ID, staff.getDepartment().getId());
        rowIdInserted = sDatabase.insert(Staff.STAFF_TABLE_NAME, null, insertValues);
        close();
        return rowIdInserted;
    }

    @Override
    public int insert(ArrayList<Staff> staffs) throws SQLException {
        open();
        int numOfRowsInserted = 0;
        ContentValues insertValues;
        sDatabase.beginTransaction();
        try {
            for (Staff staff : staffs) {
                insertValues = new ContentValues();
                insertValues.put(Staff.STAFF_NAME, staff.getName());
                insertValues.put(Staff.STAFF_PLACE_OF_BIRTH, staff.getPlaceOfBirth());
                insertValues.put(Staff.STAFF_DATE_OF_BIRTH, DateUtils.convertToString(staff.getDateOfBirth(),
                        TypesFormat.DATE_FORMAT));
                insertValues.put(Staff.STAFF_PHONE, staff.getPhone());
                insertValues.put(Staff.STAFF_POSITION, staff.getPosition());
                insertValues.put(Staff.STAFF_LEFT_JOB, staff.isLeftJob());
                insertValues.put(Staff.STAFF_DEPARTMENT_ID, staff.getDepartment().getId());
                if (sDatabase.insert(Staff.STAFF_TABLE_NAME, null, insertValues) > -1) {
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
    public int update(Staff staff) throws SQLException {
        open();
        int numOfRowsUpdated = 0;
        ContentValues insertValues;
        insertValues = new ContentValues();
        insertValues.put(Staff.STAFF_NAME, staff.getName());
        insertValues.put(Staff.STAFF_PLACE_OF_BIRTH, staff.getPlaceOfBirth());
        insertValues.put(Staff.STAFF_DATE_OF_BIRTH, DateUtils.convertToString(staff.getDateOfBirth(), TypesFormat.DATE_FORMAT));
        insertValues.put(Staff.STAFF_POSITION, staff.getPosition());
        insertValues.put(Staff.STAFF_LEFT_JOB, staff.isLeftJob());
        insertValues.put(Staff.STAFF_DEPARTMENT_ID, staff.getDepartment().getId());
        String whereClause = Staff.STAFF_ID + " = ?";
        String[] whereArgs = {String.valueOf(staff.getId())};
        numOfRowsUpdated = sDatabase.update(Staff.STAFF_TABLE_NAME, insertValues, whereClause, whereArgs);
        close();
        return numOfRowsUpdated;
    }

    @Override
    public int update(ArrayList<Staff> staffs) throws SQLException {
        open();
        int numOfRowsUpdated = 0;
        ContentValues insertValues;
        sDatabase.beginTransaction();
        try {
            for (Staff staff : staffs) {
                insertValues = new ContentValues();
                insertValues.put(Staff.STAFF_NAME, staff.getName());
                insertValues.put(Staff.STAFF_PLACE_OF_BIRTH, staff.getPlaceOfBirth());
                insertValues.put(Staff.STAFF_DATE_OF_BIRTH, DateUtils.convertToString(staff.getDateOfBirth(), TypesFormat.DATE_FORMAT));
                insertValues.put(Staff.STAFF_POSITION, staff.getPosition());
                insertValues.put(Staff.STAFF_LEFT_JOB, staff.isLeftJob());
                insertValues.put(Staff.STAFF_DEPARTMENT_ID, staff.getDepartment().getId());
                String whereClause = Staff.STAFF_ID + " = ?";
                String[] whereArgs = {String.valueOf(staff.getId())};
                numOfRowsUpdated += sDatabase.update(Staff.STAFF_TABLE_NAME, insertValues, whereClause, whereArgs);
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
        String whereClause = Staff.STAFF_ID + " IN ( " + selectionArgs + " )";
        numOfRowsDeleted = sDatabase.delete(Staff.STAFF_TABLE_NAME, whereClause, null);
        this.close();
        return numOfRowsDeleted;
    }

    @Override
    public int deleteAll() throws SQLException {
        this.open();
        int numOfRowsDeleted = 0;
        numOfRowsDeleted = sDatabase.delete(Staff.STAFF_TABLE_NAME, null, null);
        this.close();
        return numOfRowsDeleted;
    }

    @Override
    public Staff getById(int index) throws SQLException {
        open();
        Staff staff = null;
        String selection = Staff.STAFF_ID + " = ?";
        String[] selectionArgs = {String.valueOf(index)};
        Cursor cursor = sDatabase.query(Staff.STAFF_TABLE_NAME, mColumns, selection, selectionArgs, null, null, "1");
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_ID));
            String name = cursor.getString(cursor.getColumnIndex(Staff.STAFF_NAME));
            String placeOfBirth = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PLACE_OF_BIRTH));
            String dateOfBirthStr = cursor.getString(cursor.getColumnIndex(Staff.STAFF_DATE_OF_BIRTH));
            Date dateOfBirth = DateUtils.convertFromString(dateOfBirthStr, TypesFormat.DATE_FORMAT);
            String phone = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PHONE));
            String position = cursor.getString(cursor.getColumnIndex(Staff.STAFF_POSITION));
            boolean leftJob = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_NAME)) > 0;
            int departmentId = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_DEPARTMENT_ID));
            Department department = new DepartmentHelper(sContext).getById(departmentId);
            staff = new Staff(name, placeOfBirth, dateOfBirth, phone, position, leftJob, department);
            staff.setId(id);
        }
        close();
        return staff;
    }

    @Override
    public ArrayList<Staff> getAll() throws SQLException {
        open();
        ArrayList<Staff> list = new ArrayList<Staff>();
        Cursor cursor = sDatabase.query(Staff.STAFF_TABLE_NAME, mColumns, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_ID));
            String name = cursor.getString(cursor.getColumnIndex(Staff.STAFF_NAME));
            String placeOfBirth = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PLACE_OF_BIRTH));
            String dateOfBirthStr = cursor.getString(cursor.getColumnIndex(Staff.STAFF_DATE_OF_BIRTH));
            Date dateOfBirth = DateUtils.convertFromString(dateOfBirthStr, TypesFormat.DATE_FORMAT);
            String phone = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PHONE));
            String position = cursor.getString(cursor.getColumnIndex(Staff.STAFF_POSITION));
            boolean leftJob = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_NAME)) > 0;
            int departmentId = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_DEPARTMENT_ID));
            Department department = new DepartmentHelper(sContext).getById(departmentId);
            Staff staff = new Staff(name, placeOfBirth, dateOfBirth, phone, position, leftJob, department);
            staff.setId(id);
            list.add(staff);
        }
        close();
        return list;
    }

    public ArrayList<Staff> getStaffsBelongOfDeparment(Department department, int offset, int quantity) throws SQLException {
        open();
        ArrayList<Staff> list = new ArrayList<Staff>();
        String selection = Staff.STAFF_DEPARTMENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(department.getId())};
        String orderBy = Staff.STAFF_ID;
        String limit = offset + ", " + quantity;
        Cursor cursor = sDatabase.query(Staff.STAFF_TABLE_NAME, mColumns, selection, selectionArgs, null, null, orderBy, limit);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_ID));
            String name = cursor.getString(cursor.getColumnIndex(Staff.STAFF_NAME));
            String placeOfBirth = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PLACE_OF_BIRTH));
            String dateOfBirthStr = cursor.getString(cursor.getColumnIndex(Staff.STAFF_DATE_OF_BIRTH));
            Date dateOfBirth = DateUtils.convertFromString(dateOfBirthStr, TypesFormat.DATE_FORMAT);
            String phone = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PHONE));
            String position = cursor.getString(cursor.getColumnIndex(Staff.STAFF_POSITION));
            int a = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_NAME));
            boolean leftJob = cursor.getString(cursor.getColumnIndex(Staff.STAFF_NAME)).equals("true");
            Staff staff = new Staff(name, placeOfBirth, dateOfBirth, phone, position, leftJob, department);
            staff.setId(id);
            list.add(staff);
        }
        close();
        return list;
    }

    public ArrayList<Staff> getByField(String fieldName, String keyword, int offset, int quantity) throws SQLException {
        open();
        ArrayList<Staff> list = new ArrayList<Staff>();
        String selection = fieldName + " LIKE ?";
        String[] selectionArgs = {"%" + keyword + "%"};
        String orderBy = Staff.STAFF_ID;
        String limit = offset + ", " + quantity;

        if (fieldName == Staff.STAFF_DEPARTMENT_NAME) {
            ArrayList<Department> departments = new DepartmentHelper(sContext).getByField(Department.DEPARTMENT_NAME, keyword);
            int quantityOfDepartments = departments.size();
            if (quantityOfDepartments > 0) {
                int[] ids = new int[quantityOfDepartments];
                for (int i = 0; i < quantityOfDepartments; i++) {
                    ids[i] = departments.get(i).getId();
                }
                selection = Staff.STAFF_DEPARTMENT_ID + " IN ( "
                        + TextUtils.join(", ", Arrays.toString(ids).split("[\\[\\]]")[1].split(", ")) + " )";
            } else {
                selection = Staff.STAFF_DEPARTMENT_ID + " IN ()";
            }
            selectionArgs = null;
        }
        Cursor cursor = sDatabase.query(Staff.STAFF_TABLE_NAME, mColumns, selection, selectionArgs, null, null, orderBy, limit);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_ID));
            String name = cursor.getString(cursor.getColumnIndex(Staff.STAFF_NAME));
            String placeOfBirth = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PLACE_OF_BIRTH));
            String dateOfBirthStr = cursor.getString(cursor.getColumnIndex(Staff.STAFF_DATE_OF_BIRTH));
            Date dateOfBirth = DateUtils.convertFromString(dateOfBirthStr, TypesFormat.DATE_FORMAT);
            String phone = cursor.getString(cursor.getColumnIndex(Staff.STAFF_PHONE));
            String position = cursor.getString(cursor.getColumnIndex(Staff.STAFF_POSITION));
            boolean leftJob = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_NAME)) > 0;
            int departmentId = cursor.getInt(cursor.getColumnIndex(Staff.STAFF_DEPARTMENT_ID));
            Department department = new DepartmentHelper(sContext).getById(departmentId);
            Staff staff = new Staff(name, placeOfBirth, dateOfBirth, phone, position, leftJob, department);
            staff.setId(id);
            list.add(staff);
        }
        close();
        return list;
    }
}
