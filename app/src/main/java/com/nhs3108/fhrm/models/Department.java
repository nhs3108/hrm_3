package com.nhs3108.fhrm.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class Department implements Parcelable {
    public static final String DEPARTMENT_TABLE_NAME = "departments";
    public static final String DEPARTMENT_ID = "id";
    public static final String DEPARTMENT_NAME = "name";
    public static final String DEPARTMENT_DESCRIPTION = "description";
    public static final int FLAG_NOT_SAVED = -1;
    public static final Parcelable.Creator<Department> CREATOR = new Parcelable.Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };
    private int id = FLAG_NOT_SAVED;
    private String name = "";
    private String description = "";

    private Department(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
    }
}
