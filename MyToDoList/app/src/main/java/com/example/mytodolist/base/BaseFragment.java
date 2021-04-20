package com.example.mytodolist.base;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class BaseFragment extends Fragment {
    public Map<String, String> params = new HashMap<>();

    public void loadStart(){}
    public void loadEnd(){}


    public void response(String response){}

    public void requset(){}



}
