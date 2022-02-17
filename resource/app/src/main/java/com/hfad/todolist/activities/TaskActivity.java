package com.hfad.todolist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hfad.todolist.R;
import com.hfad.todolist.fragments.TaskListFragment;

import java.util.UUID;

public class TaskActivity extends AppCompatActivity {
    static final String PROJECT_ID = "project_id";

    static public Intent newIntent(Context context, UUID projectId) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(PROJECT_ID, projectId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pane);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if(fragment == null) {
            fragment = TaskListFragment.newInstance();
            fm.beginTransaction()
                .add(R.id.container, fragment)
                .commit();
        }
    }
}
