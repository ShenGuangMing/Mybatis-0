import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import sgm.entity.Employee;
import sgm.mapper.EmployeeMapper;
import sgm.util.SqlSessionFactoryUtil;

public class TestEmployee {
    @Test
    public void testGetEmpAndDept() {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession(true);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        System.out.println(mapper.getEmpAndDepOneStep(1));
    }
}
