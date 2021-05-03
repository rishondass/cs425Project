package com.rishondass.loginSystem;

import com.fasterxml.jackson.databind.ser.std.InetAddressSerializer;
import com.rishondass.loginSystem.payroll.Models.AddressModel;
import com.rishondass.loginSystem.payroll.Models.EmployeeModel;
import com.rishondass.loginSystem.payroll.Services.EmployeeService;
import org.apache.tomcat.jni.Address;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JdbcTemplate jdbcTemplate;
//Payroll Stuff========================================================================================================


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
//Payroll Stuff========================================================================================================

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
