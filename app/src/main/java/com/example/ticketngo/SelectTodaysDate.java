package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//By:Diego Cobos
public class SelectTodaysDate extends AppCompatActivity {
    static String dateFormatted;
    String tripdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trip_date);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }


        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTodaysDate.this, MainActivity.class));
            }
        });

        Button next = findViewById(R.id.next_button);
        next.setEnabled(false);
        next.setVisibility(View.GONE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTrip = new Intent(SelectTodaysDate.this, SelectTodaysTrip.class);
                System.out.println("trip date: " + tripdate);
                toTrip.putExtra("date", tripdate);
                toTrip.putExtra("formattedDate", dateFormatted);

                startActivity(toTrip);

            }
        });
        TextView textDate = findViewById(R.id.mm_dd_yyyy);
        ImageView selectDate = findViewById(R.id.drop_down_img);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SelectTodaysDate.this,R.style.datePickerDialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        tripdate = calendar.getTime().toString();
                        String myFormat="yyyy-MM-dd";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
                        dateFormatted = dateFormat.format(calendar.getTime());

                        textDate.setText(dateFormatted);


                        next.setEnabled(true);
                        next.setVisibility(View.VISIBLE);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


    }
}