package com.nhs3108.fhrm.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.models.Department;

import java.util.ArrayList;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class DepartmentAdapter extends ArrayAdapter<Department> {
    private Activity mActivity;
    private int mIdLayout;
    private ArrayList<Department> mList;

    public DepartmentAdapter(Activity activity, int idLayout, ArrayList<Department> list) {
        super(activity, idLayout, list);
        this.mActivity = activity;
        this.mIdLayout = idLayout;
        this.mList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(mIdLayout, parent);
            viewHolder = new ViewHolder();
            viewHolder.departmentName = (TextView) convertView.findViewById(R.id.department_name);
            viewHolder.departmentDescription = (TextView) convertView.findViewById(R.id.department_description);
            viewHolder.departmentStaffQuantity = (TextView) convertView.findViewById(R.id.department_staff_quantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.departmentName.setText(mList.get(position).getName());
        viewHolder.departmentDescription.setText(mList.get(position).getDescription());
        viewHolder.departmentStaffQuantity.setText("fake string");
        return convertView;
    }

    static class ViewHolder {
        TextView departmentName;
        TextView departmentDescription;
        TextView departmentStaffQuantity;
    }
}
