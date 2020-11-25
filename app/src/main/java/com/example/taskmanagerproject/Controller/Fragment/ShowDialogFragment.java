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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskRepository;
import com.example.taskmanagerproject.Utils.DateUtils;

import java.util.Date;
import java.util.UUID;

public class ShowDialogFragment extends DialogFragment {
    public static final String EXTRA_TASK = "com.example.taskmanagerproject.task";
    public static final String EXTRA_TASK_STATE = "com.example.taskmanagerproject.taskState";
    public static final String EXTRA_EDIT_TASK = "com.example.taskmanagerproject.editTask";
    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 6;
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 7;
    public static final String ARGS_USER_ID = "userId";
    private EditText mEditText_title,mEditText_description,
            mEditText_state;
    private Button mButton_date,mButton_time,mButton_edit,
            mButton_delete,mButton_save,mButton_cancel;
    private Task mTask;
    private String mTaskState;
    private TaskRepository mRepository;
    private Task mEditTask;
    private Date userSelectedDate;
    private Date userSelectedTime;
    private UUID mUserId;


    public static ShowDialogFragment newInstance(Task task, String taskState, UUID userId) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TASK,task);
        args.putString(EXTRA_TASK_STATE,taskState);
        args.putSerializable(ARGS_USER_ID,userId);
        ShowDialogFragment fragment = new ShowDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask= (Task) getArguments().getSerializable(EXTRA_TASK);
        mTaskState=getArguments().getString(EXTRA_TASK_STATE);
        mRepository=TaskRepository.getInstance();
        mUserId= (UUID) getArguments().getSerializable(ARGS_USER_ID);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_dialog,container,false);
        findViews(view);
        initViews(mTask);
        setListener();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode!=Activity.RESULT_OK||data==null)
            return;
        if (requestCode==REQUEST_CODE_DATE_PICKER_FRAGMENT){
            userSelectedDate= (Date)
                    data.getSerializableExtra(DatePickerFragment.USER_SELECTED_DATE);
            mButton_date.setText(DateUtils.getCurrentDate(userSelectedDate));

        }
        if (requestCode==REQUEST_CODE_TIME_PICKER_FRAGMENT){
            userSelectedTime= (Date)
                    data.getSerializableExtra(TimePickerFragment.TIME_USER_SELECTED);
            mButton_time.setText(DateUtils.getCurrentTime(userSelectedTime));

        }
    }

    private void findViews(View view) {
        mEditText_title=view.findViewById(R.id.show_dialog_title);
        mEditText_description=view.findViewById(R.id.show_dialog_description);
        mEditText_state=view.findViewById(R.id.show_dialog_state);
        mButton_cancel=view.findViewById(R.id.show_dialog_cancel);
        mButton_date=view.findViewById(R.id.show_dialog_date);
        mButton_time=view.findViewById(R.id.show_dialog_time);
        mButton_save=view.findViewById(R.id.show_dialog_save);
        mButton_edit=view.findViewById(R.id.show_dialog_edit);
        mButton_delete=view.findViewById(R.id.show_dialog_delete);
    }
    private void initViews(Task task){
        mEditText_title.setText(task.getTitle());
        mEditText_description.setText(task.getDescription());
        mButton_date.setText(DateUtils.getCurrentDate(task.getDate()));
        mButton_time.setText(DateUtils.getCurrentTime(task.getTime()));
        mEditText_state.setText(mTaskState);
    }
    private void setListener(){
        mButton_edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                updateUi();
                mEditTask.setState(mEditText_state.getText().toString());
                    if (!mEditTask.getState().equals(mTaskState)) {
                        mRepository.delete(mTask, mTaskState);
                        mRepository.insert(mEditTask, mEditTask.getState());
                    }

                else {
                    mRepository.editTask(mEditTask.getTaskId(),mTaskState, mEditTask);

                }
                sentResult();
                dismiss();

            }
        });
        mButton_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment=TimePickerFragment.newInstance(mTask.getTime());
                timePickerFragment.setTargetFragment(ShowDialogFragment.this,
                        REQUEST_CODE_TIME_PICKER_FRAGMENT);
                timePickerFragment.show(getActivity().getSupportFragmentManager(),
                        "timePickerFragment");

            }
        });
        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment=
                        DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.setTargetFragment(ShowDialogFragment.this,
                        REQUEST_CODE_DATE_PICKER_FRAGMENT);
                datePickerFragment.show(getActivity().getSupportFragmentManager(),
                        "showDialogFragment");

            }
        });
        mButton_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        mButton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepository.delete(mTask,mTaskState);
                sentResult();
                dismiss();

            }
        });
        mButton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateUi() {
        mEditTask =new Task();
        mEditTask.setTitle(mEditText_title.getText().toString());
        mEditTask.setDescription(mEditText_description.getText().toString());
        mEditTask.setUserId(mUserId);
        if (DateUtils.getCurrentDate(mButton_date.getText().toString()).equals(mTask.getDate())){
            mEditTask.setDate(mTask.getDate());
        }
        else {
            mEditTask.setDate(userSelectedDate);//todo
        }
        if (DateUtils.getCurrentTime(mButton_time.getText().toString()).equals(mTask.getTime())){
            mEditTask.setTime(mTask.getTime());
        }else {
            mEditTask.setTime(userSelectedTime);
        }
        //editTask.setTime(new Date());//todo
    }


    private void sentResult(){
        Fragment fragment=getTargetFragment();
        int requestCode=getTargetRequestCode();
        int resultcode= Activity.RESULT_OK;
        Intent intent=new Intent();
        intent.putExtra(EXTRA_EDIT_TASK, mEditTask);
        fragment.onActivityResult(requestCode,resultcode,intent);
    }
}
