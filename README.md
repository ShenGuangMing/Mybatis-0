# 一. Mybatis 和 Hibernate区别

---

## 1. Hibernate: 自动全映射 ORM(Object Relation Mapping) 框架

---

- ### 优点

  - 使用简单方便
  - 不用自己写 SQL 语句

- ### 缺点

  - SQL 失去灵活性, 不容易进行调优
  - 如果需要自己写 SQL 还需要学习 HQL 增加了学习的成本





## 2. Mybatis: 是一个半自动的持久层的框架

---

- ### 优点

  - Sql 与 Java 编码分离, 避免了几乎所有的 JDBC 代码和手动配置参数以及获取结果集
  - Sql 是开发人员控制
  - 只需要学好 SQL
  - 轻量级的框架
  - 可以使用简单的 XML 和注解用于配置和原始映射, 将接口和Java中的 POJO (Plain Old Java Object, 普通的Java对象) 映射成数据库中的记录



# 二. 环境搭建 Mybatis

---

## 1. 开发环境

---

IDE: IDEA

构建工具: maven

Mysqlb版本: Mysql 5.7.10

Mybatis版本: Mybatis 3.5.7



## 2.创建maven工程

---

### a.打包方式:jar

### b.引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>pro1-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <!--Mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.7</version>
        </dependency>

        <!--junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <!--mysql 驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.27</version>
        </dependency>

        <!--插件-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.4</version>
            <scope>provided</scope>
        </dependency>

        <!--日志-->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.7</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.7</version>
        </dependency>
    </dependencies>
</project>
```

## 3.mybatis的配置文件(建议命名: mysql-config.xml,文件在 resources 下)

---

```XML
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--连接数据库的环境配置-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql//localhost:3306/mybatis_study"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--映入映射的文件-->
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
</configuration>
```

## 4.创建mapper接口

---

> Mybatis的Mapper接口相当于以前的dao, 但区别在于, mapper仅仅是接口, 我们不需要提供实现类

```java
public interface UserMapper {
    
    /** 添加用户
     * User 添加的实体对象
     */
    int insertUser(); 
    
}
```

## 5.创建实体类

---

```java
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

```

## 6.创建 Mybatis 的映射文件

---

相关概念: ORM (Object Relationship Mapper) 对象关系映射

- 对象: Java 的实体对象
- 关系: 关系型数据库
- 映射: 二者之间的对应关系

| Java概念 | 数据库概念 |
| :------: | :--------: |
|    类    |     表     |
|   属性   |  字段/列   |
|   对象   |  记录/行   |

> 1.映射文件的命名规则:
>
> 表所对应的实体类的类名+Mapper.xml
>
> 例如:表t_user，映射的实体类为User，
>
> 所对应的映射文件为UserMapper.xml因此一个映射文件对应一个实体类。对应一张表的操作
> MyBatis映射文件用于编写SQL，访问以及操作表中的数据
> MyBatis映射文件存放的位置是src/main/resources/mappers目录下
>
> 2、MyBatis中可以面向接口操作数据，要保证两个—致:
>
> - 映射文件的 namespace 要和 mapper 接口的全类名一致
> - 映射文件中的 SQL 语句的 id 和 mapper 接口中方法名一致
>
> 

``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="sgm.mapper.UserMapper">

    <insert id="insertUser">
        insert into user
        values (null, 'sgm', '123456', '男', 10, '123@qq.com')
    </insert>
</mapper>
```

> <insert> 的 id 要和对应接口中的方法名要相同, 其他语句也是一样<update>...

## 7.结构预览

---

src

- main
  - java
    - sgm (我自己名字的首字母缩写)
      - entity: 实体类包
        - User: 实体类
        - ...
      - mapper: mapper包
        - UserMapper: UserMapper接口
        - ...
  - resources
    - mappers(文件目录)
      - UserMapper.xml
    - mybatis-config.xml: Mybatis 配置文件

- test
  - java
  - resources

## 8.通过 junit 测试功能

---

``` java
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import sgm.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestInsertUSer {
    @Test
    public void testInsertUSer() throws IOException {
        //读取mybatis核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        //创建 SqlSessionFactoryBuilder 对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //通过核心配置文件对应的输入流创建工厂类 SqlSessionFactory, 生产 SqlSession 对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        //创建 SqlSessionFactory 对象, 此时通过 SqlSession 对象所操作的 SQL 必须手动提交事务或回滚事务
        //通过工厂获取对象
        //给定参数 true, SqlSession 对象的 SQL 会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //通过代理模式创建 UserMapper 接口的代理实现类
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.insertUser();
        System.out.println("Insert user success row: " + i);
    }
}

```

