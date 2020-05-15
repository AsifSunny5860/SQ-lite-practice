package com.example.mysqlll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Calendar;


import static com.example.mysqlll.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbarid;

    private DatePickerDialog datePickerDialog;
    private Calendar myCalender;
    private Button dashboardID,expenseID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        toolbarid = findViewById(R.id.my_toolbar);
        dashboardID=findViewById(R.id.dashboardbtn);
        expenseID=findViewById(R.id.expensebtn);

        this.setTitle("Dashboard");


        replacefragment(new DashboardFragment());

        dashboardID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =fm.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,dashboardFragment);
                fragmentTransaction.commit();


            }
        });

        expenseID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseFragment expenseFragment = new ExpenseFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,expenseFragment);
                fragmentTransaction.commit();
            }
        });


        }

    private void replacefragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout,fragment);
        ft.commit();

    }


}





