package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//By:Diego Cobos

public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private Request request;
    ScrollView scroller;
    private static final String TAG = MainActivity.class.getName();
    private String url = "http://10.129.8.143:81/ticketngo/fetchTrips.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }
        scroller = (ScrollView)findViewById(R.id.mainscroller);
        scroller.removeAllViews();

        Button startTrip = findViewById(R.id.start_trip);
        startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectTodaysDate.class));
            }
        });

        Button uploadInfo = findViewById(R.id.export_trip_information);
        uploadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecipientHomePage.class));
            }
        });

        TextView dateDisplay = (TextView) findViewById(R.id.todays_date);
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        date = dateFormat.format(calendar.getTime());
        dateDisplay.setText(date);




        okHttpClient = new OkHttpClient();
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                List<String> list = new ArrayList<String>();
                List<String> tags = new ArrayList<String>();
                String answr = response.body().string();
                System.out.println("printing answr: " + answr);
                if(answr != null && answr.length() > 1){
                    try {
                        JSONArray list1 =  new JSONArray(answr);
                        if(list1 != null){
                            for (int i = 0; i < list1.length(); i=i+4){
                                String item = "- Date: " + list1.get(i+2) + " -Trip Name " + list1.get(i+3);
                                list.add(item);
                                tags.add(list1.get(i) + "," + list1.get(i+1) + "," + list1.get(i+2) + "," + list1.get(i+3));
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        scroller.removeAllViews();
                        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.setLayoutParams(linearParams);


                        scroller.addView(linearLayout);


                        for(int i = 0; i < list.size();i++) {
                            TextView textView = new TextView(MainActivity.this);
                            textView.setTextSize(18);
                            textView.setTypeface(null, Typeface.BOLD);
                            textView.setPadding(0, 10, 0, 10);
                            textView.setText(list.get(i));
                            textView.setTag(tags.get(i));


                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("textView Clicked: " + textView.getTag());
                                    String[] tripData = textView.getTag().toString().split(",");
                                    Intent toTripHomePage = new Intent(MainActivity.this,TripHomePage.class);
                                    toTripHomePage.putExtra("excursionDate", tripData[2]);
                                    toTripHomePage.putExtra("savedTripID", tripData[0]);
                                    toTripHomePage.putExtra("tripName", tripData[3]);
                                    toTripHomePage.putExtra("formattedDate", tripData[2]);

                                    startActivity(toTripHomePage);
                                }
                            });
                            linearLayout.addView(textView);
                        }
                    }
                });




            }
        });

    }
}