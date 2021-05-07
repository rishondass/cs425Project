package com.rishondass.loginSystem;


import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {

    @Query(value="SELECT * FROM login.users WHERE username=?1",nativeQuery = true)
    UserModel findAllWithUsername(String username);

}
