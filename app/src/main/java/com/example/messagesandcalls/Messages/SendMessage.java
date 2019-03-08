package com.example.messagesandcalls.Messages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messagesandcalls.Shared.Constants;

public class SendMessage extends AppCompatActivity implements Messages{
    //Global Objects:
    private Context context;
    private String sms , phoneNumber;
    private EditText txtSMS , txtPhoneNumber;

    //Constructor:
    public SendMessage(Context context) {
        this.context = context;
    }

    @Override
    public boolean isGranted() { //Checking Permissions
        boolean flag = false;
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED)){
            flag = true;
        } else {
            requestPermission();
            flag = false;
        }

        if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED){
            flag = true;
        } else {
            requestPermissionPhoneState();
            flag = false;
        }
        return flag;
    }

    @Override
    public void requestPermission() { //Requesting Permission
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{Manifest.permission.SEND_SMS},
                    Constants.REQUEST__SMS_CODE);
    }

    @Override
    public void requestPermissionPhoneState() {
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                Constants.REQUEST_READ_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //After the Permission is Displayed and Allowed or Deny
        switch (requestCode){
            case (Constants.REQUEST__SMS_CODE):
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //Checking the answer of the permission
                    Toast.makeText(context, Constants.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, Constants.PERMISSION_NOT_GRANTED, Toast.LENGTH_SHORT).show();
                }
                break;

            case (Constants.REQUEST_READ_PHONE_STATE):
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context, Constants.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, Constants.PERMISSION_NOT_GRANTED, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void sendSms(EditText txtSMS, EditText txtPhoneNumber) {
        //Getting String from EditText:
        phoneNumber = txtPhoneNumber.getText().toString();
        sms = txtSMS.getText().toString();

        boolean flag = false;

        //Validation:
        if (TextUtils.isEmpty(sms)) {
            txtSMS.setError("Please Enter a Message");
        } else {
            flag = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            txtPhoneNumber.setError("Please Enter a Phone Number");
        } else if (!(android.util.Patterns.PHONE.matcher(phoneNumber).matches())) {
            txtPhoneNumber.setError("Please Enter a Valid Phone Number");
        } else {
            flag = true;
        }

        if (flag){
            if (isGranted()){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,null,sms,null,null);
            } else {
                Toast.makeText(context, Constants.PERMISSION_NOT_GRANTED, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
