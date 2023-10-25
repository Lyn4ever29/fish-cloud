/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.lyn4ever;

import cn.lyn4ever.common.utils.SpringContextHolder;
import cn.lyn4ever.security.annotation.rest.AnonymousGetMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开启审计功能 -> @EnableJpaAuditing
 *
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@RestController
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class LogService {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(LogService.class);
        // 监控应用的PID，启动时可指定PID路径：--spring.pid.file=/home/eladmin/app.pid
        // 或者在 application.yml 添加文件路径，方便 kill，kill `cat /home/eladmin/app.pid`
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
