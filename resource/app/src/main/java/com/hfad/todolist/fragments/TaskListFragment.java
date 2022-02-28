package com.hfad.todolist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.R;
import com.hfad.todolist.databinding.FragmentTaskListBinding;
import com.hfad.todolist.databinding.ListTaskItemBinding;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;
import com.hfad.todolist.models.Task;
import com.hfad.todolist.models.TaskList;
import com.hfad.todolist.viewmodels.ProjectViewModel;
import com.hfad.todolist.viewmodels.TaskViewModel;

import java.util.List;

public class TaskListFragment extends Fragment
    implements AddTaskFragment.CallBacks{
    private FragmentTaskListBinding mBinding;
    private TaskList mTaskList;
    private TaskAdapter mAdapter;

    private boolean showFormAddTask = false;

    public static Fragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskList = TaskList.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_task_list, container, false);

        mBinding.taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBinding.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toogleFormAddTask();
            }
        });

        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_add_task);
        if(fragment == null) {
            fragment = AddTaskFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_add_task, fragment)
                    .commit();
        }

        updateUI();

        return mBinding.getRoot();
    }

    private void toogleFormAddTask() {
        showFormAddTask = !showFormAddTask;
        if(showFormAddTask) {
            mBinding.fragmentAddTask.setVisibility(View.VISIBLE);
            mBinding.addTaskButton.setVisibility(View.GONE);
        }
        else {
            mBinding.fragmentAddTask.setVisibility(View.GONE);
            mBinding.addTaskButton.setVisibility(View.VISIBLE);
        }
    }

    public void updateUI() {
        List<Task> tasks = mTaskList.getAllTasks();

        //Check để hiển thị recycler view hay empty view
        if(tasks.size()>0) {
            mBinding.taskRecyclerView.setVisibility(View.VISIBLE);
            mBinding.emptyView.setVisibility(View.GONE);
        }
        else {
            mBinding.taskRecyclerView.setVisibility(View.GONE);
            mBinding.emptyView.setVisibility(View.VISIBLE);
        }

        if(mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mBinding.taskRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setTasks(tasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onExecuteTask(Task task) {
        TaskList.getInstance(getActivity()).addTask(task);
        showFormAddTask = false;
    }

    @Override
    public void onCancel() {
        showFormAddTask = false;
    }

    /**
     * Set up view holder
     */
    private class TaskHolder extends  RecyclerView.ViewHolder {
        private ListTaskItemBinding mBinding;

        public TaskHolder(ListTaskItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setTaskViewModel(new TaskViewModel());
        }

        public void bindData(Task task) {
            mBinding.getTaskViewModel().setTask(task);
            mBinding.executePendingBindings();
        }
    }

    /**
     * Set up adapter
     */
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            this.mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            ListTaskItemBinding binding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.list_task_item,
                    parent, false);
            return new TaskHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindData(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        public void setTasks(List<Task> tasks) {
            this.mTasks = tasks;
        }
    }
}
