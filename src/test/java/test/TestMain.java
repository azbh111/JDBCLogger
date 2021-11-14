package test;

import com.github.azbh111.jdbclogger.JdbcLoggerConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * @author: zyp
 * @since: 2021/11/14 15:58
 */
@Slf4j
@SpringBootApplication
@PropertySource("classpath:application.yaml")
@MapperScan("test.mapper")
public class TestMain {

    static {
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        JdbcLoggerConfig.loadConfig(TestMain.class.getClassLoader());
    }

    public static void main(String[] args) {
        SpringApplication.run(TestMain.class, args);
    }
}
