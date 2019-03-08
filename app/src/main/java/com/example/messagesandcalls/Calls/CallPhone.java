package com.example.messagesandcalls.Calls;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messagesandcalls.Shared.Constants;
import com.example.messagesandcalls.Shared.Permissions;

public class CallPhone extends AppCompatActivity implements Permissions {

    //Global Objects:
    private Context context;
    private EditText txtPhoneNumber;
    private String phoneNumber;

    //Constructor:
    public CallPhone(Context context) {
        this.context = context;
    }

    @Override
    public boolean isGranted() {
        boolean flag = false;

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED){
            flag = true;
        } else {
            requestPermission();
            flag = false;
        }
        return flag;
    }

    @Override
    public void requestPermission() {
        ActivityCompat.requestPermissions((Activity)context,
                new String[]{Manifest.permission.CALL_PHONE}
                , Constants.REQUEST__CALL_CODE);
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
            case (Constants.REQUEST__CALL_CODE):
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
}
