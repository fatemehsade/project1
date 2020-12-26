package com.example.taskmanagerproject.Controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.taskmanagerproject.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String ARGS_TASK_DATE = "com.example.taskmanagerproject.taskDate";
    public static final String USER_SELECTED_DATE = "com.example.taskmanagerproject.userSelected";
    private Date mTaskDate;
    private DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }


    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskDate = (Date) getArguments().getSerializable(ARGS_TASK_DATE);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_date_picker, null);
        findViews(view);
        initViews();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("crime");
        builder.setIcon(R.mipmap.ic_launcher); builder.setNegativeButton(android.R.string.cancel,
                null);
        builder.setView(view);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date date = userSelectedDate();
                sendResult(date);

            }
        });
        return builder.create();


    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker_fragment);
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }


    private Date userSelectedDate() {
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth() + 1;
        int year = mDatePicker.getYear();

        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTime();
    }

    private void sendResult(Date userSelected) {
        Fragment fragment = getTargetFragment();

        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(USER_SELECTED_DATE, userSelected);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}