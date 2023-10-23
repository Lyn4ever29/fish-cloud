package cn.lyn4ever.clients.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/22
 */
@EnableFeignClients(basePackages = {"cn.lyn4ever.clients"})
@Configuration
public class FeignClientConfig {
}
