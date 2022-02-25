package com.example.java_demo_sdk.client;

import lombok.Data;

@Data
public class CreateBillRequest {
    /**
     * 应用ID 参加签名
     */
    private String appId;

    /**
     * 签名 : md5(amount+ appId+ oid+ key).toLowerCase()
     */
    private String sign;

    /**
     * 客户自身平台订单号（唯一） 参加签名
     */
    private String oid;

    /**
     * 充值数量(usdt) 参加签名
     */
    private String amount;
}
