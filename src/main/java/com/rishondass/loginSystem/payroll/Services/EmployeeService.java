package com.rishondass.loginSystem.payroll.Services;

import com.rishondass.loginSystem.UserModel;
import com.rishondass.loginSystem.payroll.Models.AddressModel;
import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import com.rishondass.loginSystem.payroll.Repositories.AddressRepository;
import com.rishondass.loginSystem.payroll.Repositories.EmployeeRepository;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AddressRepository addressRepository;

    public List<EmployeeModel> listAll() {
        return (List<EmployeeModel>) employeeRepository.findAll();
    }

   /* public List<AddressModel> getAddressesWithID(int id){
       *//* List<AddressModel> addresses = jdbcTemplate.queryForObject(
                "SELECT * FROM payroll.address WHERE payroll.address.employeeid = ?",new Object[]{id},new BeanPropertyRowMapper<>(AddressModel.class));
*//*      List<AddressModel>addresses = employeeRepository.fetchEmployeeAddresses(id);
        addresses.forEach(address -> System.out.println(address.toString()));
        return addresses;
    }*/

    public EmployeeModel getEmployeeWithID(int id){
        List<EmployeeModel> employee= employeeRepository.getEmployeeById(id);
        return employee.get(0);
    }

    public void saveEmployee(EmployeeModel employee){
        if(employee.employeeID <= 0){
            employeeRepository.querySaveEmployee(employee.getSsn(),employee.getFirstName(),employee.getLastName(),employee.getJobTitle(),employee.getJobPerformance());
        }
        else{
            employeeRepository.save(employee);
        }

    }

    public void deleteEmployee(int empID){
        employeeRepository.deleteById(empID);
    }


    //========================================Address==================================================================
    public List<AddressModel> getAddresses(int id){
        List<AddressModel> list = addressRepository.fetchEmployeeAddresses(id);
        return list;
    }

    public void saveAddress(AddressModel address){
        addressRepository.save(address);
    }

    public AddressModel getAddressObject(int empID,String address){
        return addressRepository.fetchAddressWithIdAndAddress(empID, address);
    }

    public void deleteAddress(String name){
        addressRepository.deleteById(name);
    }


}
