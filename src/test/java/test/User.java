package test;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: zyp
 * @since: 2021/11/14 16:21
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("user")
public class User implements Serializable {
    @TableId
    private String id;
    private String name;
    private Integer age;
}
