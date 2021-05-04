package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.AddressModel;
import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressModel, String> {

    @Query(value = "SELECT * FROM payroll.addresses a WHERE a.employeeid = ?1",nativeQuery = true)
    List<AddressModel> fetchEmployeeAddresses(int id);

    @Query(value="SELECT * FROM payroll.addresses a WHERE a.employeeid = ?1 AND a.address = ?2",nativeQuery = true)
    AddressModel fetchAddressWithIdAndAddress(int empId, String address);

}
