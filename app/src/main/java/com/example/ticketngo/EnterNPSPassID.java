package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//By:Diego Cobos
public class EnterNPSPassID extends AppCompatActivity {
    EditText inputPassID;
    private String tripDay;
    private String formattedTripDate;
    private String tripID;
    private String tripName;
    private String enterRoomNumber;
    private String numOfPeople;
    private String passID;
    private static final String TAG = EnterNPSPassID.class.getName();
    private OkHttpClient okHttpClient;
    private Request request;
    private String url = "http://10.129.8.143:81/ticketngo/postGuestAndPass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_n_p_s_pass_i_d);

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
        enterRoomNumber = getIntent().getStringExtra("roomNumber");
        numOfPeople = getIntent().getStringExtra("numOfPeople");
        System.out.println("tripDay: " + tripDay);
        System.out.println("formattedTripDate: " + formattedTripDate);
        System.out.println("tripID: " + tripID);
        System.out.println("tripName: " + tripName);
        System.out.println("roomnum: " + enterRoomNumber);
        System.out.println("numberofpeople: " + numOfPeople);

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterNPSPassID.this, TripHomePage.class));
            }
        });

        Button finish = findViewById(R.id.finish_button);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("ticket_date", formattedTripDate)
                        .add("trip_id", tripID)
                        .add("ticket_room", enterRoomNumber)
                        .add("num_people", numOfPeople)
                        .add("pass_id", passID)
                        .add("trip_name", tripName)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                //request = new Request.Builder().url(url).build();
                okHttpClient.newCall(request).enqueue(new Callback(){

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.i(TAG, response.body().string());
                        String returnedID = response.body().string();

                        System.out.println(returnedID);
                        Intent toTripHomePage = new Intent(EnterNPSPassID.this,TripHomePage.class);
                        toTripHomePage.putExtra("excursionDate", tripDay);
                        toTripHomePage.putExtra("savedTripID", tripID);
                        toTripHomePage.putExtra("tripName", tripName);
                        toTripHomePage.putExtra("formattedDate", formattedTripDate);

                        startActivity(toTripHomePage);

                    }
                });
                startActivity(new Intent(EnterNPSPassID.this, TripHomePage.class));
            }
        });

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterNPSPassID.this, EnterAmountOfPeople.class));
            }
        });

         inputPassID = (EditText) findViewById(R.id.input_pass_id);

        inputPassID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String maybeRightNumber = inputPassID.getText().toString();
                try{
                     passID = maybeRightNumber;
                }
                catch(NumberFormatException ex){
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid Pass ID. " +
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