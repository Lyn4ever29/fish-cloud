package cn.lyn4ever.clients.admin;

import cn.lyn4ever.clients.admin.dto.JwtUserDto;
import cn.lyn4ever.clients.admin.dto.OnlineUserDto;
import cn.lyn4ever.clients.config.AuthorizationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * feign调用admin模块的方法
 */
@FeignClient(value = "system-admin", configuration = {AuthorizationRequestInterceptor.class})
public interface SysUserClientService {

    /**
     * 传入token信息，返回当前token是否可用
     *
     * @param token
     * @return
     */
    @GetMapping(value = "auth/resolveUserByToken")
    public OnlineUserDto resolveUserByToken(String token);

    /**
     * 传入用户名，返回用户信息
     *
     * @param username
     * @return
     */
    @GetMapping(value = "auth/loadUserByUsername")
    public JwtUserDto loadUserByUsername(String username);
}
