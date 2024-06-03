# 说明

本项目后端采取 Spring Boot + MyBatis Plus + Lombok 进行开发，前端使用 vue3

前后端分离，通信均采用 json 格式

为了便于协作，采用 apifox 对 api 进行具体记录，大家可以加入团队 online-banking-system https://app.apifox.com/invite?token=sDvWHbIYAOXNu8HzsfqDp

# 目录说明

`resources/application.yml` 为项目的基本配置，具体内容可见参数名。

`resources/db/init.sql` 为 初始化schema 的 sql 命令，`data-init.sql` 为 插入初始数据的命令

源代码主要分成三层，controller 直接处理 api，mapper 作为数据库操作的封装，entity 里为操作的实体，例如 user 等，

service 是 controller 和 mapper 之间的层，各模块的函数，主要是避免裸的数据库操作以及便于模块间互相调用。

文件夹内有占位的空 java 文件可忽视

# 补充

后端的返回格式统一使用 `utils/RespResult` 的格式，payload 中为返回值，err 为错误信息，code 为错误代码