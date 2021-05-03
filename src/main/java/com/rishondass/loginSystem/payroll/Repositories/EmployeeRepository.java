package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.AddressModel;
import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import org.apache.tomcat.jni.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer> {
    @Query(value = "SELECT * FROM payroll.employee a WHERE a.employeeid=?1",nativeQuery = true)
    List<EmployeeModel> getEmployeeById(int id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO payroll.employee (ssn,firstname,lastname,jobtitle,jobperformance) VALUES (:ssn,:firstName,:lastName,:jobTitle,:jobPerformance)",nativeQuery = true)
    void querySaveEmployee(@Param("ssn")String ssn, @Param("firstName")String firstName, @Param("lastName")String lastName, @Param("jobTitle")String jobTitle, @Param("jobPerformance")String jobPerformance);


}
