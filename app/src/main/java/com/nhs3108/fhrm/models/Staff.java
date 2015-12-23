package com.nhs3108.fhrm.models;

/**
 * Created by hongson on 22/12/2015.
 */

import java.util.Date;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class Staff {
    public static final String STAFF_TABLE_NAME = "staffs";
    public static final String STAFF_ID = "id";
    public static final String STAFF_NAME = "name";
    public static final String STAFF_PLACE_OF_BIRTH = "place_of_birth";
    public static final String STAFF_DATE_OF_BIRTH = "date_of_birth";
    public static final String STAFF_PHONE = "phone";
    public static final String STAFF_POSITION = "position";
    public static final String STAFF_LEFT_JOB = "left_job";
    public static final String STAFF_DEPARTMENT_ID = "department_id";
    public static final int FLAG_NOT_SAVED = -1;

    private int id = FLAG_NOT_SAVED;
    private String name = "";
    private String placeOfBirth = "";
    private Date dateOfBirth = new Date();
    private String phone = "";
    private String position = "";
    private boolean leftJob = false;
    private Department department;


    public Staff(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Staff(String name, String placeOfBirth, Date dateOfBirth, String phone, String position, boolean leftJob, Department department) {
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.position = position;
        this.leftJob = leftJob;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isLeftJob() {
        return leftJob;
    }

    public void setLeftJob(boolean leftJob) {
        this.leftJob = leftJob;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
