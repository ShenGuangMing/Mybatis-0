package sgm.mapper;

import org.apache.ibatis.annotations.Param;
import sgm.entity.Employee;

public interface EmployeeMapper {

    /**
     * 获取员工以及员工部门
     * @param empId
     * @return
     */
    Employee getEmpAndDepOneStep(@Param("empId") Integer empId);

    /**
     * 获取部门所有员工分步第二步
     * @param deptId
     * @return
     */
    Employee getDeptAndEmpTwoStep(@Param("deptId") Integer deptId);
}
