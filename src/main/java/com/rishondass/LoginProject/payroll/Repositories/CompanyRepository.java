package com.rishondass.loginSystem.payroll.Repositories;

import com.rishondass.loginSystem.payroll.Models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel,String> {
}
