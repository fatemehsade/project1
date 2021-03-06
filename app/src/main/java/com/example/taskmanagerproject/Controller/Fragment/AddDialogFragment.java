package com.example.taskmanagerproject.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.Repository.TaskDBRepository;
import com.example.taskmanagerproject.Utils.DateUtils;

import java.util.Date;
import java.util.UUID;

public class AddDialogFragment extends DialogFragment {
    public static final String ARGS_TASK_STATE = "com.example.taskmanagerproject.taskState";
    public static final String EXTRA_TASK = "com.example.taskmanagerproject.Task";
    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 4;
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 5;
    public static final String ARGS_USER_ID = "userId";
    public static final String TAG_DATE_PICKER_FRAGMENT =
            "com.example.taskmanagerproject.datePickerFragment";
    public static final String TAG_TIME_PICKER_FRAGMENT =
            "com.example.taskmanagerproject.timePickerFragment";
    private EditText mEditText_title,mEditText_description;
    private Button mButton_time,mButton_date,mButton_save,mButton_cancel;
    private TaskDBRepository mRepository;
    private Task mTask;
    private String mTaskState;
    private Date userSelectedDate,userSelectedTime;
    private Date randomDate=DateUtils.randomDate();
    private UUID mUserId;


    public static AddDialogFragment newInstance(String taskState, UUID userId) {

        Bundle args = new Bundle();
        AddDialogFragment fragment = new AddDialogFragment();
        args.putString(ARGS_TASK_STATE,taskState);
        args.putSerializable(ARGS_USER_ID,userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository=TaskDBRepository.getInstance(getActivity());
        mTaskState=getArguments().getString(ARGS_TASK_STATE);
        mUserId= (UUID) getArguments().getSerializable(ARGS_USER_ID);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(com.example.taskmanagerproject.R.layout.fragment_add_dialog
                ,container,false);
        findViews(view);
        setListener();
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode!=Activity.RESULT_OK || data==null)
            return;
        if (requestCode==REQUEST_CODE_DATE_PICKER_FRAGMENT){
            userSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment.USER_SELECTED_DATE);
        }
        if (requestCode==REQUEST_CODE_TIME_PICKER_FRAGMENT){
            userSelectedTime= (Date) data.getSerializableExtra(TimePickerFragment.TIME_USER_SELECTED);
        }
    }

    private void findViews(View view) {
        mEditText_title=view.findViewById(com.example.taskmanagerproject.R.id.dialog_title);
        mEditText_description=view.findViewById(
                com.example.taskmanagerproject.R.id.dialog_description);
        mButton_date=view.findViewById(com.example.taskmanagerproject.R.id.dialog_date);
        mButton_time=view.findViewById(com.example.taskmanagerproject.R.id.dialog_time);
        mButton_save=view.findViewById(com.example.taskmanagerproject.R.id.dialog_save);
        mButton_cancel=view.findViewById(com.example.taskmanagerproject.R.id.dialog_cancel);
    }
    private void initViews(){
        mTask=new Task();
        mTask.setState(mTaskState);
        if (mEditText_title.getText().toString().equals("")) {
            mEditText_title.setText(" ");
        }
        mTask.setTitle(mEditText_title.getText().toString());

        if (mEditText_description.getText().toString().equals("")){
            mEditText_description.setText(" ");
        }
        mTask.setDescription(mEditText_description.getText().toString());

        if (mButton_date.getText().toString().equals("")){
            mButton_date.setText(DateUtils.getCurrentDate(new Date()));
            mTask.setDate(DateUtils.getCurrentDate(mButton_date.getText().toString()));
        }else {
        mTask.setDate(userSelectedDate);}

        if (mButton_time.getText().toString().equals("")){
            mButton_time.setText(DateUtils.getCurrentTime(new Date()));
            mTask.setTime(DateUtils.getCurrentTime(mButton_time.getText().toString()));
        }else {
        mTask.setTime(userSelectedTime);}
        mTask.setUserId(mUserId);
    }

    private void setListener(){
        mButton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        mButton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViews();
                mRepository.insertTask(mTask);
                sendResult();
                dismiss();

            }
        });

        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment=DatePickerFragment.newInstance(randomDate);
                datePickerFragment.setTargetFragment(AddDialogFragment.this,
                        REQUEST_CODE_DATE_PICKER_FRAGMENT);
                datePickerFragment.show(getActivity().getSupportFragmentManager()
                        , TAG_DATE_PICKER_FRAGMENT);
            }
        });

        mButton_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment=TimePickerFragment.newInstance(randomDate);
                timePickerFragment.setTargetFragment(AddDialogFragment.this,
                        REQUEST_CODE_TIME_PICKER_FRAGMENT);
                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        TAG_TIME_PICKER_FRAGMENT);

            }
        });
    }

    private void sendResult(){
        Fragment fragment=getTargetFragment();
        int requestCode=getTargetRequestCode();
        int resultCode= Activity.RESULT_OK;
        Intent intent=new Intent();
        intent.putExtra(EXTRA_TASK,mTask);
        fragment.onActivityResult(requestCode,resultCode,intent);
    }
}
