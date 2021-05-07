package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.DependantModel;
import com.rishondass.loginSystem.payroll.Models.SalaryModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DependantRepository extends CrudRepository<DependantModel, Integer> {
    @Query(value = "SELECT * FROM payroll.dependant a WHERE a.parentssn=?1",nativeQuery = true)
    List<DependantModel> getDependentsByEmployeeSSN(String ssn);

    @Query(value = "SELECT COUNT(*) FROM payroll.employee a WHERE a.ssn=?1",nativeQuery = true)
    int checkIfUniqueSSN(String ssn);

    @Query(value = "SELECT * FROM payroll.dependant a WHERE a.ssn=?1",nativeQuery = true)
    DependantModel findBySSN(String ssn);

    @Query(value = "SELECT COUNT(*) FROM payroll.dependant a WHERE a.ssn=?1",nativeQuery = true)
    int checkIfUinqueInTable(String ssn);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO payroll.dependant (ssn,firstname,lastname,relation,parentssn) VALUES (?1,?2,?3,?4,?5)",nativeQuery = true)
    void saveByInsert(String a, String b, String c, String d, String e);

    @Query(value = "SELECT * FROM payroll.dependant a WHERE a.id=?1",nativeQuery = true)
    DependantModel getByID(int id);

}
