package com.hfad.todolist.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.hfad.todolist.R;
import com.hfad.todolist.databinding.DialogAddProjectBinding;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;

import java.util.UUID;


public class AddProjectFragment extends DialogFragment {
    private static final String ARG_PROJECT_ID = "project_id";
    private Project project;

    private DialogAddProjectBinding binding;

    CallBacks mCallBacks;

    public interface CallBacks {
        public void onExcuteProject(Project project);
    }

    public static AddProjectFragment newInstance(UUID projectId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROJECT_ID, projectId);
        AddProjectFragment fragment = new AddProjectFragment();
        //Đính kèm đối tượng Bundle với fragment vừa khởi tạo
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBacks = (CallBacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            UUID projectId = (UUID) args.getSerializable(ARG_PROJECT_ID);
            project = ProjectList.getInstance(getActivity())
                    .getProject(projectId);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogAddProjectBinding.inflate(
                LayoutInflater.from(getActivity()),
                null, false);
//        View v = LayoutInflater.from(getActivity())
//                .inflate(R.layout.dialog_add_project, null);

        if (project != null) {
            binding.dialogTitle.setText(R.string.edit_project);
            binding.inputTitle.setText(project.getTitle());
            binding.inputDescription.setText(project.getDescription());
            binding.addButton.setText(R.string.save);
        }

        AlertDialog.Builder dialogBuilder= new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(binding.getRoot());

        final AlertDialog alertDialog= dialogBuilder.create();
        binding.cancelButton.setOnClickListener(view -> alertDialog.dismiss());
        binding.addButton.setOnClickListener(view -> {
            String title = binding.inputTitle.getText().toString().trim();
            String description = binding.inputDescription.getText().toString().trim();
            if (project != null) {
                project.setTitle(title);
                project.setDescription(description);
            } else {
                project = new Project(title, description);
            }

            mCallBacks.onExcuteProject(project);
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.background_dialog));

        return alertDialog;
    }

}
