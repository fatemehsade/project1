package com.example.taskmanagerproject.Controller.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.taskmanagerproject.R;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment {

    public static final String ARGS_TIME_TASK = "com.example.taskmanagerproject.timeTask";
    public static final String TIME_USER_SELECTED = "com.example.taskmanagerproject.timeSelected";
    private Date mTimeTask;
    private TimePicker mTimePicker;

    public TimePickerFragment() {
        // Required empty public constructor
    }


    public static TimePickerFragment newInstance(Date time) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TIME_TASK, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimeTask = (Date) getArguments().getSerializable(ARGS_TIME_TASK);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_time_picker, null);
        findViews(view);
        initViews();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date date = userSelectedTime();
                sendResult(date);

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();

    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_fragment);
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTimeTask);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

    }

    private Date userSelectedTime() {
        int hour = mTimePicker.getCurrentHour();
        int minute = mTimePicker.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();

    }

    private void sendResult(Date userSelected) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(TIME_USER_SELECTED, userSelected);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}