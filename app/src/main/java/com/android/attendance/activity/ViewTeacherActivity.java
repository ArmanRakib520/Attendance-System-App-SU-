package com.android.attendance.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.attendance.bean.TeacherBean;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import java.util.ArrayList;

public class ViewTeacherActivity extends Activity {

    ArrayList<TeacherBean> teacherBeanList;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    TextView no_teacher;

    DBAdapter dbAdapter = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_teacher_listview_main);

        listView = findViewById(R.id.listview);
        no_teacher = findViewById(R.id.no_teacher);

        final ArrayList<String> facultyList = new ArrayList<String>();

        teacherBeanList = dbAdapter.getAllFaculty();

        if (teacherBeanList.isEmpty()) {
            no_teacher.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }

        for (TeacherBean teacherBean : teacherBeanList) {
            String users = "Name: " + teacherBean.getFaculty_firstname() + "\n" + "Room No:  " + teacherBean.getFaculty_address() + "\n" + "Teacher ID: " + teacherBean.getFaculty_username() + "\n" + "Phone: " + teacherBean.getFaculty_mobilenumber();

            facultyList.add(users);
            Log.d("users: ", users);

        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.view_teacher_list, R.id.labelF, facultyList);
        listView.setAdapter(listAdapter);

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewTeacherActivity.this);

                alertDialogBuilder.setTitle(getTitle() + "decision");
                alertDialogBuilder.setMessage("Are you sure?");

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        facultyList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetInvalidated();

                        dbAdapter.deleteFaculty(teacherBeanList.get(position).getFaculty_id());
                        teacherBeanList = dbAdapter.getAllFaculty();

                        for (TeacherBean teacherBean : teacherBeanList) {
                            String users = " FirstName: " + teacherBean.getFaculty_firstname();
                            facultyList.add(users);
                            Log.d("users: ", users);

                        }

                    }

                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the alert box and put a Toast to the user
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You choose cancel",
                                Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();


                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
