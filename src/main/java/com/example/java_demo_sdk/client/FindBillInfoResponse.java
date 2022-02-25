package com.example.java_demo_sdk.client;

import lombok.Data;

@Data
public class FindBillInfoResponse {
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
    private BillInfo data;

    @Data
    public class BillInfo{

        /**
         * 订单充值金额
         */
        private String amount;
        /**
         * 商户appid
         */
        private String app_id;
        /**
         * 1： 充币 2：提币
         */
        private String bill_type;
        /**
         * 1：回调成功 0 ：未回调
         */
        private String call_back;
        /**
         * 创建时间
         */
        private String created_at;
        /**
         * 备注
         */
        private String desc;
        /**
         * 手续费
         */
        private String fee;
        /**
         * 充值账户
         */
        private String from_addr;
        /**
         * 商户订单
         */
        private String oid;
        /**
         * 完成时间
         */
        private String overdated_at;
        /**
         * 订单状态（1：订单创建成功 2：支付中 3：支付成功 4：支付失败）
         */
        private String status;
        /**
         * 订单号
         */
        private String tid;
        /**
         * 临时充值金额
         */
        private String tmp_amount;
        /**
         * 规定充值地址
         */
        private String to_addr;
    }
}
