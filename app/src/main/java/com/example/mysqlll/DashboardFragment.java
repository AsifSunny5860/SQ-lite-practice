package com.example.mysqlll;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText fromdateET,todateET;

    public DashboardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final Spinner spinner = (Spinner) view.findViewById(R.id.choseTypeSpndb);
        spinner.setOnItemSelectedListener(this);

        fromdateET=view.findViewById(R.id.edittextdb);
        todateET=view.findViewById(R.id.edittextdb2);

        fromdateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFromDatePicker();
            }
        });
        
        
        todateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openToDatePicker();
            }
        });




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        return view;
    }

    private void openFromDatePicker() {
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
                fromdateET.setText(dateSDF.format(date));

            }
        };

        Calendar calendar  = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
        datePickerDialog.show();
    }

    private void openToDatePicker() {

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
                todateET.setText(dateSDF.format(date));

                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

            }
        };



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
