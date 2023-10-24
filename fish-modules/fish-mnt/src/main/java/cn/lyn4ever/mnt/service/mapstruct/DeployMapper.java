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
package cn.lyn4ever.mnt.service.mapstruct;

import cn.lyn4ever.mnt.domain.Deploy;
import cn.lyn4ever.mnt.service.dto.DeployDto;
import cn.lyn4ever.mvc.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Mapper(componentModel = "spring", uses = {AppMapper.class, ServerDeployMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeployMapper extends BaseMapper<DeployDto, Deploy> {

}
