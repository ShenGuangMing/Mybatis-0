package sgm.mapper;

import org.apache.ibatis.annotations.Param;
import sgm.entity.Dept;
import sgm.entity.Employee;

import java.util.List;

public interface DeptMapper {

    /**
     * 通过分步查询查询员工以及员工所对应的部门信息
     * 分步查询第二步:通过did查询员工所对应的部门
     * @param depId
     * @return
     */
    Dept getEmpAndDeptTwoStep(@Param("depId") Integer depId);

    /**
     * 通过分步查询查部门的所有员工信息
     * 分布查询第一步: 通过 deptId 查询部门
     * @param depId
     * @return
     */
    List<Employee> getDeptAndEmpOneStep(@Param("depId") Integer depId);
}
