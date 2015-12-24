package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.constants.TypesFormat;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.DepartmentHelper;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.models.StaffHelper;
import com.nhs3108.fhrm.utils.DateUtils;

import java.util.ArrayList;

/**
 * Created by hongson on 24/12/2015.
 */
public class StaffEditingActivity extends Activity {
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
        setContentView(R.layout.activity_staff_editing);
        mContainerEditing = (ViewGroup) findViewById(R.id.container_editing_body);
        setEnableControls(false, mContainerEditing);
        Intent data = getIntent();
        int staffId = data.getIntExtra("staffId", -1);
        try {
            mStaff = mStaffHelper.getById(staffId);
            mDepartments = mDepartmentHelper.getAll();
        } catch (NullPointerException e) {
            Toast.makeText(this, getString(R.string.msg_staff_not_found), Toast.LENGTH_SHORT).show();
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

        mEditStaffName.setText(mStaff.getName());
        mEditStaffBirthday.setText(DateUtils.convertToString(mStaff.getDateOfBirth(), TypesFormat.DATE_FORMAT));
        mEditStaffAddress.setText(mStaff.getPlaceOfBirth());
        mEditStaffPhone.setText(mStaff.getPhone());
        mEditStaffPositon.setText(mStaff.getPosition());
        mCheckBoxLeftJob.setChecked(mStaff.isLeftJob());
        for (Department department : mDepartments) {
            mListOfDeparmentsName.add(department.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mListOfDeparmentsName);
        mSpinnerDepartment.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(mStaff.getDepartment().getName());
        mSpinnerDepartment.setSelection(spinnerPosition);
    }

    public void enableStaffEditing(View v) {
        mEnableEditingButton.setVisibility(View.GONE);
        setEnableControls(true, mContainerEditing);
        final Button saveButton = new Button(this);
        saveButton.setText(getString(R.string.label_save));
        LinearLayout layout = (LinearLayout) findViewById(R.id.container_action_buttons);
        layout.addView(saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    updateStaffProfile();
                    setEnableControls(false, mContainerEditing);
                    saveButton.setVisibility(View.GONE);
                    mEnableEditingButton.setVisibility(View.VISIBLE);
                    setResult(RESULT_OK);
                    Toast.makeText(StaffEditingActivity.this, getString(R.string.msg_successfully),
                            Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    Toast.makeText(StaffEditingActivity.this, getString(R.string.error_failed),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateStaffProfile() throws SQLException {
        mStaff.setName(mEditStaffName.getText().toString());
        mStaff.setDateOfBirth(DateUtils.convertFromString(mEditStaffBirthday.getText().toString(), TypesFormat.DATE_FORMAT));
        mStaff.setPlaceOfBirth(mEditStaffAddress.getText().toString());
        mStaff.setPhone(mEditStaffPhone.getText().toString());
        mStaff.setPosition(mEditStaffPositon.getText().toString());
        mStaff.setLeftJob(mCheckBoxLeftJob.isChecked());
        mStaff.setDepartment(mDepartments.get(mSpinnerDepartment.getSelectedItemPosition()));

        mStaffHelper.update(mStaff);
    }

    private void setEnableControls(boolean enable, ViewGroup parent) {
        int quantityOfChilds = parent.getChildCount();
        for (int i = 0; i < quantityOfChilds; i++) {
            View child = parent.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                setEnableControls(enable, (ViewGroup) child);
            }
        }
    }
}
