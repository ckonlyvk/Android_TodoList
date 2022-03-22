package com.hfad.todolist.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.databinding.ItemContainerProjectBinding;
import com.hfad.todolist.models.Project;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectHolder> {
    private List<Project> mProjects;
    public ProjectsAdapter(List<Project> projects) {
        mProjects = projects;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemContainerProjectBinding binding = ItemContainerProjectBinding.inflate(
                layoutInflater, parent, false);

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

    /**
     * Setup ViewHolder
     */
    public class ProjectHolder extends  RecyclerView.ViewHolder{
        private ItemContainerProjectBinding binding;

        public ProjectHolder(ItemContainerProjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingData(Project project) {
            binding.projectTitle.setText(project.getTitle());
            binding.projectDescription.setText(project.getDescription());
            binding.getRoot().setOnClickListener(view -> {

            });
        }

//        @Override
//        public void onClick(View view) {
//            Intent intent = newIntent(view.getContext(),
//                    TaskType.PROJECT_ID,
//                    mProject.getId());
//            view.getContext().startActivity(intent);
//        }
    }
}
