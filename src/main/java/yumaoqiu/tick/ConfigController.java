package yumaoqiu.tick;

import org.springframework.web.bind.annotation.*;

/**
 * <p> 设置配置文件 </p>
 *
 * @author winter
 * @version V1.0
 * @className: ConfigController
 * @date 2023/8/30 11:42 AM
 */
@RestController
public class ConfigController {
    @RequestMapping("/config")
    public String config(@RequestParam("key") String key, @RequestParam("cdstring") String cdstring) {
        ScheduledTask.setWxKey(key);
        return "OK";
    }
}
