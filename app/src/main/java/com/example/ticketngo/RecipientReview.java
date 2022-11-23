package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//By:Diego Cobos
public class RecipientReview extends AppCompatActivity {
    ArrayList<String> chosenEs = new ArrayList<String>();
    ArrayList<String> chosenNs = new ArrayList<String>();
    ScrollView scroller;
    SimpleDateFormat formatter;
    List<String> list = new ArrayList<String>();
    private static final String TAG = RecipientReview.class.getName();
    private OkHttpClient okHttpClient;
    Date tripdate = new Date();
    private Request request;
    private String url = "http://10.129.8.143:81/ticketngo/fetchEmailData.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_review);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }



        Intent receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getExtras();
        chosenNs = getIntent().getStringArrayListExtra("listofnames");
        chosenEs = getIntent().getStringArrayListExtra("listofemails");
        System.out.println("names list: " + chosenNs + "size: " + chosenNs.size());
        System.out.println("emails list: " + chosenEs + "size: " + chosenEs.size());
        scroller = (ScrollView)findViewById(R.id.reviewscroller);
        scroller.removeAllViews();
        recipientList();



        tripdate.setHours(0);
        String pattern = "yyyy-MM-dd";
        formatter = new SimpleDateFormat(pattern);

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipientReview.this, RecipientHomePage.class));
            }
        });

        getData();

        Button submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });
    }
    private void sendEmail(View view) {
        String body = "Trips Completed for today:\n\n\n";
        for(int i = 0; i < list.size(); i++){

            body = body + list.get(i) + "\n\n";
        }
        System.out.println("email body :" + body);



        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , chosenEs.toArray(new String[0]));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Trips for " + tripdate);
        emailIntent.putExtra(Intent.EXTRA_TEXT   , body);

        try {
            startActivityForResult(Intent.createChooser(emailIntent, "Select a Email Client"),800);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RecipientReview.this, "No Email client found!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 800) {
            startActivity(new Intent(RecipientReview.this, CongratsInfoSent.class));
            //////////////////////////////////////////////////////////////////////////////////////

        }
    }

    private void recipientList(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                scroller.removeAllViews();
                LinearLayout linearLayout = new LinearLayout(RecipientReview.this);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(linearParams);


                scroller.addView(linearLayout);


                for(int i = 0; i < chosenNs.size();i++) {
                    TextView textView = new TextView(RecipientReview.this);
                    textView.setTextSize(18);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setPadding(0, 10, 0, 10);
                    textView.setText(chosenNs.get(i));
                    textView.setTag( "3, 62");


                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("textView Clicked: " + textView.getTag());
                        }
                    });
                    linearLayout.addView(textView);
                }
            }
        });
//
        //System.out.println("list size: " + list.size());
        //String [] lastList = list.toArray(new String[0]);
        //System.out.println(lastList);



    }

    private void getData(){
        String mysqlDateString = formatter.format(tripdate);
        System.out.println("sql date string: " + mysqlDateString);
        okHttpClient = new OkHttpClient();
        request = new Request.Builder().url(url + "?tripdate="+mysqlDateString).build();
        okHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                list.clear();
                String answr = response.body().string();

                if(answr != null && answr.length() > 1){
                    try {
                        JSONArray list1 =  new JSONArray(answr);
                        if(list1 != null){
                            for (int i = 0; i < list1.length(); i=i+5){
                                String item = "- Trip Name: " + list1.get(i+2) + " - Guest Room #: " + list1.get(i+4) + " - Number of People in Party: " + list1.get(i+3);
                                list.add(item);
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }


                }




            }
        });
    }
}