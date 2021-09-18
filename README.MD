# JDBCLogger

记录可执行的sql

记录sql执行时长

通过javaagent来修改要代理的jdbc驱动

项目修改小

可以打印执行sql时, 所在的类名, 方法名, 和执行sql的方法名

# 用法

1. 添加java启动参数

  -Xbootclasspath/p:jar绝对路径 -javaagent:jar绝对路径

2. 将 System.out 重定向到slf4j

  https://blog.csdn.net/qq_31683775/article/details/104940649 
