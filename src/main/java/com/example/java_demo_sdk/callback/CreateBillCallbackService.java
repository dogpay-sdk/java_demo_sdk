package com.example.java_demo_sdk.callback;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateBillCallbackService {

    public String createBillCallback(CreateBillCallbackRequest request){
        System.out.println(JSON.toJSONString(request));
        if(Objects.nonNull(request)){
            //todo 验签、业务系统自身逻辑

            return "SUCCESS";
        }
        return "err";
    }

}
