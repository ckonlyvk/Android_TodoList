package com.hfad.todolist.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hfad.todolist.R;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;

import java.util.UUID;


public class AddProjectFragment extends DialogFragment {
    private static final String ARG_PROJECT_ID = "project_id";
    private Project mProject;

    private TextView mDialogTitle;
    private EditText mTitleInput;
    private EditText mDescriptionInput;

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
        if(args != null) {
            UUID projectId = (UUID) args.getSerializable(ARG_PROJECT_ID);
            mProject = ProjectList.getInstance(getActivity())
                    .getProject(projectId);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_project, null);

        mDialogTitle = v.findViewById(R.id.dialog_title);
        mTitleInput =  v.findViewById(R.id.input_title);
        mDescriptionInput =  v.findViewById(R.id.input_description);

        if(mProject != null) {
            mDialogTitle.setText(R.string.edit_project_label);
            mTitleInput.setText(mProject.getTitle());
            mDescriptionInput.setText(mProject.getDescription());
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(
                    mProject != null ? R.string.edit_label : R.string.add_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String title = mTitleInput.getText().toString();
                            String description = mDescriptionInput.getText().toString();
                            if(mProject != null) {
                                mProject.setTitle(title);
                                mProject.setDescription(description);
                            }
                            else {
                                mProject = new Project(title, description);
                            }

                            mCallBacks.onExcuteProject(mProject);
                        }
                })
                .setNegativeButton(R.string.cancle_label, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
    }
}
