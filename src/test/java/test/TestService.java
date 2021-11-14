package test;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.mapper.UserMapper;

/**
 * @author: zyp
 * @since: 2021/11/14 16:20
 */
@Slf4j
@Service
public class TestService extends ServiceImpl<UserMapper, User> {

}