> - SqlSession: 代表 Java 程序与数据库之间的会话.(HttpSession 是 Java 程序和浏览器之间的会话)
>
> - SqlSessionFactory: 是生产 'SqlSession'的工厂
> - 工程模式: 如果创建某个对象, 使用的过程基本固定, 那么我们就可以把创建这个对象的相关代码封装到一个 ‘工厂类’ 中, 以后都是用我们工厂类 '生产' 我们需要的对象

# 三. 了解 mybatis 的核心配置文件

## 1.配置文件中的标签顺序

```XML
<!-- mybatis核心配置文件中的标签顺序如下
The content of element type "configuration" must match "(properties?,settings?,typeAliases?,
typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,
databaseIdProvider?,mappers?)".-->
```

## 2.标签作用与认识(一部分即可)

- properties: 当需要引入属性文件时配置, 如数据库配置文件

  ```XML
  <properties resource="datasource.properties" />
  ```

- typeAliases: 设置类型别名

  - typeAlias: 配置具体类的别名

    ```xml
    <typeAliases>
        <typeAlias type="sgm.entity.User" alias="User"/>
    </typeAliases>
    ```

  - package: 配置实体类所在包名

    ```xml
    <!--设置类型别名-->
    <typeAliases>
        <!--配置实体类所在包-->
        <package name="smg.entity"/>
    </typeAliases>
    ```

- environments: 配置数据库环境的



- mappers: 配置映入映射的文件
  - <mapper resource="mappers/UserMapper.xml"/>: 指明具体的mapper映射
  
  - <package name="sgm.mapper"/>: 设置mapper包依包为单位引入映射文件
  

# 四. mapper接口方法的参数(这里只介绍我推荐的方式, 其他的方式就不讲了)

---

## 1.如果参数是一个或多个基本数据包装类(String, Integer, Double, ...), 建议使用 @Param 注解

1. UserMapper 接口 getUserByUsername 方法

```java

    /**
     * 根据 username 模糊查询
     * @param username 账号
     * @return User 集合
     */
    List<User> getUserByUsername(@Param("username") String username);

```

2. UserMapper.xml 映射文件 <select>

```xml
<select id="getUserByUsername" resultType="sgm.entity.User">
    select *
    from user where username like #{username};
</select>
```

> @Param 注解可以给value赋值为: ‘name’, 在映射文件中我们可以使用 #{'name'}, 使用我们配置的 'name'
>
> value的值可以和形参变量名不同, 自定义

## 2.当参数是普通的Java实体类(POJO)

---

1. UserMapper 接口

   ```java
       /**
        * 添加用户
        * @param user User实体对象
        * @return 影响行数
        */
       int insertUser(User user);
   ```

2. UserMapper.xml 映射文件 <insert>

   ```xml
   <insert id="insertUser">
       insert into user values (null, #{username}, #{password}, #{gender}, #{age}, #{email})
   </insert>
   ```

> #{‘name’} 中的 'name' 要和 POJO 中的属性名相同

# 五. Mapper映射文件中的占位符 #{} 与 ${} 的简单区别, 详细可以看官方文档(github上应该有中文文档, 目前我们只需要会使用就行, 底层的现在可以不需要理解, 因为以后是要看源码的)

- ## #{}: 我们可能使用的会多点

  >可以避免 SQL 注入, 会将特殊符号经过转移字符的处理, 来避免 SQL 注入

- ## ${}

  > 底层是在进行字符串的拼接, 存在 SQL 注入的风险, 所以我们使用的会偏少一些

# 六. 表中字段名与POJO中的属性名不一致时

---

## 1. 命名规则

---

| SQL字段名  | POJO属性名 | 中文意思 |
| :--------: | :--------: | :------: |
| book_name  |  bookName  |   书名   |
| book_price | bookPrice  |   书价   |
|    ...     |    ...     |   ...    |

>当我们返回值是 POJO, List<POJO> 或 Map<String, object> 时, 并且我们的 SQL 中的字段名和 POJO 中的属性名不一致就会出现匹配不一致, Mybatis就会报错



## 2.采用的解决方案

---

- 在 mybtis 核心配置文件中配置<settings>

  ```xml
      <settings>
          <!--将下划线命名映射到驼峰命名-->
          <setting name="mapUnderscoreToCamelCase" value="true"/>
      </settings>
  ```

- 在对应的 Mapper 映射文件中配置 resultMap, 表中字段对应的 POJO 属性

  ```xml
     <resultMap id="bookResultMap" type="Book">
          <result column="book_name" property="bookName"/>
          <result column="book_price" property="bookPrice"/>
          <result column="book_id" property="bookId"/>
      </resultMap>
  ```
  
