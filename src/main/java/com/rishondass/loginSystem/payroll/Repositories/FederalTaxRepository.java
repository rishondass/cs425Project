package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.CompanyModel;
import com.rishondass.loginSystem.payroll.Models.FederalTaxModel;
import com.rishondass.loginSystem.payroll.Models.StateTaxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FederalTaxRepository extends JpaRepository<FederalTaxModel,Integer> {
    @Transactional
    @Modifying
    @Query(value="INSERT INTO payroll.federalTax VALUES (?1,?2,?3)",nativeQuery = true)
    void saveByQuery(double rate, String country, int year);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM payroll.federalTax a WHERE a.taxrate=?1 AND a.country=?2 AND a.year=?3",nativeQuery = true)
    void deleteByQuery(double rate,String country,int year);

    @Query(value="SELECT COUNT(*) FROM payroll.federalTax a WHERE a.taxrate=?1 AND a.country=?2 AND a.year=?3",nativeQuery = true)
    int checkUniqueByQuery(double rate, String country, int year);

    @Query(value="SELECT * FROM payroll.federalTax a WHERE a.country=?1 AND (a.year>=?2 AND a.year<=?3)",nativeQuery = true)
    FederalTaxModel findByCountryAndYears(String country, int syear, int eyear);
}
