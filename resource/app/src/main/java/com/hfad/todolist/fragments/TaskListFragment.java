package com.hfad.todolist.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.R;
import com.hfad.todolist.activities.TaskActivity;
import com.hfad.todolist.activities.TaskActivity.TaskType;
import com.hfad.todolist.adapter.TaskAdapter;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;
import com.hfad.todolist.models.Task;
import com.hfad.todolist.models.TaskList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskListFragment extends Fragment
    implements AddTaskFragment.CallBacks{
    private static final String ARG_TASK_TYPE = "task_type";
    private static final String ARG_PROJECT_ID = "project_id";
    private Button mAddTaskButton;
    private FrameLayout mAddTaskForm;
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;

    private TaskType mTaskType;
    private UUID mProjectId;
    private TaskList mTaskList;
    private TaskAdapter mAdapter;

    private boolean showFormAddTask = false;

    public static Fragment newInstance(TaskType type, UUID projectId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TASK_TYPE, type);
        bundle.putSerializable(ARG_PROJECT_ID, projectId);
        Fragment fragment = new TaskListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskList = TaskList.getInstance(getActivity());
        Bundle args = getArguments();
        if(args != null) {
            mTaskType = (TaskType) args.getSerializable(ARG_TASK_TYPE);
            mProjectId = (UUID) args.getSerializable(ARG_PROJECT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        inflateView(view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toogleFormAddTask();
            }
        });

        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_add_task);
        if(fragment == null) {
            fragment = AddTaskFragment.newInstance(mProjectId,null);
            fm.beginTransaction()
                    .add(R.id.fragment_add_task, fragment)
                    .commit();
        }

        updateUI();

        return view;
    }

    @Override
    public void onExecuteTask(Task task) {
        TaskList.getInstance(getActivity()).addTask(task);
        updateItem(task);
        toogleFormAddTask();
        hideKeyboard(getActivity());
    }

    @Override
    public void onCancel() {
        toogleFormAddTask();
        hideKeyboard(getActivity());
    }

    void inflateView(View view) {
        mRecyclerView = view.findViewById(R.id.task_recycler_view);
        mAddTaskButton = view.findViewById(R.id.add_task_button);
        mAddTaskForm = view.findViewById(R.id.fragment_add_task);
        mEmptyView = view.findViewById(R.id.empty_view);
    }

    private void toogleFormAddTask() {
        showFormAddTask = !showFormAddTask;
        if(showFormAddTask) {
            mAddTaskForm.setVisibility(View.VISIBLE);
            mAddTaskButton.setVisibility(View.GONE);
        }
        else {
            mAddTaskForm.setVisibility(View.GONE);
            mAddTaskButton.setVisibility(View.VISIBLE);
        }
    }

    public void updateUI() {
        List<Task> tasks = getTasks();

        //Check để hiển thị recycler view hay empty view
        if(tasks.size()>0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
        else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }

        if(mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateItem(Task task) {
        if(mAdapter != null) {
            mAdapter.addTask(task);
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<Task> getTasks() {
        if(mTaskType.equals(TaskType.ALL)) {
            return mTaskList.getAllTasks();
        }
        else if(mTaskType.equals(TaskType.TODAY)) {
            return mTaskList.getTasksByDate(new Date());
        }
        else if(mTaskType.equals(TaskType.PROJECT_ID)) {
            return mTaskList.getTasksByProjectId(mProjectId);
        }
        return new ArrayList<Task>();
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
