package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer> {
}
