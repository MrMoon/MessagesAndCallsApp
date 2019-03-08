package com.example.messagesandcalls.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messagesandcalls.Calls.CallPhone;
import com.example.messagesandcalls.Messages.SendMessage;
import com.example.messagesandcalls.R;
import com.example.messagesandcalls.Shared.Constants;

public class MessageAndCallActivity extends AppCompatActivity {

    //Global Objects:
    private EditText txtMessage, txtPhoneNumber;
    private Button btnSendSms , btnCall;
    private SendMessage sendMessage;
    private CallPhone callPhone;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_call);

        //Layout Components:
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtPhoneNumber = (EditText) findViewById(R.id.txtNumber);
        btnSendSms = (Button) findViewById(R.id.btnSendSms);
        btnCall = (Button) findViewById(R.id.btnCall);

        //Objects:
        sendMessage = new SendMessage(this);
        callPhone = new CallPhone(this);

        //Checking OS Version:
        if (Build.VERSION.SDK_INT >= 23){
            sendMessage.isGranted();
            callPhone.isGranted();
        }

        //Sending SMS:
        btnSendSms.setOnClickListener(v -> {
            sendMessage.sendSms(txtMessage,txtPhoneNumber);
        });

        //Calls:
        btnCall.setOnClickListener(v -> {
            call(txtPhoneNumber);
        });

    }

    public void call(EditText txtPhoneNumber) {
        //Getting String from EditText:
        phoneNumber = txtPhoneNumber.getText().toString();

        boolean flag = false;

        //Validation:
        if (TextUtils.isEmpty(phoneNumber)) {
            txtPhoneNumber.setError("Please Enter a Phone Number");
        } else if (!(android.util.Patterns.PHONE.matcher(phoneNumber).matches())) {
            txtPhoneNumber.setError("Please Enter a Valid Phone Number");
        } else {
            flag = true;
        }

        if (flag){
            if (callPhone.isGranted()){
                String dial = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            } else {
                Toast.makeText(this, Constants.PERMISSION_NOT_GRANTED, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
