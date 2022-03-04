package com.hfad.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.R;
import com.hfad.todolist.models.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder>  {
    List<Task> mTasks;

    public TaskAdapter(List<Task> tasks) {
        this.mTasks = tasks;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_task_item, parent, false);
        return new TaskHolder(view);
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

    public void addTask(Task task) {mTasks.add(task);}
//        public void updateTask(Task task) {
//            for(int i = 0; i < mTasks.size(); i++) {
//                if(mTasks.get(i).getId().equals(task.getId())) {
//                    mTasks.replace()
//                }
//            }
//        }
    /**
     * Set up view holder
     */
    public class TaskHolder extends  RecyclerView.ViewHolder {
        private Task mTask;
        private TextView taskTitle;
        private TextView taskDescription;
        private TextView taskDeadlineDate;
        private TextView taskDeadlineTime;

        public TaskHolder(View view) {
            super(view);
            taskTitle = view.findViewById(R.id.task_title);
            taskDescription = view.findViewById(R.id.task_description);
            taskDeadlineDate = view.findViewById(R.id.deadline_date);
            taskDeadlineTime = view.findViewById(R.id.deadline_time);
        }

        public void bindData(Task task) {
            mTask = task;
            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());
            taskDeadlineDate.setText(getDate(mTask.getDeadline()));
            taskDeadlineTime.setText(getTime(mTask.getDeadline()));
        }

        public String getDate(Date deadline) {
            if(deadline == null) return "No Date";
            return new SimpleDateFormat("MMM dd, YYYY").format(deadline);
        }

        public String getTime(Date deadline) {
            if(deadline == null) return "No Time";
            return new SimpleDateFormat("HH : mm").format(deadline);
        }
    }
}
