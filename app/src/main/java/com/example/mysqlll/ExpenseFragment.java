package com.example.mysqlll;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FloatingActionButton floatingActionButton;
    private EditText fromdateex,todateex;
    private List<Expense> expenseList = new ArrayList<>();
    private DatabaseeHelper helper;
    private RecyclerView recyclerviewID;
    private CustomAdapter customAdapter;




    public ExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_expense, container, false);
        final Spinner spinner = (Spinner) view.findViewById(R.id.choseTypeSpnex);
        spinner.setOnItemSelectedListener(this);

        fromdateex=view.findViewById(R.id.edittextex);
        todateex=view.findViewById(R.id.edittextex2);

        recyclerviewID = view.findViewById(R.id.recyclerid);


        helper = new DatabaseeHelper(getActivity());
        Cursor cursor = helper.showData();

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            String type = cursor.getString(cursor.getColumnIndex(helper.COL_TYPE));
            String amount = cursor.getString(cursor.getColumnIndex(helper.COL_AMOUNT));
            long Date = cursor.getLong(cursor.getColumnIndex(helper.COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));

            expenseList.add(new Expense(id,type,amount,Date,time));
        }
        customAdapter = new CustomAdapter(getActivity(),expenseList,helper);
        recyclerviewID.setLayoutManager(new LinearLayoutManager(getActivity()));
        // recyclerviewID.setItemAnimator(new DefaultItemAnimator());
        recyclerviewID.setAdapter(customAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fromdateex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFromDateEx();
            }
        });

        todateex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openToDateEx();
            }
        });



        floatingActionButton= view.findViewById(R.id.fabbutton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), AddExpenseActivity.class);
                startActivity(intent);

            }
        });





        return view;


    }

    private void openFromDateEx() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String selectedDate = year+"/"+month+"/"+dayOfMonth+" 00:00:00";

                SimpleDateFormat dateandTimeSDF =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat dateSDF= new SimpleDateFormat("dd/MM/yyyy");
                Date date =null;
                try {
                    date = dateandTimeSDF.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long selectedDateinMS = date.getTime();
                fromdateex.setText(dateSDF.format(date));

            }
        };
        /////getcurntdate
        Calendar calendar  = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        datePickerDialog.show();
    }

    private void openToDateEx() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String selectedDate = year+"/"+month+"/"+dayOfMonth+" 23:59:59";

                SimpleDateFormat dateandTimeSDF =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat dateSDF= new SimpleDateFormat("dd/MM/yyyy");
                Date date =null;
                try {
                    date = dateandTimeSDF.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long selectedDateinMS = date.getTime();
                todateex.setText(dateSDF.format(date));

                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

            }
        };


        /////getcurntdate
        Calendar calendar  = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        datePickerDialog.show();


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position>0)
        {
            Toast.makeText(getActivity(), "Selected: " + item, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
