package sgm.entity;

import lombok.Data;

import java.util.List;

@Data
public class Dept {
    private Integer deptId;
    private String deptName;
    private Integer deptNum;
    private List<Employee> employees;
}
