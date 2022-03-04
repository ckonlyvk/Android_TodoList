package com.hfad.todolist.adapter;

import static com.hfad.todolist.activities.TaskActivity.*;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.R;
import com.hfad.todolist.activities.TaskActivity;
import com.hfad.todolist.fragments.ProjectListFragment;
import com.hfad.todolist.models.Project;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {
    private List<Project> mProjects;
    public ProjectAdapter(List<Project> projects) {
        mProjects = projects;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R.layout.list_project_item, parent, false);

        return new ProjectHolder(view);
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
    public class ProjectHolder extends  RecyclerView.ViewHolder
            implements View.OnClickListener{
        private Project mProject;
        private TextView projectTitle;
        private TextView projectDescription;

        public ProjectHolder(View view) {
            super(view);
            projectTitle = view.findViewById(R.id.project_title);
            projectDescription = view.findViewById(R.id.project_description);

            view.setOnClickListener(this);
        }

        public void bindingData(Project project) {
            mProject = project;
            projectTitle.setText(project.getTitle());
            projectDescription.setText(project.getDescription());
        }

        @Override
        public void onClick(View view) {
            Intent intent = newIntent(view.getContext(),
                    TaskType.PROJECT_ID,
                    mProject.getId());
            view.getContext().startActivity(intent);
        }
    }
}
