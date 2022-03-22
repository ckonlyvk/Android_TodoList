package com.hfad.todolist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfad.todolist.adapter.ProjectsAdapter;
import com.hfad.todolist.databinding.FragmentProjectsBinding;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.viewmodels.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectsFragment extends Fragment {
    private FragmentProjectsBinding binding;

    private ProjectsAdapter adapter;
    private List<Project> projects;
    private ProjectsViewModel projectsViewModel;

    public static Fragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProjectsBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {
        projectsViewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);
        projects = new ArrayList<>();
        adapter = new ProjectsAdapter(projects);
        binding.projectsRecyclerview.setAdapter(adapter);
        loadProjects();
    }

    private void loadProjects() {
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(projectsViewModel.loadProject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(projectList -> {
                    binding.setIsLoading(false);
                    if(projectList.size()>0) {
                        projects = projectList;
                        adapter.setProjects(projectList);
                        adapter.notifyDataSetChanged();
                        binding.setIsEmpty(false);
                    }
                    else {
                        binding.setIsEmpty(true);
                    }
                    compositeDisposable.dispose();
                }));
    }

    public void addNewProject(Project project) {
        int startPosition = projects.size();
        projects.add(project);
        adapter.setProjects(projects);
        adapter.notifyItemRangeInserted(startPosition, projects.size());
        binding.setIsEmpty(false);
        binding.projectsRecyclerview.smoothScrollToPosition(projects.size()-1);
    }

}
