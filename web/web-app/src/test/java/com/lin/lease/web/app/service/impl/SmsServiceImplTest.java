package com.lin.lease.web.app.service.impl;

import com.lin.lease.web.app.service.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SmsServiceImplTest {

    @Autowired
    private SmsService smsService;
    @Test
    void sendSms() {
        smsService.sendSms("13349807938","10086");
    }
}