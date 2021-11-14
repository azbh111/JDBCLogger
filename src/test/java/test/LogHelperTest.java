package test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.mapper.UserMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: zyp
 * @since: 2021/10/21 21:37
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestMain.class)
public class LogHelperTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TestService testService;
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    private void init() {
        log.info("init");
    }

    @Test
    public void test() throws SQLException {
        log.info("init {}", dataSource);
        ping();
        createTable();
        selectAll();
        batchInsert();
        selectAll();
        executeBatch();
        executeBatchUpdate();
    }

    private void executeBatchUpdate() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.addBatch("update `user` set `name`= `id` where `id` = '1'");
        statement.addBatch("update `user` set `name`= `id` where `id` = '2'");
        statement.addBatch("update `user` set `name`= `id` where `id` = '3'");
        statement.executeBatch();
    }

    private void executeBatch() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.addBatch("insert into `user` (`id`) values ('1')");
        statement.addBatch("insert into `user` (`id`) values ('2')");
        statement.addBatch("insert into `user` (`id`) values ('3')");
        statement.executeBatch();
    }

    private void batchInsert() {
        List<User> users = new ArrayList<>();
        users = new ArrayList<>();
        users.add(createUser());
        users.add(createUser());
        testService.saveBatch(users);
    }


    private void selectAll() {
        List<User> users2 = userMapper.selectList(Wrappers.lambdaQuery(User.class));
        System.out.println(users2);
    }

    private void ping() throws SQLException {
        JSONArray objects = queryList("select 1");
        System.out.println(objects);
    }

    private void createTable() throws SQLException {
        getConnection().createStatement().execute("create table user (\n" +
                "    `id` varchar(16) not null,\n" +
                "    `name` varchar(16) null,\n" +
                "    `age` int null,\n" +
                "    primary key (`id`)\n" +
                ")\n");
    }

    private User createUser() {
        User user = new User();
        user.setId(String.valueOf(Math.abs(ThreadLocalRandom.current().nextInt())));
        user.setName("");
        user.setAge(0);
        return user;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private JSONArray queryList(String sql, Object... args) throws SQLException {
        return queryList(sql, args == null ? new ArrayList<>(0) : Arrays.asList(args));
    }

    private JSONArray queryList(String sql, List args) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (args != null && !args.isEmpty()) {
                    int size = args.size();
                    for (int i = 0; i < size; i++) {
                        preparedStatement.setObject(i + 1, args.get(i));
                    }
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return extractList(resultSet);
                }
            }
        }
    }

    private JSONArray extractList(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        JSONArray jsonArray = new JSONArray();
        while (rs.next()) {
            JSONObject object = new JSONObject();
            for (int i = 0; i < columnCount; i++) {
                String name = meta.getColumnName(i + 1);
                Object v = rs.getObject(i + 1);
                object.put(name, v);
            }
            jsonArray.add(object);
        }
        return jsonArray;
    }
}