package com.example.taskmanagerproject.Controller.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskRepository;

public class ShowDialogFragment extends DialogFragment {
    public static final String EXTRA_TASK = "com.example.taskmanagerproject.task";
    public static final String EXTRA_TASK_STATE = "com.example.taskmanagerproject.taskState";
    private EditText mEditText_title,mEditText_description,
            mEditText_state;
    private Button mButton_date,mButton_time,mButton_edit,
            mButton_delete,mButton_save,mButton_cancel;
    private Task mTask;
    private String mTaskState;
    private TaskRepository mRepository;


    public static ShowDialogFragment newInstance(Task task,String taskState) {

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TASK,task);
        args.putString(EXTRA_TASK_STATE,taskState);
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
        mButton_date.setText(task.getDate().toString());
        mButton_time.setText(task.getTime().toString());
        mEditText_state.setText(mTaskState);
    }
    private void setListener(){
        mButton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        mButton_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
