package com.example.java_demo_sdk.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class DogpayClient {

    @Value("${dogpay.app.id}")
    private String appId;

    @Value("${dogpay.app.key}")
    private String appKey;

    @Value("${dogpay.client.url}")
    private String clientUrl;

    @Value("${dogpay.create.bill.url}")
    private String createBillUrl;

    @Value("${dogpay.find.bill.url}")
    private String findBillUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用dogpay创建充值订单
     *
     * @param oid 业务系统订单ID
     * @param amount 订单金额
     * @return true:订单创建成功 false:订单创建失败
     */
    public boolean createBill(String oid, String amount){
        //业务参数（amount：充值金额、appId：应用ID、oid：订单ID、key：app密钥） 参与签名
        String param = String.format("amount=%s&appId=%s&oid=%s&key=%s",amount, appId, oid, appKey);
        //签名  md5(amount+ appId+ oid+ key).toLowerCase()
        String sign = DigestUtils.md5DigestAsHex(param.getBytes(StandardCharsets.UTF_8)).toLowerCase();
        //请求url
        String url = clientUrl + createBillUrl + "?" + param + "&sign=" + sign;
        //调用接口
        ResponseEntity<CreateBillResponse> responseEntity = restTemplate.postForEntity(url, null, CreateBillResponse.class);
        CreateBillResponse response;
        return Objects.nonNull(response = responseEntity.getBody()) && response.getCode() == 200;
    }

    /**
     * 查询订单信息
     *
     * @param tid dogpay系统订单ID
     * @return 订单信息
     */
    public FindBillInfoResponse.BillInfo findBillInfo(String tid){
        //业务参数(appId:应用ID、tid：dogpay系统订单ID、appKey：app密钥) 参与签名
        String param = String.format("appId=%s&tid=%s&key=%s", appId, tid, appKey);
        //签名 md5(appId+ tid+ key).toLowerCase()
        String sign = DigestUtils.md5DigestAsHex(param.getBytes(StandardCharsets.UTF_8)).toLowerCase();
        //请求url
        String url = clientUrl + findBillUrl + "?" + param + "&sign=" + sign;
        //调用接口
        ResponseEntity<FindBillInfoResponse> responseEntity = restTemplate.postForEntity(url, null, FindBillInfoResponse.class);
        FindBillInfoResponse response;
        if(Objects.nonNull(response = responseEntity.getBody()) && response.getCode() == 200){
            return response.getData();
        }else{
            throw new RuntimeException("未找到订单");
        }
    }

}
