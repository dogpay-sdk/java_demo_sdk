package com.example.java_demo_sdk.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class CallDogpayTestController {

    @Autowired
    private DogpayClient dogpayClient;

    @PostMapping("/create_bill")
    @ResponseBody
    public boolean createBill(){
        String amount = "10";
        String oid = UUID.randomUUID().toString();
        return dogpayClient.createBill(oid, amount);
    }

    @GetMapping("/find_bill")
    @ResponseBody
    public FindBillInfoResponse.BillInfo findBill(@RequestParam String tid){
        return dogpayClient.findBillInfo(tid);
    }

}
