# bbq-entry
微服务的基础框架搭建，基础组件封装 & 集成spring security权限框架。

记录学习过程

### 技术选型

* springboot 2+ 基础框架
* springsecurity 安全框架
* lombok (must require) 
* guava 谷歌工具库
* swagger2 接口文档工具
* mybatis ORM映射框架
* pagehelper (建议使用)
* ……

### 部分内容声明：

* 统一异常处理： `ExceptionAdviceHandler`
* 自定义登录、认证过滤器：`JwtLoginFilter` 、`JwtAuthenticationFilter`
* 自定义身份认证验证组件：`MyCustomAuthenticationProvider`

* 自定义JWT工具类：`JwtUtility` （based on `com.auth0(java-jwt)`）

* 注解方式的SQLProvider：`repository.sqlprovider.*`

* 接口返回体封装：`ResponseJson`

### 目录结构说明

```
├─com.micro.bbqentry
│    │ 
│    ├─config   	所有配置类 
│    │ 
│    ├─controller   控制器层，暴露Restful接口 
│    │ 
│    ├─general      通用公共部分
│    │    ├─common  		公共代码
│    │    ├─constant 		全局常量定义 
│    │    ├─exception		异常处理
│    │    └─utils			工具类 
│    │ 
│    ├─model    	数据模型层
│    │    ├─entity    		数据库实体映射，与表结构相对应 
│    │    ├─param      		参数，包括vo，dto，param等 
│    │    │    ├─xxxVO.java    		前端视图模型，通常在controller层使用
│    │    │    ├─xxxParam.java  	前端传入参数，通常在controller层使用
│    │    │    └─xxxDTO.java    	数据传输对象，通常在service层使用
│    │
│    ├─repository	数据访问层
│    │    ├─sqlprovider		基于mybatis注解方式实现sql拼接器
│    │    └─xxxMapper.java  mybatis数据访问接口定义
│    │
│    ├─security	  	安全框架相关内容
│    │ 
│    ├─service	  	服务层
│    │    ├─impl		 	服务的实现类
│    │    └─I(xxx)Service.java 
│    │
│    └─Application.java		程序入口
```

