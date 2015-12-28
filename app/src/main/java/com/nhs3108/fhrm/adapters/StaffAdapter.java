package com.nhs3108.fhrm.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.activites.StaffEditingActivity;
import com.nhs3108.fhrm.constants.TypesFormat;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.utils.DateUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private static ArrayList<Staff> sStaffList;
    private static Activity sActivity;
    private LayoutInflater mInflater;

    public StaffAdapter(Activity activity, ArrayList<Staff> list) {
        sStaffList = list;
        sActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemLayoutView = mInflater.inflate(R.layout.item_staff, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Staff staff = sStaffList.get(position);
        viewHolder.staffName.setText(staff.getName());
        viewHolder.staffDateOfBirth.setText(DateUtils.convertToString(staff.getDateOfBirth(), TypesFormat.DATE_FORMAT));
        viewHolder.staffDepartment.setText(staff.getDepartment().getName());
        viewHolder.staffId = staff.getId();
    }

    @Override
    public int getItemCount() {
        return sStaffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView staffName;
        TextView staffDateOfBirth;
        TextView staffDepartment;
        int staffId;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            staffName = (TextView) itemLayoutView.findViewById(R.id.text_staff_name);
            staffDateOfBirth = (TextView) itemLayoutView.findViewById(R.id.text_date_of_birth);
            staffDepartment = (TextView) itemLayoutView.findViewById(R.id.text_staff_department_name);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sActivity, StaffEditingActivity.class);
                    intent.putExtra("staffId", staffId);
                    sActivity.startActivityForResult(intent, 1);
                }
            });
        }

    }
}
