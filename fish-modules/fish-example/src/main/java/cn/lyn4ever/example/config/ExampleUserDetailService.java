package cn.lyn4ever.example.config;

import cn.lyn4ever.clients.admin.SysUserClientService;
import cn.lyn4ever.security.service.CloudUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/25
 */
@Component
@RequiredArgsConstructor
public class ExampleUserDetailService implements CloudUserDetailService {

    private final SysUserClientService sysUserClientService;

    /**
     * 从token中解析用户信息
     * 这里直接返回一个new Object() 值。
     * @param token
     */
    @Override
    public Object resolveUserByToken(String token) {
//        return sysUserClientService.resolveUserByToken(token);
        return new Object();
    }

    /**
     * 根据用户名获取用户信息
     * 远程调用system-admin模块获取内容
     * @param username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = sysUserClientService.loadUserByUsername(username);
        return userDetails;
    }
}
