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
package cn.lyn4ever.mnt.service.impl;

import cn.lyn4ever.jpa.config.PageResult;
import cn.lyn4ever.jpa.config.PageUtil;
import cn.lyn4ever.jpa.config.QueryHelp;
import cn.lyn4ever.mnt.domain.App;
import cn.lyn4ever.mnt.repository.AppRepository;
import cn.lyn4ever.mnt.service.AppService;
import cn.lyn4ever.mnt.service.dto.AppDto;
import cn.lyn4ever.mnt.service.dto.AppQueryCriteria;
import cn.lyn4ever.mnt.service.mapstruct.AppMapper;
import cn.lyn4ever.mvc.exception.BadRequestException;
import cn.lyn4ever.mvc.utils.FileUtil;
import cn.lyn4ever.mvc.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;
    @Resource
    private AppMapper appMapper;

    @Override
    public PageResult<AppDto> queryAll(AppQueryCriteria criteria, Pageable pageable) {
        Page<App> page = appRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(appMapper::toDto));
    }

    @Override
    public List<AppDto> queryAll(AppQueryCriteria criteria) {
        return appMapper.toDto(appRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public AppDto findById(Long id) {
        App app = appRepository.findById(id).orElseGet(App::new);
        ValidationUtil.isNull(app.getId(), "App", "id", id);
        return appMapper.toDto(app);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(App resources) {
        verification(resources);
        appRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(App resources) {
        verification(resources);
        App app = appRepository.findById(resources.getId()).orElseGet(App::new);
        ValidationUtil.isNull(app.getId(), "App", "id", resources.getId());
        app.copy(resources);
        appRepository.save(app);
    }

    private void verification(App resources) {
        String opt = "/opt";
        String home = "/home";
        if (!(resources.getUploadPath().startsWith(opt) || resources.getUploadPath().startsWith(home))) {
            throw new BadRequestException("文件只能上传在opt目录或者home目录 ");
        }
        if (!(resources.getDeployPath().startsWith(opt) || resources.getDeployPath().startsWith(home))) {
            throw new BadRequestException("文件只能部署在opt目录或者home目录 ");
        }
        if (!(resources.getBackupPath().startsWith(opt) || resources.getBackupPath().startsWith(home))) {
            throw new BadRequestException("文件只能备份在opt目录或者home目录 ");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            appRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AppDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AppDto appDto : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("应用名称", appDto.getName());
            map.put("端口", appDto.getPort());
            map.put("上传目录", appDto.getUploadPath());
            map.put("部署目录", appDto.getDeployPath());
            map.put("备份目录", appDto.getBackupPath());
            map.put("启动脚本", appDto.getStartScript());
            map.put("部署脚本", appDto.getDeployScript());
            map.put("创建日期", appDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
