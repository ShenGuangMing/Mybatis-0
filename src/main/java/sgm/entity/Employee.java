package sgm.entity;

import lombok.Data;

@Data
public class Employee {
    private Integer empId;
    private String empName;
    private String empGender;
    private String empAge;
    private Dept dept;

}
