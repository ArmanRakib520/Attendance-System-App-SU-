package com.android.attendance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.attendance.bean.TeacherBean;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

public class AddTeacherActivity extends Activity {

    Button registerButton,Cancel_Button;
    EditText textFirstName, textcontact, textaddress, textusername, textpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_teacher);


        textFirstName = findViewById(R.id.editTextFirstName);
        textcontact = findViewById(R.id.editTextPhone);
        textaddress = findViewById(R.id.editTextaddr);
        textusername = findViewById(R.id.editTextUserName);
        textpassword = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.RegisterButton);
        Cancel_Button = findViewById(R.id.Cancel_Button);

        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String first_name = textFirstName.getText().toString();
                String phone_no = textcontact.getText().toString();
                String address = textaddress.getText().toString();
                String userName = textusername.getText().toString();
                String passWord = textpassword.getText().toString();

                if (TextUtils.isEmpty(first_name)) {
                    textFirstName.setError("Please enter Teacher's name");
                } else if (TextUtils.isEmpty(phone_no) ||  textcontact.getText().toString().length()<11) {
                    textcontact.setError("Enter Valid phone Number");
                } else if (TextUtils.isEmpty(address)) {
                    textaddress.setError("Enter Address");
                } else if (TextUtils.isEmpty(userName)) {
                    textusername.setError("please enter Teacher ID");
                } else if (TextUtils.isEmpty(passWord)) {
                    textpassword.setError("enter password");
                } else {

                    if (android.util.Patterns.PHONE.matcher(phone_no).matches()) {
                        TeacherBean teacherBean = new TeacherBean();
                        teacherBean.setFaculty_firstname(first_name);
                        teacherBean.setFaculty_mobilenumber(phone_no);
                        teacherBean.setFaculty_address(address);
                        teacherBean.setFaculty_username(userName);
                        teacherBean.setFaculty_password(passWord);

                        DBAdapter dbAdapter = new DBAdapter(AddTeacherActivity.this);
                        dbAdapter.addFaculty(teacherBean);

                        Intent intent = new Intent(AddTeacherActivity.this, MenuActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Teacher added successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        textcontact.setError("Phone Number is not valid");
                    }

                }

            }
        });


        Cancel_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MenuActivity.class);
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
