package com.hfad.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.ProjectList;


public class AddProjectFragment extends DialogFragment {
    CallBacks mCallBacks;

    public interface CallBacks {
        public void onExcuteProject(Project project);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBacks = (CallBacks) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_project, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.add_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView titleTextView = v.findViewById(R.id.input_title);
                        TextView descTextView = v.findViewById(R.id.input_description);

                        String title = titleTextView.getText().toString();
                        String description = descTextView.getText().toString();
                        Project project = new Project(title, description);

                        mCallBacks.onExcuteProject(project);
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
