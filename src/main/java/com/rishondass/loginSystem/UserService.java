package com.rishondass.loginSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserModel> listAll() {
        return repo.findAll();
    }

    public void save(UserModel user) {
        repo.save(user);
    }

    public UserModel get(int id) {
        return repo.findById(id).get();
    }

    public boolean userExists(String username){
        /*return !(repo.findAll().isEmpty());*/
        String test = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM login.users WHERE username = ?",new Object[]{username},String.class);
        if(Integer.parseInt(test)==0){
            return false;
        }
        return true;
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

}
