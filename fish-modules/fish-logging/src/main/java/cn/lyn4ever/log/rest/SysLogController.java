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
package cn.lyn4ever.log.rest;

import cn.lyn4ever.common.annotation.Log;
import cn.lyn4ever.jpa.config.PageResult;
import cn.lyn4ever.log.domain.SysLog;
import cn.lyn4ever.log.service.SysLogService;
import cn.lyn4ever.log.service.dto.SysLogQueryCriteria;
import cn.lyn4ever.log.service.dto.SysLogSmallDto;
import cn.lyn4ever.security.annotation.AnonymousAccess;
import cn.lyn4ever.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Tag(name = "系统：日志管理")
public class SysLogController {

    private final SysLogService sysLogService;

    @Log("导出数据")
    @Operation(description = "导出数据")
    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check()")
    public void exportLog(HttpServletResponse response, SysLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("INFO");
        sysLogService.download(sysLogService.queryAll(criteria), response);
    }

    @Log("导出错误数据")
   @Operation(description ="导出错误数据")
    @GetMapping(value = "/error/download")
//    @PreAuthorize("@el.check()")
    public void exportErrorLog(HttpServletResponse response, SysLogQueryCriteria criteria) throws IOException {
        criteria.setLogType("ERROR");
        sysLogService.download(sysLogService.queryAll(criteria), response);
    }

    @GetMapping
   @Operation(description ="日志查询")
//    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryLog(SysLogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        return new ResponseEntity<>(sysLogService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
//   @Operation(description ="用户日志查询")
    public ResponseEntity<PageResult<SysLogSmallDto>> queryUserLog(SysLogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        criteria.setUsername(SecurityUtils.getCurrentUsername());
        return new ResponseEntity<>(sysLogService.queryAllByUser(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
   @Operation(description ="错误日志查询")
//    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLog(SysLogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("ERROR");
        return new ResponseEntity<>(sysLogService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
   @Operation(description ="日志异常详情查询")
//    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> queryErrorLogDetail(@PathVariable Long id) {
        return new ResponseEntity<>(sysLogService.findByErrDetail(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
   @Operation(description ="删除所有ERROR日志")
//    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllErrorLog() {
        sysLogService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
   @Operation(description ="删除所有INFO日志")
//    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delAllInfoLog() {
        sysLogService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/saveLog")
//    @Log("保存日志")
   @Operation(description ="保存日志")
    @AnonymousAccess
    public void saveLog(@RequestBody SysLog sysLog) {
        sysLogService.saveLog(sysLog);
    }

}
