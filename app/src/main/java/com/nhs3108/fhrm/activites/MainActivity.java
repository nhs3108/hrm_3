package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.adapters.DepartmentAdapter;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.DepartmentHelper;
import com.nhs3108.fhrm.models.StaffHelper;

import java.util.ArrayList;

/**
 * Created by hongson on 20/12/2015.
 */
public class MainActivity extends Activity {
    DepartmentHelper departmentHelper = new DepartmentHelper(this);
    StaffHelper staffHelper = new StaffHelper(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ListView listViewDepartments = (ListView) findViewById(R.id.list_departments);
            ArrayList<Department> departments = departmentHelper.getAll();
            DepartmentAdapter adapter = new DepartmentAdapter(this, R.layout.item_department, departments);
            listViewDepartments.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }
}
