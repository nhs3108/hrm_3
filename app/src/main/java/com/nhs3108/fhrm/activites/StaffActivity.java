package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ListView;
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
    DepartmentHelper departmentHelper = new DepartmentHelper(this);
    StaffHelper staffHelper = new StaffHelper(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        try {
            Intent data = getIntent();
            Department department = (Department) data.getExtras().get("department");
            ArrayList<Staff> staffs = staffHelper.getStaffsBelongOfDeparment(department);
            ListView listViewStaffs = (ListView) findViewById(R.id.list_staffs);
            StaffAdapter adapter = new StaffAdapter(this, R.layout.item_staff, staffs);
            listViewStaffs.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }
}
