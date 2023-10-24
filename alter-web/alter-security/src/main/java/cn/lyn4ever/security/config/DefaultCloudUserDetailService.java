package cn.lyn4ever.security.config;

import cn.lyn4ever.security.service.CloudUserDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 没有配置CloudUserDetailService时，使用默认的CloudUserDetailService
 *
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/22
 */
public class DefaultCloudUserDetailService implements CloudUserDetailService {
    /**
     * 从token中解析用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Object resolveUserByToken(String token) {
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
        return null;
    }
}
