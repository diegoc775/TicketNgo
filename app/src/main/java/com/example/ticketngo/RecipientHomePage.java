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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//By:Diego Cobos
public class RecipientHomePage extends AppCompatActivity {


    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> chosenEs = new ArrayList<String>();
    ArrayList<String> chosenNs = new ArrayList<String>();
    private static final String TAG = MainActivity.class.getName();
    ScrollView scroller;
    private OkHttpClient okHttpClient;
    private Request request;
    private String url = "http://10.129.8.143:81/ticketngo/fetchRecipients.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_home_page);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }
        Intent receiveIntent = getIntent();
        if(receiveIntent.hasExtra("recipientName") && receiveIntent.hasExtra("recipientEmail")){
            String tempEmail = receiveIntent.getStringExtra("recipientEmail");
            String tempName = receiveIntent.getStringExtra("recipientName");
            chosenEs.add(tempEmail);
            chosenNs.add(tempName);
            System.out.println("you got it: " + chosenEs.get(0));
            System.out.println("chosen emails list: " + tempEmail);
            System.out.println("chosen names list" + tempName);


        }

        scroller = (ScrollView)findViewById(R.id.recipientscroller);
        scroller.removeAllViews();
        recipientList();

        names.add("Add Recipient:");
        Button enterRecipient = findViewById(R.id.add_recipient_button);
        enterRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipientHomePage.this, EnterRecipientName.class));
            }
        });

        Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenEs.isEmpty()|| chosenNs.isEmpty()){
                    Toast.makeText(RecipientHomePage.this, "Please select Recipient/s to continue.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent toRecipientReview = new Intent(RecipientHomePage.this, RecipientReview.class);
                    toRecipientReview.putStringArrayListExtra("listofnames", chosenNs);
                    toRecipientReview.putStringArrayListExtra("listofemails", chosenEs);
                    startActivity(toRecipientReview);
                }
            }
        });





        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipientHomePage.this, MainActivity.class));
            }
        });
        Spinner spinnerSelectTrip = findViewById(R.id.spinner_select_trip);

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
                String finalAnswr = answr;
                String [] temp = answr.split(",", -2);

                for(int i = 0; i < temp.length;i++){
                    if(i % 2 != 0){
                        names.add(temp[i]);
                    }
                    else{
                        emails.add(temp[i]);
                    }
                }
                String [] lastList = names.toArray(new String[0]);


                System.out.println(lastList);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(RecipientHomePage.this,
                                android.R.layout.simple_spinner_dropdown_item, lastList);//dbmsTripPlaceHolder
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSelectTrip.setAdapter(adapter);
                    }
                });

            }
        });
        //////////////////////////////////////////////////////////////////////

        spinnerSelectTrip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinnerSelectTrip.getSelectedItem().toString();
                if(item != "Add Recipient:"){
                    int idx = names.indexOf(item) - 1;
                    if(!chosenNs.contains(item)){
                        chosenNs.add(item);
                        recipientList();
                        System.out.println(item + "was added to the names list");
                    }
                    if(!chosenEs.contains(emails.get(idx))){
                        chosenEs.add(emails.get(idx));
                        System.out.println(emails.get(idx) + "was added to the email list");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void recipientList(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                scroller.removeAllViews();
                LinearLayout linearLayout = new LinearLayout(RecipientHomePage.this);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(linearParams);


                scroller.addView(linearLayout);


                for(int i = 0; i < chosenNs.size();i++) {
                    TextView textView = new TextView(RecipientHomePage.this);
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




    }

}