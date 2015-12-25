package com.nhs3108.fhrm.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhs3108.fhrm.R;
import com.nhs3108.fhrm.adapters.StaffAdapter;
import com.nhs3108.fhrm.constants.MenuConstants;
import com.nhs3108.fhrm.listeners.EndlessRecyclerOnScrollListener;
import com.nhs3108.fhrm.models.Department;
import com.nhs3108.fhrm.models.DepartmentHelper;
import com.nhs3108.fhrm.models.Staff;
import com.nhs3108.fhrm.models.StaffHelper;

import java.util.ArrayList;

/**
 * Created by hongson on 23/12/2015.
 */
public class StaffActivity extends Activity {
    private static int sCurrentPage = 1;
    private final int PER_PAGE = 10;
    private DepartmentHelper mDepartmentHelper = new DepartmentHelper(this);
    private StaffHelper mStaffHelper = new StaffHelper(this);
    private ArrayList<Staff> mStaffList = new ArrayList<Staff>();
    private LinearLayoutManager mLayoutManager;
    private Department mDepartment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        try {
            Intent data = getIntent();
            mDepartment = (Department) data.getExtras().get("department");
            TextView textDepartmentName = (TextView) findViewById(R.id.text_department_header);
            textDepartmentName.setText(mDepartment.getName());
            loadData();
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_staffs);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new StaffAdapter(this, mStaffList);
            registerForContextMenu(mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    loadMoreData();
                }
            });
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
                intent.putExtra("staffId", mStaffList.get(info.position).getId());
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

    private void loadData() {
        new StaffLoadingAsyc().execute();
    }

    private void loadMoreData() {
        loadData();
        mAdapter.notifyDataSetChanged();
    }

    public class StaffLoadingAsyc extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            super.onPreExecute();
            String dialogTitle = getString(R.string.title_loading);
            String dialogMessage = getString(R.string.msg_please_wait);
            dialog = ProgressDialog.show(StaffActivity.this, dialogTitle, dialogMessage, true, false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<Staff> newStaffs = mStaffHelper.getStaffsBelongOfDeparment(mDepartment, mStaffList.size(), PER_PAGE);
            mStaffList.addAll(newStaffs);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPreExecute();
            dialog.dismiss();
        }
    }
}
