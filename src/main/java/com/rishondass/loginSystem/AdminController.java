package com.rishondass.loginSystem;

import com.rishondass.loginSystem.payroll.Models.*;
import com.rishondass.loginSystem.payroll.Repositories.ExpenseReportRepository;
import com.rishondass.loginSystem.payroll.Services.EmployeeService;
import com.rishondass.loginSystem.payroll.Services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TaxService taxService;

    @Autowired
    JdbcTemplate jdbcTemplate;



//Payroll Employee Stuff================================================================================================


    @RequestMapping("/admin/ListEmployees")
    public String listEmployees(Model model){
        List<EmployeeModel> listEmployees = employeeService.listAll();
        model.addAttribute("listEmployees",listEmployees);
        return "adminPages/payroll/ListEmployees";
    }
    @RequestMapping("/admin/ListEmployees/edit/{empID}")
    public ModelAndView editEmployee(@PathVariable(name="empID")int empID){
        ModelAndView mav = new ModelAndView("adminPages/payroll/edit_employee");
        EmployeeModel employee = employeeService.getEmployeeWithID(empID);
        mav.addObject("employee",employee);
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/save")
    public String saveEmployee(@ModelAttribute(name="employee")EmployeeModel employee){
        employeeService.saveEmployee(employee);
        return "redirect:/admin/ListEmployees/";
    }

    @RequestMapping("/admin/ListEmployees/delete/{empID}")
    public String deleteEmployee(@PathVariable("empID")int empID){
        employeeService.deleteEmployee(empID);

        return "redirect:/admin/ListEmployees/";
    }

    @RequestMapping("/admin/ListEmployees/create")
    public String createEmployee(Model model){
        model.addAttribute("employee",new EmployeeModel());
        return "adminPages/payroll/add_employee";
    }

    @RequestMapping("/admin/ListEmployees/processEmployee")
    public String processEmployee(@ModelAttribute("employee") EmployeeModel employee,Model model){

        try{
            employeeService.saveEmployee(employee);
        }catch (Exception e){
            System.out.println("Couldn't add user");
        }

        return "redirect:/admin/ListEmployees/";
    }



    //==========================================Employee Addresses Handling=============================================

    @RequestMapping("/admin/ListEmployees/shoEmployeeAddresses/{id}")
    public ModelAndView showEmployeeAddresses(@PathVariable(name="id")int id, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/showEmployeeAddresses");
        List<AddressModel> addresses = employeeService.getAddresses(id);
        session.setAttribute("currentUser",employeeService.getEmployeeWithID(id));
        mav.addObject("addresses",addresses);
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeAddresses/edit/{empID}/{address}")
    public ModelAndView editEmployeeAddresses(@PathVariable("empID") int empID, @PathVariable("address")String address, HttpSession session){
        System.out.println(employeeService.getEmployeeWithID(empID).getFirstName()+", "+address);
        AddressModel currentAddress = employeeService.getAddressObject(empID,address);
        ModelAndView mav = new ModelAndView("adminPages/payroll/edit_address");
        mav.addObject("address",currentAddress);
        session.setAttribute("employeeID",currentAddress.getEmployeeID());
        session.setAttribute("prevAddress",currentAddress.getAddress());
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeAddresses/add")
    public String addEmployeeAddress(Model model){
        model.addAttribute("address",new AddressModel());
        return "adminPages/payroll/add_address";
    }
    @RequestMapping("/admin/ListEmployees/showEmployeeAddresses/processAddress/{empID}")
    public String processEmployeeAddress(@ModelAttribute(name="address")AddressModel address,@PathVariable(name="empID")String empID){
        address.setEmployeeID(Integer.parseInt(empID));
        employeeService.saveAddress(address);
        return "redirect:/admin/ListEmployees/shoEmployeeAddresses/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeAddresses/save")
    public String saveEmployeeAddresses(@ModelAttribute(name="address")AddressModel address,HttpSession session){
        employeeService.deleteAddress((String) session.getAttribute("prevAddress"));
        employeeService.saveAddress(address);
        return "redirect:/admin/ListEmployees/shoEmployeeAddresses/" + address.getEmployeeID();
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeAddresses/delete/{employeeID}/{address}")
    public String deleteEmployeeAddress(@PathVariable("employeeID")int employeeID,@PathVariable("address")String address){
        employeeService.deleteAddress(address);
        return "redirect:/admin/ListEmployees/shoEmployeeAddresses/" + employeeID;
    }
    //==================================================================================================================
    //==========================================Employee Addresses Handling=============================================

    @RequestMapping("/admin/ListEmployees/showEmployeeSalaries/{empID}")
    public ModelAndView showEmployeeSalaries(@PathVariable(name="empID")int id, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/showEmployeeSalaries");
        List<SalaryModel> salaries = employeeService.getAllSalaries(id);
        mav.addObject("salaries",salaries);
        session.setAttribute("currentUser", employeeService.getEmployeeWithID(id));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeSalaries/addSalary/{empID}")
    public String addEmployeeSalary(Model model,@PathVariable("empID")int empID, HttpSession session){
        session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
        model.addAttribute("salary",new SalaryModel());
        return "adminPages/payroll/add_salary";
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeSalaries/processSalary/{empID}")
    public String processEmployeeSalary(@ModelAttribute(name="salary")SalaryModel salary,@PathVariable(name="empID")int empID){
        try {
            salary.setEmployeeID(empID);
            employeeService.saveSalary(salary);
        }catch (Exception e){
            System.out.println("couldn't add salary");
        }

        return "redirect:/admin/ListEmployees/showEmployeeSalaries/" + empID;

    }

    @RequestMapping("/admin/ListEmployees/showEmployeeAddresses/deleteAddress/{salaryBase}/{year}/{empID}")
    public String deleteSalary(@PathVariable(name="salaryBase")double salaryBase, @PathVariable(name="year")int year,@PathVariable("empID")int empID){
        try{
            employeeService.deleteSalary(salaryBase,year);
        }catch (Exception e){
            System.out.println("Couldn't Delete Salary");
        }

        return "redirect:/admin/ListEmployees/showEmployeeSalaries/" + empID;
    }

    //==================================================================================================================

    //==========================================Employee Dependants=====================================================

    @RequestMapping("/admin/ListEmployees/showEmployeeDependents/{empID}")
    public ModelAndView showEmployeeDependants(@PathVariable("empID")int empID, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/showEmployeeDependants");
        List<DependantModel> dependants = employeeService.getAllWithParentSSN(employeeService.getEmployeeWithID(empID).getSsn());
        session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
        mav.addObject("dependants",dependants);
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependents/add/{empID}")
    public ModelAndView addEmployeeDependant(@PathVariable("empID")int empID, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/add_dependant");
        mav.addObject("dependant",new DependantModel());
        session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependents/processDependant/{empID}")
    public String processEmployeeDependant(@PathVariable("empID")int empID, @ModelAttribute DependantModel dependant){
        try{
            if(employeeService.checkIfSSNUnique(dependant.getSsn()) && employeeService.ifUniqueInTable(dependant.getSsn())){
                dependant.setParentSSN(employeeService.getEmployeeWithID(empID).getSsn());
                employeeService.saveDependant(dependant);

            }
        }catch(Exception e){
            System.out.println("Couldn't add dependant");
        }
        return "redirect:/admin/ListEmployees/showEmployeeDependents/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependents/delete/{empID}/{dependantSSN}")
    public String deleteEmployeeDependant(@PathVariable("empID")int empID, @PathVariable("dependantSSN")String dependantSSN){
        try {
            employeeService.deleteDependant(dependantSSN);
        }catch (Exception e){
            System.out.println("couldn't delete dependant");
        }
        return "redirect:/admin/ListEmployees/showEmployeeDependents/" + empID;
    }



    //==================================================================================================================

    //==========================================Employee & Dependant Benefits===========================================
    @RequestMapping("/admin/ListEmployees/showEmployeeBenefits/{empID}")
    public ModelAndView showEmployeeBenefits(@PathVariable("empID")int empID, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/showEmployeeBenefits");
        try{
            String employeeSsn = employeeService.getEmployeeWithID(empID).getSsn();
            List<BenefitsModel> benefits = employeeService.getEmployeeBenefits(employeeSsn);
            session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
            mav.addObject("benefits",benefits);

        }catch (Exception e){
            System.out.println("something went wrong");
        }
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeBenefits/addBenefit/{empID}")
    public ModelAndView addEmployeeBenefit(@PathVariable("empID")int empID, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/add_benefit_employee");
        mav.addObject("benefit",new BenefitsModel());
        session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeBenefits/processBenefit/{empID}")
    public String processEmployeeBenefit(@PathVariable("empID")int empID, @ModelAttribute BenefitsModel benefit){
        try{
            benefit.setParentSsn(employeeService.getEmployeeWithID(empID).getSsn());
            if(employeeService.checkIfUniqueInEmployeeBenefits(benefit.getBenefitType(),benefit.getBenefitName(),benefit.getParentSsn())){
                employeeService.saveBenefit(benefit);
            }else{
                System.out.println("Its not Unique");
            }

        }catch (Exception e){

            System.out.println("couldn't add benefit");
        }

        return "redirect:/admin/ListEmployees/showEmployeeBenefits/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeBenefits/delete/{empID}/{benefitType}/{benefitName}/{childSsn}/{parentSsn}")
    public String deleteEmployeeBenefit(@PathVariable("empID")int empID,@PathVariable("benefitType")String benefitType,@PathVariable("benefitName")String benefitName,@PathVariable("childSsn")String childSsn,@PathVariable("parentSsn")String parentSsn){
        try {
            employeeService.deleteBenefit(benefitType,benefitName,childSsn,parentSsn);
        }catch (Exception e){
            System.out.println("couldn't delete dependant");
        }
        return "redirect:/admin/ListEmployees/showEmployeeBenefits/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependantBenefits/{empID}/{depID}")
    public ModelAndView showEmployeeDependantBenefits(@PathVariable("empID")int empID,@PathVariable("depID")int depID, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/showEmployeeDependantBenefits");
        try{
            String employeeSsn = employeeService.getEmployeeWithID(empID).getSsn();
            String dependantSSN = employeeService.getDependantWithID(depID).getSsn();
            List<BenefitsModel> benefits = employeeService.getEmployeeDependantBenefits(dependantSSN,employeeSsn);
            session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
            session.setAttribute("currentDependant", employeeService.getDependantWithID(depID));
            mav.addObject("benefits",benefits);

        }catch (Exception e){
            System.out.println("something went wrong");
        }
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependantBenefits/addBenefit/{empID}/{depID}")
    public ModelAndView addEmployeeDependantBenefit(@PathVariable("empID")int empID,@PathVariable("depID")int depID, HttpSession session){
        ModelAndView mav = new ModelAndView("adminPages/payroll/add_benefit_dependant");
        mav.addObject("benefit",new BenefitsModel());
        session.setAttribute("currentUser", employeeService.getEmployeeWithID(empID));
        session.setAttribute("currentDependant", employeeService.getDependantWithID(depID));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependantBenefits/processBenefit/{empID}/{depID}")
    public String processEmployeeBenefit(@PathVariable("empID")int empID,@PathVariable("depID")int depID, @ModelAttribute BenefitsModel benefit){
        try{
            benefit.setParentSsn(employeeService.getEmployeeWithID(empID).getSsn());
            benefit.setChildSsn(employeeService.getDependantWithID(depID).getSsn());
            benefit.setNumFamilyMembers(1);
            if(employeeService.checkIfUniqueInEmployeeDependantsBenefits(benefit.getBenefitType(),benefit.getBenefitName(),benefit.getChildSsn(),benefit.getParentSsn())){
                employeeService.saveBenefit(benefit);
            }


        }catch (Exception e){

            System.out.println("couldn't add benefit");
        }

        return "redirect:/admin/ListEmployees/showEmployeeDependantBenefits/" + empID +"/" + depID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeDependantBenefits/delete/{empID}/{depID}/{benefitType}/{benefitName}/{childSsn}/{parentSsn}")
    public String deleteEmployeeDependantBenefit(@PathVariable("empID")int empID,@PathVariable("depID")int depID,@PathVariable("benefitType")String benefitType,@PathVariable("benefitName")String benefitName,@PathVariable("childSsn")String childSsn,@PathVariable("parentSsn")String parentSsn){
        try {
            employeeService.deleteBenefit(benefitType,benefitName,childSsn,parentSsn);
        }catch (Exception e){
            System.out.println("couldn't delete dependant");
        }
        return "redirect:/admin/ListEmployees/showEmployeeDependantBenefits/" + empID +"/" + depID;
    }







    //==================================================================================================================


    //==========================================Employee Forms/Taxes====================================================

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/{empID}")
    public String showFromsAndTaxesInfo(@PathVariable("empID")int empID,Model model){
        model.addAttribute("currentUser",employeeService.getEmployeeWithID(empID));
        List<PaycheckModel> paychecks = employeeService.getEmployeePayChecksWithSSN(employeeService.getEmployeeWithID(empID).getSsn());
        List<W2Model> w2s = employeeService.listAllW2BySSN(employeeService.getEmployeeWithID(empID).getSsn());
        List<ExpenseReportModel> expenseReports = employeeService.listAllExpenseReportsWithSsn(employeeService.getEmployeeWithID(empID).getSsn());
        model.addAttribute("expenseReports",expenseReports);
        model.addAttribute("w2s",w2s);
        model.addAttribute("listPayChecks",paychecks);
        return "adminPages/payroll/forms/showFormOptions";
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/generatePayCheck/{empID}/{dateRange}")
    public String generateEmployeePayCheck(@PathVariable("empID")int empID,@PathVariable("dateRange")String dateRange, Model model){
        model.addAttribute("currentUser",employeeService.getEmployeeWithID(empID));
        PaycheckModel paycheck = employeeService.getEmployeePayChecksWithSSNAndDateRange(employeeService.getEmployeeWithID(empID).getSsn(),dateRange);
        model.addAttribute("paycheck",paycheck);

        return "adminPages/payroll/forms/generateEmployeePayCheck";
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/addPayCheck/{empID}")
    public ModelAndView addEmployeePayCheck(@PathVariable("empID")int empID){
        ModelAndView mav = new ModelAndView("adminPages/payroll/forms/addEmployeePayCheck");
        mav.addObject("paycheck",new PaycheckModel());
        mav.addObject("currentUser",employeeService.getEmployeeWithID(empID));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/processPayCheck/{empID}")
    public String processEmployeePayCheck(@PathVariable("empID")int empID,@ModelAttribute PaycheckModel paycheck){
        try{
            int dateRangeStartYear = Integer.parseInt(paycheck.getDateRange().substring(6,10));
            int dateRangeEndYear = Integer.parseInt(paycheck.getDateRange().substring(17,21));
            double grossIncomePerWeek = 0;
            double totalStateTaxes = 0;
            double totalFederalTaxes = 0;
            List<SalaryModel> salariesWithYears = employeeService.getAllSalariesWithIdAndYear(empID,dateRangeStartYear,dateRangeEndYear);
            for(SalaryModel salary : salariesWithYears){
                grossIncomePerWeek += salary.getSalaryTotal()/26.07145;
            }

            paycheck.setGrossIncome(grossIncomePerWeek);
            List<AddressModel> employeeAddresses = employeeService.getAddresses(empID);
            List<StateTaxModel> stateTaxesOfEmployee = new ArrayList<>();
            List<FederalTaxModel> federalTaxesOfEmployee = new ArrayList<>();
            for(AddressModel address : employeeAddresses){
                stateTaxesOfEmployee.add(taxService.findStateTaxWithStateAndYears(address.getState(),dateRangeStartYear,dateRangeEndYear));
                federalTaxesOfEmployee.add(taxService.findFederalTaxWithCountryAndYears(address.getCountry(),dateRangeStartYear,dateRangeEndYear));
            }
            for(StateTaxModel tax : stateTaxesOfEmployee){
                totalStateTaxes=tax.getTaxRate()/100;
            }
            for(FederalTaxModel tax : federalTaxesOfEmployee){
                totalFederalTaxes = tax.getTaxRate()/100;
            }
            double totalTaxCut = totalFederalTaxes + totalStateTaxes;
            double totalDeductions = 0;
            double totalDepedantDeduction = 0;
            double totalEmployeeDeductions = 0;
            List<DependantModel> employeeDependants = employeeService.getAllWithParentSSN(employeeService.getEmployeeWithID(empID).getSsn());
            List<BenefitsModel> dependantsBenefits = new ArrayList<>();
            for(DependantModel dependant : employeeDependants){
                totalDepedantDeduction += employeeService.getEmployeeDependantBenefits(dependant.getSsn(), employeeService.getEmployeeWithID(empID).getSsn()).stream().mapToDouble(BenefitsModel::getCostPerFamily).sum()/2.5;
            }
            List<BenefitsModel> employeeBenefits = employeeService.getEmployeeBenefits(employeeService.getEmployeeWithID(empID).getSsn());
            for(BenefitsModel benefit : employeeBenefits){

                totalEmployeeDeductions += benefit.getCostPerFamily()/2.5;
            }
            totalDeductions = totalEmployeeDeductions + totalDepedantDeduction;

        /*System.out.println(grossIncomePerWeek);
        System.out.println(totalDepedantDeduction);
        System.out.println(totalEmployeeDeductions);
        System.out.println(totalDeductions);
        System.out.println(grossIncomePerWeek-(grossIncomePerWeek*(totalFederalTaxes+totalStateTaxes)));*/
            String concatDateRange = paycheck.getDateRange().substring(0,2) + paycheck.getDateRange().substring(3,5)+paycheck.getDateRange().substring(6,10)+paycheck.getDateRange().substring(11,13)+paycheck.getDateRange().substring(14,16)+paycheck.getDateRange().substring(17,21);
            paycheck.setConcatDateRange(concatDateRange);
            paycheck.setSsn(employeeService.getEmployeeWithID(empID).getSsn());
            paycheck.setGrossIncome(grossIncomePerWeek);
            paycheck.setTaxCut(totalTaxCut);
            paycheck.setTotalDeductions(totalDeductions);
            employeeService.savePaycheck(paycheck);
        }catch(Exception e){
            System.out.println("Couldn't find information for the paycheck");
        }

        return "redirect:/admin/ListEmployees/showEmployeeForms/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/deleteCheck/{empID}/{dateRange}")
    public String deleteEmployeePayCheck(@PathVariable("empID")int empID,@PathVariable("dateRange")String dateRange, Model model){
        employeeService.deleteEmployeePayCheck(employeeService.getEmployeeWithID(empID).getSsn(),dateRange);
        return "redirect:/admin/ListEmployees/showEmployeeForms/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/addW2/{empID}")
    public ModelAndView addEmployeeW2(@PathVariable("empID")int empID){
        ModelAndView mav = new ModelAndView("adminPages/payroll/forms/addEmployeeW2");
        mav.addObject("w2",new W2Model());
        mav.addObject("currentUser",employeeService.getEmployeeWithID(empID));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/processW2/{empID}")
    public String processEmployeeW2(@PathVariable("empID")int empID,@ModelAttribute W2Model w2){

        try{
            int year = Integer.parseInt(w2.getDateRange());
            double grossIncomePerWeek = 0;
            double totalStateTaxes = 0;
            double totalFederalTaxes = 0;
            double extraEarnings = 0;
            List<SalaryModel> salariesWithYears = employeeService.getAllSalariesWithIdAndYear(empID,year,year);
            for(SalaryModel salary : salariesWithYears){
                grossIncomePerWeek += salary.getSalaryTotal();
                extraEarnings += salary.getBonus()/100;
            }

            List<AddressModel> employeeAddresses = employeeService.getAddresses(empID);
            List<StateTaxModel> stateTaxesOfEmployee = new ArrayList<>();
            List<FederalTaxModel> federalTaxesOfEmployee = new ArrayList<>();
            for(AddressModel address : employeeAddresses){
                stateTaxesOfEmployee.add(taxService.findStateTaxWithStateAndYears(address.getState(),year,year));
                federalTaxesOfEmployee.add(taxService.findFederalTaxWithCountryAndYears(address.getCountry(),year,year));
            }
            for(StateTaxModel tax : stateTaxesOfEmployee){
                totalStateTaxes=tax.getTaxRate()/100;
            }
            for(FederalTaxModel tax : federalTaxesOfEmployee){
                totalFederalTaxes = tax.getTaxRate()/100;
            }
            double totalTaxCut = totalFederalTaxes + totalStateTaxes;
            double totalDeductions = 0;
            double totalDepedantDeduction = 0;
            double totalEmployeeDeductions = 0;
            List<DependantModel> employeeDependants = employeeService.getAllWithParentSSN(employeeService.getEmployeeWithID(empID).getSsn());
            List<BenefitsModel> dependantsBenefits = new ArrayList<>();
            for(DependantModel dependant : employeeDependants){
                totalDepedantDeduction += employeeService.getEmployeeDependantBenefits(dependant.getSsn(), employeeService.getEmployeeWithID(empID).getSsn()).stream().mapToDouble(BenefitsModel::getCostPerFamily).sum()/2.5;
            }
            List<BenefitsModel> employeeBenefits = employeeService.getEmployeeBenefits(employeeService.getEmployeeWithID(empID).getSsn());
            for(BenefitsModel benefit : employeeBenefits){

                totalEmployeeDeductions += benefit.getCostPerFamily();
            }
            totalDeductions = totalEmployeeDeductions + totalDepedantDeduction;

       /* System.out.println(grossIncomePerWeek);
        System.out.println(totalDepedantDeduction);
        System.out.println(totalEmployeeDeductions);
        System.out.println(totalDeductions);
        System.out.println(grossIncomePerWeek-(grossIncomePerWeek*(totalFederalTaxes+totalStateTaxes)));*/

            w2.setSsn(employeeService.getEmployeeWithID(empID).getSsn());
            w2.setGrossIncome(grossIncomePerWeek);
            w2.setTaxCut(totalTaxCut);
            w2.setTotalDeductions(totalDeductions);
            w2.setExtraEarnings(extraEarnings);
            employeeService.saveW2(w2);
        }catch(Exception e){
            System.out.println("Couldn't find information for the paycheck");
        }

        return "redirect:/admin/ListEmployees/showEmployeeForms/" + empID;
    }


    @RequestMapping("/admin/ListEmployees/showEmployeeForms/generateW2/{empID}/{dateRange}")
    public String generateEmployeeW2(@PathVariable("empID")int empID,@PathVariable("dateRange")String dateRange, Model model){
        model.addAttribute("currentUser",employeeService.getEmployeeWithID(empID));
        W2Model w2 = employeeService.findW2BySSNAndDateRange(employeeService.getEmployeeWithID(empID).getSsn(),dateRange);
        model.addAttribute("w2",w2);

        return "adminPages/payroll/forms/generateEmployeeW2";
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/deleteW2/{empID}/{dateRange}")
    public String deleteEmployeeW2(@PathVariable("empID")int empID,@PathVariable("dateRange")String dateRange, Model model){
        employeeService.deleteEmployeeW2(employeeService.getEmployeeWithID(empID).getSsn(),dateRange);
        return "redirect:/admin/ListEmployees/showEmployeeForms/" + empID;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/addExpenseReport/{empID}")
    public ModelAndView addEmployeeExpenseReport(@PathVariable("empID")int empID){
        ModelAndView mav = new ModelAndView("adminPages/payroll/forms/addEmployeeExpenseReport");
        mav.addObject("expenseReport",new ExpenseReportModel());
        mav.addObject("currentUser",employeeService.getEmployeeWithID(empID));
        return mav;
    }

    @RequestMapping("/admin/ListEmployees/showEmployeeForms/processExpenseReport/{empID}")
    public String processEmployeeExpenseReport(@PathVariable("empID")int empID, @ModelAttribute ExpenseReportModel expenseReport){

        try{
            int year = Integer.parseInt(expenseReport.getDateRange());
            double grossIncomePerWeek = 0;
            double totalStateTaxes = 0;
            double totalFederalTaxes = 0;
            double extraEarnings = 0;
            List<SalaryModel> salariesWithYears = employeeService.getAllSalariesWithIdAndYear(empID,year,year);
            for(SalaryModel salary : salariesWithYears){
                grossIncomePerWeek += salary.getSalaryTotal();
                extraEarnings += salary.getBonus()/100;
            }

            List<AddressModel> employeeAddresses = employeeService.getAddresses(empID);
            List<StateTaxModel> stateTaxesOfEmployee = new ArrayList<>();
            List<FederalTaxModel> federalTaxesOfEmployee = new ArrayList<>();
            for(AddressModel address : employeeAddresses){
                stateTaxesOfEmployee.add(taxService.findStateTaxWithStateAndYears(address.getState(),year,year));
                federalTaxesOfEmployee.add(taxService.findFederalTaxWithCountryAndYears(address.getCountry(),year,year));
            }
            for(StateTaxModel tax : stateTaxesOfEmployee){
                totalStateTaxes=tax.getTaxRate()/100;
            }
            for(FederalTaxModel tax : federalTaxesOfEmployee){
                totalFederalTaxes = tax.getTaxRate()/100;
            }
            double totalTaxCut = totalFederalTaxes + totalStateTaxes;
            double totalDeductions = 0;
            double totalDepedantDeduction = 0;
            double totalEmployeeDeductions = 0;
            List<DependantModel> employeeDependants = employeeService.getAllWithParentSSN(employeeService.getEmployeeWithID(empID).getSsn());
            List<BenefitsModel> dependantsBenefits = new ArrayList<>();
            for(DependantModel dependant : employeeDependants){
                totalDepedantDeduction += employeeService.getEmployeeDependantBenefits(dependant.getSsn(), employeeService.getEmployeeWithID(empID).getSsn()).stream().mapToDouble(BenefitsModel::getCostPerFamily).sum()/2.5;
            }
            List<BenefitsModel> employeeBenefits = employeeService.getEmployeeBenefits(employeeService.getEmployeeWithID(empID).getSsn());
            for(BenefitsModel benefit : employeeBenefits){

                totalEmployeeDeductions += benefit.getCostPerFamily();
            }
            totalDeductions = totalEmployeeDeductions + totalDepedantDeduction;

       /* System.out.println(grossIncomePerWeek);
        System.out.println(totalDepedantDeduction);
        System.out.println(totalEmployeeDeductions);
        System.out.println(totalDeductions);
        System.out.println(grossIncomePerWeek-(grossIncomePerWeek*(totalFederalTaxes+totalStateTaxes)));*/

            expenseReport.setSsn(employeeService.getEmployeeWithID(empID).getSsn());
            expenseReport.setGrossIncome(grossIncomePerWeek);
            expenseReport.setTotalDeductions(totalDeductions);
            expenseReport.setExtraEarnings(extraEarnings);
            employeeService.saveExpenseReport(expenseReport);
            /*System.out.println(expenseReport.getSsn()+","+expenseReport.getGrossIncome()+","+expenseReport.getTotalExpense()+","+expenseReport.getTotalDeductions());*/
        }catch(Exception e){
            System.out.println("Couldn't find information for the paycheck");
        }

        return "redirect:/admin/ListEmployees/showEmployeeForms/" + empID;
    }


    @RequestMapping("/admin/ListEmployees/showEmployeeForms/generateExpenseReport/{empID}/{dateRange}")
    public String generateEmployeeExpenseReport(@PathVariable("empID")int empID,@PathVariable("dateRange")String dateRange, Model model){
        model.addAttribute("currentUser",employeeService.getEmployeeWithID(empID));
        ExpenseReportModel expenseReport = employeeService.findExpenseReportsBySSNAndDateRange(employeeService.getEmployeeWithID(empID).getSsn(),dateRange);
        model.addAttribute("expenseReport",expenseReport);

        return "adminPages/payroll/forms/generateEmployeeExpenseReport";
    }



    @RequestMapping("/admin/ListEmployees/showEmployeeForms/deleteExpenseReport/{empID}/{dateRange}")
    public String deleteEmployeeExpenseReport(@PathVariable("empID")int empID,@PathVariable("dateRange")String dateRange, Model model){
        employeeService.deleteEmployeeExpenseReport(employeeService.getEmployeeWithID(empID).getSsn(),dateRange);
        return "redirect:/admin/ListEmployees/showEmployeeForms/" + empID;
    }
    //==================================================================================================================

//Payroll Employee Stuff================================================================================================
//Payroll Tax Stuff=====================================================================================================

    @RequestMapping("/admin/ListTaxInformation")
    public ModelAndView listTaxInformation(){
        ModelAndView mav = new ModelAndView("adminPages/payroll/tax/showTaxOptions");
        List<StateTaxModel> stateTaxes = taxService.listAllStateTaxes();
        List<FederalTaxModel> federalTaxes = taxService.listAllFederalTaxes();
        mav.addObject("stateTaxes",stateTaxes);
        mav.addObject("federalTaxes",federalTaxes);
        return mav;

    }
    @RequestMapping("/admin/ListTaxInformation/addStateTax")
    public String addStateTax(Model model){
        model.addAttribute("stateTax",new StateTaxModel());
        return "adminPages/payroll/tax/addStateTax";

    }

    @RequestMapping("/admin/ListTaxInformation/processStateTax")
    public String processStateTax(@ModelAttribute StateTaxModel tax){
        if(taxService.stateTaxIsUinque(tax)){
            taxService.saveStateTax(tax);
        }

        return "redirect:/admin/ListTaxInformation/";
    }

    @RequestMapping("/admin/ListTaxInformation/addFederalTax")
    public String addFederalTax(Model model){
        model.addAttribute("federalTax",new FederalTaxModel());
        return "adminPages/payroll/tax/addFederalTax";

    }

    @RequestMapping("/admin/ListTaxInformation/processFederalTax")
    public String processFederalTax(@ModelAttribute FederalTaxModel tax){
        if(taxService.federalTaxIsUinque(tax)) {
            taxService.saveFederalTax(tax);
        }
        return "redirect:/admin/ListTaxInformation";
    }

    @RequestMapping("/admin/ListTaxInformation/deleteStateTax/{rate}/{state}/{year}")
    public String deleteStateTax(@PathVariable("rate")double rate,@PathVariable("state")String state, @PathVariable("year")int year){
        taxService.deleteStateTax(rate,state,year);
        return "redirect:/admin/ListTaxInformation";
    }

    @RequestMapping("/admin/ListTaxInformation/deleteFederalTax/{rate}/{country}/{year}")
    public String deleteFederalTax(@PathVariable("rate")double rate,@PathVariable("country")String country, @PathVariable("year")int year){
        taxService.deleteFederalTax(rate,country,year);
        return "redirect:/admin/ListTaxInformation";
    }










//Payroll Tax Stuff=====================================================================================================
//Login Stuff==========================================================================================================
    @RequestMapping("/admin/ListUsers")
    public String listUsers(Model model){
        List<UserModel> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "adminPages/ListUsers";
    }


    @RequestMapping("/admin/edit/{id}")
    public ModelAndView showEditUserForm(@PathVariable(name="id") int id){
        ModelAndView mav = new ModelAndView("adminPages/edit_user");
        UserModel user = userService.get(id);
        mav.addObject("user",user);
        return mav;
    }

    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("user") UserModel user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String prevPassword = jdbcTemplate.queryForObject("SELECT password FROM login.users WHERE user_id="+user.getUser_id(),new Object[0],String.class);
        System.out.println(prevPassword);
        String newPassword = passwordEncoder.encode(user.getPassword());

        if(prevPassword.equals(user.getPassword())){
            System.out.println("Password MATCH!");
            System.out.println(prevPassword);

        }else{
            user.setPassword(newPassword);
        }
        userService.save(user);

        return "redirect:/admin/ListUsers";
    }

    @RequestMapping(value="/admin/delete/{id}", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute("id") int id){
        userService.delete(id);
        return "redirect:/admin/ListUsers";
    }

    @RequestMapping("/admin/addUser")
    public String addUser(Model model){
        model.addAttribute("user", new UserModel());


        return "adminPages/create_user";

    }

    @RequestMapping(value="/admin/processRegristriation", method = RequestMethod.POST)
    public String processRegristriation(@Validated UserModel user, BindingResult bindingResult){

        if(!bindingResult.hasErrors() && !userService.userExists(user.getUsername())){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(newPassword);
            userService.save(user);

            return "redirect:/admin/ListUsers";
        }else{
            return "redirect:/admin/ListUsers";
        }

    }

    //Login Stuff==========================================================================================================

}
