package cn.lyn4ever.example.rest;

import cn.lyn4ever.security.annotation.AnonymousAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "测试接口")
public class ExampleHelloController {
    @GetMapping(value = "/echo/{text}")
    @AnonymousAccess // 接口放行
    @Operation(summary = "普通body请求")
    @Parameters({
            @Parameter(name = "text",description = "输出内容",in = ParameterIn.PATH),
    })
    public String echoText(@PathVariable("text") String text) {
        return "欢迎使用Fish-Cloud项目：" + text;
    }
}
