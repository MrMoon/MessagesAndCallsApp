package com.example.messagesandcalls.Shared;

public interface Permissions {
    boolean isGranted();
    void requestPermission();
    void requestPermissionPhoneState();
}
