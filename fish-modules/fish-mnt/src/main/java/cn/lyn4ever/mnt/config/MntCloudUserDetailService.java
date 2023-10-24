package cn.lyn4ever.mnt.config;

import cn.lyn4ever.clients.admin.SysUserClientService;
import cn.lyn4ever.clients.admin.dto.JwtUserDto;
import cn.lyn4ever.security.service.CloudUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/23
 */
@Component
@RequiredArgsConstructor
public class MntCloudUserDetailService implements CloudUserDetailService {
    private final SysUserClientService sysUserClientService;

    /**
     * 从token中解析用户信息
     *
     * @param token
     */
    @Override
    public Object resolveUserByToken(String token) {
//        return sysUserClientService.resolveUserByToken(jwt);
        return new Object();
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDto userDetails = sysUserClientService.loadUserByUsername(username);
//        JwtUserDto
        return userDetails;

    }
}
