package com.lin.lease.web.app.service;

public interface SmsService {
    //发送短信
    void sendSms(String phoneNumber, String message);
}
