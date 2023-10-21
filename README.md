<h1 style="text-align: center">Fish-Cloud 后台管理系统</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/elunez/eladmin/blob/master/LICENSE)

</div>

## 项目简介 ##

一个基于 Spring Boot 2.6.13 、 Spring Boot Jpa、 JWT、Spring Security、Redis、Vue的前后端分离的后台管理系统

项目来源于ELADMIN,作者将其改造成SpringCloud项目，原项目地址：[https://github.com/elunez/eladmin-mp](https://github.com/elunez/eladmin-mp)、[https://gitee.com/elunez/eladmin-mp](https://gitee.com/elunez/eladmin-mp)。

**开发文档：**  [https://cloud.jhacker.cn](https://cloud.jhacker.cn)

**体验地址：**  [https://cloud.jhacker.cn/demo](cloud.jhacker.cn/demo)

**账号密码：** `admin / 123456`

## 项目源码 ## 

|        | 后端源码                              | 前端源码                                  |
|--------|-----------------------------------|---------------------------------------|
| github | https://github.com/elunez/eladmin | https://github.com/elunez/eladmin-web |
| 码云     | https://gitee.com/elunez/eladmin  | https://gitee.com/elunez/eladmin-web  |

## 项目组件 ## 

#### SpringCloud

#### Nacos

#### Setinal

#### Gateway

## 主要特性 ##

- 使用最新技术栈，社区资源丰富。
- 高效率开发，代码生成器可一键生成前后端代码
- 支持数据字典，可方便地对一些状态进行管理
- 支持接口限流，避免恶意请求导致服务层压力过大
- 支持接口级别的功能权限与数据权限，可自定义操作
- 自定义权限注解与匿名接口注解，可快速对接口拦截与放行
- 对一些常用地前端组件封装：表格数据请求、数据字典等
- 前后端统一异常拦截处理，统一输出异常，避免繁琐的判断
- 支持在线用户管理与服务器性能监控，支持限制单用户登录
- 支持运维管理，可方便地对远程服务器的应用进行部署与管理

## 系统功能 ##

- 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
- 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
- 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
- 部门管理：可配置系统组织架构，树形表格展示
- 岗位管理：配置各个部门的职位
- 字典管理：可维护常用一些固定的数据，如：状态，性别等
- 系统日志：记录用户操作日志与异常日志，方便开发人员定位排错
- SQL监控：采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 定时任务：整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
- 代码生成：高灵活度生成前后端代码，减少大量重复的工作任务
- 邮件工具：配合富文本，发送html格式的邮件
- 七牛云存储：可同步七牛云存储的数据到系统，无需登录七牛云直接操作云数据
- 支付宝支付：整合了支付宝支付并且提供了测试账号，可自行测试
- 服务监控：监控服务器的负载情况
- 运维管理：一键部署你的应用

## 项目结构 ##

项目采用按功能分模块的开发方式，结构如下

- `fish-common` 为系统的公共模块，各种工具类，公共配置存在该模块

- `fish-system` 为系统核心模块也是项目入口模块，也是最终需要打包部署的模块

- `eladmin-logging` 为系统的日志模块，其他模块如果需要记录日志需要引入该模块

- `fish-tools` 为第三方工具模块，包含：邮件、七牛云存储、本地存储、支付宝

- `fish-generator` 为系统的代码生成模块，支持生成前后端CRUD代码

## 详细结构 ##

```
- fish-common 公共模块
    - annotation 为系统自定义注解
    - aspect 自定义注解的切面
    - base 提供了Entity、DTO基类和mapstruct的通用mapper
    - config 自定义权限实现、redis配置、swagger配置、Rsa配置等
    - exception 项目统一异常的处理
    - utils 系统通用工具类
- fish-system 系统核心模块（系统启动入口）
	- config 配置跨域与静态资源，与数据权限
	    - thread 线程池相关
	- modules 系统相关模块(登录授权、系统监控、定时任务、运维管理等)
- eladmin-logging 系统日志模块
- fish-tools 系统第三方工具模块
- fish-generator 系统代码生成模块
```

## 特别鸣谢 ##

- 再次感谢原作者[elunez](https://github.com/elunez/)提供了优秀的开源框架
- 感谢 [PanJiaChen](https://github.com/PanJiaChen/vue-element-admin) 大佬提供的前端模板

- 感谢 [Moxun](https://github.com/moxun1639) 大佬提供的前端 Curd 通用组件

- 感谢 [zhy6599](https://gitee.com/zhy6599) 大佬提供的后端运维管理相关功能

- 感谢 [j.yao.SUSE](https://github.com/everhopingandwaiting) 大佬提供的匿名接口与Redis限流等功能

- 感谢 [d15801543974](https://github.com/d15801543974) 大佬提供的基于注解的通用查询方式

## 开源说明 ##

作者在开发时，对原作者的代码修改主要有如下几点：、

1. 本项目遵守[LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)协议
2. 尽极大可能的保留作者在源码中的版权信息，包括但不限于**类文件及其他文件**、**接口文档**中的版权说明、
3. 原项目中的大多数功能保持了一致，并未作过多修改
4. 出于对项目统一配置与管理，将原作者的报名进行了修改，由**me.zhengjie**修改为**cn.lyn4ever**
