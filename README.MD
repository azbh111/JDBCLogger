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

执行耗时49秒, 对应的日志
```
2021-09-18 15:48:51.344 LogHelper.log:19 - CustomerService:getCustomerById:selectById:103 cost 49 [select * from customer where id = 3]
```

## 特性

记录可执行的sql

记录sql执行时长

可以打印执行sql时, 所在的类名, 方法名, 和执行sql的方法名

支持原生jdbc

支持mybatis/hibernate等所有orm框架

接入时项目修改小

## 原理

通过javaagent来修改指定的jdbc驱动, 修改其中的connect方法, 返回代理后的 ConnectionWrapper

从而可以干预 connection.createStatement/connection.prepareStatement 流程

能方便的记录sql日志和执行时间

并读取堆栈信息, 分析出执行sql的位置

## 用法

1. 添加java启动参数

  -Xbootclasspath/p:jar绝对路径 -javaagent:jar绝对路径

2.1 将 System.out 重定向到slf4j 或者 自定义日志输出逻辑

```
  将 System.out 重定向到slf4j
  https://blog.csdn.net/qq_31683775/article/details/104940649 
```
或者
```java
public class Main {
    private static Logger logger = LoggerFactory.getLogger("sql");
    public static void main(String[] args) {
        // 自定义日志输出逻辑
        LogHelper.setLogger(logger);
    }
}
```
    
  