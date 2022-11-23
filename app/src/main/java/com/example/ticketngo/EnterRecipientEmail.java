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
public class EnterRecipientEmail extends AppCompatActivity {
    EditText inputRecipientEmail;
    String recipientEmail;
    String recipientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_recipient_email);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_hospitality_and_leisure_dark));
        }
        Intent receiveIntent = getIntent();
        recipientName = getIntent().getStringExtra("recipientName");

        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterRecipientEmail.this, RecipientHomePage.class));
            }
        });

        Button finish = findViewById(R.id.finish_button);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipientEmail == null || recipientEmail.trim().isEmpty()){
                    Toast.makeText(EnterRecipientEmail.this, "Please enter Recipient email to continue.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent toRecipientHome = new Intent(EnterRecipientEmail.this, RecipientHomePage.class);
                    toRecipientHome.putExtra("recipientName", recipientName);
                    toRecipientHome.putExtra("recipientEmail", recipientEmail);

                    startActivity(toRecipientHome);

                }

                startActivity(new Intent(EnterRecipientEmail.this, RecipientHomePage.class));
            }
        });

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterRecipientEmail.this, EnterRecipientName.class));
            }
        });

        inputRecipientEmail = (EditText) findViewById(R.id.input_recipient_email);

        inputRecipientEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipientEmail = inputRecipientEmail.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}