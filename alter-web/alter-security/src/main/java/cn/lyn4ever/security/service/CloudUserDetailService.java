package cn.lyn4ever.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 接口继承Security的UserDetailService
 * 并添加方法
 */
public interface CloudUserDetailService extends UserDetailsService {


    /**
     * 从token中解析用户信息
     *
     * @param token
     */
    Object resolveUserByToken(String token);


}
