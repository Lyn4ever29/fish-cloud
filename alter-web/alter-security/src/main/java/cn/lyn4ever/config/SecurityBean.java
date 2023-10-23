package cn.lyn4ever.config;

import cn.lyn4ever.security.service.CloudUserDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/22
 */
@Configuration
public class SecurityBean {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    @ConditionalOnMissingBean(CloudUserDetailService.class)
    public CloudUserDetailService defaultCloudUserDetailService() {
        return new DefaultCloudUserDetailService();
    }
}
