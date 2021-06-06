package com.example.mytodolist.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mytodolist.R;
import com.example.mytodolist.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
    BottomNavigationView bottomNavigation;

    AddFragment addFragment;
    HomeFragment homeFragment;
//    MapFragment mapFragment;
//    NotificationFragment notificationFragment;
//    MyPageFragment myPageFragment;

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fragment setup
        addFragment = new AddFragment();
        homeFragment = new HomeFragment();
//        mapFragment = new MapFragment();
//        notificationFragment = new NotificationFragment();
//        myPageFragment = new MyPageFragment();
        currentFragment = null;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.body_rl, homeFragment)
                .commit();

        // bottom navigation bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    onFragmentSelected(1, null);
                    break;
                case R.id.page_2:
                    onFragmentSelected(2, null);
                    break;
                case R.id.page_3:
                    onFragmentSelected(3, null);
                    break;
                case R.id.page_4:
                    onFragmentSelected(4, null);
                    break;
                case R.id.page_5:
                    onFragmentSelected(5, null);
                    break;
            }
            return true;
        }
    };

    public void onFragmentSelected(int position, Bundle bundle) {
        hideKeyboard(this);
        switch (position) {
            case 1:
                homeFragment = new HomeFragment();
                currentFragment = homeFragment;
                break;
//            case 2:
//                mapFragment = new MapFragment();
//                currentFragment = mapFragment;
//                break;
            case 3:
                addFragment = new AddFragment();
                currentFragment = addFragment;
                break;
//            case 4:
//                notificationFragment = new NotificationFragment();
//                currentFragment = notificationFragment;
//                break;
//            case 5:
//                myPageFragment = new MyPageFragment();
//                currentFragment = myPageFragment;
//                break;

        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.body_rl, currentFragment)
                .commit();
    }
}