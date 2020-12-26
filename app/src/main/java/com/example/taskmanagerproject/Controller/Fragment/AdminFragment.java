package com.example.taskmanagerproject.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerproject.Controller.Activity.ViewPagerActivity;
import com.example.taskmanagerproject.Model.Task;
import com.example.taskmanagerproject.Model.User;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.TaskDBRepository;
import com.example.taskmanagerproject.Repository.UserDBRepository;
import com.example.taskmanagerproject.Utils.DateUtils;

import java.util.List;


public class AdminFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private UserDBRepository mUserRepository;
    private TaskDBRepository mTaskRepository;
    private List<User> mUser;
    private userAdaptor adaptor;
    private boolean flag = true;

    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserDBRepository.getInstance(getActivity());
        mTaskRepository = TaskDBRepository.getInstance(getActivity());
        mUser = mUserRepository.getUsers();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.admin_recycler_view);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
    }

    private void updateUi() {
        List<User> users = mUserRepository.getUsers();

        if (adaptor == null) {
            adaptor = new userAdaptor(mUser);
            mRecyclerView.setAdapter(adaptor);
        } else {
            adaptor.setUsers(users);
            adaptor.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_icon_menu) {
            if (flag) {
                List<User> users = mUserRepository.getUsers();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).isChoose()) {
                        mTaskRepository.deleteTaskWithUserId(users.get(i).getUserId());
                        mUserRepository.deleteUser(users.get(i));
                        flag = false;
                        updateUi();
                        return true;
                    }
                }
            }
        }
        Toast.makeText(getActivity(), "you can just delete one user ", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);


    }


    public class userAdaptor extends RecyclerView.Adapter<UserViewHolder> {
        List<User> mUsers;

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
        }

        public userAdaptor(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.admin_page_view, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = mUsers.get(position);
            holder.bindUsers(user);

        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserName, mDate, mNumberOfTask;
        private CheckBox mCheckBoxDelete;
        private User mUser;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.UserName_admin_page);
            mNumberOfTask = itemView.findViewById(R.id.numberTask_admin_page);
            mDate = itemView.findViewById(R.id.date_admin_page);
            mCheckBoxDelete = itemView.findViewById(R.id.delete_admin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ViewPagerActivity.newIntent(getActivity(), mUser.getUserId());
                    startActivity(intent);
                }
            });
        }

        public void bindUsers(User user) {
            mUser = user;
            mUserName.setText(user.getUserName());
            mDate.setText(DateUtils.getCurrentDate(user.getDateInput()));
            String number = String.valueOf(mTaskRepository.numberOfTaskEveryUser(user.getUserId()));
            mNumberOfTask.setText(number);
            mCheckBoxDelete.setChecked(user.isChoose());//what do you do?
            mCheckBoxDelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    user.setChoose(isChecked);
                    mUserRepository.updateUser(user);
                }
            });
        }
    }
}