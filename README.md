# springComing
尝试以登录模块功能为例，尝试各种后端技术

记录整个项目过程中使用的技术和相关问题的思考

- SpringBoot的启动过程
- BeanFactory和ApplicationContext的理解
   - SpringBoot如何根据application.yml加载预定义的配置类和自定义的配置类
- Springboot如何获得ApplicaiontContext实例
   - 我们怎么通过SpringBoot提供的接口，修改默认的配置类
- SpringBoot如何加载自定义的数据源
   - 如何用自定义的配置类代替SpringBoot提供的默认配置类
      - spring.datasource.url与spring.datasource.jdbc-url的区别
   - 多数据源实现（数据库的读写分离）
      - @Pointcut 的使用
- SpringBoot对Mybatis的整合
   - 提供了怎么样的支持（Mybatis的xml配置文件和Application.yml文件合并在了一起）
- SpringBoot对Mybatis的测试支持
- Ajax的使用
- 如何保证密码传输的安全
   - 网络协议如何支持
   - Springboot security提供何种保护
  
