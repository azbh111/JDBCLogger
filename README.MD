# JDBCLogger

## 记录的日志例子

java类
```java
@Service
public class CustomerService {
    
    @Resource
    private CustoemrMapper customerMapper;
   
    public Customer getCustomerById(Long id) {
        return customerMapper.selectById(id); // 假设这里是103行
    }
}
```

执行耗时49毫秒, 对应的日志
```
2021-09-18 15:48:51.344 SqlLog.log:19 - CustomerService:getCustomerById:selectById:103 cost 49 select `id`, `name`, `sex`, `age` from customer where id = 3
2021-09-18 15:48:51.344 SqlLog.log:19 - CustomerService:getCustomerById:selectById:103 cost 49 select `id`, `name`, `sex`, `age` from customer where id = 3
2021-09-18 15:48:51.344 SqlLog.log:19 - CustomerService:getCustomerById:selectById:103 cost 49 select `id`, `name`, `sex`, `age` from customer where id = 3
```

## 特性

记录可执行的sql

记录sql执行时长

可以打印执行sql时, 所在的类名, 方法名, 和执行sql的方法名

支持原生jdbc

支持mybatis/hibernate等所有orm框架

对业务无侵入

## 原理

通过javaagent来代理指定的jdbc驱动, 增强connect方法

从而可以干预 connection.createStatement/connection.prepareStatement 流程

能方便的记录sql日志和执行时间

并读取堆栈信息, 分析出执行sql的位置

## 用法

1.1 将 System.out 重定向到日志框架, 比如slf4j 

参考 https://blog.csdn.net/qq_31683775/article/details/104940649

1.2 或者 自定义日志输出逻辑
```java
public class Main {
    private static final Logger logger = Logger.getLogger("sql");

    public static void main(String[] args) {
//        自定义日志输出逻辑
        SqlLog.setLogger(str -> logger.info(str));
    }
}
```

2. 配置文件 jdbclogger.properties 放在classPath下
```properties
# 修改成自己项目的配置
# 要代理的jdbc驱动类, 多个用逗号分隔
jdbclogger.proxy.driverClass=com.mysql.cj.jdbc.Driver
# 项目包路径前缀, 多个用逗号分隔
jdbclogger.project.packagePrefixs=com.github.azbh111
# 非项目包路径前缀, 多个用逗号分隔, 用来排除 jdbclogger.project.packagePrefixs 中的某些包路径 
jdbclogger.project.packageExecludes=com.github.azbh111.common
```

3. 从maven中央仓库中下载JDBCLogger.jar
https://repo1.maven.org/maven2/com/github/azbh111/JDBCLogger/0.0.4/JDBCLogger-0.0.4.jar

4. 添加java启动参数

  -Xbootclasspath/p:JDBCLogger.jar的绝对路径 -javaagent:JDBCLogger.jar的绝对路径
