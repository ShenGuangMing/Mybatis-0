import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import sgm.entity.Employee;
import sgm.mapper.DeptMapper;
import sgm.util.SqlSessionFactoryUtil;

import java.util.List;

public class TestDeptCURD {
    @Test
    public void TestGetDeptAndEmp() {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession(true);
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        System.out.println(mapper.getDeptAndEmpOneStep(1));
    }
}
