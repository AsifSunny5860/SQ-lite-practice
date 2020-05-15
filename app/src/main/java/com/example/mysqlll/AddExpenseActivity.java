package com.example.mysqlll;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.makeText;

public class AddExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner myspinner;

    private EditText edittextone,edittexttwo,edittextthree;
    private List<Expense> expenseList = new ArrayList<>();
    private ImageView imagebtn;
    private Button documentbtn,addbtn;
    private DatabaseeHelper helper;
    private long selectedDateinMS;
    private String exTime,exType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        myspinner=findViewById(R.id.my_spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);
        myspinner.setOnItemSelectedListener(this);

        edittextone=findViewById(R.id.edittextone);
        edittexttwo=findViewById(R.id.edittexttwo);
        edittextthree=findViewById(R.id.edittextthree);
        imagebtn=findViewById(R.id.imagebuttonID);
        documentbtn=findViewById(R.id.docbuttonID);
        addbtn=findViewById(R.id.addbuttonID);
        helper= new DatabaseeHelper(this);


        edittexttwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatepicker();
            }
        });

        edittextthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimepicker();
            }
        });

         documentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();

            }
        });


        //pass the spinner value to database...

        //helper class and insert the datas and stackflow...


        expenseList = new ArrayList<>();


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = edittextone.getText().toString();
                long exDate =selectedDateinMS;
                String extime= exTime.toString();


                long id = helper.insertdata(exType,amount,exDate,extime);
                makeText(AddExpenseActivity.this, "saved" +extime, Toast.LENGTH_SHORT).show();

                if(amount.equals("")){
                    makeText(AddExpenseActivity.this, "Amount is required!", Toast.LENGTH_SHORT).show();
                }




            }
        });


    }

   private void showPictureDialog() {
        AlertDialog.Builder pictureDialog =new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select one ");
        String[] pictureDialogitems ={
              "Select photo from Gallery",
              "Select photo from Camera"
        } ;
        pictureDialog.setItems(pictureDialogitems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();

    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,0);
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       if (resultCode == this.RESULT_CANCELED) {
            return;
        }
            if (data!=null) {
                if (requestCode==0) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    String image = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,100);
                    imagebtn.setImageBitmap(bitmap);
                    makeText(AddExpenseActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                }

            } else if (requestCode == 1) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imagebtn.setImageBitmap(selectedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    makeText(AddExpenseActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //cnverting to string

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
        //cnverting to bitmap
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }








    private void openTimepicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_time_picker,null);

        Button doneBTN = view.findViewById(R.id.doneBtn);
        final TimePicker timePicker = view.findViewById(R.id.timePickerID);

        builder.setView(view);
        final Dialog dialog= builder.create();
        dialog.show();

        final SimpleDateFormat timeSDF = new SimpleDateFormat("hh:mm aa");
        doneBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                Time time = new Time(hour,minute,0);
                exTime= String.valueOf(timeSDF.format(time));
                edittextthree.setText(timeSDF.format(time));
                dialog.dismiss();
            }
        });


    }

    private void openDatepicker() {

        long date = selectedDateinMS;

        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String selecteddate= year+"/"+month+"/"+dayOfMonth+" 00:00:00";

                SimpleDateFormat dateandtime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                SimpleDateFormat datesdf = new SimpleDateFormat("dd/ MM /yyyy");

                Date date =null;

                try {
                    date = dateandtime.parse(selecteddate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                selectedDateinMS= date.getTime();
                edittexttwo.setText(datesdf.format(date));

            }
        };
        Calendar calendar = Calendar.getInstance();
        int year= calendar.get(calendar.YEAR);
        int month=calendar.get(calendar.MONTH);
        int day=calendar.get(calendar.DAY_OF_MONTH);

       DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener,year,month,day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position>0)
        {
            exType= item.toString();
            makeText(AddExpenseActivity.this, "selected" + item, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


