package com.example.mytodolist.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.mytodolist.R;
import com.example.mytodolist.base.BaseFragment;
import com.example.mytodolist.database.EventDB;
import com.example.mytodolist.database.MyDatabaseHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    EditText etContent, etMemo, etDate, etTime, etLocation, etShare, etLevel;
    SwitchCompat switchDate, switchTime, switchLocation, switchShare, switchLevel;

    Button btnSubmit;

    String finalDate, finalTime;

//    Address finalLocation;
//    String finalShare;
//    String finalLevel;

    public AddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 布局服务
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        etContent = view.findViewById(R.id.et_content);
        etMemo = view.findViewById(R.id.et_memo);
        etDate = view.findViewById(R.id.et_date);
        etTime = view.findViewById(R.id.et_time);
//        etLocation = view.findViewById(R.id.et_location);
//        etShare = view.findViewById(R.id.et_share);
//        etLevel = view.findViewById(R.id.et_level);

        switchDate = view.findViewById(R.id.switch_date);
        switchTime = view.findViewById(R.id.switch_time);
//        switchLocation = view.findViewById(R.id.switch_location);
//        switchShare = view.findViewById(R.id.switch_share);
//        switchLevel = view.findViewById(R.id.switch_level);

        btnSubmit = view.findViewById(R.id.btn_submit);

        finalDate = "";
        finalTime = "";
//        finalLocation = new Address();
//        finalShare = "";
//        finalLevel = "";

        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);
//        etLocation.setOnClickListener(this);
//        etShare.setOnClickListener(this);
//        etLevel.setOnClickListener(this);

        switchDate.setOnCheckedChangeListener(this);
        switchTime.setOnCheckedChangeListener(this);
//        switchLocation.setOnCheckedChangeListener(this);
//        switchShare.setOnCheckedChangeListener(this);
//        switchLevel.setOnCheckedChangeListener(this);


        btnSubmit.setOnClickListener(v -> {
            hideKeyboard(getActivity());
            if (etContent.length() == 0) {
                Toast.makeText(getContext(), "Tell What You Want To Do.", Toast.LENGTH_SHORT).show();
                etContent.requestFocus();
            } else if (etDate.length() == 0 && etTime.length() > 0) {
                Toast.makeText(getContext(), "Choose A Day.", Toast.LENGTH_SHORT).show();
                switchDate.setChecked(true);
                etDate.requestFocus();
            } else {
                insertItem();
                ((BottomNavigationView)getActivity().findViewById(R.id.bottom_navigation)).setSelectedItemId(R.id.page_1);
            }
        });
        return view;
    }

    public void insertItem() {
        ContentValues values = new ContentValues();
        values.put(EventDB.content, etContent.getText().toString());
        values.put(EventDB.memo, etMemo.getText().toString());
        values.put(EventDB.done, "false");
        values.put(EventDB.date, finalDate);
        values.put(EventDB.time, finalTime);

        MyDatabaseHelper mysql = new MyDatabaseHelper(getContext());
        EventDB.insertEvent(mysql, values);

        Toast.makeText(getContext(), "Successfully Added!  " + "Content: " + etContent.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    // 按下 EditText 的提示
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.et_date) {
            setupDate();
            Log.i("addEvent", "** Select Date.");
        } else if (v.getId() == R.id.et_time) {
            setupTime();
            Log.i("addEvent", "** Select Time.");
//        } else if (v.getId() == R.id.et_location) {
//            setupLocation();
//            Log.i("addEvent", "** Select Location.");
//        } else if (v.getId() == R.id.et_share) {
//            setupShare();
//            Log.i("addEvent", "** Select Share.");
//        } else if (v.getId() == R.id.et_level) {
//            setupLevel();
//            Log.i("addEvent", "** Select Level.");
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupTime() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_time, null);
        TimePicker timePicker = view.findViewById(R.id.add_time_picker);
        timePicker.setIs24HourView(true);
        builder.setView(view);

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            if (switchTime.isChecked() && etTime.getText().toString().length() == 0) {
                switchTime.setChecked(false);
            }
        });
        builder.setPositiveButton("Submit", (dialog, which) -> {
            int hour = timePicker.getHour();
            int min = timePicker.getMinute();
            finalTime = hour + ":" + min;

            String am_pm;
            if (hour > 12) {
                am_pm = "PM";
                hour = hour - 12;
            } else {
                am_pm = "AM";
            }
            etTime.setText(hour + ":" + min + " " + am_pm);
            switchTime.setChecked(true);
        });

        builder.create().show();
    }

    private void setupDate() {
        Calendar cal = Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_date, null);
        DatePicker datePicker = view.findViewById(R.id.add_date_picker);
        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (switchDate.isChecked() && etDate.getText().toString().length() == 0) {
                    switchDate.setChecked(false);
                }
            }
        });

        builder.setPositiveButton("Submit", (dialog, which) -> {
            cal.set(Calendar.YEAR, datePicker.getYear());
            cal.set(Calendar.MONTH, datePicker.getMonth());
            cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            etDate.setText(sdf.format(cal.getTime()));

            switchDate.setChecked(true);
        });

        builder.create().show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.switch_date) {
            if (isChecked) {
                setupDate();
            } else {
                etDate.setText("");
            }
        } else if (buttonView.getId() == R.id.switch_time) {
            if (isChecked) {
                setupTime();
            } else {
                etTime.setText("");
            }
//        } else if (buttonView.getId() == R.id.switch_location) {
//            if (isChecked) {
//                setupLocation();
//            } else {
//                etLocation.setText("");
//            }
//        } else if (buttonView.getId() == R.id.switch_share) {
//            if (isChecked) {
//                setupShare();
//            } else {
//                etShare.setText("");
//            }
//        } else if (buttonView.getId() == R.id.switch_level) {
//            if (isChecked) {
//                setupLevel();
//            } else {
//                etLevel.setText("");
//            }
        }
    }
}