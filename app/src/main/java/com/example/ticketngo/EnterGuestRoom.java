package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//By:Diego Cobos
public class EnterGuestRoom extends AppCompatActivity {
    EditText inputRoomNum;
    private String tripDay;
    private String formattedTripDate;
    private String tripID;
    private String tripName;
    private String enterRoomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_guest_room);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }

        Intent receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getExtras();
        tripDay = getIntent().getStringExtra("excursionDate");
        formattedTripDate = getIntent().getStringExtra("formattedDate");
        tripID = getIntent().getStringExtra("savedTripID");
        tripName = getIntent().getStringExtra("tripName");
        System.out.println("tripDay: " + tripDay);
        System.out.println("formattedTripDate: " + formattedTripDate);
        System.out.println("tripID: " + tripID);
        System.out.println("tripName: " + tripName);

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("delete clicked");
                startActivity(new Intent(EnterGuestRoom.this, TripHomePage.class));
            }
        });

        Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("next clicked");

                Intent toEnterPeople = new Intent(EnterGuestRoom.this,EnterAmountOfPeople.class);
                toEnterPeople.putExtra("excursionDate", tripDay);
                toEnterPeople.putExtra("savedTripID", tripID);
                toEnterPeople.putExtra("tripName", tripName);
                toEnterPeople.putExtra("formattedDate", formattedTripDate);
                toEnterPeople.putExtra("roomNumber", enterRoomNumber);
                startActivity(toEnterPeople);




            }
        });

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("back clicked");
                startActivity(new Intent(EnterGuestRoom.this, TripHomePage.class));
            }
        });

        inputRoomNum = (EditText) findViewById(R.id.input_room);

        inputRoomNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String maybeRightNumber = inputRoomNum.getText().toString();
                try{
                    enterRoomNumber = maybeRightNumber;
                }
                catch(NumberFormatException ex){
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid Room Number. " +
                                    "Please try again.",Toast.LENGTH_SHORT
                            );
                    toast.show();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}