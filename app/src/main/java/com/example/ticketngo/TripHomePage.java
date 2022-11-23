package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//By:Diego Cobos
public class TripHomePage extends AppCompatActivity {
    private String tripDay;
    private String formattedTripDate;
    private String tripID;
    private String tripName;
    private OkHttpClient okHttpClient;
    private Request request;
    ScrollView scroller;
    private String url = "http://10.129.8.143:81/ticketngo/fetchGuests.php";
    private static final String TAG = TripHomePage.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_home_page);

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




        TextView monthDayYear = findViewById(R.id.tripDate);
        monthDayYear.setText(tripDay);

        TextView nameOfExcursion = findViewById(R.id.tripName1);
        nameOfExcursion.setText(tripName);


        Button addGuest = findViewById(R.id.add_guest_button);
        addGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEnterRoom = new Intent(TripHomePage.this,EnterGuestRoom.class);
                toEnterRoom.putExtra("excursionDate", tripDay);
                toEnterRoom.putExtra("savedTripID", tripID);
                toEnterRoom.putExtra("tripName", tripName);
                toEnterRoom.putExtra("formattedDate", formattedTripDate);
                startActivity(toEnterRoom);
            }
        });

        Button saveTrip = findViewById(R.id.save_trip_button);
        saveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TripHomePage.this, MainActivity.class));
            }
        });
        scroller = (ScrollView)findViewById(R.id.trip_home_scroll_view);
        scroller.removeAllViews();




        okHttpClient = new OkHttpClient();
        request = new Request.Builder().url(url + "?tripid="+tripID).build();
        okHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                List<String> list = new ArrayList<String>();
                String answr = response.body().string();
                System.out.println("printing answr: " + answr);
                if(answr != null && answr.length() > 1){
                    try {
                         JSONArray list1 =  new JSONArray(answr);
                         if(list1 != null){
                             for (int i = 0; i < list1.length(); i=i+3){
                                 String item = "- Guest Room # " + list1.get(i) + " - # of People " + list1.get(i+2);
                                 list.add(item);
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
                        LinearLayout linearLayout = new LinearLayout(TripHomePage.this);
                        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.setLayoutParams(linearParams);


                        scroller.addView(linearLayout);


                        for(int i = 0; i < list.size();i++) {
                            TextView textView = new TextView(TripHomePage.this);
                            textView.setTextSize(18);
                            textView.setTypeface(null, Typeface.BOLD);
                            textView.setPadding(0, 10, 0, 10);
                            textView.setText(list.get(i));
                            linearLayout.addView(textView);
                        }
                    }
                });




            }
        });
    }
}