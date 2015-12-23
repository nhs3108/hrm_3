package com.nhs3108.fhrm.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.utils.DateUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class StaffAdapter extends ArrayAdapter<Staff> {
    private Activity mActivity;
    private int mIdLayout;
    private ArrayList<Staff> mList;

    public StaffAdapter(Activity activity, int idLayout, ArrayList<Staff> list) {
        super(activity, idLayout, list);
        this.mActivity = activity;
        this.mIdLayout = idLayout;
        this.mList = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(mIdLayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.staffName = (TextView) convertView.findViewById(R.id.text_staff_name);
            viewHolder.staffPlaceOfBirth = (TextView) convertView.findViewById(R.id.text_place_of_birth);
            viewHolder.staffDateOfBirth = (TextView) convertView.findViewById(R.id.text_date_of_birth);
            viewHolder.staffPhone = (TextView) convertView.findViewById(R.id.text_phone);
            viewHolder.staffPosition = (TextView) convertView.findViewById(R.id.text_position);
            viewHolder.staffDepartment = (TextView) convertView.findViewById(R.id.text_staff_deparment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.staffName.setText(mList.get(position).getName());
        viewHolder.staffPlaceOfBirth.setText(mList.get(position).getPlaceOfBirth());
        viewHolder.staffDateOfBirth.setText(DateUtils.convertToString(mList.get(position).getDateOfBirth(), "yyyy-mm-ddd"));
        viewHolder.staffPhone.setText(mList.get(position).getPhone());
        viewHolder.staffPosition.setText(mList.get(position).getPosition());
        viewHolder.staffDepartment.setText(String.valueOf(mList.get(position).isLeftJob()));
        if (mList.get(position).isLeftJob()){
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.colorNegativeBackground));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView staffName;
        TextView staffPlaceOfBirth;
        TextView staffDateOfBirth;
        TextView staffPhone;
        TextView staffPosition;
        TextView staffDepartment;
    }
}
