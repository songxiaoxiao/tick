package yumaoqiu.tick;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 设置配置文件 </p>
 *
 * @author winter
 * @version V1.0
 * @className: ConfigController
 * @date 2023/8/30 11:42 AM
 */
@Slf4j
@RestController
public class ConfigController {
    @RequestMapping("/config")
    public String config(@RequestParam("key") String key, @RequestParam("cdstring") String cdstring) {
        log.info("key: {}, cdstring: {}", key, cdstring);
        ScheduledTask.setWxKey(key);
        return "OK";
    }

    @RequestMapping("/order")
    public CreaOrderResponse order(@RequestParam("date") String date, @RequestParam("cdstring") String cdstring) {
        ScheduledTask scheduledTask = new ScheduledTask();

        CreaOrderResponse creaOrderResponse = scheduledTask.memberOrder(date, cdstring);
        scheduledTask.getminipaystring(creaOrderResponse.getData2(), creaOrderResponse.getData1());
        return creaOrderResponse;
    }


}
