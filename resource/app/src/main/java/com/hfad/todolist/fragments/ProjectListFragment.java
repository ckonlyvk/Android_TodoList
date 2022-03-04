package com.hfad.todolist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.todolist.activities.TaskActivity;
import com.hfad.todolist.R;
import com.hfad.todolist.adapter.ProjectAdapter;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;

import java.util.List;

public class ProjectListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;

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
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

        inflateView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    void inflateView(View view) {
        mRecyclerView = view.findViewById(R.id.project_recycler_view);
        mEmptyView = view.findViewById(R.id.empty_view);
    }

    public void updateUI() {
        List<Project> projects = mProjectList.getProjects();

        //Check để hiển thị recycler view hay empty view
        if(projects.size()>0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
        else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }

        if(mAdapter == null) {
            mAdapter = new ProjectAdapter(projects);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setProjects(projects);
            mAdapter.notifyDataSetChanged();
        }
    }


}
