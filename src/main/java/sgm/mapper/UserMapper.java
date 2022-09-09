package sgm.mapper;


import org.apache.ibatis.annotations.Param;
import sgm.entity.User;

import java.util.List;

public interface UserMapper {

    /**
     * 根据 username 模糊查询
     * @param username 账号
     * @return User 集合
     */
    List<User> getUserByUsername(@Param("username") String username);

    /**
     * 添加用户
     * @param user User实体对象
     * @return 影响行数
     */
    int insertUser(User user);

    int updateUser();
}
