package com.hfad.todolist.activities;

import static com.hfad.todolist.activities.TaskActivity.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hfad.todolist.fragments.AddProjectFragment;
import com.hfad.todolist.fragments.ProjectListFragment;
import com.hfad.todolist.R;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;
import com.hfad.todolist.models.TaskList;

import java.util.Date;

public class DashBoardActivity extends AppCompatActivity
    implements AddProjectFragment.CallBacks, View.OnClickListener{
    private TextView tvBannerTitle, tvBannerSubtitle;
    private CardView mAllButton, mTodayButton, mUpcomingButton;
    private ImageButton mAddProjectButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        getSupportActionBar().hide();

        inflateView();

        setUpBanner();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.project_list_fragment);

        if(fragment==null) {
            fragment = ProjectListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.project_list_fragment, fragment)
                    .commit();
        }

    }

    private void inflateView() {
        tvBannerTitle = findViewById(R.id.banner_title);
        tvBannerSubtitle = findViewById(R.id.banner_subtitle);
        mAllButton = findViewById(R.id.all_button);
        mTodayButton = findViewById(R.id.today_button);
        mUpcomingButton = findViewById(R.id.upcoming_button);
        mAddProjectButton = findViewById(R.id.btn_add_project);

        mAllButton.setOnClickListener(this);
        mTodayButton.setOnClickListener(this);
        mUpcomingButton.setOnClickListener(this);
        mAddProjectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.all_button:
                startTaskActivity(TaskType.ALL);
                break;
            case R.id.today_button:
                startTaskActivity(TaskType.TODAY);
                break;
            case R.id.upcoming_button:
                break;
            case R.id.btn_add_project:
                showAddProjectDialog();
                break;
            default:
                break;
        }
    }

    void setUpBanner() {
        Date now = new Date();
        int numTasks = TaskList.getInstance(this)
                .getTasksByDate(now).size();

        if(numTasks == 0) {
            tvBannerTitle.setText(R.string.create_now);
            tvBannerSubtitle.setText(R.string.no_task);
        }
        else {
            String title = getResources()
                    .getQuantityString(R.plurals.num_tasks, numTasks, numTasks);
            tvBannerTitle.setText(title);
            tvBannerSubtitle.setText(R.string.banner_subtitle);
        }
    }

    private void startTaskActivity(TaskType type) {
        Intent intent = TaskActivity.newIntent(this, type, null);
        startActivity(intent);
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
