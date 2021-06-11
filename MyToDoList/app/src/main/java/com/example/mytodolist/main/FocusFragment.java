package com.example.mytodolist.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytodolist.R;
import com.example.mytodolist.base.BaseFragment;

/**
 * @program: MyToDoList
 * @description: FocusFragment
 */
public class FocusFragment extends BaseFragment implements View.OnClickListener {

    EditText etFocusTime;
    Button btUp, btDown, btSubmit, btData;
    ProgressBar pgTime;
    int index = 2;
    final int[] times = {5, 10, 25, 30, 45, 60, 90, 120};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.focus_set_time, container, false);

        //TODO Pop Time-Picker
        etFocusTime = view.findViewById(R.id.et_focus_time);
        btUp = view.findViewById(R.id.bt_focus_time_up);
        btDown = view.findViewById(R.id.bt_focus_time_down);
        btSubmit = view.findViewById(R.id.bt_focus_submit);
        btData = view.findViewById(R.id.bt_focus_data);
        pgTime = view.findViewById(R.id.count_down_progressbar);

//        etFocusTime.setOnClickListener(this);
        btUp.setOnClickListener(this);
        btDown.setOnClickListener(this);
        btData.setOnClickListener(this);

        btSubmit.setOnClickListener(l->{

        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_focus_data) {
            Toast.makeText(getContext(), "Data Viewer is developing...Waiting...", Toast.LENGTH_SHORT).show();
            // TODO Add Data Viewer
        } else if (v.getId() == R.id.bt_focus_time_up) {
            if(index < 7){
                pgTime.setProgress(times[++index], true);
                etFocusTime.setText(times[index]+":00");
            }
        } else if (v.getId() == R.id.bt_focus_time_down) {
            if (index > 0) {
                pgTime.setProgress(times[--index],true);
                etFocusTime.setText(times[index]+":00");
            }
        }
    }
}
