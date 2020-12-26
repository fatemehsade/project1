package com.example.taskmanagerproject.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskDBRepository;
import com.example.taskmanagerproject.Utils.DateUtils;
import com.example.taskmanagerproject.Utils.PictureUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
    public static final int REQUEST_CODE_IMAGE_CAPTURE = 6;
    public static final String AUTHORITY = "com.example.taskmanagerprojec.fileProvider";

    private ImageView mImageViewTakePhoto;
    private ImageView mEmpty_paper;
    private FloatingActionButton mAddBtn;
    private RecyclerView mRecyclerView;
    private String mTaskState;
    private TaskDBRepository mRepository;
    private UUID mUserId;
    private TaskAdaptor mTaskAdaptor;
    private Task inputTask;
    private File mPhotoFile;


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_DIALOG_FRAGMENT) {
            updateUi();
        }
        else if (requestCode == REQUEST_CODE_SHOW_DIALOG_FRAGMENT) {
            inputTask = (Task) data.getSerializableExtra(ShowDialogFragment.EXTRA_EDIT_TASK);
            updateUi();
        }else if (requestCode==REQUEST_CODE_IMAGE_CAPTURE){
            Uri photoUri=generateUriForPhotoFile();
            getActivity().revokeUriPermission(photoUri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();

        }
    }

    private void findViews(View view) {
        mEmpty_paper = view.findViewById(R.id.emty_paper);
        mAddBtn = view.findViewById(R.id.add_btn);
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setListener() {
        mAddBtn.setOnClickListener(new View.OnClickListener() {
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
        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView)
                searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTaskAdaptor.getFilter().filter(newText);
                return false;
            }
        });

    }


    public class TaskAdaptor extends RecyclerView.Adapter<taskViewHolder> implements Filterable {
        private List<Task> mTaskList;
        private List<Task> mSearchResult;
        public void setTaskList(List<Task> taskList) {
            mTaskList = taskList;
        }

        public TaskAdaptor(List<Task> taskList) {
            mTaskList = taskList;
            mSearchResult=new ArrayList<>(taskList);
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


        private Filter mFilter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Task> resultList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0)
                    resultList.addAll(mSearchResult);
                else {

                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Task task : mSearchResult) {
                        if (task.getTitle().contains(filterPattern))
                            resultList.add(task);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = resultList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mTaskList.clear();
                mTaskList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };


        @Override
        public Filter getFilter() {
            return mFilter;
        }
    }

    public class taskViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewTakePhoto;
        private TextView mEditText_date;
        private TextView mEditText_time;
        private TextView mEditText_Title;
        private ImageButton mImageView_share;
        private Task mTask;


        public taskViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditText_Title = itemView.findViewById(R.id.item_title);
            mEditText_date = itemView.findViewById(R.id.item_date);
            mEditText_time = itemView.findViewById(R.id.item_time);
            StateFragment.this.mImageViewTakePhoto = itemView.findViewById(R.id.img_circle);
            mImageView_share = itemView.findViewById(R.id.share_btn);
            mImageViewTakePhoto =itemView.findViewById(R.id.ima_btn_take_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogFragment dialogFragment =
                            ShowDialogFragment.newInstance(mTask, mTaskState, mUserId);
                    dialogFragment.setTargetFragment(StateFragment.this,
                            REQUEST_CODE_SHOW_DIALOG_FRAGMENT);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),
                            TAG_DIALOG_FRAGMENT);
                    setListener();
                }
            });
        }

        public void bindTask(Task tasks) {
            mTask = tasks;
            mEditText_Title.setText(tasks.getTitle());
            mEditText_date.setText(DateUtils.getCurrentDate(tasks.getDate()));
            mEditText_time.setText(DateUtils.getCurrentTime(tasks.getTime()));
        }
        private void setListener() {
            mImageView_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    implicitShareIntent();
                }
            });

            mImageViewTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentTakePhoto();
                }
            });
        }

        private void implicitShareIntent() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, getDescriptionTask(mTask));
            Toast.makeText(getActivity(), getDescriptionTask(mTask), Toast.LENGTH_SHORT).show();
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    private void intentTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            mPhotoFile = null;
            try {
                mPhotoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (mPhotoFile != null) {
                Uri photoURI = generateUriForPhotoFile();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
            }
        }
    }

    private Uri generateUriForPhotoFile() {
        return FileProvider.getUriForFile(
                getContext(),
                AUTHORITY,
                mPhotoFile);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getContext().getApplicationContext(),DividerItemDecoration.VERTICAL));

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
    private String getDescriptionTask(Task task) {
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
                    task.getTitle(),
                    task.getDescription(),
                    DateUtils.getCurrentDate(task.getDate()),
                    DateUtils.getCurrentTime(task.getTime()));

        }
        return TaskDescription;


    }
    private void updatePhotoView(){
        if (mPhotoFile==null ||!mPhotoFile.exists())
            return;

        Bitmap bitmap= PictureUtils.getScaleBitMap(mPhotoFile.getAbsolutePath(),getActivity());
        mImageViewTakePhoto.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getFilesDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}