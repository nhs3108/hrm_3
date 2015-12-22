package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.adapters.StaffAdapter;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.DepartmentHelper;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.models.StaffHelper;

import java.util.ArrayList;

/**
 * Created by hongson on 23/12/2015.
 */
public class StaffActivity extends Activity {
    private DepartmentHelper departmentHelper = new DepartmentHelper(this);
    private StaffHelper staffHelper = new StaffHelper(this);
    private ArrayList<Staff> staffs;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        try {
            Intent data = getIntent();
            Department department = (Department) data.getExtras().get("department");
            TextView textDepartmentName = (TextView) findViewById(R.id.text_department_header);
            textDepartmentName.setText(department.getName());
            staffs = staffHelper.getStaffsBelongOfDeparment(department);
            ListView listStaffs = (ListView) findViewById(R.id.list_staffs);
            StaffAdapter adapter = new StaffAdapter(this, R.layout.item_staff, staffs);
            listStaffs.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(this, getString(R.string.msg_department_not_found), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }
}
