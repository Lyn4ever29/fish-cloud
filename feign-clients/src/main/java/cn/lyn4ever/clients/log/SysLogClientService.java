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
package cn.lyn4ever.clients.log;

import cn.lyn4ever.clients.config.AuthorizationRequestInterceptor;
import cn.lyn4ever.clients.log.dto.SysLogClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 这个注解表明这是一个Feign的客户端，
 * 其中的value属性就是目标微服务的服务名
 */
@FeignClient(value = "system-log", configuration = {AuthorizationRequestInterceptor.class})
public interface SysLogClientService {

    /**
     * 这个其实是provider提供者中的方法
     *
     * @param sysLog
     * @return
     */
    @PostMapping("/api/logs/saveLog")
    public String saveLog(@RequestBody SysLogClientDto sysLog);

}
