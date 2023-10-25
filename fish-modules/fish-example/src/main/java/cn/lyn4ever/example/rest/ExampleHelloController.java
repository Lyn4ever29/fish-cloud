package cn.lyn4ever.example.rest;

import cn.lyn4ever.security.annotation.AnonymousAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/25
 */
@RestController
@RequestMapping("/test")
public class ExampleHelloController {
    @GetMapping(value = "/echo/{text}")
    @AnonymousAccess // 接口放行
    public String echoText(@PathVariable("text") String text) {
        return "欢迎使用Fish-Cloud项目：" + text;
    }
}
