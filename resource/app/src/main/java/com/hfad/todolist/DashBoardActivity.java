package com.hfad.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;

public class DashBoardActivity extends AppCompatActivity
    implements AddProjectFragment.CallBacks{
    ImageButton addProjectButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getSupportActionBar().hide();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.project_list_fragment);

        if(fragment==null) {
            fragment = ProjectListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.project_list_fragment, fragment)
                    .commit();
        }

        addProjectButton = findViewById(R.id.btn_add_project);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddProjectDialog();
            }
        });
    }

    private void showAddProjectDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddProjectFragment dialog = new AddProjectFragment();
        dialog.show(fm, "Add Project");
    }

    @Override
    public void onExcuteProject(Project project) {
        ProjectList.getInstance(DashBoardActivity.this)
                .addProject(project);
        ProjectListFragment projectListFragment = (ProjectListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.project_list_fragment);

        if(projectListFragment!= null)
            projectListFragment.updateUI();
    }
}