- 查询POJO和List<POJO> select语句

  ```xml
  	<!--查询所有书本-->
      <select id="getAllBooks" resultMap="bookResultMap">
          select *
          from book;
      </select>
  
      <!--根据id查询-->
      <select id="getBookByBookId" resultMap="bookResultMap">
          select *
          from book
          where book_id = #{bookId}
          limit 1;
      </select>
  ```


> - <resultMap>
>   - id: 唯一表示, 查询语句返回的结果集 resultMap 赋值为对应的 id 就使用对应的匹配结果集
>   - tyoe: POJO 类型

# 七. 解决多对一映射关系

---



## 1. 出现场景

两表关系 

employee 的部门id 与部门有关系

|       employee       |        dept         |
| :------------------: | :-----------------: |
|   dept_id(部门id)    |   dept_id(部门id)   |
|    emp_id(员工id)    | dep_name(部门名i在) |
|  emp_name(员工名字)  | dep_num(部门员工数) |
|  emp_age(员工年龄)   |         ...         |
| emp_gender(员工性别) |                     |
|         ...          |                     |

两 POJO

|  (类型) Employee   |    (类型)Dept    |
| :----------------: | :--------------: |
|  (Inteder) empId   | (Integer) deptId |
|  (String) empName  | (String) depName |
|  (Integer) empAge  | (Inteder) depNum |
| (String) empGender |                  |
|     (Dept) dep     |                  |



## 2.解决方案

- ### 1. 使用级联属性的方式

  ```xml
  <resultMap id="empResultMap" type="Employee">
      <result column="emp_id" property="empId"/>
      <result column="emp_name" property="empName"/>
      <result column="emp_age" property="empAge"/>
      <result column="emp_gender" property="empGender"/>
      <result column="dept_id" property="dept.deptId"/>
      <result column="dept_name" property="dept.deptName"/>
      <result column="dept_num" property="dept.deptNum"/>
  </resultMap>
  ```

- ### 2.使用 <association>

  ```xml
  <resultMap id="empResultMap" type="Employee">
      <result column="emp_id" property="empId"/>
      <result column="emp_name" property="empName"/>
      <result column="emp_age" property="empAge"/>
      <result column="emp_gender" property="empGender"/>
      <association property="dept" javaType="Dept">
          <result column="dept_id" property="deptId"/>
          <result column="dept_name" property="deptName"/>
          <result column="dept_num" property="deptNum"/>
      </association>
  </resultMap>
  ```

  > association: 处理一对多映射关系
  >
  > property: 需要处理一对多映射关系的属性名
  >
  > javaTyoe: 该属性的 POJO 类型

