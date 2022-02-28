package com.hfad.todolist.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.hfad.todolist.R;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "DatePickerFragment";
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;
    private Calendar calendar;
    private Date mDate;

    //Thay thế constructor để khởi tạo đối tượng fragment này
    //Bắt buộc nơi khởi tạo ra fragment này cần truyền cho nó 1 argument với đối tượng bundle
    //có cặp key (ARG_DATE) và value là date (initial date)
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Inflate 1 view object từ src layout dialog_date
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //Lấy ra đối tượng bundle đc nhận vào từ nơi khởi tạo ra frament này
        mDate = (Date) getArguments().getSerializable(ARG_DATE);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);

        setupDatePicker();

        //Sử dụng mẫu builder
        //AlertDialog.Builder(Context) -> trả về 1 instance của AlertDialog.Builder
        //Với method setView() nhận vào 1 view object (v (dễ custom hơn) hoặc DatePicker Object)
        //Với method create() giúp trả về 1 AlertDialog đã được cấu hình
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("result", mDate);
                        getParentFragmentManager().setFragmentResult(AddTaskFragment.REQUEST_DATE, bundle);
                    }
                })
                .create();
    }

    private void setupDatePicker() {
        calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker,  int year,
                                      int monthOfYear,
                                      int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                mDate = calendar.getTime();
            }
        });
    }
}
