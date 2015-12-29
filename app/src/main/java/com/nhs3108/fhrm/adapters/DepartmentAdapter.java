package com.nhs3108.fhrm.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.StaffHelper;

import java.util.ArrayList;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class DepartmentAdapter extends ArrayAdapter<Department> {
    private Activity activity;
    private int mIdLayout;
    private ArrayList<Department> mList;
    private LayoutInflater mInflater;
    private StaffHelper mStaffHelper;

    public DepartmentAdapter(Activity activity, int idLayout, ArrayList<Department> list) {
        super(activity, idLayout, list);
        this.activity = activity;
        this.mIdLayout = idLayout;
        this.mList = list;
        this.mStaffHelper = new StaffHelper(activity);
        this.mInflater = LayoutInflater.from(activity);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Department department = mList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(mIdLayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.departmentName = (TextView) convertView.findViewById(R.id.department_name);
            viewHolder.departmentDescription = (TextView) convertView.findViewById(R.id.department_description);
            viewHolder.departmentStaffQuantity = (TextView) convertView.findViewById(R.id.department_staff_quantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.departmentName.setText(department.getName());
        viewHolder.departmentDescription.setText(department.getDescription());

        viewHolder.departmentStaffQuantity.setText(mStaffHelper.sumOfStaffsBelongsTo(department) + "");
        return convertView;
    }

    static class ViewHolder {
        TextView departmentName;
        TextView departmentDescription;
        TextView departmentStaffQuantity;
    }
}
