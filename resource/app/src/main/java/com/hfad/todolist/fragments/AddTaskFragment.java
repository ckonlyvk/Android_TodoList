package com.hfad.todolist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.hfad.todolist.R;
import com.hfad.todolist.databinding.FragmentAddTaskBinding;
import com.hfad.todolist.generated.callback.OnClickListener;
import com.hfad.todolist.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddTaskFragment extends Fragment {
    public static final String REQUEST_DATE = "date_of_task";
    public static final String ARG_TASK_ID = "task_id";
    private final String TAG = "AddTaskFragment";
    private FragmentAddTaskBinding mBinding;

    private CallBacks mCallBacks;
    private Task mTask;

    public interface CallBacks {
        public void onExecuteTask(Task task);
        public void onCancel();
    }

    static public Fragment newInstance() {
        return new AddTaskFragment();
    }

    static public Fragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        AddTaskFragment fragment = new AddTaskFragment();
        //Đính kèm đối tượng Bundle với fragment vừa khởi tạo
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallBacks = (CallBacks) getParentFragment();
        Bundle args = getArguments();
        if(args != null) {
            UUID crimeId = (UUID) args.getSerializable(ARG_TASK_ID);
        }
        else {
            mTask = new Task();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_add_task, container, false);

        mBinding.datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        mBinding.timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        setUpExecuteButton();
        setUpCancelButton();
        return mBinding.getRoot();
    }

    private void setUpExecuteButton() {
        mBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mBinding.inputTitle.getText().toString().trim();
                String description = mBinding.inputDescription.getText().toString().trim();
                mTask.setTitle(title);
                mTask.setDescription(description);
                mCallBacks.onExecuteTask(mTask);
            }
        });
    }

    private void setUpCancelButton() {
        mBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBacks.onCancel();
            }
        });
    }

    private void showDatePicker() {
        FragmentManager fm = getChildFragmentManager();
        Date date = Calendar.getInstance().getTime();
        if(mTask.getDeadline() != null) {
            date = mTask.getDeadline();
        }
        DatePickerFragment dialog = DatePickerFragment.newInstance(date);
        //Giup lắng nghe kết quả nhận về từ fragment (DateTimePicker)
        fm.setFragmentResultListener(REQUEST_DATE,
                AddTaskFragment.this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        getResultDateBack(result);
                    }
                });
        dialog.show(fm, TAG);
    }

    private void showTimePicker() {
        FragmentManager fm = getChildFragmentManager();
        Date date = Calendar.getInstance().getTime();
        if(mTask.getDeadline() != null) {
            date = mTask.getDeadline();
        }
        TimePickerFragment dialog = TimePickerFragment.newInstance(date);
        //Giup lắng nghe kết quả nhận về từ fragment (DateTimePicker)
        fm.setFragmentResultListener(REQUEST_DATE,
                AddTaskFragment.this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        getResultDateBack(result);
                    }
                });
        dialog.show(fm, TAG);
    }

    private void getResultDateBack(Bundle result) {
        Date date = (Date) result.getSerializable("result");
        mTask.setDeadline(date);
        String dateFormat = new SimpleDateFormat("EEEE, MMM dd, YYYY")
                .format(date);
        String timeFormat = new SimpleDateFormat("HH : mm")
                .format(date);
        mBinding.timePickerButton.setText(timeFormat);
        mBinding.datePickerButton.setText(dateFormat);
    }
}
