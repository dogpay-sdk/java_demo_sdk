package com.example.java_demo_sdk.client;

import lombok.Data;

@Data
public class CreateBillResponse {

    /**
     * 正常200；异常其它 不参加签名
     */
    private int code;

    /**
     * 异常有信息输出 不参加签名
     */
    private String msg;

    /**
     * 不参加签名
     */
    private CreateBillResult data;

    @Data
    public class CreateBillResult{

        /**
         * 客户自身平台订单号（唯一）  参加签名
         */
        private String oid;

        /**
         * 平台订单ID  参加签名
         */
        private String tid;

        /**
         * 订单状态（1：待审核 2：审核中 3：审核完成 4：提币成功）  参加签名
         */
        private int stauts;

        /**
         * 充值地址  参加签名
         */
        private String address;

        /**
         * 签名 不参加签名
         */
        private String sign;
    }
}



