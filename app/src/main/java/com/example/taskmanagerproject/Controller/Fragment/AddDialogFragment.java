package com.example.taskmanagerproject.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskRepository;

import java.util.ArrayList;
import java.util.Date;

import static android.os.Build.VERSION_CODES.R;

public class AddDialogFragment extends DialogFragment {
    public static final String ARGS_TASK_STATE = "com.example.taskmanagerproject.taskState";
    private EditText mEditText_title,mEditText_description;
    private Button mButton_time,mButton_date,mButton_save,mButton_cancel;
    private TaskRepository mRepository;
    private Task mTask;
    private String mTaskState;


    public static AddDialogFragment newInstance(String taskState) {

        Bundle args = new Bundle();
        AddDialogFragment fragment = new AddDialogFragment();
        args.putString(ARGS_TASK_STATE,taskState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository=TaskRepository.getInstance();
        mTaskState=getArguments().getString(ARGS_TASK_STATE);
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
        if (mEditText_title.getText()==null) {
            mEditText_title.setText("");
        }
        mTask.setTitle(mEditText_title.getText().toString());
        if (mEditText_description.getText()==null){
            mEditText_description.setText("");
        }
        mTask.setDescription(mEditText_description.getText().toString());
        mTask.setDate(new Date());// todo
        mTask.setTime(new Date());//todo
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
                mRepository.insert(mTask,mTaskState);
                mRepository.setBoolean(true,mTaskState);
                dismiss();

            }
        });

        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButton_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
