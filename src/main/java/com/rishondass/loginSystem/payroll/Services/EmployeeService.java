package com.rishondass.loginSystem.payroll.Services;

import com.rishondass.loginSystem.UserModel;
import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import com.rishondass.loginSystem.payroll.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<EmployeeModel> listAll() {
        return (List<EmployeeModel>) employeeRepository.findAll();
    }

/*    public boolean userExists(String username){
        *//*return !(repo.findAll().isEmpty());*//*
        String test = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM login.users WHERE username = ?",new Object[]{username},String.class);
        if(Integer.parseInt(test)==0){
            return false;
        }
        return true;
    }*/

    public List<EmployeeModel> getAddresses(){
        List<EmployeeModel> list = employeeRepository.fetchAddresses();
        return list;
    }


}
