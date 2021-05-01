package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer> {
    @Query(value = "SELECT * FROM payroll.address a LEFT JOIN payroll.employee b ON a.employeeid=b.employeeid",nativeQuery = true)
    List<EmployeeModel> fetchAddresses();
}
