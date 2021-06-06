package com.example.mytodolist.main;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.example.mytodolist.R;
import com.example.mytodolist.base.BaseFragment;
import com.example.mytodolist.database.EventDB;
import com.example.mytodolist.database.MyDatabaseHelper;
import com.example.mytodolist.entity.Address;
import com.example.mytodolist.entity.AddressEvent;

/**
 * @program: MyToDoList
 * @description:
 */
public class EditFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    EditText etContent, etMemo, etDate, etTime, etLocation, etShare, etLevel;
    SwitchCompat switchDate, switchTime, switchLocation, switchShare, switchLevel;

    Button btnSubmit;

    String finalDate, finalTime;
    Address finalLocation;
    String finalShare;
    String finalLevel;

    AddressEvent event;

    public EditFragment(AddressEvent event) {
        this.event = event;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        init(v);

        etContent.setText(event.getEvent().getContent());
        etMemo.setText(event.getEvent().getMemo());

        // 聚焦content
        etContent.requestFocus();
        return v;
    }

    private void init(View view) {
        etContent = view.findViewById(R.id.et_content);
        etMemo = view.findViewById(R.id.et_memo);

        etDate = view.findViewById(R.id.et_date);
        etTime = view.findViewById(R.id.et_time);
        etLocation = view.findViewById(R.id.et_location);
        etShare = view.findViewById(R.id.et_share);
        etLevel = view.findViewById(R.id.et_level);

        switchDate = view.findViewById(R.id.switch_date);
        switchTime = view.findViewById(R.id.switch_time);
        switchLocation = view.findViewById(R.id.switch_location);
        switchShare = view.findViewById(R.id.switch_share);
        switchLevel = view.findViewById(R.id.switch_level);

        finalDate = "";
        finalTime = "";
        finalLocation = new Address();
        finalShare = "";
        finalLevel = "";

        btnSubmit = view.findViewById(R.id.btn_submit);

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
            if (etContent.length() == 0) {
                Toast.makeText(getContext(), "Please Add Something.", Toast.LENGTH_SHORT).show();
                etContent.requestFocus();
            } else if (etDate.length() == 0 && etTime.length() > 0) {
                Toast.makeText(getContext(), "Please Set A DDL.", Toast.LENGTH_SHORT).show();
                switchDate.setChecked(true);
                etDate.requestFocus();
            } else {
                editItem();
            }
        });
    }

    private void editItem() {
        ContentValues values = new ContentValues();
        values.put(EventDB.content, etContent.getText().toString());
        values.put(EventDB.memo, etMemo.getText().toString());
        values.put(EventDB.date, finalDate);
        values.put(EventDB.time, finalTime);

        //TODO Put Location
        MyDatabaseHelper mysql = new MyDatabaseHelper(getContext());
        EventDB.updateEventById(mysql, event.getEvent().get_id() + "", values);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.et_date) {
            setupDate();
        } else if (v.getId() == R.id.et_time) {
            setupTime();
        } else if (v.getId() == R.id.et_location) {
            setupLocation();
//        } else if (v.getId() == R.id.et_share) {
//            setupShare();
//        } else if (v.getId() == R.id.et_level) {
//            setupLevel();
        }
    }

    private void setupLocation() {
    }

    private void setupTime() {
    }

    private void setupDate() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        }
    }
}
