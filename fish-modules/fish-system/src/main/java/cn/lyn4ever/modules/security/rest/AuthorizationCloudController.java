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
package cn.lyn4ever.modules.security.rest;

import cn.lyn4ever.modules.security.config.bean.LoginProperties;
import cn.lyn4ever.modules.security.service.OnlineUserService;
import cn.lyn4ever.modules.security.service.UserDetailsServiceImpl;
import cn.lyn4ever.modules.security.service.dto.OnlineUserDto;
import cn.lyn4ever.redis.utils.RedisUtils;
import cn.lyn4ever.security.annotation.AnonymousAccess;
import cn.lyn4ever.security.config.SecurityProperties;
import cn.lyn4ever.security.jwt.TokenProvider;
import cn.lyn4ever.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 仅供内部调用的接口
 *
 * @author Zheng Jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationCloudController {
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    @Resource
    private LoginProperties loginProperties;

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/resolveUserByToken")
    @AnonymousAccess //todo 先放行，后续处理
    public OnlineUserDto resolveUserByToken(String token) {
        return userDetailsService.resolveUserByToken(token);
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping(value = "/loadUserByUsername")
    @AnonymousAccess //todo 先放行，后续处理
    public UserDetails loadUserByUsername() {
        return SecurityUtils.getCurrentUser();
    }


}
