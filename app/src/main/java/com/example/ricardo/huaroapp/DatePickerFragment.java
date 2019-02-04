package com.example.ricardo.huaroapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Ricardo on 12/04/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    private DatePickerDialog.OnDateSetListener listener;
    public String fechas;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }
    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener=listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);

        newInstance(listener);
        return new DatePickerDialog(getActivity(),listener,year,month,day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        final String selectDate= day + " / "+ (month+1) +" / "+ year;

    }
}
