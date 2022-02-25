## 一、说明
- 该SDK是对 dogpay api接口文档的JAVA版封装，如有疑问，可咨询官网客服

## 二、使用步骤

```java

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
    public boolean createBill(String oid, String amount) {
        //业务参数（amount：充值金额、appId：应用ID、oid：订单ID、key：app密钥） 参与签名
        String param = String.format("amount=%s&appId=%s&oid=%s&key=%s", amount, appId, oid, appKey);
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
    public FindBillInfoResponse.BillInfo findBillInfo(String tid) {
        //业务参数(appId:应用ID、tid：dogpay系统订单ID、appKey：app密钥) 参与签名
        String param = String.format("appId=%s&tid=%s&key=%s", appId, tid, appKey);
        //签名 md5(appId+ tid+ key).toLowerCase()
        String sign = DigestUtils.md5DigestAsHex(param.getBytes(StandardCharsets.UTF_8)).toLowerCase();
        //请求url
        String url = clientUrl + findBillUrl + "?" + param + "&sign=" + sign;
        //调用接口
        ResponseEntity<FindBillInfoResponse> responseEntity = restTemplate.postForEntity(url, null, FindBillInfoResponse.class);
        FindBillInfoResponse response;
        if (Objects.nonNull(response = responseEntity.getBody()) && response.getCode() == 200) {
            return response.getData();
        } else {
            throw new RuntimeException("未找到订单");
        }
    }
}

/**
 * 创建订单返回结果
 */
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


/**
 * 查询订单返回结果
 */
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
    public class BillInfo {

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

```

## 三、关于回调

```java
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


```
