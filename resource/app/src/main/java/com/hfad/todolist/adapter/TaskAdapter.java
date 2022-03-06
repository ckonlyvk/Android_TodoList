package com.hfad.todolist.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.R;
import com.hfad.todolist.models.Task;
import com.hfad.todolist.models.TaskList;

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
        private TextView taskTitle, taskDescription, taskDeadlineDate, taskDeadlineTime;
        private ImageButton settingButton;

        public TaskHolder(View view) {
            super(view);
            taskTitle = view.findViewById(R.id.task_title);
            taskDescription = view.findViewById(R.id.task_description);
            taskDeadlineDate = view.findViewById(R.id.deadline_date);
            taskDeadlineTime = view.findViewById(R.id.deadline_time);
            settingButton = view.findViewById(R.id.setting_button);

            settingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });
        }

        public void bindData(Task task) {
            mTask = task;
            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());
            taskDeadlineDate.setText(getDate(mTask.getDeadline()));
            taskDeadlineTime.setText(getTime(mTask.getDeadline()));
        }

        private void showMenu(View view) {
            PopupMenu menu = new PopupMenu(view.getContext(), view);
            menu.inflate(R.menu.setting_task_menu);
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.edit_project:
                            return editTask();
                        case R.id.delete_project:
                            return deleteTask(view);
                    }
                    return false;
                }
            });
            menu.show();
        }

        private boolean editTask() {
            return true;
        }

        private boolean deleteTask(View view) {
            TaskList.getInstance(view.getContext())
                    .deleteTaskById(mTask.getId());
            mTasks.removeIf(element -> element.getId().equals(mTask.getId()));
            notifyDataSetChanged();
            return true;
        }

        private String getDate(Date deadline) {
            if(deadline == null) return "No Date";
            return new SimpleDateFormat("MMM dd, YYYY").format(deadline);
        }

        private String getTime(Date deadline) {
            if(deadline == null) return "No Time";
            return new SimpleDateFormat("HH : mm").format(deadline);
        }
    }

    public class EdittingTaskHolder extends RecyclerView.ViewHolder {

        public EdittingTaskHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
