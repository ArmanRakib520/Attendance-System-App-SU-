package com.android.attendance.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.attendance.bean.AttendanceBean;
import com.android.attendance.bean.AttendanceSessionBean;
import com.android.attendance.bean.TeacherBean;
import com.android.attendance.bean.StudentBean;
import com.android.attendance.context.ApplicationContext;
import com.android.attendance.db.DBAdapter;
import com.example.androidattendancesystem.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAttandanceSessionActivity<AddAttandanceActivity> extends Activity {

    private ImageButton date;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;
    private EditText dateEditText;
    Button submit, btn_logout, viewAttendance, viewTotalAttendance;
    Spinner spinner_department, spinneryear, spinnerSubject;
    String branch = "CSE";
    String year = "SE";
    String subject = "SC";

    private String[] branchString = new String[]{"Select Department", "CSE(Computer Science & Engineering)", "Civil Engineering", "EEE (Electrical & Electronics Engineering",
            "Architecture", "Fashion Design & Technology", "Mechanical Engineering", "Textile Engineering", "Naval Architecture & Marine Engineering", "Apparel Manufacture & Technology", "LAW", "Business Administration", "BA Honours (Bangla)"};
    private String[] yearString = new String[]{"Select Semester", "1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester", "9th Semester", "10th Semester", "11th Semester", "12th Semester"};
    private String[] subjectSEString = new String[]{"SC", "MC"};
    private String[] subjectTEString = new String[]{"GT", "CN"};
    private String[] subjectBEString = new String[]{"DS", "NS"};

    private String[] subjectFinal = new String[]{"Select Subject", "Computer System", "Programming", "Physics", "English", "Math", "Practical"};
    AttendanceSessionBean attendanceSessionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_attandance);

        //Assume subject will be SE
        //subjectFinal = subjectSEString;

        spinner_department = findViewById(R.id.spinner_department);
        spinneryear = findViewById(R.id.spinneryear);
        spinnerSubject = findViewById(R.id.spinner_subject);
        btn_logout = findViewById(R.id.btn_logout);
        viewTotalAttendance = findViewById(R.id.viewTotalAttendanceButton);
        viewAttendance = findViewById(R.id.viewAttendancebutton);
        submit = findViewById(R.id.buttonsubmit);
        date = findViewById(R.id.DateImageButton);
        dateEditText = findViewById(R.id.DateEditText);

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchString);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(adapter_branch);
        spinner_department.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                branch = (String) spinner_department.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ///......................spinner2
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearString);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(adapter_year);
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

        ArrayAdapter<String> adapter_subject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectFinal);
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapter_subject);
        spinnerSubject.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                subject = (String) spinnerSubject.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        date.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(0);

            }
        });

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (spinner_department.getSelectedItem().toString().equals("Select Department")) {
                    Toast.makeText(AddAttandanceSessionActivity.this, "Select Department", Toast.LENGTH_SHORT).show();
                } else if (spinneryear.getSelectedItem().toString().equals("Select Semester")) {
                    Toast.makeText(AddAttandanceSessionActivity.this, "Select Semester", Toast.LENGTH_SHORT).show();
                } else {

                    AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                    TeacherBean bean = ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).getTeacherBean();

                    attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                    attendanceSessionBean.setAttendance_session_department(branch);
                    attendanceSessionBean.setAttendance_session_class(year);
                    attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
                    attendanceSessionBean.setAttendance_session_subject(subject);

                    DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);
                    int sessionId = dbAdapter.addAttendanceSession(attendanceSessionBean);

                    ArrayList<StudentBean> studentBeanList = dbAdapter.getAllStudentByBranchYear(branch, year);
                    ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).setStudentBeanList(studentBeanList);


                    Intent intent = new Intent(AddAttandanceSessionActivity.this, AddAttendanceActivity.class);
                    intent.putExtra("sessionId", sessionId);
                    startActivity(intent);
                }
            }
        });

        viewAttendance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                TeacherBean bean = ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).getTeacherBean();

                attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                attendanceSessionBean.setAttendance_session_department(branch);
                attendanceSessionBean.setAttendance_session_class(year);
                attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
                attendanceSessionBean.setAttendance_session_subject(subject);

                DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);

                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getAttendanceBySessionID(attendanceSessionBean);
                ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(AddAttandanceSessionActivity.this, ViewAttendanceByFacultyActivity.class);
                startActivity(intent);

            }
        });

        viewTotalAttendance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                TeacherBean bean = ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).getTeacherBean();

                attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                attendanceSessionBean.setAttendance_session_department(branch);
                attendanceSessionBean.setAttendance_session_class(year);
                attendanceSessionBean.setAttendance_session_subject(subject);

                DBAdapter dbAdapter = new DBAdapter(AddAttandanceSessionActivity.this);

                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getTotalAttendanceBySessionID(attendanceSessionBean);
                ((ApplicationContext) AddAttandanceSessionActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(AddAttandanceSessionActivity.this, ViewAttendanceByFacultyActivity.class);
                startActivity(intent);

            }
        });

        btn_logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateEditText.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

}
