package com.rishondass.loginSystem.payroll.Services;

import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import com.rishondass.loginSystem.payroll.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<EmployeeModel> listAll() {
        return employeeRepository.findAll();
    }
}
