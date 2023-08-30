package yumaoqiu.tick;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.text.DateFormat;
import java.util.Map;

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
    private static String url = "https://www.koksoft.com/booking/guestorder";

    private static String datestring = "2023-09-02";
    private static String cdstringy4_3_4 = "Y:4,15:00-16:00";
    private static String cdstringy4_4_5 = "Y:4,16:00-17:00";
    private static String cdstringy4_10_11 = "Y:4,10:00-11:00";
    private static String cdstringy4_11_12 = "Y:4,11:00-12:00";
    private static String phone = "18516313142";
    private static String guestname = "宋肖肖";

    @Value("${wxkey}")
    private static String wxKeyValue;
    @Value("${cdstring}")
    private static String cdstringValue;

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
    @Scheduled(cron = "00 00 06 * * 4")
    public void task() {
        log.info("========定时抢羽毛球场地 begin==========");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Connection", "keep-alive");
        headers.set("Host", "www.koksoft.com");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "cross-site");
        headers.set("content-type", "multipart/form-data");
        headers.set("wxkey", wxKey);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //  获取当前日期+2天
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date2 = df.format(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);

        body.add("datestring", date2);
        body.add("cdstring", cdstringy4_3_4);
        body.add("phone", phone);
        body.add("guestname", guestname);
        log.info("请求参数:{}", body);
        log.info("请求头:{}", headers);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("结果：{}", response.getBody());

        body.add("cdstring", cdstringy4_4_5);
        HttpEntity<MultiValueMap<String, String>> requestEntity2 = new HttpEntity<>(body, headers);


        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity2, String.class);
        log.info("结果：{}", response2.getBody());


        log.info("========定时抢羽毛球场地 end==========");
    }


    /**
     * 每周无早晨6点执行curl请求抢周天10点到12点的羽毛球场地
     *
     * @author: winter
     * @method: task
     * @date: 2023/8/30 11:27 AM
     * @return
     */
    @Scheduled(cron = "00 00 06 * * 5")
    public void task2() {
        log.info("========定时抢羽毛球场地 begin==========");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Connection", "keep-alive");
        headers.set("Host", "www.koksoft.com");
        headers.set("Sec-Fetch-Mode", "cors");
        headers.set("Sec-Fetch-Site", "cross-site");
        headers.set("content-type", "multipart/form-data");
        headers.set("wxkey", wxKey);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //  获取当前日期+2天
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date2 = df.format(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);

        body.add("datestring", date2);
        body.add("cdstring", cdstringy4_10_11);
        body.add("phone", phone);
        body.add("guestname", guestname);
        log.info("请求参数:{}", body);
        log.info("请求头:{}", headers);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        log.info("结果：{}", response.getBody());

        body.add("cdstring", cdstringy4_10_11);
        HttpEntity<MultiValueMap<String, String>> requestEntity2 = new HttpEntity<>(body, headers);


        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity2, String.class);
        log.info("结果：{}", response2.getBody());


        log.info("========定时抢羽毛球场地 end==========");
    }
}
