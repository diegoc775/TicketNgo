package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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
public class EnterRecipientName extends AppCompatActivity {
    EditText inputRecipientName;
    String recipientName;
    NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_recipient_name);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterRecipientName.this, RecipientHomePage.class));
            }
        });

        Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipientName == null || recipientName.trim().isEmpty()){
                    Toast.makeText(EnterRecipientName.this, "Please enter Recipient Name to continue.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent toRecipientEmail = new Intent(EnterRecipientName.this, EnterRecipientEmail.class);
                    toRecipientEmail.putExtra("recipientName", recipientName);
                    System.out.println(toRecipientEmail.getStringExtra("recipientName"));

//                    Intent notificationIntent = new Intent(EnterRecipientName.this, EnterRecipientName.class);
//                    PendingIntent contentIntent = PendingIntent.getActivity(EnterRecipientName.this, 0, notificationIntent, 0);
//
//                    Notification.Builder builder = new Notification.Builder(EnterRecipientName.this);
//                    builder.setAutoCancel(true);
//                    builder.setTicker("this is ticker text");
//                    builder.setContentTitle("This is the content title");
//                    builder.setContentText("this is the content text");
//                    builder.setSmallIcon(R.drawable.ic_stat_ticketngo_status_bar_icon);
//                    builder.setContentIntent(contentIntent);
//                    builder.setOngoing(true);
//                    builder.setSubText("This is subtext...");
//                    builder.setNumber(100);
//
//                    builder.build();
//                    manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    Notification notification = builder.build();
//                    manager.notify(100, notification);

                    startActivity(toRecipientEmail);

                }
            }
        });

        inputRecipientName = (EditText) findViewById(R.id.input_recipient_name);

        inputRecipientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipientName = inputRecipientName.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}