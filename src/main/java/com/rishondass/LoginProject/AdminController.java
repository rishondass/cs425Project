package com.rishondass.loginSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

}
