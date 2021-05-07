package com.rishondass.loginSystem.payroll.Services;


import com.rishondass.loginSystem.payroll.Models.*;
import com.rishondass.loginSystem.payroll.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    DependantRepository dependantRepository;

    @Autowired
    BenefitsRepository benefitsRepository;

    @Autowired
    PaycheckRepository paycheckRepository;

    @Autowired
    W2Repository w2Repository;

    @Autowired
    ExpenseReportRepository expenseReportRepository;

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

    public List<EmployeeModel> getEmployeeWithMangID(int mangID){
        return employeeRepository.getEmployeeByMangID(mangID);
    }


    public EmployeeModel getEmployeeWithID(int id){
        List<EmployeeModel> employee= employeeRepository.getEmployeeById(id);
        return employee.get(0);
    }

    public EmployeeModel getEmployeeWithSSN(String ssn){
        return employeeRepository.getEmployeeModelBySsn(ssn);
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

    //========================================Salary====================================================================
    public List<SalaryModel> getAllSalaries(int id){
        return salaryRepository.getSalaryByEmployeeID(id);
    }

    public void saveSalary(SalaryModel salary){
        salaryRepository.save(salary);
    }

    public void deleteSalary(double salaryBase,int year){
        salaryRepository.delete(salaryRepository.getSalaryBySalaryAndYear(salaryBase,year));
    }

    public List<SalaryModel> getAllSalariesWithIdAndYear(int id, int startYear, int endYear){
        if(startYear==endYear){
            return salaryRepository.getSalaryByIdAndOneYear(id, startYear);
        }
        return salaryRepository.getSalaryByIdAndYear(id, startYear, endYear);
    }

    //========================================Dependants================================================================

    public List<DependantModel> getAllWithParentSSN(String ssn){
        return dependantRepository.getDependentsByEmployeeSSN(ssn);
    }

    public DependantModel getDependantWithID(int id){
        return dependantRepository.getByID(id);
    }

    public boolean ifUniqueInTable(String ssn){
        if(0 >= dependantRepository.checkIfUinqueInTable(ssn)){
            return true;
        }
        return false;
    }

    public boolean checkIfSSNUnique(String ssn){
        if(0 >= dependantRepository.checkIfUniqueSSN(ssn)){
            return true;
        }
        return false;
    }

    public void saveDependant(DependantModel dependant){

        dependantRepository.saveByInsert(dependant.getSsn(),dependant.getFirstName(),dependant.getLastName(),dependant.getRelation(),dependant.getParentSSN());
    }

    public void deleteDependant(String ssn){
        dependantRepository.delete(dependantRepository.findBySSN(ssn));
    }

    //========================================Benefits================================================================
    public List<BenefitsModel> getEmployeeBenefits(String ssn){
        return benefitsRepository.getBenefitsByEmployeeSSN(ssn);
    }


    public List<BenefitsModel> getEmployeeDependantBenefits(String childSsn, String parentSsn){
        return benefitsRepository.getBenefitsByEmployeeDependantSSN(childSsn, parentSsn);
    }


    public void saveBenefit(BenefitsModel benefit){
        if(benefit.getChildSsn()==null){
            benefitsRepository.saveAsInsertEmployee(benefit.getBenefitType(),benefit.getBenefitName(),benefit.getRate(),benefit.getCompanyContribution(),benefit.getNumFamilyMembers(),benefit.getParentSsn());
        }else{
            benefitsRepository.saveAsInsertEmployeeDependant(benefit.getBenefitType(),benefit.getBenefitName(),benefit.getRate(),benefit.getCompanyContribution(),benefit.getNumFamilyMembers(),benefit.getChildSsn(),benefit.getParentSsn());
        }

    }

    public void deleteBenefit(String benefitType, String benefitName, String childSSN, String parentSSN){
        if(childSSN.equals("null")){
            benefitsRepository.deleteEmployeeBenefit(benefitType,benefitName,parentSSN);
        }else{
            benefitsRepository.deleteEmployeeDependantBenefit(benefitType,benefitName,childSSN,parentSSN);
        }

    }

    public boolean checkIfUniqueInEmployeeBenefits(String benefitType, String benefitName, String parentSSN){
        if(0 >= benefitsRepository.checkIfUniqueInEmployee(benefitType,benefitName,parentSSN)){
            return true;
        }
        return false;
    }

    public boolean checkIfUniqueInEmployeeDependantsBenefits(String benefitType, String benefitName,String childSsn, String parentSSN){
        if(0 >= benefitsRepository.checkIfUniqueInEmployeeDependant(benefitType,benefitName,childSsn,parentSSN)){
            return true;
        }
        return false;
    }

    //========================================Paychecks================================================================
        public void savePaycheck(PaycheckModel paycheck){
            paycheckRepository.save(paycheck);
        }

        public List<PaycheckModel> getEmployeePayChecksWithSSN(String ssn){
            return paycheckRepository.findAllBySSN(ssn);
        }

    public PaycheckModel getEmployeePayChecksWithSSNAndDateRange(String ssn,String dateRange){
        return paycheckRepository.findAllBySSNAndDateRange(ssn,dateRange);
    }

    public void deleteEmployeePayCheck(String ssn, String daterange){
        paycheckRepository.deleteBySSNAndDateRange(ssn,daterange);
    }

    //========================================W2========================================================================
    public List<W2Model> listAllW2BySSN(String ssn){
        return w2Repository.findAllBySSN(ssn);
    }

    public void saveW2(W2Model w2){
        w2Repository.save(w2);
    }

    public W2Model findW2BySSNAndDateRange(String ssn, String dateRange){
        return w2Repository.findAllBySSNAndDateRange(ssn, dateRange);
    }

    public void deleteEmployeeW2(String ssn, String daterange){
        w2Repository.deleteBySSNAndDateRange(ssn,daterange);
    }

    //========================================Expense Reports===========================================================
    public List<ExpenseReportModel> listAllExpenseReportsWithSsn(String ssn){
        return expenseReportRepository.findAllBySSN(ssn);
    }

    public void saveExpenseReport(ExpenseReportModel report){
        expenseReportRepository.save(report);
    }

    public ExpenseReportModel findExpenseReportsBySSNAndDateRange(String ssn, String dateRange){
        return expenseReportRepository.findAllBySSNAndDateRange(ssn,dateRange);
    }

    public void deleteEmployeeExpenseReport(String ssn, String daterange){
        expenseReportRepository.deleteBySSNAndDateRange(ssn,daterange);
    }

}
