package com.example.java_demo_sdk.callback;

import lombok.Data;

@Data
public class CreateBillCallbackRequest {

    /**
     * appId 应用ID
     */
    private String appId;

    /**
     * 签名 : md5(body + key + nonce + timestamp).toLowerCase()
     */
    private String sign;

    /**
     * 客户自身平台订单号（唯一）
     */
    private String oid;

    /**
     * 平台订单ID
     */
    private String tid;

    /**
     * 订单状态 ( 1：充值成功 2：充值失败)
     */
    private int status;


}
