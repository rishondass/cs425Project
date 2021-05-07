package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.SalaryModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SalaryRepository extends CrudRepository<SalaryModel, Integer> {
    @Query(value = "SELECT * FROM payroll.salary a WHERE a.employeeid=?1",nativeQuery = true)
    List<SalaryModel> getSalaryByEmployeeID(int id);

    @Query(value = "SELECT * FROM payroll.salary a WHERE a.salaryBase=?1 AND a.year=?2",nativeQuery = true)
    SalaryModel getSalaryBySalaryAndYear(double salaryBase, int year);

    @Query(value = "SELECT * FROM payroll.salary a WHERE a.employeeid=?1 AND (a.year>=?2 AND a.year<=?3)",nativeQuery = true)
    List<SalaryModel> getSalaryByIdAndYear(int id, int startYear, int endYear);

    @Query(value = "SELECT * FROM payroll.salary a WHERE a.employeeid=?1 AND (a.year=?2)",nativeQuery = true)
    List<SalaryModel> getSalaryByIdAndOneYear(int id, int year);

}
