package cn.lyn4ever.example1.rest;

import cn.lyn4ever.security.annotation.AnonymousAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Lyn4ever29
 * @url https://jhacker.cn
 * @date 2023/10/25
 */
@RestController
@RequestMapping("/test")
@Tag(name = "测试接口")
public class FasController {
    @Operation(summary = "普通body请求+Param+Header+Path")
    @Parameters({
            @Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
            @Parameter(name = "name",description = "文件名称",required = true,in=ParameterIn.QUERY)
    })
    @AnonymousAccess
    @PostMapping("/bodyParamHeaderPath/{id}")
    public ResponseEntity<Object> bodyParamHeaderPath(@PathVariable("id") String id,
                                                      @RequestHeader("token") String token,
                                                      @RequestParam("name")String name,
                                                      @RequestBody Map<String,String> map){
        map.put("id", id);
        map.put("token", token);
        map.put("name",name);
        return ResponseEntity.ok(map);
    }
}
