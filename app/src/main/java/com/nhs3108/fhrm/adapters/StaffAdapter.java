package com.nhs3108.fhrm.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.constants.TypesFormat;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private ArrayList<Staff> mStaffList;
    private LayoutInflater mInflater;

    public StaffAdapter(Activity activity, ArrayList<Staff> list) {
        this.mStaffList = list;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = mInflater.inflate(R.layout.item_staff, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Staff staff = mStaffList.get(position);
        viewHolder.staffName.setText(staff.getName());
        viewHolder.staffDateOfBirth.setText(DateUtils.convertToString(staff.getDateOfBirth(), TypesFormat.DATE_FORMAT));
        viewHolder.staffDepartment.setText(staff.getDepartment().getName());
    }

    @Override
    public int getItemCount() {
        return mStaffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView staffName;
        TextView staffDateOfBirth;
        TextView staffDepartment;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            staffName = (TextView) itemLayoutView.findViewById(R.id.text_staff_name);
            staffDateOfBirth = (TextView) itemLayoutView.findViewById(R.id.text_date_of_birth);
            staffDepartment = (TextView) itemLayoutView.findViewById(R.id.text_staff_department_name);
        }

    }
}
