package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.FederalTaxModel;
import com.rishondass.loginSystem.payroll.Models.StateTaxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StateTaxRepository extends JpaRepository<StateTaxModel,Integer> {
    @Query(value="SELECT * FROM payroll.stateTax",nativeQuery = true)
    List<StateTaxModel> findAllByQuery();


    @Transactional
    @Modifying
    @Query(value="INSERT INTO payroll.stateTax VALUES (?1,?2,?3)",nativeQuery = true)
    void saveByQuery(double rate, String state, int year);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM payroll.stateTax a WHERE a.taxrate=?1 AND a.state=?2 AND a.year=?3",nativeQuery = true)
    void deleteByQuery(double rate,String state,int year);

    @Query(value="SELECT COUNT(*) FROM payroll.stateTax a WHERE a.taxrate=?1 AND a.state=?2 AND a.year=?3",nativeQuery = true)
    int checkUniqueByQuery(double rate, String state, int year);

    @Query(value="SELECT * FROM payroll.stateTax a WHERE a.state=?1 AND (a.year>=?2 AND a.year<=?3)",nativeQuery = true)
    StateTaxModel findByStateAndYears(String state,int syear, int eyear);

}
