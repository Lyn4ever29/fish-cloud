package cn.lyn4ever.clients.log.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign中的请求拦截器
 * 主要用来拦截 Authorization
 *
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/22
 */
@Configuration
public class AuthorizationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(tokenHeader)) {
            tokenHeader = (String) request.getAttribute("loginToken");
        }
        requestTemplate.header(HttpHeaders.AUTHORIZATION, tokenHeader);
    }
}
