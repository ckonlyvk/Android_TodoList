package com.hfad.todolist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.UUID;

public class TaskActivity extends AppCompatActivity
    implements AddProjectFragment.CallBacks{
    static final String TAG = "TaskActivity";
    static final String PROJECT_ID = "project_id";
    private Project mProject;

    static public Intent newIntent(Context context, UUID projectId) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(PROJECT_ID, projectId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pane);

        Intent intent = getIntent();
        ProjectList projectList = ProjectList.getInstance(this);
        mProject = projectList.getProject((UUID) intent.getSerializableExtra(PROJECT_ID));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if(fragment == null) {
            fragment = TaskListFragment.newInstance();
            fm.beginTransaction()
                .add(R.id.container, fragment)
                .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);

        return true;
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
