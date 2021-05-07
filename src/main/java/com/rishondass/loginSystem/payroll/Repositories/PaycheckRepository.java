package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.PaycheckModel;
import com.rishondass.loginSystem.payroll.Models.SalaryModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaycheckRepository extends CrudRepository<PaycheckModel, Integer> {
    @Query(value="SELECT * FROM payroll.paycheck a WHERE a.ssn=?1",nativeQuery = true)
    List<PaycheckModel> findAllBySSN(String ssn);

    @Query(value="SELECT * FROM payroll.paycheck a WHERE a.ssn=?1 AND a.concatdaterange=?2",nativeQuery = true)
    PaycheckModel findAllBySSNAndDateRange(String ssn, String dateRange);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payroll.paycheck a WHERE a.ssn=?1 AND a.concatdaterange=?2",nativeQuery = true)
    void deleteBySSNAndDateRange(String ssn, String dateRange);

}
