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
package cn.lyn4ever.rest;

import cn.lyn4ever.domain.GenConfig;
import cn.lyn4ever.service.GenConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genConfig")
@Tag(name = "系统：代码生成器配置管理")
public class GenConfigController {

    private final GenConfigService genConfigService;

     @Operation(summary = "查询")
    @GetMapping(value = "/{tableName}")
    public ResponseEntity<GenConfig> queryGenConfig(@PathVariable String tableName) {
        return new ResponseEntity<>(genConfigService.find(tableName), HttpStatus.OK);
    }

    @PutMapping
     @Operation(summary = "修改")
    public ResponseEntity<Object> updateGenConfig(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity<>(genConfigService.update(genConfig.getTableName(), genConfig), HttpStatus.OK);
    }
}
