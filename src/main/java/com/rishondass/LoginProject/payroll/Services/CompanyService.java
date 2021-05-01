package com.rishondass.loginSystem.payroll.Services;



import com.rishondass.loginSystem.payroll.Models.CompanyModel;
import com.rishondass.loginSystem.payroll.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyrepo;

    public List<CompanyModel> listAll() {
        return companyrepo.findAll();
    }


}
