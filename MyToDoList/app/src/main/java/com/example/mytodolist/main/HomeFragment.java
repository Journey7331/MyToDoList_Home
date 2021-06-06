package com.example.mytodolist.main;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mytodolist.R;
import com.example.mytodolist.adapter.EventAdapter;
import com.example.mytodolist.base.BaseFragment;
import com.example.mytodolist.database.EventDB;
import com.example.mytodolist.database.MyDatabaseHelper;
import com.example.mytodolist.entity.AddressEvent;
import com.example.mytodolist.entity.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @program: MyToDoList
 * @description:
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    EventAdapter adapter;
    ArrayList<AddressEvent> arr;
    ArrayList<AddressEvent> filtered;
    ListView list;
    SwipeRefreshLayout pullToRefresh;
    SwitchCompat switchDone;

    TextView logoIcon;
    Button btnSearchFriend;
    Spinner filterSpinner;

    SearchFragment searchFragment;

    RelativeLayout emptyPage;
    MyDatabaseHelper mysql;

    public HomeFragment() {
    }

    // 获取数据并刷新
    public void refresh(ArrayList<AddressEvent> arr) {
        adapter = new EventAdapter(getActivity(), arr);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        checkEmpty(arr);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        load_start();

        mysql = new MyDatabaseHelper(getContext());
        arr = new ArrayList<>();
        filtered = new ArrayList<>();

        ArrayList allEvent = EventDB.queryAllEvent(mysql);
        for (Object o : allEvent) {
            AddressEvent event = new AddressEvent();
            event.setEvent((Event) o);
            arr.add(event);
        }

        list = (ListView) view.findViewById(R.id.home_list);
        list.setItemsCanFocus(false);
        logoIcon = view.findViewById(R.id.tv_icon);
        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh);
        filterSpinner = (Spinner) view.findViewById(R.id.filter_spinner);
        btnSearchFriend = (Button) view.findViewById(R.id.btn_search_friend);
        switchDone = view.findViewById(R.id.switch_done);
        emptyPage = view.findViewById(R.id.empty_status);
        switchDone.setChecked(true);
        searchFragment = new SearchFragment();

        refresh(arr);
        checkEmpty(arr);

        // Pull To Refresh
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filterSpinner.setSelection(0);
                filterSpinner.setSelected(false);
                refresh(arr);
                checkEmpty(arr);
                SystemClock.sleep(300);
                pullToRefresh.setRefreshing(false);
            }
        });


        btnSearchFriend.setOnClickListener(this);
        logoIcon.setOnClickListener(this);
        filterSpinner.setOnItemSelectedListener(this);
        switchDone.setOnCheckedChangeListener(this);

        return view;
    }


    private void filterEvents(int position) {
        switch (position) {
            case 0:
                // 登记顺序
                arr.sort((o1, o2) -> o1.getEvent().get_id() - o2.getEvent().get_id());
                refresh(arr);
                break;
            case 1:
                // 时间顺序
                arr.sort((o1, o2) -> {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    String date1 = o1.getEvent().getDate();
                    String date2 = o2.getEvent().getDate();
                    Date o1Date = null;
                    Date o2Date = null;

                    String time1 = o1.getEvent().getTime();
                    String time2 = o2.getEvent().getTime();
                    Date o1Time = null;
                    Date o2Time = null;

                    if (date1.isEmpty() && date2.isEmpty()) return 0;
                    if (date1.isEmpty()) return 1;
                    if (date2.isEmpty()) return -1;

                    try {
                        o1Date = sdf1.parse(date1);
                        o2Date = sdf1.parse(date2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int resultDate = o1Date.compareTo(o2Date);
                    if (resultDate > 0) return 1;
                    else if (resultDate < 0) return -1;
                    else {
                        // 时间排序
                        if (time1.isEmpty() && time2.isEmpty()) return 0;
                        if (time1.isEmpty()) return 1;
                        if (time2.isEmpty()) return -1;

                        try {
                            o1Time = sdf2.parse(time1);
                            o2Time = sdf2.parse(time2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        int resultTime = o1Time.compareTo(o2Time);
                        if (resultTime > 0) return 1;
                        else if (resultTime < 0) return -1;
                        else return 0;

                    }
                });
                refresh(arr);
                break;
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            refresh(arr);
        } else {
            filtered.clear();
            for (AddressEvent event : arr) {
                if (!event.getEvent().isDone()) {
                    filtered.add(event);
                }
            }
            refresh(filtered);
        }
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filterEvents(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void checkEmpty(ArrayList<AddressEvent> arr) {
        if (arr.size() < 1) {
            emptyPage.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        } else {
            emptyPage.setVisibility(View.INVISIBLE);
            list.setVisibility(View.VISIBLE);
        }
    }


}
