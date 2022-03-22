package com.hfad.todolist.activities;

import static com.hfad.todolist.activities.TaskActivity.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.todolist.databinding.ActivityDashBoardBinding;
import com.hfad.todolist.fragments.AddProjectFragment;
import com.hfad.todolist.fragments.ProjectsFragment;
import com.hfad.todolist.R;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;
import com.hfad.todolist.models.TaskList;
import com.hfad.todolist.viewmodels.DashBoardViewModel;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DashBoardActivity extends AppCompatActivity
        implements AddProjectFragment.CallBacks {
    private ActivityDashBoardBinding binding;
    private DashBoardViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        setListeners();

        setUpBanner();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.project_list_fragment);

        if (fragment == null) {
            fragment = ProjectsFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.project_list_fragment, fragment)
                    .commit();
        }

    }

    private void setListeners() {
        binding.allButton.setOnClickListener(view -> startTaskActivity(TaskType.ALL));
        binding.todayButton.setOnClickListener(view -> startTaskActivity(TaskType.TODAY));
//        binding.upcomingButton.setOnClickListener(view -> startTaskActivity(TaskType.ALL));
        binding.btnAddProject.setOnClickListener(view -> showAddProjectDialog());
    }

    void setUpBanner() {
        Date now = new Date();
        int numTasks = TaskList.getInstance(this)
                .getTasksByDate(now).size();

        if (numTasks == 0) {
            binding.bannerTitle.setText(R.string.create_now);
            binding.bannerSubtitle.setText(R.string.no_task);
        } else {
            String title = getResources()
                    .getQuantityString(R.plurals.num_tasks, numTasks, numTasks);
            binding.bannerTitle.setText(title);
            binding.bannerSubtitle.setText(R.string.tasks_in_today_remain);
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
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.addProject(project)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    ProjectsFragment projectsFragment = (ProjectsFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.project_list_fragment);
                    projectsFragment.addNewProject(project);
                    compositeDisposable.dispose();
                })
        );
    }

}
