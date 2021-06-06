package com.example.mytodolist.main;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mytodolist.base.BaseFragment;

/**
 * @program: MyToDoList
 * @description:
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener,
        TextView.OnEditorActionListener, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
