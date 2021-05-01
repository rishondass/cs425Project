package com.rishondass.loginSystem;


import com.rishondass.loginSystem.payroll.Models.CompanyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CompanyDataService companyRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    private UserService userService;


    @RequestMapping("/admin")
    public String admin(Model model){
        List<UserModel> listUsers = userRepo.findAll();
        List<CompanyModel> listCompany = companyRepo.findAll();
        /*List<CompanyModel> companies = jdbcTemplate.query(
                "SELECT * FROM payroll.company;",
                new RowMapper<CompanyModel>() {
                    @Override
                    public CompanyModel mapRow(ResultSet resultSet, int i) throws SQLException {
                        CompanyModel company = new CompanyModel();
                        company.setName(resultSet.getString("name"));
                        company.setSales(resultSet.getInt("sales"));
                        company.setBonusPercentage(resultSet.getInt("bonuspercentage"));
                        return company;
                    }
                }
        );*/
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listCompany",listCompany);
        /*System.out.println(jdbcTemplate.queryForList("SELECT * FROM payroll.company;"));*/
        return "admin";
    }

    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    @RequestMapping("/manager")
    public String manager(){
        return "manager";
    }
    @GetMapping("/error")
    public String error(){
        return "error";
    }


}
