package com.rishondass.loginSystem.payroll.Services;



import com.rishondass.loginSystem.payroll.Models.CompanyModel;
import com.rishondass.loginSystem.payroll.Models.FederalTaxModel;
import com.rishondass.loginSystem.payroll.Models.StateTaxModel;
import com.rishondass.loginSystem.payroll.Repositories.CompanyRepository;
import com.rishondass.loginSystem.payroll.Repositories.FederalTaxRepository;
import com.rishondass.loginSystem.payroll.Repositories.StateTaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;
import java.util.List;

@Service
public class TaxService {
    @Autowired
    private StateTaxRepository stateTaxRepository;

    @Autowired
    private FederalTaxRepository federalTaxRepository;

    public List<StateTaxModel> listAllStateTaxes(){
        return stateTaxRepository.findAllByQuery();
    }

    public List<FederalTaxModel> listAllFederalTaxes(){
        return federalTaxRepository.findAll();
    }

    public void saveStateTax(StateTaxModel tax){
        stateTaxRepository.saveByQuery(tax.getTaxRate(),tax.getState(),tax.getYear());
    }

    public void saveFederalTax(FederalTaxModel tax){
        federalTaxRepository.saveByQuery(tax.getTaxRate(),tax.getCountry(),tax.getYear());
    }

    public void deleteStateTax(double rate,String state,int year){
        stateTaxRepository.deleteByQuery(rate, state,year);
    }

    public void deleteFederalTax(double rate,String country,int year){
        federalTaxRepository.deleteByQuery(rate, country,year);
    }

    public boolean stateTaxIsUinque(StateTaxModel tax){
        if(0 >= stateTaxRepository.checkUniqueByQuery(tax.getTaxRate(),tax.getState(),tax.getYear())){
            return true;
        }
        return false;


    }
    public boolean federalTaxIsUinque(FederalTaxModel tax){
        if(0 >= federalTaxRepository.checkUniqueByQuery(tax.getTaxRate(),tax.getCountry(),tax.getYear())){
            return true;
        }
        return false;


    }

    public StateTaxModel findStateTaxWithStateAndYears(String state,int syear, int eyear){
        return stateTaxRepository.findByStateAndYears(state,syear,eyear);
    }

    public FederalTaxModel findFederalTaxWithCountryAndYears(String country,int syear, int eyear){
        return federalTaxRepository.findByCountryAndYears(country,syear,eyear);
    }


}
