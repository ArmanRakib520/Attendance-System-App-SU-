package com.android.attendance.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.attendance.bean.StudentBean;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class AddStudentActivity extends Activity {

    Button registerButton, Cancel_Button;
    EditText textFirstName, textcontact, textLastName, textaddress;
    Spinner spinnerdept, spinneryear;
    String userrole, branch, year;
    StudentBean studentBean;
    DBAdapter dbAdapter;
    private String[] branchString = new String[]{"Select Department", "CSE(Computer Science & Engineering)", "Civil Engineering", "EEE (Electrical & Electronics Engineering",
            "Architecture", "Fashion Design & Technology", "Mechanical Engineering", "Textile Engineering", "Naval Architecture & Marine Engineering", "Apparel Manufacture & Technology", "LAW", "Business Administration", "BA Honours (Bangla)"};
    private String[] yearString = new String[]{"Select Semester", "1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester", "9th Semester", "10th Semester", "11th Semester", "12th Semester"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        spinnerdept = findViewById(R.id.spinnerdept);
        spinneryear = findViewById(R.id.spinneryear);
        textFirstName = findViewById(R.id.editTextFirstName);
        textLastName = findViewById(R.id.editTextLastName);
        textcontact = findViewById(R.id.editTextPhone);
        textaddress = findViewById(R.id.editTextaddr);
        registerButton = findViewById(R.id.RegisterButton);
        Cancel_Button = findViewById(R.id.Cancel_Button);

        spinnerdept.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                branch = (String) spinnerdept.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchString);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdept.setAdapter(adapter_branch);


        spinneryear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                year = (String) spinneryear.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearString);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(adapter_year);


        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String first_name = textFirstName.getText().toString();
                String last_name = textLastName.getText().toString();
                String phone_no = textcontact.getText().toString();
                String address = "Dhaka";

                if (TextUtils.isEmpty(first_name)) {
                    textFirstName.setError("Please Enter First Name");
                } else if (TextUtils.isEmpty(last_name)) {
                    textLastName.setError("Please Enter Last Name");
                } else if (TextUtils.isEmpty(phone_no)|| textcontact.getText().toString().length()<11 ) {
                    textcontact.setError("Enter Valid Phone Number");
                } else if (TextUtils.isEmpty(address)) {
                    textaddress.setError("Please Enter Address");
                }else if (spinnerdept.getSelectedItem().toString().equals("Select Department")) {
                    Toast.makeText(AddStudentActivity.this, "Select Department", Toast.LENGTH_SHORT).show();
                }else if (spinneryear.getSelectedItem().toString().equals("Select Semester")) {
                    Toast.makeText(AddStudentActivity.this, "Select Semester", Toast.LENGTH_SHORT).show();
                } else {

                    if (android.util.Patterns.PHONE.matcher(phone_no).matches()) {
                        studentBean = new StudentBean();

                        studentBean.setStudent_firstname(first_name);
                        studentBean.setStudent_lastname(last_name);
                        studentBean.setStudent_mobilenumber(phone_no);
                        studentBean.setStudent_address(address);
                        studentBean.setStudent_department(branch);
                        studentBean.setStudent_class(year);

                        dbAdapter = new DBAdapter(AddStudentActivity.this);
                        dbAdapter.addStudent(studentBean);

                        Intent intent = new Intent(AddStudentActivity.this, MenuActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "student added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        textcontact.setError("Phone Number is not valid");
                    }
                }
            }
        });

        Cancel_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
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
