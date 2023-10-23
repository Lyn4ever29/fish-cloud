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
package cn.lyn4ever.modules.security.service;

import cn.lyn4ever.exception.BadRequestException;
import cn.lyn4ever.modules.security.service.dto.JwtUserDto;
import cn.lyn4ever.modules.security.service.dto.OnlineUserDto;
import cn.lyn4ever.modules.system.service.DataService;
import cn.lyn4ever.modules.system.service.RoleService;
import cn.lyn4ever.modules.system.service.UserService;
import cn.lyn4ever.modules.system.service.dto.UserLoginDto;
import cn.lyn4ever.security.security.TokenProvider;
import cn.lyn4ever.security.service.CloudUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements CloudUserDetailService {
    private final UserService userService;
    private final RoleService roleService;
    private final DataService dataService;
    private final UserCacheManager userCacheManager;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;

    @Override
    public JwtUserDto loadUserByUsername(String username) {
        JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
        if (jwtUserDto == null) {
            UserLoginDto user;
            try {
                user = userService.getLoginData(username);
            } catch (EntityNotFoundException e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException(username, e);
            }
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!user.getEnabled()) {
                    throw new BadRequestException("账号未激活！");
                }
                jwtUserDto = new JwtUserDto(
                        user,
                        dataService.getDeptIds(user),
                        roleService.mapToGrantedAuthorities(user)
                );
                // 添加缓存数据
                userCacheManager.addUserCache(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }

    /**
     * token刷新前方法，system中主要是判断下用户是否在线
     *
     * @param token
     */
    @Override
    public Object resolveUserByToken(String token) {
        OnlineUserDto onlineUserDto = null;
        boolean cleanUserCache = false;
        try {
            String loginKey = tokenProvider.loginKey(token);
            onlineUserDto = onlineUserService.getOne(loginKey);
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            cleanUserCache = true;
        } finally {
            if (cleanUserCache || Objects.isNull(onlineUserDto)) {
                userCacheManager.cleanUserCache(String.valueOf(tokenProvider.getClaims(token).get(TokenProvider.AUTHORITIES_KEY)));
            }
        }
        return onlineUserDto;
    }
}
