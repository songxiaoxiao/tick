package yumaoqiu.tick;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池初始化
 *
 * @author na.liu
 */

@Configuration
@Slf4j
public class ConfigThread {

    /**
     * 定时任务
     *
     * @author: winter
     * @method:
     * @date: 2023/7/5 12:03 PM
     * @return
     */
    @Bean(name = "jobExecutor")
    public ThreadPoolTaskExecutor jobExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setKeepAliveSeconds(3000);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(0);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(120);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setTaskDecorator(r -> {
            Map<String, String> contextMap = MDC.getCopyOfContextMap();
            Map<String, String> copyContextMap = contextMap != null ? contextMap : new HashMap<>();
            return () -> {
                MDC.setContextMap(copyContextMap);
                r.run();
            };
        });
        executor.setThreadNamePrefix("jobExecutor-search-pool-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
