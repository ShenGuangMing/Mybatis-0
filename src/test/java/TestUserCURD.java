import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import sgm.entity.User;
import sgm.mapper.UserMapper;
import sgm.util.SqlSessionFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class TestUserCURD {
    @Test
    public void testInsertUSer() throws IOException {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setGender("男");
        user.setAge(18);
        user.setEmail("test@example.com");
        int i = mapper.insertUser(user);
        System.out.println("Inserted user " + i);
    }

    @Test
    public void testUpdateUser() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        int i = sqlSession.getMapper(UserMapper.class).updateUser();
        log.info("Update user success row: " + i);
    }


    @Test
    public void testGetUserByUsername() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        List<User> list = sqlSession.getMapper(UserMapper.class).getUserByUsername("%s%");
        list.forEach(System.out::println);

    }


}
