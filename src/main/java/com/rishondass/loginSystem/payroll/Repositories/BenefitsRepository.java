package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.BenefitsModel;
import com.rishondass.loginSystem.payroll.Models.DependantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BenefitsRepository extends CrudRepository<BenefitsModel, String> {
    @Query(value = "SELECT * FROM payroll.benefits a WHERE a.childSsn IS NULL AND a.parentssn=?1",nativeQuery = true)
    List<BenefitsModel> getBenefitsByEmployeeSSN(String ssn);

    @Query(value = "SELECT * FROM payroll.benefits a WHERE a.childSsn=?1 AND a.parentssn=?2",nativeQuery = true)
    List<BenefitsModel> getBenefitsByEmployeeDependantSSN(String childSsn, String parentSsn);

    @Query(value = "SELECT COUNT(*) FROM payroll.benefits a WHERE a.benefitType=:benefitType AND a.benefitName=:benefitName AND a.childSsn IS NULL AND a.parentSsn=:parentSsn",nativeQuery = true)
    int checkIfUniqueInEmployee(@Param("benefitType")String benefitType, @Param("benefitName")String benefitName,@Param("parentSsn")String parentSsn);

    @Query(value = "SELECT COUNT(*) FROM payroll.benefits a WHERE a.benefitType=:benefitType AND a.benefitName=:benefitName AND a.childSsn=:childSsn AND a.parentSsn=:parentSsn",nativeQuery = true)
    int checkIfUniqueInEmployeeDependant(@Param("benefitType")String benefitType, @Param("benefitName")String benefitName,@Param("childSsn")String childSsn,@Param("parentSsn")String parentSsn);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payroll.benefits WHERE (payroll.benefits.benefitType=:benefitType AND payroll.benefits.benefitName=:benefitName AND payroll.benefits.parentSsn=:parentSsn)",nativeQuery = true)
    void deleteEmployeeBenefit(@Param("benefitType")String benefitType, @Param("benefitName")String benefitName,@Param("parentSsn")String parentSsn);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payroll.benefits WHERE (payroll.benefits.benefitType=:benefitType AND payroll.benefits.benefitName=:benefitName AND payroll.benefits.childSsn=:childSsn AND payroll.benefits.parentSsn=:parentSsn)",nativeQuery = true)
    void deleteEmployeeDependantBenefit(@Param("benefitType")String benefitType, @Param("benefitName")String benefitName,@Param("childSsn")String childSsn,@Param("parentSsn")String parentSsn);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO payroll.benefits (benefitType,benefitName,rate,companyContribution,numFamilyMembers,parentSsn) VALUES (:a,:b,:c,:d,:e,:f)",nativeQuery = true)
    void saveAsInsertEmployee(@Param("a")String a, @Param("b")String b, @Param("c")double c, @Param("d")double d, @Param("e")int e, @Param("f")String f);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO payroll.benefits (benefitType,benefitName,rate,companyContribution,numFamilyMembers,childSsn,parentSsn) VALUES (:a,:b,:c,:d,:e,:f,:g)",nativeQuery = true)
    void saveAsInsertEmployeeDependant(@Param("a")String a, @Param("b")String b, @Param("c")double c, @Param("d")double d, @Param("e")int e, @Param("f")String f,@Param("g")String g);

}
