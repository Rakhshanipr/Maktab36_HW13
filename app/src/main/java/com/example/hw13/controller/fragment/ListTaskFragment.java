package com.example.hw13.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hw13.R;
import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;
import com.example.hw13.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListTaskFragment extends Fragment {
    public static final String TAG_EDIT_TASK_DIALOG_FRAGMENT = "com.example.hw13.controller.fragment.editTaskDialogFragment";
    //region defind variable
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    TaskRepository mTaskRepository;
    FloatingActionButton mFloatingActionButtonAddTask;
    //endregion

    //region defind static method and variable
    public static final String ARG_STATE = "com.example.hw13.controller.fragment.ListTaskFragment.state";
    public static final String TAG_ADD_TASK_FRAGMENT_DIALOG = "com.example.hw13.controller.fragment.ListTaskFragment.addTaskFragmentDialog";


    //endregion

    public static ListTaskFragment newInstance(int intState) {
        State state = State.values()[intState];
        Bundle args = new Bundle();
        args.putSerializable(ARG_STATE, state);
        ListTaskFragment fragment = new ListTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_task, container, false);
        findViews(view);
        setInitialization();
        setOnClickListners();
        return view;
    }

    private void findViews(View view) {
        mFloatingActionButtonAddTask = view.findViewById(R.id.floatingActionButton_fragmentListTask_addTask);
        mRecyclerView = view.findViewById(R.id.recyclerview_fragmentListTask);
    }

    private void setOnClickListners() {
        mFloatingActionButtonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//      mTaskRepository.add(new Task("programing", "excesise 13", State.Done, new Date(), null, null));

                AddTaskFragment fragment =AddTaskFragment.newInstance();
                fragment.show(getFragmentManager(), TAG_ADD_TASK_FRAGMENT_DIALOG);
            }
        });

    }

    private void setInitialization() {
        State state = (State) getArguments().getSerializable(ARG_STATE);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        mTaskRepository.add(new Task("maktab","to solve excesise 13",State.Doing,new Date(),LocalTime.of(12,12), User.sOnlineUser));
        mMyAdapter = new MyAdapter(mTaskRepository.getList(state));

        mRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMyAdapter.notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //region defind variable
        private TextView mEditTextTitle;
        private TextView mEditTextDescribe;
        private TextView mEditTextDate;
        private TextView mEditTextTime;
        private TextView mEditTextState;

        Task mTask;
        //endregion
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditTextTitle = itemView.findViewById(R.id.textView_recyclerview_title);
            mEditTextDescribe = itemView.findViewById(R.id.textView_recyclerview_describe);
            mEditTextState = itemView.findViewById(R.id.textView_recyclerview_state);
            mEditTextDate = itemView.findViewById(R.id.textView_recyclerview_date);
            mEditTextTime = itemView.findViewById(R.id.textView_recyclerview_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditTaskFragment editTaskFragment=EditTaskFragment.newInstance(mTask.getId());
                    editTaskFragment.show(getFragmentManager(), TAG_EDIT_TASK_DIALOG_FRAGMENT);
                }
            });
        }

        public void setItem(Task task) {
            mTask=task;
            mEditTextTitle.setText(task.getTitle());
            mEditTextDescribe.setText(task.getDescribe());
            mEditTextTime.setText(task.getLocalTime().toString());
            mEditTextState.setText(task.getState().toString());
            mEditTextDate.setText(task.getDate().toString());

        }
    }
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<Task> mTaskList;


        public MyAdapter(List<Task> list) {
            mTaskList = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_list_task, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setItem(mTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }
}