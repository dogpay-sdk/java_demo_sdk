package com.example.java_demo_sdk.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CreateBillCallbackController {

    @Autowired
    private CreateBillCallbackService createBillCallbackService;

    @PostMapping("/callback")
    @ResponseBody
    public String callback(@RequestBody CreateBillCallbackRequest request){
        return createBillCallbackService.createBillCallback(request);
    }
}
