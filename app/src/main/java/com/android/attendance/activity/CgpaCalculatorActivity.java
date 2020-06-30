package com.android.attendance.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidattendancesystem.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CgpaCalculatorActivity extends AppCompatActivity {

    private EditText edit_sgpa1, edit_sgpa2, edit_sgpa3, edit_sgpa4, edit_sgpa5, edit_sgpa6, edit_sgpa7, edit_sgpa8, edit_sgpa9, edit_sgpa10, edit_sgpa11, edit_sgpa12, edit_credit1, edit_credit2, edit_credit3, edit_credit4, edit_credit5, edit_credit6, edit_credit7, edit_credit8, edit_credit9, edit_credit10, edit_credit11, edit_credit12;

    private ArrayList<EditText> sgpaList = new ArrayList<>();
    private ArrayList<EditText> creditList = new ArrayList<>();

    private String[] pre_name_sgpa = new String[]{"sgpa1", "sgpa2", "sgpa3", "sgpa4", "sgpa5", "sgpa6", "sgpa7", "sgpa8", "sgpa9", "sgpa10", "sgpa11", "sgpa12"};
    private String[] pre_name_credit = new String[]{"credit1", "credit2", "credit3", "credit4", "credit5", "credit6", "credit7", "credit8", "credit9", "credit10", "credit11", "credit12"};

    private String[] s_sgpa = new String[12];
    private String[] s_credit = new String[12];

    private float[] sgpa = new float[14];
    private float[] credit = new float[12];

    private String test_sgpa, test_credit;

    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String SETTINGS_PREFS_NAME = "settingsPrefsFile";

    private int nOfSem;

    LinearLayout layout7, layout8, layout9, layout10, layout11, layout12, lyt_Result, lyt_calculator;
    Button btn_Calculate, btn_back, btn_again_calculate;
    TextView txt_final_cgpa, txt_final_cradit;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cgpa_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layout7 = findViewById(R.id.layout7);
        layout8 = findViewById(R.id.layout8);
        layout9 = findViewById(R.id.layout9);
        layout10 = findViewById(R.id.layout10);
        layout11 = findViewById(R.id.layout11);
        layout12 = findViewById(R.id.layout12);
        lyt_Result = findViewById(R.id.lyt_Result);
        lyt_calculator = findViewById(R.id.lyt_calculator);
        txt_final_cgpa = findViewById(R.id.txt_final_cgpa);
        txt_final_cradit = findViewById(R.id.txt_final_cradit);
        btn_Calculate = findViewById(R.id.btn_calculate);
        btn_back = findViewById(R.id.btn_back);
        btn_again_calculate = findViewById(R.id.btn_again_calculate);

        edit_sgpa1 = findViewById(R.id.edit_sgpa1);
        edit_sgpa2 = findViewById(R.id.edit_sgpa2);
        edit_sgpa3 = findViewById(R.id.edit_sgpa3);
        edit_sgpa4 = findViewById(R.id.edit_sgpa4);
        edit_sgpa5 = findViewById(R.id.edit_sgpa5);
        edit_sgpa6 = findViewById(R.id.edit_sgpa6);
        edit_sgpa7 = findViewById(R.id.edit_sgpa7);
        edit_sgpa8 = findViewById(R.id.edit_sgpa8);
        edit_sgpa9 = findViewById(R.id.edit_sgpa9);
        edit_sgpa10 = findViewById(R.id.edit_sgpa10);
        edit_sgpa11 = findViewById(R.id.edit_sgpa11);
        edit_sgpa12 = findViewById(R.id.edit_sgpa12);

        edit_credit1 = findViewById(R.id.edit_credit1);
        edit_credit2 = findViewById(R.id.edit_credit2);
        edit_credit3 = findViewById(R.id.edit_credit3);
        edit_credit4 = findViewById(R.id.edit_credit4);
        edit_credit5 = findViewById(R.id.edit_credit5);
        edit_credit6 = findViewById(R.id.edit_credit6);
        edit_credit7 = findViewById(R.id.edit_credit7);
        edit_credit8 = findViewById(R.id.edit_credit8);
        edit_credit9 = findViewById(R.id.edit_credit9);
        edit_credit10 = findViewById(R.id.edit_credit10);
        edit_credit11 = findViewById(R.id.edit_credit11);
        edit_credit12 = findViewById(R.id.edit_credit12);

        btn_again_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyt_calculator.setVisibility(View.VISIBLE);
                lyt_Result.setVisibility(View.GONE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences set_prefs = getSharedPreferences(SETTINGS_PREFS_NAME, MODE_PRIVATE);

        if (set_prefs.getBoolean("key", false)) {
            sgpaList.add(edit_sgpa1);
            sgpaList.add(edit_sgpa2);
            sgpaList.add(edit_sgpa3);
            sgpaList.add(edit_sgpa4);
            sgpaList.add(edit_sgpa5);
            sgpaList.add(edit_sgpa6);
            sgpaList.add(edit_sgpa7);
            sgpaList.add(edit_sgpa8);
            sgpaList.add(edit_sgpa9);
            sgpaList.add(edit_sgpa10);
            sgpaList.add(edit_sgpa11);
            sgpaList.add(edit_sgpa12);

            creditList.add(edit_credit1);
            creditList.add(edit_credit2);
            creditList.add(edit_credit3);
            creditList.add(edit_credit4);
            creditList.add(edit_credit5);
            creditList.add(edit_credit6);
            creditList.add(edit_credit7);
            creditList.add(edit_credit8);
            creditList.add(edit_credit9);
            creditList.add(edit_credit10);
            creditList.add(edit_credit11);
            creditList.add(edit_credit12);
            nOfSem = 12;

        } else {

            sgpaList.add(edit_sgpa1);
            sgpaList.add(edit_sgpa2);
            sgpaList.add(edit_sgpa3);
            sgpaList.add(edit_sgpa4);
            sgpaList.add(edit_sgpa5);
            sgpaList.add(edit_sgpa6);
            sgpaList.add(edit_sgpa7);
            sgpaList.add(edit_sgpa8);
            sgpaList.add(edit_sgpa9);
            sgpaList.add(edit_sgpa10);
            sgpaList.add(edit_sgpa11);
            sgpaList.add(edit_sgpa12);

            creditList.add(edit_credit1);
            creditList.add(edit_credit2);
            creditList.add(edit_credit3);
            creditList.add(edit_credit4);
            creditList.add(edit_credit5);
            creditList.add(edit_credit6);
            creditList.add(edit_credit7);
            creditList.add(edit_credit8);
            creditList.add(edit_credit9);
            creditList.add(edit_credit10);
            creditList.add(edit_credit11);
            creditList.add(edit_credit12);

            nOfSem = 12;
        }


        for (int i = 0; i < nOfSem; i++) {
            sgpaList.get(i).setText(prefs.getString(pre_name_sgpa[i], ""));
            creditList.get(i).setText(prefs.getString(pre_name_credit[i], ""));
        }


        btn_Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                float total_credit = 0, mult = 0, result;

                for (int i = 0; i < nOfSem; i++) {
                    test_sgpa = sgpaList.get(i).getText().toString();
                    Log.d("list" + i, "onClick: " + test_sgpa);
                    if (test_sgpa.isEmpty() || test_sgpa.equals(".")) {
                        s_sgpa[i] = "0";
                        sgpaList.get(i).setText("");
                    } else {
                        s_sgpa[i] = test_sgpa;
                    }

                    test_credit = creditList.get(i).getText().toString();
                    if (test_credit.isEmpty() || test_credit.equals(".")) {
                        s_credit[i] = "0";
                        creditList.get(i).setText("");
                    } else {
                        s_credit[i] = test_credit;
                    }
                    Log.d("list" + i, "onClick: after " + test_sgpa);
                    sgpa[i] = Float.parseFloat(s_sgpa[i]);
                    credit[i] = Float.parseFloat(s_credit[i]);


                    mult = mult + (credit[i] * sgpa[i]);
                    total_credit = total_credit + credit[i];

                    editor.putString(pre_name_sgpa[i], s_sgpa[i]);    // Saving String
                    editor.putString(pre_name_credit[i], s_credit[i]);    // Saving String
                    editor.apply();


                }


                if (total_credit != 0) {
                    result = mult / total_credit;

                    sgpa[nOfSem] = result;
                    sgpa[nOfSem + 1] = total_credit;

                    Bundle b = new Bundle();
                    b.putFloatArray("sgpa", sgpa);

                    String cgpa = new DecimalFormat("##.##").format(sgpa[nOfSem]);
                    int total_credits = (int) sgpa[nOfSem + 1];

                    lyt_calculator.setVisibility(View.GONE);
                    lyt_Result.setVisibility(View.VISIBLE);
                    txt_final_cgpa.setText("Your CGPA is  : " + cgpa);
                    txt_final_cradit.setText("Total Credit  : " + total_credits);

//                    Toast.makeText(CgpaCalculatorActivity.this, "" + "CGPA : " + cgpa + "\nTotal Credit : " + total_credits, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Please add credit", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_clearSgpa_field:
                for (int i = 0; i < nOfSem; i++) {
                    sgpaList.get(i).setText("");
                }
                return true;
            case R.id.action_clearCredit_field:
                for (int i = 0; i < nOfSem; i++) {
                    creditList.get(i).setText("");
                }
                return true;

            case R.id.action_clearAll_field:
                for (int i = 0; i < nOfSem; i++) {
                    sgpaList.get(i).setText("");
                    creditList.get(i).setText("");
                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}