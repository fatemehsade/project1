package com.example.taskmanagerproject.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class StateFragment extends Fragment {
    public static final int REQUEST_CODE = 2;
    private ImageView mEmpty_paper;
    private FloatingActionButton mAddBtn;
    private RecyclerView mRecyclerView;
    private String mTaskState;
    private TaskRepository mRepository;
    private List<Task> mTasks;

    public static final String ARGS_STATE_TASK = "com.example.taskmanagerproject.stateTask";

    public StateFragment() {
        // Required empty public constructor
    }

    public static StateFragment newInstance(String stateTask) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_STATE_TASK,stateTask);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskState=getArguments().getString(ARGS_STATE_TASK);
        mRepository=TaskRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_state, container, false);
        findViews(view);
        setListener();
        initViews();
        return view;
    }

    private void findViews(View view){
        mEmpty_paper=view.findViewById(R.id.emty_paper);
        mAddBtn=view.findViewById(R.id.add_btn);
        mRecyclerView=view.findViewById(R.id.recycler_view);
    }

    private void setListener(){
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment fragment =AddDialogFragment.newInstance(mTaskState);
                //fragment.show(getActivity().getSupportFragmentManager(),"addDialogFragment");
                fragment.show(getActivity().getSupportFragmentManager(),"addDialogFragment");



            }
        });
    }

    public class TaskAdaptor extends RecyclerView.Adapter<taskViewHolder>{
        private List<Task> mTaskList;

        public TaskAdaptor(List<Task> taskList) {
            mTaskList = taskList;
        }

        @NonNull
        @Override
        public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_view,parent,false);
            taskViewHolder viewHolder=new taskViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull taskViewHolder holder, int position) {
            Task task=mTaskList.get(position);
            holder.bindTask(task);
            if (mRepository.setBoolean(true,mTaskState)){
                mEmpty_paper.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }


    }

    public class taskViewHolder extends RecyclerView.ViewHolder {
        private EditText mEditText_imageCircle;
        private TextView mEditText_date,mEditText_time ,mEditText_Title;
        private Task mTasks;

        public taskViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditText_Title=itemView.findViewById(R.id.item_title);
            mEditText_date=itemView.findViewById(R.id.item_date);
            mEditText_time=itemView.findViewById(R.id.item_time);
            mEditText_imageCircle=itemView.findViewById(R.id.img_circle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogFragment dialogFragment=ShowDialogFragment.newInstance(mTasks,mTaskState);
                    //dialogFragment.setTargetFragment(StateFragment.this, REQUEST_CODE);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),
                            "showDialogFrragment");
                }
            });
        }

    public void bindTask(Task tasks) {
        mTasks=tasks;
        mEditText_Title.setText(tasks.getTitle());
        mEditText_date.setText(tasks.getDate().toString());
        mEditText_time.setText(tasks.getTime().toString());
        mEditText_imageCircle.setText(tasks.getTitle().substring(0,1));
    }


    }

    private void initViews(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTasks=mRepository.get(mTaskState);
        TaskAdaptor taskAdaptor=new TaskAdaptor(mTasks);
        mRecyclerView.setAdapter(taskAdaptor);

        //taskAdaptor.notifyDataSetChanged();
    }
}