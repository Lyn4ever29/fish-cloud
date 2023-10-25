package cn.lyn4ever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/25
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FishExampleService {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FishExampleService.class);
        springApplication.run(args);
    }
}
