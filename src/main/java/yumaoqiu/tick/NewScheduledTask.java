package yumaoqiu.tick;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
public class NewScheduledTask {

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
        NewScheduledTask.wxKey = wxKey;
    }

    public static void setUrl(String url) {
        NewScheduledTask.url = url;
    }

    public static void setDatestring(String datestring) {
        NewScheduledTask.datestring = datestring;
    }


    public static void setPhone(String phone) {
        NewScheduledTask.phone = phone;
    }

    public static void setGuestname(String guestname) {
        NewScheduledTask.guestname = guestname;
    }

        /**
         * 每周四早晨6点执行curl请求抢周六三点到五点的羽毛球场地
         *
         * @author: winter
         * @method: task
         * @date: 2023/8/30 11:27 AM
         * @return
         */
    @Scheduled(cron = "59 59 23 * * *", zone = "Asia/Shanghai")
    public void test() throws IOException {
        try{
            Thread.sleep(900);
        }catch (Exception e){
            log.info("sleep error");
        }
        log.info("========定时抢羽毛球场地 begin==========");
        //  获取当前日期+2天
        String date3 = getDate3();

        getminipaystring("", date3);


        log.info("========定时抢羽毛球场地 end==========");
    }

    public static void main(String[] args) throws IOException {
        String date2 = getDate2();

        getminipaystring("", date2);
    }

    @Scheduled(cron = "00 50 05 * * 3", zone = "Asia/Shanghai")
    public void yure() {
        log.info("========定时抢羽毛球场地 begin==========");
        //  获取当前日期+2天
        for (int i = 0; i < 5; i++) {
            jobExecutor.execute(() -> {
                // 日期
                String date2 = getDate2();
            });
        }
        log.info("========定时抢羽毛球场地 end==========");
    }

    /**
     * 获取当前日期+2
     *
     * @author: winter
     * @method: task2
     * @date: 2023/11/20 11:53 AM
     * @return
     */
    public static String getDate2() {
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return df.format(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);
    }

    /**
     * 获取当前日期+3
     *
     * @author: winter
     * @method: task2
     * @date: 2023/11/20 11:53 AM
     * @return
     */
    public static String getDate3() {
        DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return df.format(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000);
    }


    public static void getminipaystring(String payje, String date) throws IOException {
            String url = "https://stmember.styd.cn/v2/reserve/submit";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // 设置请求方法为POST
            con.setRequestMethod("POST");

            // 设置请求头
            con.setRequestProperty("Host", "stmember.styd.cn");
            con.setRequestProperty("theme-compatible", "1");
            con.setRequestProperty("brand-code", "DyKGrOyBKxD");
            con.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; U; Android 9; zh-cn; Redmi Note 5 Build/PKQ1.180904.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.141 Mobile Safari/537.36 XiaoMi/MiuiBrowser/11.10.8");
            con.setRequestProperty("wx-token", "Cnp9p3jyrdzCuwk8n_1oU-xKHce-8lF2");
            con.setRequestProperty("referer", "https://servicewechat.com/wx42459a712712364c/1/page-frame.html");
            con.setRequestProperty("app-id", "mina");
            con.setRequestProperty("xweb_xhr", "1");
            con.setRequestProperty("mina-version", "independent");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("shop-id", "2312675187571576");
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Sec-Fetch-Site", "cross-site");
            con.setRequestProperty("Sec-Fetch-Mode", "cors");
            con.setRequestProperty("Sec-Fetch-Dest", "empty");
            con.setRequestProperty("Accept-Language", "zh-CN,zh");
            con.setRequestProperty("Cookie", "acw_tc=2f624a7e17023700442751830e7818b1d3c669fbff2300bc763a0fd7b9ae5b");

            // 启用输入流和输出流
            con.setDoOutput(true);

            // 设置请求体
            String requestBody = "{\n" +
                    "    \"schedule_id\": 0,\n" +
                    "    \"coach_id\": 0,\n" +
                    "    \"course_id\": 0,\n" +
                    "    \"seat\": [],\n" +
                    "    \"consume_type\": \"wechat\",\n" +
                    "    \"consume_id\": \"wechat\",\n" +
                    "    \"current_reservation_num\": 1,\n" +
                    "    \"reserve_type\": \"venues\",\n" +
                    "    \"remark\": \"\",\n" +
                    "    \"venues_id\": \"2325874511315032\",\n" +
                    "    \"venues_date\":\"" + date + "\",\n" +
                    "    \"venues_site_time\": [\n" +
                    "        {\n" +
                    "            \"site_id\": 2325880735662119,\n" +
                    "            \"site_name\": \"羽毛球场地4\",\n" +
                    "            \"start_time\": \"08:00\",\n" +
                    "            \"end_time\": \"09:00\",\n" +
                    "            \"price\": \"60.0\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"site_id\": 2325880735662119,\n" +
                    "            \"site_name\": \"羽毛球场地4\",\n" +
                    "            \"start_time\": \"09:00\",\n" +
                    "            \"end_time\": \"10:00\",\n" +
                    "            \"price\": \"60.0\"\n" +
                    "        }\n" +
                    "    ]\n" +

                    "}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        // 读取响应体
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                responseCode >= 400 ? con.getErrorStream() : con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("Response Body: " + response.toString());
        }

    }


}
