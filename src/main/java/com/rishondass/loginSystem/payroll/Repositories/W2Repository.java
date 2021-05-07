package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.PaycheckModel;
import com.rishondass.loginSystem.payroll.Models.W2Model;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface W2Repository extends CrudRepository<W2Model, Integer> {
    @Query(value="SELECT * FROM payroll.w2 a WHERE a.ssn=?1",nativeQuery = true)
    List<W2Model> findAllBySSN(String ssn);

    @Query(value="SELECT * FROM payroll.w2 a WHERE a.ssn=?1 AND a.daterange=?2",nativeQuery = true)
    W2Model findAllBySSNAndDateRange(String ssn, String dateRange);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payroll.w2 a WHERE a.ssn=?1 AND a.daterange=?2",nativeQuery = true)
    void deleteBySSNAndDateRange(String ssn, String dateRange);

}
