package com.nhs3108.fhrm.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by hongson on 29/12/2015.
 */
public abstract class DatePickerDialogFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public DatePickerDialogFragment() {
    }

    public DatePickerDialogFragment(int year, int month, int day) {
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    public abstract void onDateSet(DatePicker view, int year, int month, int day);

    public void updateDate(int year, int month, int day) {
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }
}
