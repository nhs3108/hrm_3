package com.nhs3108.fhrm.activites;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.constants.TypesFormat;
import com.nhs3108.fhrm.fragments.DatePickerDialogFragment;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.DepartmentHelper;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.models.StaffHelper;
import com.nhs3108.fhrm.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hongson on 25/12/2015.
 */
public class StaffAddingActivity extends FragmentActivity {
    private DepartmentHelper mDepartmentHelper = new DepartmentHelper(this);
    private StaffHelper mStaffHelper = new StaffHelper(this);
    private EditText mEditStaffName;
    private EditText mEditStaffBirthday;
    private EditText mEditStaffAddress;
    private EditText mEditStaffPhone;
    private EditText mEditStaffPositon;
    private Spinner mSpinnerDepartment;
    private CheckBox mCheckBoxLeftJob;
    private ViewGroup mContainerEditing;
    private Button mEnableEditingButton;
    private Staff mStaff;
    private ArrayList<Department> mDepartments = new ArrayList<Department>();
    private ArrayList<String> mListOfDeparmentsName = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_adding);
        try {
            mDepartments = mDepartmentHelper.getAll();
            if (mDepartments.size() == 0) {
                Toast.makeText(this, getString(R.string.msg_has_no_department), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }

        mEditStaffName = (EditText) findViewById(R.id.edit_staff_name);
        mEditStaffBirthday = (EditText) findViewById(R.id.edit_staff_birthday);
        mEditStaffAddress = (EditText) findViewById(R.id.edit_staff_address);
        mEditStaffPhone = (EditText) findViewById(R.id.edit_staff_phone);
        mEditStaffPositon = (EditText) findViewById(R.id.edit_staff_position);
        mSpinnerDepartment = (Spinner) findViewById(R.id.spinner_staff_department);
        mCheckBoxLeftJob = (CheckBox) findViewById(R.id.checkbox_left_job);
        mEnableEditingButton = (Button) findViewById(R.id.btn_enable_editing);

        mEditStaffBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment fragment = new DatePickerDialogFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mEditStaffBirthday.setText(String.format("%s-%s-%s", year, month + 1, day));
                        updateDate(year, month, day);
                    }
                };
                fragment.show(getSupportFragmentManager(), "");
            }
        });

        for (Department department : mDepartments) {
            mListOfDeparmentsName.add(department.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mListOfDeparmentsName);
        mSpinnerDepartment.setAdapter(adapter);
    }

    public void saveNewStaff(View view) {
        String staffName = mEditStaffName.getText().toString();
        Date staffBirthday = DateUtils.convertFromString(mEditStaffBirthday.getText().toString(), TypesFormat.DATE_FORMAT);
        String staffPlaceOfBirth = mEditStaffAddress.getText().toString();
        String staffPhone = mEditStaffPhone.getText().toString();
        String staffPosition = mEditStaffPositon.getText().toString();
        Boolean staffLeftJob = mCheckBoxLeftJob.isChecked();
        Department department = mDepartments.get(mSpinnerDepartment.getSelectedItemPosition());
        mStaff = new Staff(staffName, staffPlaceOfBirth, staffBirthday, staffPhone, staffPosition, staffLeftJob, department);
        try {
            String insertingStatus = getString(R.string.error_failed);
            if (mStaffHelper.insert(mStaff) > -1) {
                insertingStatus = String.format(getString(R.string.msg_insert_successfully), staffName);
                mEditStaffName.setText(null);
                mEditStaffBirthday.setText(null);
                mEditStaffAddress.setText(null);
                mEditStaffPositon.setText(null);
                mEditStaffPhone.setText(null);
            }
            Toast.makeText(this, insertingStatus, Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }
}
