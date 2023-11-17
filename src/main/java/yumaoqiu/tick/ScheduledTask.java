package yumaoqiu.tick;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 *
 * <p> 定时抢羽毛球场地
 *
 * @author winter
 * @version V1.0
 * @className: ScheduledTask
 * @date 2023/8/30 11:25 AM
 */
@Component
@Slf4j
@Data
public class ScheduledTask {

    // 微信key
    private static String wxKey;
    // 请求接口地址
    private static String url = "https://www.koksoft.com/booking/memberorder";
    private static String getminipaystring = "https://www.koksoft.com/booking/memberorder";

    private static String datestring = "2023-09-02";
    private static String cdstringy4_10_11 = "Y:4,10:00-11:00";
    private static String cdstringy4_14_15 = "Y:4,14:00-15:00";
    private static String cdstringy4_08_09 = "Y:4,08:00-09:00";
    private static String cdstringy4_09_10 = "Y:4,09:00-10:00";
    private static String phone = "18516313142";
    private static String guestname = "宋肖肖";

    @Value("${wxkey}")
    private static String wxKeyValue;
    @Value("${cdstring}")
    private static String cdstringValue;

    @Resource(name = "jobExecutor")
    private ThreadPoolTaskExecutor jobExecutor;

    @PostConstruct
    public void init() {
        setWxKey(wxKeyValue);
    }

    public static void setWxKey(String wxKey) {
        ScheduledTask.wxKey = wxKey;
    }

    public static void setUrl(String url) {
        ScheduledTask.url = url;
    }

    public static void setDatestring(String datestring) {
        ScheduledTask.datestring = datestring;
    }


    public static void setPhone(String phone) {
        ScheduledTask.phone = phone;
    }

    public static void setGuestname(String guestname) {
        ScheduledTask.guestname = guestname;
    }

        /**
         * 每周四早晨6点执行curl请求抢周六三点到五点的羽毛球场地
         *
         * @author: winter
         * @method: task
         * @date: 2023/8/30 11:27 AM
         * @return
         */
    @Scheduled(cron = "59 59 05 * * 4", zone = "Asia/Shanghai")
    public void task() {
        log.info("========定时抢羽毛球场地 begin==========");


        //  获取当前日期+2天
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date2 = df.format(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);


        List<CompletableFuture<CreaOrderResponse>> futures = new ArrayList<>();
        // 4 10-11点
        futures.add(CompletableFuture.supplyAsync(() -> {
            CreaOrderResponse creaOrderResponse = memberOrder(date2, cdstringy4_08_09);
            return creaOrderResponse;
        }, jobExecutor));

        futures.add(CompletableFuture.supplyAsync(() -> {
            CreaOrderResponse creaOrderResponse = memberOrder(date2, cdstringy4_09_10);
            return creaOrderResponse;
        }, jobExecutor));

        // 生成支付单号
        futures.forEach(future -> {
            try {
                CreaOrderResponse creaOrderResponse = future.get();
                getminipaystring(creaOrderResponse.getData2(), creaOrderResponse.getData1());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        getminipaystring(creaOrderResponse.getData2(), creaOrderResponse.getData1());
//        getminipaystring(creaOrderResponse2.getData2(), creaOrderResponse2.getData1());

        log.info("========定时抢羽毛球场地 end==========");
    }


    @Scheduled(cron = "58 59 05 * * 6", zone = "Asia/Shanghai")
    public void task2() {
        log.info("========定时抢羽毛球场地 begin==========");
        //  获取当前日期+2天
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date2 = df.format(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);

        List<CompletableFuture<CreaOrderResponse>> futures = new ArrayList<>();

        // 4 10-11点
        futures.add(CompletableFuture.supplyAsync(() -> {
            CreaOrderResponse creaOrderResponse = memberOrder(date2, cdstringy4_08_09);
            return creaOrderResponse;
        }, jobExecutor));

        // 生成支付单号
        futures.forEach(future -> {
            try {
                CreaOrderResponse creaOrderResponse = future.get();
                getminipaystring(creaOrderResponse.getData2(), creaOrderResponse.getData1());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        log.info("========定时抢羽毛球场地 end==========");
    }
    @Scheduled(cron = "58 59 05 * * 6", zone = "Asia/Shanghai")
    public void task3() {
        log.info("========定时抢羽毛球场地 begin==========");
        //  获取当前日期+2天
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date2 = df.format(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);

        List<CompletableFuture<CreaOrderResponse>> futures = new ArrayList<>();

        CreaOrderResponse creaOrderResponse = memberOrder(date2, cdstringy4_09_10);

        getminipaystring(creaOrderResponse.getData2(), creaOrderResponse.getData1());

        log.info("========定时抢羽毛球场地 end==========");
    }



    public CreaOrderResponse memberOrder(String data, String cdstring ) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Connection", "keep-alive");
            headers.set("Host", "www.koksoft.com");
            headers.set("Sec-Fetch-Mode", "cors");
            headers.set("Sec-Fetch-Site", "cross-site");
            headers.set("content-type", "application/x-www-form-urlencoded");
            headers.set("wxkey", wxKey);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("datestring", data);
            body.add("cdstring", cdstring);
            body.add("paytype", "W");
            body.add("guestname", guestname);
            log.info("请求参数:{}", body);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);


            log.info("结果：{}", exchange.getBody());
            Response<CreaOrderResponse> creaOrderResponseResponse =
                    JSON.parseObject(exchange.getBody(), new TypeReference<Response<CreaOrderResponse>>(Response.class) {});
            return creaOrderResponseResponse.getResult();
        } catch (Exception e) {
            log.error("========定时抢羽毛球场地 error==========", e);
        }
        return new CreaOrderResponse();
    }



    public void getminipaystring(String payje, String no){
        String baseUrl = "https://www.koksoft.com/common/getminipaystring";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("payje", payje);
        queryParams.put("paykey", "guestorder");
        queryParams.put("goodsshuoming", "小程序订场");
        queryParams.put("out_trade_no", no);
        queryParams.put("qgbh", "001");

        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?");

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        String finalUrl = urlBuilder.toString();
        finalUrl = finalUrl.substring(0, finalUrl.length() - 1); // Remove the last "&"

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("wxkey", wxKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("请求头:{}", headers);
        log.info("请求参数:{}", entity);
        ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, String.class);

        log.info("结果：{}", response.getBody());
    }


}