- ### 3.使用分布查询

  - EmployeeMapper 接口

    ```java
    public interface EmployeeMapper {
    
        /**
         * 获取员工以及员工部门
         * @param empId
         * @return
         */
        Employee getEmpAndDepOneStep(@Param("empId") Integer empId);
    }	
    ```

  - EmployeeMapper.xml 映射文件

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="sgm.mapper.EmployeeMapper">
        <resultMap id="getEmpAndDepByStepResultMap" type="Employee">
            <!--设置了 <setting name="mapUnderscoreToCamelCase" value="true"/> -->
            <!--
            select:
                设置的分布查询的唯一标识, 一个 Mapper 映射文件不能有重复的 id 但其他的 Mapper 文件可以用别的 Mapper 文件的id
            column:
                设置分布查询的条件
            -->
            <association property="dept"
                         select="sgm.mapper.DeptMapper.getEmpAndDeptTwoStep"
                         column="dept_id"/>
        </resultMap>
        <!--Employee getEmpAndDepOneStep(@Param("empId") Integer empId);-->
        <select id="getEmpAndDepOneStep" resultMap="getEmpAndDepByStepResultMap">
            select * from empoyee where emp_id=#{empId};
        </select>
    </mapper>
    ```

    > 需要设置 resultMap, 可以不需要设置 employee 和 Employee类的字段与属性的映射, 但需要设置 Dept dept 属性的映射
    >
    > <association> 就是设置 dept 属性的映射
    >
    > - property: 属性变量名
    >
    > - select: 设置的分布查询的唯一标识, 一个 Mapper 映射文件不能有重复的 id 但其他的 Mapper 文件可以用别的 Mapper 文件的id
    >
    >   可直接在对应的Mapper接口中的方式右键选择 Copy Reference 就可以复制方法的全类名
    >
    > - column: 设置分布查询的条件

## 4. Test

@Test

```java
@Test
public void testGetEmpAndDept() {
    SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession(true);
    EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
    System.out.println(mapper.getEmpAndDepOneStep(1));
}
```

Result

```java
Employee(empId=1, empName=谌光明, empGender=男, empAge=20, dept=Dept(deptId=1, deptName=IT部, deptNum=20, employees=null))
```



# 8. 解决一对多映射问题

---



## 1. 出现情况: 查询一个部门有多少员工

---

|  (类型) Employee   |        (类型)Dept        |
| :----------------: | :----------------------: |
|  (Inteder) empId   |     (Integer) deptId     |
|  (String) empName  |     (String) depName     |
|  (Integer) empAge  |     (Inteder) depNum     |
| (String) empGender | List<Employee> employees |
|     (Dept) dep     |                          |

## 2. 分布查询解决方案

---

- DeptMapper 接口

  ```Java
  /**
   * 通过分步查询查部门的所有员工信息
   * 分布查询第一步: 通过 deptId 查询部门
   * @param depId
   * @return
   */
  List<Employee> getDeptAndEmpOneStep(@Param("depId") Integer depId);
  ```

- DeptMapper 映射文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="sgm.mapper.DeptMapper">
      <resultMap id="getDeptAndEmpByStepResultMap" type="Dept">
          <collection property="employees"
                      select="sgm.mapper.EmployeeMapper.getDeptAndEmpTwoStep"
                      column="dept_id"
  
          />
      </resultMap>
  
      <!--List<Employee> getDeptAndEmpOneStep(@Param("depId") Integer depId);
          一对多
      -->
      <select id="getDeptAndEmpOneStep" resultMap="getDeptAndEmpByStepResultMap">
          select * from dept where dept_id = #{depId};
      </select>
  
      <!--Dept getEmpAndDeptTwoStep(@Param("depId") Integer depId);
          多对一
      -->
      <select id="getEmpAndDeptTwoStep" resultType="sgm.entity.Dept">
          select * from dept where dept_id = #{depId};
      </select>
  </mapper>
  ```

- EmployeeMapper 接口

  ```java
  /**
   * 获取部门所有员工分步第二步
   * @param deptId
   * @return
   */
  Employee getDeptAndEmpTwoStep(@Param("deptId") Integer deptId);
  ```

- EmployeeMapper 映射文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="sgm.mapper.EmployeeMapper">
      <resultMap id="getEmpAndDepByStepResultMap" type="Employee">
          <!--设置了 <setting name="mapUnderscoreToCamelCase" value="true"/> -->
          <!--
          select:
              设置的分布查询的唯一标识, 一个 Mapper 映射文件不能有重复的 id 但其他的 Mapper 文件可以用别的 Mapper 文件的id
          column:
              设置分布查询的条件
          -->
          <association property="dept"
                       select="sgm.mapper.DeptMapper.getEmpAndDeptTwoStep"
                       column="dept_id"/>
      </resultMap>
      <!--Employee getEmpAndDepOneStep(@Param("empId") Integer empId);
          多对一
      -->
      <select id="getEmpAndDepOneStep" resultMap="getEmpAndDepByStepResultMap">
          select * from empoyee where emp_id=#{empId};
      </select>
      <!--
          一对多
      -->
      <select id="getDeptAndEmpTwoStep" resultType="Employee">
          select * from empoyee where dept_id=#{deptId};
      </select>
  </mapper>
  ```

# 9. Mybatis的动态 SQL 

---

- <if>

  ```xml
  <select id="getUserByUsername" resultType="sgm.entity.User">
      select *
      from user
      where 
      <if test="username != null and username!=''">
          username = #{username}
      </if>
  </select>
  ```

  > <if>: 根据标签中的 test 属性对应的表达式决定标签的中的内容是否拼接到 SQL 中
  >
  > 
  >
  > 当 username 不为空并且不是空字符串就进行拼接 'select * from user where username= #{username}',
  >
  > 但是当 test 中的而语句不成立, 就会变成'select * from user where' 明显这时错误的SQL,所以这需要我们下面的 标签

- <where>

  ```xml
  <select id="getUserByUsernameOrPassword" resultType="sgm.entity.User">
      select *
      from user
      <where>
          <if test="username != null and username!=''">
              username = #{username}
          </if>
          <if test="password != null and password!=''">
              or password = #{password}
          </if>
      </where>
  </select>
  ```

  > <where>: 当where标签中有内容时，会自动生成where关键字，并且将内容前多余的and或or去掉当where标签中没有内容时，此时where标签没有任何效果
  > 注意: where标签不能将其中内容后面多余的and或or去掉
  >
  > 当第一个<if>不成立第一个<if>的or会自动删去
  >
  > 
