package yumaoqiu.tick;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

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

    @Resource
    private NewScheduledTask newScheduledTask;

    /**
     * 微信key 设置。
     *
     * @param  key      参数描述
     * @param  dateSite 参数描述
     * @return          返回值描述
     */
    @RequestMapping("/config")
    public String config(@RequestParam("key") String key, @RequestParam("dateSite") String dateSite) {
        log.info("key: {}, dateSite: {}", key, dateSite);
        NewScheduledTask.setWxKeyValue(key);
        return "OK";
    }

    @RequestMapping("/test")
    public String config2() throws IOException {
        newScheduledTask.test();
        return "OK";
    }

    @RequestMapping("/yure")
    public CreaOrderResponse yure() {
        newScheduledTask.yure();
        return null;
    }


}
