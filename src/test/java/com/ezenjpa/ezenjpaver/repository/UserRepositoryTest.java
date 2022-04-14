package com.ezenjpa.ezenjpaver.repository;

import com.ezenjpa.ezenjpaver.VO.UserListForAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void getAllForAdmin() {
        List<UserListForAdmin> userListForAdmins = userRepository.getAllForAdmin();
        userListForAdmins.forEach(user -> System.out.println(user.toString())
        );
    }
}