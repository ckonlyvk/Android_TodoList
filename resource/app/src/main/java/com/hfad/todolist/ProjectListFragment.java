package com.hfad.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.databinding.FragmentProjectListBinding;
import com.hfad.todolist.databinding.ListProjectItemBinding;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;

import java.util.List;

public class ProjectListFragment extends Fragment {
    private FragmentProjectListBinding binding;
    private ProjectList mProjectList;
    private ProjectAdapter mAdapter;

    public static Fragment newInstance() {
        return new ProjectListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectList = ProjectList.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_project_list, container, false);

        binding.projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return binding.getRoot();
    }

    public void updateUI() {
        List<Project> projects = mProjectList.getProjects();

        //Check để hiển thị recycler view hay empty view
        if(projects.size()>0) {
            binding.projectRecyclerView.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
        }
        else {
            binding.projectRecyclerView.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        }

        if(mAdapter == null) {
            mAdapter = new ProjectAdapter(projects);
            binding.projectRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setProjects(projects);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Setup ViewHolder
     */
    private class ProjectHolder extends  RecyclerView.ViewHolder {
        ListProjectItemBinding mBinding;

        public ProjectHolder(ListProjectItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setProjectViewModel(new ProjectViewModel());
        }

        public void bindingData(Project project) {
            mBinding.getProjectViewModel().setProject(project);
            mBinding.executePendingBindings();
        }
    }

    /**Setup
     * Setup Adapter
     */
    private class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {
        private List<Project> mProjects;
        public ProjectAdapter(List<Project> projects) {
            mProjects = projects;
        }

        @NonNull
        @Override
        public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            ListProjectItemBinding binding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.list_project_item,
                    parent, false);

            return new ProjectHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
            Project project = mProjects.get(position);
            holder.bindingData(project);
        }

        @Override
        public int getItemCount() {
            return mProjects.size();
        }

        public void setProjects(List<Project> projects) {
            mProjects = projects;
        }
    }
}
