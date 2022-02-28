package com.hfad.todolist.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hfad.todolist.R;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";

    private Date mDate;
    private Calendar calendar;
    private TimePicker mTimePicker;

    //Thay thế constructor để khởi tạo đối tượng fragment này
    //Bắt buộc nơi khởi tạo ra fragment này cần truyền cho nó 1 argument với đối tượng bundle
    //có cặp key (ARG_DATE) và value là date (initial date)
    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Inflate 1 view object từ src layout dialog_date
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        //Lấy ra đối tượng bundle đc nhận vào từ nơi khởi tạo ra frament này
        mDate = (Date) getArguments().getSerializable(ARG_DATE);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);

        setupTimePicker();

        //Sử dụng mẫu builder
        //AlertDialog.Builder(Context) -> trả về 1 instance của AlertDialog.Builder
        //Với method setView() nhận vào 1 view object (v (dễ custom hơn) hoặc TimePicker Object)
        //Với method create() giúp trả về 1 AlertDialog đã được cấu hình
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);

                        Date date = calendar.getTime();

                        System.out.println(hour + " " + minute);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("result", date);
                        getParentFragmentManager().setFragmentResult(AddTaskFragment.REQUEST_DATE, bundle);
                    }
                })
                .create();
    }

    private void setupTimePicker() {
        calendar = Calendar.getInstance();

        calendar.setTime(mDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
    }
}
