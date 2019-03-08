package com.example.messagesandcalls.Messages;

import android.widget.EditText;

import com.example.messagesandcalls.Shared.Permissions;

public interface Messages extends Permissions {
    public void sendSms(EditText txtSMS, EditText txtPhoneNumber);
}
