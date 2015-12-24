package com.nhs3108.fhrm.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.constants.TypesFormat;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class StaffAdapter extends ArrayAdapter<Staff> {
    private Activity activity;
    private int mIdLayout;
    private ArrayList<Staff> mList;
    private LayoutInflater mInflater;

    public StaffAdapter(Activity activity, int idLayout, ArrayList<Staff> list) {
        super(activity, idLayout, list);
        this.activity = activity;
        this.mIdLayout = idLayout;
        this.mList = list;
        this.mInflater = LayoutInflater.from(activity);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Staff staff = mList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(mIdLayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.staffName = (TextView) convertView.findViewById(R.id.text_staff_name);
            viewHolder.staffDateOfBirth = (TextView) convertView.findViewById(R.id.text_date_of_birth);
            viewHolder.staffPosition = (TextView) convertView.findViewById(R.id.text_position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.staffName.setText(staff.getName());
        viewHolder.staffDateOfBirth.setText(DateUtils.convertToString(staff.getDateOfBirth(), TypesFormat.DATE_FORMAT));
        viewHolder.staffPosition.setText(staff.getPosition());
        if (staff.isLeftJob()) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.colorNegativeBackground));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView staffName;
        TextView staffDateOfBirth;
        TextView staffPosition;
    }
}
