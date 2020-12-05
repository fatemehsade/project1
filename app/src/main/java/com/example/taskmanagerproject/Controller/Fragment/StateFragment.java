package com.example.taskmanagerproject.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskDBRepository;
import com.example.taskmanagerproject.Utils.DateUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class StateFragment extends Fragment {
    public static final int REQUEST_CODE_ADD_DIALOG_FRAGMENT = 0;
    public static final int REQUEST_CODE_SHOW_DIALOG_FRAGMENT = 3;
    public static final String ARGS_USER_ID =
            "com.example.taskmanagerproject.userId";
    public static final String TAG_ADD_DIALOG_FRAGMENT =
            "com.example.taskmanagerproject.addDialogFragment";
    public static final String TAG_DIALOG_FRAGMENT =
            "com.example.taskmanagerproject.dialogFragment";
    public static final String ARGS_STATE_TASK =
            "com.example.taskmanagerproject.stateTask";


    private ImageView mEmpty_paper;
    private FloatingActionButton mAddBtn;
    private RecyclerView mRecyclerView;
    private String mTaskState;
    private TaskDBRepository mRepository;
    private UUID mUserId;
    private TaskAdaptor mTaskAdaptor;
    private Task inputTask;
    private Task mTask;


    public StateFragment() {
        // Required empty public constructor
    }

    public static StateFragment newInstance(String stateTask, UUID userId) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_STATE_TASK, stateTask);
        args.putSerializable(ARGS_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskState = getArguments().getString(ARGS_STATE_TASK);
        mRepository = TaskDBRepository.getInstance(getActivity());
        mUserId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_state, container, false);
        findViews(view);
        initViews();
        setListener();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_DIALOG_FRAGMENT) {
            updateUi();
        }
        if (requestCode == REQUEST_CODE_SHOW_DIALOG_FRAGMENT) {
            inputTask = (Task) data.getSerializableExtra(ShowDialogFragment.EXTRA_EDIT_TASK);
            updateUi();
        }
    }

    private void findViews(View view) {
        mEmpty_paper = view.findViewById(R.id.emty_paper);
        mAddBtn = view.findViewById(R.id.add_btn);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setListener() {
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AddDialogFragment fragment = AddDialogFragment.newInstance(mTaskState, mUserId);
                fragment.setTargetFragment(StateFragment.this,
                        REQUEST_CODE_ADD_DIALOG_FRAGMENT);
                fragment.show(getActivity().getSupportFragmentManager(), TAG_ADD_DIALOG_FRAGMENT);


            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchview, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:


        }
        return false;
    }

    public class TaskAdaptor extends RecyclerView.Adapter<taskViewHolder> {
        private List<Task> mTaskList;

        public List<Task> getTaskList() {
            return mTaskList;
        }

        public void setTaskList(List<Task> taskList) {
            mTaskList = taskList;
        }

        public TaskAdaptor(List<Task> taskList) {
            mTaskList = taskList;
        }

        @NonNull
        @Override
        public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_view, parent, false);
            return new taskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull taskViewHolder holder, int position) {
            Task task = mTaskList.get(position);
            holder.bindTask(task);


        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }


    }

    public class taskViewHolder extends RecyclerView.ViewHolder {
        private final EditText mEditText_imageCircle;
        private final TextView mEditText_date;
        private final TextView mEditText_time;
        private final TextView mEditText_Title;
        private final ImageButton mImageView_share;


        public taskViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditText_Title = itemView.findViewById(R.id.item_title);
            mEditText_date = itemView.findViewById(R.id.item_date);
            mEditText_time = itemView.findViewById(R.id.item_time);
            mEditText_imageCircle = itemView.findViewById(R.id.img_circle);
            mImageView_share = itemView.findViewById(R.id.share_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogFragment dialogFragment =
                            ShowDialogFragment.newInstance(mTask, mTaskState, mUserId);
                    dialogFragment.setTargetFragment(StateFragment.this,
                            REQUEST_CODE_SHOW_DIALOG_FRAGMENT);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_DIALOG_FRAGMENT);
                }
            });

        }

        public void bindTask(Task tasks) {
            mTask = tasks;
            mEditText_Title.setText(tasks.getTitle());
            mEditText_date.setText(DateUtils.getCurrentDate(tasks.getDate()));
            mEditText_time.setText(DateUtils.getCurrentTime(tasks.getTime()));
            if (tasks.getTitle().equals("")) {
                mEditText_imageCircle.setText("");

            } else {
                mEditText_imageCircle.setText(tasks.getTitle().substring(0, 1));
            }

            setListener();
        }

        private void setListener() {
            mImageView_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_STREAM, getDescriptionTask());
                    Toast.makeText(getActivity(), getDescriptionTask(), Toast.LENGTH_SHORT).show();
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }


    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
    }

    private void updateUi() {
        List<Task> tasks = mRepository.getTasks(mUserId, mTaskState);

        mEmpty_paper.setVisibility(View.GONE);
        if (mTaskAdaptor == null) {
            mTaskAdaptor = new TaskAdaptor(tasks);
            mRecyclerView.setAdapter(mTaskAdaptor);
        } else {
            mTaskAdaptor.setTaskList(tasks);
            mTaskAdaptor.notifyDataSetChanged();
        }

        if (tasks.size() == 0) {
            mEmpty_paper.setVisibility(View.VISIBLE);

        }
    }

    private String getDescriptionTask() {
        String TaskDescription;
        if (inputTask != null) {
            TaskDescription = getString(
                    R.string.description_Task,
                    inputTask.getTitle(),
                    inputTask.getDescription(),
                    DateUtils.getCurrentDate(inputTask.getDate()),
                    DateUtils.getCurrentTime(inputTask.getDate()));
        }

        else {
            TaskDescription = getString(
                    R.string.description_Task,
                    mTask.getTitle(),
                    mTask.getDescription(),
                    DateUtils.getCurrentDate(mTask.getDate()),
                    DateUtils.getCurrentTime(mTask.getTime()));

        }
        return TaskDescription;


    }
}