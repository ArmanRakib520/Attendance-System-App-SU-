package com.android.attendance.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidattendancesystem.R;

public class ViewStudentActivity extends Activity {

    Spinner spinnerbranch, spinneryear;
    String userrole, branch, year;
    private String[] branchString = new String[]{"Select Department", "CSE(Computer Science & Engineering)", "Civil Engineering", "EEE (Electrical & Electronics Engineering",
            "Architecture", "Fashion Design & Technology", "Mechanical Engineering", "Textile Engineering", "Naval Architecture & Marine Engineering", "Apparel Manufacture & Technology", "LAW", "Business Administration", "BA Honours (Bangla)"};
    private String[] yearString = new String[]{"Select Semester", "1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester", "9th Semester", "10th Semester", "11th Semester", "12th Semester"};

    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_student);

        spinnerbranch = findViewById(R.id.spinnerbranchView);
        spinneryear = findViewById(R.id.spinneryearView);
        submit = findViewById(R.id.submitButton);


        spinnerbranch.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                branch = (String) spinnerbranch.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, branchString);
        adapter_branch
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbranch.setAdapter(adapter_branch);

        ///......................spinner2

        spinneryear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                year = (String) spinneryear.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearString);
        adapter_year
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(adapter_year);

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (spinnerbranch.getSelectedItem().toString().equals("Select Department")) {
                    Toast.makeText(getApplicationContext(), "Select Department", Toast.LENGTH_SHORT).show();
                } else if (spinneryear.getSelectedItem().toString().equals("Select Semester")) {
                    Toast.makeText(getApplicationContext(), "Select Semester", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(ViewStudentActivity.this, ViewStudentByBranchYear.class);
                    intent.putExtra("branch", branch);
                    intent.putExtra("year", year);
                    startActivity(intent);

                }

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
