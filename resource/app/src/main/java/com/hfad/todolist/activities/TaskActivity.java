package com.hfad.todolist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hfad.todolist.R;
import com.hfad.todolist.fragments.AddProjectFragment;
import com.hfad.todolist.fragments.TaskListFragment;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;
import com.hfad.todolist.models.Task;
import com.hfad.todolist.models.TaskList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskActivity extends AppCompatActivity
    implements AddProjectFragment.CallBacks{
    static final String TAG = "TaskActivity";
    static final String TASK_TYPE = "task_type";
    static final String PROJECT_ID = "project_id";

    private TaskType mTaskType;
    private Project mProject;

    public enum TaskType {
        ALL, TODAY, UPCOMING, PROJECT_ID
    }

    static public Intent newIntent(Context context, TaskType type, UUID projectId) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(TASK_TYPE, type);
        intent.putExtra(PROJECT_ID, projectId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        mTaskType = (TaskType) intent.getSerializableExtra(TASK_TYPE);

        if(mTaskType.equals(TaskType.PROJECT_ID)) {
            ProjectList projectList = ProjectList.getInstance(this);
            mProject = projectList.getProject((UUID) intent.getSerializableExtra(PROJECT_ID));
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if(fragment == null) {
            fragment = TaskListFragment.newInstance(
                    mTaskType,
                    mProject!=null ? mProject.getId() : null);
            fm.beginTransaction()
                .add(R.id.container, fragment)
                .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_project_menu, menu);

        setTitleActionBar();
        setSubtitleActionBar();
        setMenuItem(menu);

        return true;
    }

    private void setTitleActionBar() {
        switch(mTaskType) {
            case ALL:
                setTitle("Inbox");
                break;
            case TODAY:
                Date date = new Date();
                String dateStr = new SimpleDateFormat("MMM dd, YYYY").format(date);
                setTitle("Today - " + dateStr);
                break;
            case UPCOMING:
                setTitle("Upcoming");
                break;
            case PROJECT_ID:
                setTitle(mProject.getTitle());
                break;
            default:
                break;
        }
    }

    private void setSubtitleActionBar() {
        TaskList taskList = TaskList.getInstance(this);
        List<Task> tasks = new ArrayList<Task>();
        switch(mTaskType) {
            case ALL:
                tasks = taskList.getAllTasks();
                break;
            case TODAY:
                Date now = new Date();
                tasks = taskList.getTasksByDate(now);
                break;
            case UPCOMING:
                break;
            case PROJECT_ID:
                tasks = taskList.getTasksByProjectId(mProject.getId());
                break;
        }
        int numTasks = tasks.size();
        //Plurals String
        String subtitle = getResources()
                .getQuantityString(R.plurals.num_tasks, numTasks, numTasks);
        getSupportActionBar().setSubtitle(subtitle);
    }

    private void setMenuItem(Menu menu) {
        MenuItem editProjectButton = menu.findItem(R.id.edit_project);
        MenuItem deleteProjectButton = menu.findItem(R.id.delete_project);
        switch(mTaskType) {
            case ALL:
            case TODAY:
            case UPCOMING:
                editProjectButton.setVisible(false);
                deleteProjectButton.setVisible(false);
                break;
            case PROJECT_ID:
                editProjectButton.setVisible(true);
                deleteProjectButton.setVisible(true);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit_project:
                Log.i(TAG, "Edit Project" + mProject.getId().toString());
                return editProject();
            case R.id.delete_project:
                Log.i(TAG, "Delete Project" + mProject.getId().toString());
                return deleteProject();
        }
        return false;
    }

    private boolean editProject(){
        FragmentManager fm = getSupportFragmentManager();
        UUID projectId = (UUID) getIntent().getSerializableExtra(PROJECT_ID);
        AddProjectFragment dialog = AddProjectFragment.newInstance(projectId);
        dialog.show(fm, "Edit Project");
        return true;
    }

    private boolean deleteProject(){
        FragmentManager fm = getSupportFragmentManager();
        UUID projectId = (UUID) getIntent().getSerializableExtra(PROJECT_ID);
        ProjectList.getInstance(TaskActivity.this)
                .deleteProject(projectId);
        Intent intent = new Intent(TaskActivity.this, DashBoardActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onExcuteProject(Project project) {
        ProjectList.getInstance(TaskActivity.this)
                .updateProject(project);
    }
}
