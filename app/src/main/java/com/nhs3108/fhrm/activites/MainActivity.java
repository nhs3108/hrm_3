package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private DepartmentHelper departmentHelper = new DepartmentHelper(this);
    private StaffHelper staffHelper = new StaffHelper(this);
    private ArrayList<Department> departments;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ListView listViewDepartments = (ListView) findViewById(R.id.list_departments);
            departments = departmentHelper.getAll();
            DepartmentAdapter adapter = new DepartmentAdapter(this, R.layout.item_department, departments);
            listViewDepartments.setOnItemClickListener(this);
            listViewDepartments.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, StaffActivity.class);
        Department department = departments.get(position);
        intent.putExtra("department", department);
        startActivity(intent);
    }
}
