package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//By:Diego Cobos
public class SelectTodaysTrip extends AppCompatActivity {


    private List<String> tripList = new ArrayList<String>();
    private ArrayList<Integer> tripIDs = new ArrayList<Integer>();
    private static final String TAG = MainActivity.class.getName();
    private OkHttpClient okHttpClient;
    private Request request;
    private String url = "http://10.129.8.143:81/ticketngo/fetchData.php";
    private String postUrl = "http://10.129.8.143:81/ticketngo/postTrip.php";
    private String tripID;
    private String tripDate;
    private String formattedTripDate;
    private String returnedID;
    private String tripName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_todays_trip);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }
        Intent receiveIntent = getIntent();

        tripDate = getIntent().getStringExtra("date");
        System.out.println("tripdate from bundle " + tripDate);
        formattedTripDate = getIntent().getStringExtra("formattedDate");

        Spinner spinnerSelectTrip = findViewById(R.id.spinner_select_trip);
        tripList.add("Select Trip:");

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTodaysTrip.this, SelectTodaysDate.class));
            }
        });

        Button finish = findViewById(R.id.finish_button);
        finish.setEnabled(false);
        finish.setVisibility(View.GONE);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okHttpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("date", formattedTripDate)
                        .add("tripID", tripID)
                        .add("tripName", tripName)
                        .build();

                Request request = new Request.Builder()
                        .url(postUrl)
                        .post(formBody)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback(){

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        returnedID = response.body().string();

                        System.out.println(returnedID);
                        Intent toTripHomePage = new Intent(SelectTodaysTrip.this,TripHomePage.class);
                        toTripHomePage.putExtra("excursionDate", tripDate);
                        toTripHomePage.putExtra("savedTripID", returnedID);
                        toTripHomePage.putExtra("tripName", tripName);
                        toTripHomePage.putExtra("formattedDate", formattedTripDate);

                        startActivity(toTripHomePage);

                    }
                });
                /////////////////////////////////

            }
        });


        okHttpClient = new OkHttpClient();
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String answr = response.body().string();
                answr = answr.replace("\"", "");
                answr = answr.replace("[","");
                answr = answr.replace("]", "");
                //System.out.println(answr);
                String finalAnswr = answr;
                String [] temp = answr.split(",", -2);
                for(int i = 0; i < temp.length;i++){
                    if(i % 2 == 0){
                        tripList.add(temp[i]);
                    }
                    else {
                        tripIDs.add(Integer.parseInt(temp[i]));
                    }
                }

                String [] lastList = tripList.toArray(new String[0]);
                System.out.println(lastList);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectTodaysTrip.this,
                                android.R.layout.simple_spinner_dropdown_item, lastList);//dbmsTripPlaceHolder
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSelectTrip.setAdapter(adapter);
                    }
                });

            }
        });

        ////////////////////////////////////////////////////////////////////


        spinnerSelectTrip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinnerSelectTrip.getSelectedItem().toString();
                if(item != "Select Trip:"){
                    finish.setEnabled(true);
                    finish.setVisibility(View.VISIBLE);

                    int idx = tripList.indexOf(item) - 1;
                    int idNeeded = tripIDs.get(idx);

                    tripID = Integer.toString(idNeeded);
                    System.out.println(item);
                    System.out.println(idNeeded);
                    tripName = item;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
}