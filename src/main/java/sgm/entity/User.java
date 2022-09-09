package sgm.entity;

import lombok.Data;

// @Data: 自动构建 getter, setter 和 toString 方法
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private String gender;
    private String email;
}
