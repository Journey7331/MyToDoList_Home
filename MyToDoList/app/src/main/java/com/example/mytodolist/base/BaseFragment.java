package com.example.mytodolist.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class BaseFragment extends Fragment {

    public ProgressBar progressBar;

    public void loadStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void loadEnd() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    private boolean isLocationServiceActivated() {
//        ActivityManager acm = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
//        if (acm != null) {
//            for (ActivityManager.RunningServiceInfo service : acm.getRunningServices(Integer.MAX_VALUE)) {
//                if (LocationService.class.getName().equals(service.service.getClassName())) {
//                    if (service.foreground) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        } else {
//            return false;
//        }
//    }
//
//    public void startLocationService() {
//        if (!isLocationServiceActivated()) {
//            Intent intent = new Intent(getContext(), LocationService.class);
//            intent.setAction(LocationService.ACTION_START_LOCATION_SERVICE);
//            getActivity().startService(intent);
//            Toast.makeText(getContext(), "location service started", Toast.LENGTH_SHORT).show();
//        }
//    }

}
