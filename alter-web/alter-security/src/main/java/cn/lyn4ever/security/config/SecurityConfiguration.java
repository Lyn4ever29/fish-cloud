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
package cn.lyn4ever.security.config;

import cn.lyn4ever.common.utils.enums.RequestMethodEnum;
import cn.lyn4ever.security.annotation.AnonymousAccess;
import cn.lyn4ever.security.jwt.JwtAccessDeniedHandler;
import cn.lyn4ever.security.jwt.JwtAuthenticationEntryPoint;
import cn.lyn4ever.security.jwt.TokenConfigurer;
import cn.lyn4ever.security.jwt.TokenProvider;
import cn.lyn4ever.security.service.CloudUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author Zheng Jie
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true) //PreAuthorize
public class SecurityConfiguration {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final SecurityProperties properties;
    private final CloudUserDetailService cloudUserDetailService;


    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获取匿名标记
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
        // 获取匿名url配置
        String[] ignoreUrls = properties.getIgnoreUrls().toArray(String[]::new);

        http.authorizeHttpRequests(auth -> {
            auth// 静态资源等等
                    .requestMatchers(
                            HttpMethod.GET,
                            "/*.html",
                            "/**.html",
                            "/**.css",
                            "/**.js",
                            "/webSocket/**"
                    ).permitAll()
                    // swagger 文档
                    .requestMatchers("/swagger-ui.html").permitAll()
                    .requestMatchers("/swagger-resources/**").permitAll()
                    .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/doc.html").permitAll()
                    .requestMatchers("/*/api-docs/**").permitAll()
                    // 放行OPTIONS请求
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    //放行配置的url
                    .requestMatchers(ignoreUrls).permitAll()
                    // 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
                    // GET
                    .requestMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                    // POST
                    .requestMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                    // PUT
                    .requestMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll()
                    // PATCH
                    .requestMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll()
                    // DELETE
                    .requestMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll()
                    // 所有类型的接口都放行
                    .requestMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
                    // 其他所有请求都需要验证
                    .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        // 禁用 CSRF
        http.csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()
                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(securityConfigurerAdapter());
//                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
        return http.build();
    }


    private TokenConfigurer securityConfigurerAdapter() {
        return new TokenConfigurer(tokenProvider, properties, cloudUserDetailService);
    }

    private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<>(8);
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(requestMethods.isEmpty() ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
                if (null != infoEntry.getKey().getPatternsCondition()) {
                    switch (Objects.requireNonNull(request)) {
                        case GET:
                            get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case POST:
                            post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case PUT:
                            put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case PATCH:
                            patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        case DELETE:
                            delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                        default:
                            all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                            break;
                    }
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }
}
