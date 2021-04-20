package com.example.mytodolist.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.mytodolist.R;
import com.example.mytodolist.base.BaseFragment;

public class AddFragment extends BaseFragment implements View.OnClickListener {

    EditText etContent;
    EditText etMemo;

    EditText etDate, etTime, etLocation, etShare;
    SwitchCompat switchDate, switchTime, switchLocation, switchShare;

    Button btnSubmit;

    public AddFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        etContent = view.findViewById(R.id.et_content);
        etMemo = view.findViewById(R.id.et_memo);
        etDate = view.findViewById(R.id.et_date);
        etTime = view.findViewById(R.id.et_time);
        etLocation = view.findViewById(R.id.et_location);
        etShare = view.findViewById(R.id.et_share);
        switchDate = view.findViewById(R.id.switch_date);
        switchTime = view.findViewById(R.id.switch_time);
        switchLocation = view.findViewById(R.id.switch_location);
        switchShare = view.findViewById(R.id.switch_share);

        btnSubmit = view.findViewById(R.id.btn_submit);

        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);
        etLocation.setOnClickListener(this);
        etShare.setOnClickListener(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });
        return view;
    }

    public void insertItem() {

    }


    @Override
    public void response(String response) {


    }

    // EDIT TEXT
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.et_date) {
            // 日期
            Log.d("debugging", "Select Date.");
        } else if (v.getId() == R.id.et_time) {
            Log.d("debugging", "Select Time.");
        } else if (v.getId() == R.id.et_location) {
            Log.d("debugging", "Select Location.");
        } else if (v.getId() == R.id.et_share) {
            Log.d("debugging", "Select Share");
        }
    }
}