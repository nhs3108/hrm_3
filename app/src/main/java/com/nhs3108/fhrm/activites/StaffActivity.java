package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.adapters.StaffAdapter;
import com.nhs3108.fhrm.constants.MenuConstants;
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
            registerForContextMenu(listStaffs);
        } catch (NullPointerException e) {
            Toast.makeText(this, getString(R.string.msg_department_not_found), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, view.getId(), Menu.NONE, MenuConstants.TITLE_SHOW_DETAIL);
        menu.add(Menu.NONE, view.getId(), Menu.NONE, MenuConstants.TITLE_DELETE);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case MenuConstants.TITLE_SHOW_DETAIL:
                Intent intent = new Intent(this, StaffEditingActivity.class);
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                intent.putExtra("staffId", staffs.get(info.position).getId());
                startActivityForResult(intent, 1);
                break;
            case MenuConstants.TITLE_DELETE:
                break;
            default:
                break;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.recreate();
        }
    }
}
