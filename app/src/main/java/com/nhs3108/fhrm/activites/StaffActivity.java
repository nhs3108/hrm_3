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
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.nhs3108.fhrm.utils.StringWithTag;

import java.util.ArrayList;

/**
 * Created by hongson on 23/12/2015.
 */
public class StaffActivity extends Activity {
    private enum TypeOfStaffList {SEARCH_RESULTS, SAME_DEPARTMENT};

    private static int sCurrentPage = 1;
    private final int PER_PAGE = 10;
    private DepartmentHelper mDepartmentHelper = new DepartmentHelper(this);
    private StaffHelper mStaffHelper = new StaffHelper(this);
    private ArrayList<Staff> mStaffList = new ArrayList<Staff>();
    private LinearLayoutManager mLayoutManager;
    private Department mDepartment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private SearchView mStaffSearchView;
    private EndlessRecyclerOnScrollListener mOnScrollListener;
    private Spinner mSpinnerSearchFieldChosing;
    private TypeOfStaffList mStaffListType = TypeOfStaffList.SAME_DEPARTMENT;

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
            mRecyclerView.setAdapter(mAdapter);
            mOnScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    loadMoreData();
                }
            };
            mRecyclerView.addOnScrollListener(mOnScrollListener);
            mStaffSearchView = (SearchView) findViewById(R.id.search_input_query);
            mStaffSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    reloadData(TypeOfStaffList.SEARCH_RESULTS);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty() || newText == null) {
                        reloadData(TypeOfStaffList.SAME_DEPARTMENT);
                    }
                    return false;
                }
            });
            mStaffSearchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<StringWithTag> searchFields = new ArrayList<StringWithTag>();
                    searchFields.add(new StringWithTag(getString(R.string.field_staff_name), Staff.STAFF_NAME));
                    searchFields.add(new StringWithTag(getString(R.string.field_staff_address), Staff.STAFF_PLACE_OF_BIRTH));
                    searchFields.add(new StringWithTag(getString(R.string.field_staff_deparment), Staff.STAFF_DEPARTMENT_NAME));
                    mSpinnerSearchFieldChosing = (Spinner) findViewById(R.id.search_field_chosing);
                    ArrayAdapter adapter = new ArrayAdapter<StringWithTag>(StaffActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, searchFields);
                    mSpinnerSearchFieldChosing.setAdapter(adapter);
                    mSpinnerSearchFieldChosing.setVisibility(View.VISIBLE);
                }
            });
            mStaffSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    mSpinnerSearchFieldChosing.setVisibility(View.GONE);
                    return false;
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(this, getString(R.string.msg_department_not_found), Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.msg_sql_exception), Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.recreate();
        }
    }

    private void loadData() {
        if (mStaffListType == TypeOfStaffList.SAME_DEPARTMENT) {
            new StaffLoadingAsyc().execute();
        } else {
            String searchField = ((StringWithTag) mSpinnerSearchFieldChosing.getSelectedItem()).getTag();
            new StaffLoadingAsyc().execute(searchField, mStaffSearchView.getQuery().toString());
        }
    }

    private void reloadData(TypeOfStaffList type) {
        mStaffListType = type;
        mStaffList.clear();
        mOnScrollListener.reset();
        loadData();
        mAdapter.notifyDataSetChanged();
    }

    private void loadMoreData() {
        loadData();
        mAdapter.notifyDataSetChanged();
    }

    public class StaffLoadingAsyc extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            super.onPreExecute();
            String dialogTitle = getString(R.string.title_loading);
            String dialogMessage = getString(R.string.msg_please_wait);
            dialog = ProgressDialog.show(StaffActivity.this, dialogTitle, dialogMessage, true, false);
        }

        @Override
        protected Void doInBackground(String... params) {
            ArrayList<Staff> newStaffs;
            if (params.length == 2) {
                newStaffs = mStaffHelper.getByField(params[0], params[1], mStaffList.size(), PER_PAGE);
            } else {
                newStaffs = mStaffHelper.getStaffsBelongOfDeparment(mDepartment, mStaffList.size(), PER_PAGE);
            }
            mStaffList.addAll(newStaffs);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPreExecute();
            dialog.dismiss();
        }
    }
}
