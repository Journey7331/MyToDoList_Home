package com.example.mytodolist.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mytodolist.R;
import com.example.mytodolist.database.EventDB;
import com.example.mytodolist.database.MyDatabaseHelper;
import com.example.mytodolist.entity.AddressEvent;
import com.example.mytodolist.main.EditFragment;

import java.util.ArrayList;

/**
 * @program: MyToDoList
 * @description:
 */
public class EventAdapter extends ArrayAdapter {

    LayoutInflater inflater;
    ArrayList<AddressEvent> arrayList;
    Activity ctx;
    MyDatabaseHelper mysql = new MyDatabaseHelper(getContext());

    // 内部类
    class EventItemHolder {
        TextView tvContent;
        CheckBox cbIsDone;
        TextView tvDate;
    }

    public EventAdapter(Activity context, ArrayList<AddressEvent> arr) {
        super(context, R.layout.fragment_home, arr);
        this.ctx = context;
        this.arrayList = arr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public AddressEvent getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final EventItemHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.todos_list, parent, false);

            viewHolder = new EventItemHolder();
            viewHolder.tvContent = convertView.findViewById(R.id.tv_content);
            viewHolder.cbIsDone = convertView.findViewById(R.id.cb_is_done);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventItemHolder) convertView.getTag();
        }


        viewHolder.tvContent.setText(arrayList.get(position).getEvent().getContent());
        viewHolder.cbIsDone.setChecked(arrayList.get(position).getEvent().isDone());

        viewHolder.cbIsDone.setOnClickListener(v -> {
            boolean status = viewHolder.cbIsDone.isChecked();
            Toast.makeText(getContext(), status + "", Toast.LENGTH_SHORT).show();
            // Set Event Done
            ContentValues values = new ContentValues();
            values.put(EventDB.content, arrayList.get(position).getEvent().getContent());
            values.put(EventDB.done, status + "");
            EventDB.updateEventById(mysql,arrayList.get(position).getEvent().get_id() + "", values);
        });

        // LongPress
        convertView.setOnLongClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
            View titleView = inflater.inflate(R.layout.add_level_title, null);
            TextView title = titleView.findViewById(R.id.dialog_title);
            title.setText("Modify / Delete");
            builder.setCustomTitle(titleView);

            // Modify
            builder.setPositiveButton("Modify", (dialog, which) -> {
                Fragment editFragment = new EditFragment(getItem(position));
                ((AppCompatActivity) ctx).getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                ).replace(R.id.body_rl, editFragment).addToBackStack(null).commit();
            });

            // Delete
            builder.setNegativeButton("Delete", (dialog, which) -> {
                EventDB.deleteEventById(mysql,(getItem(position)).getEvent().get_id() + "");
                remove(getItem(position));
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
            });

            builder.setNeutralButton("Cancel", (dialog, which) -> {
            });

            builder.create().show();
            return true;
        });
        return convertView;
    }

}
