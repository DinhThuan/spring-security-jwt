package com.example.spring.security.jwt.authentication;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Service
public class AuthenticationService {
    public static List<UserLogin> userList = new ArrayList<>();

    static {
        userList.add(new UserLogin(1, "admin", "123456"));
        userList.get(0).setRoles(new String[]{"ROLE_ADMIN"});
        userList.add(new UserLogin(2, "user", "123456"));
        userList.get(1).setRoles(new String[]{"ROLE_USER"});
        userList.add(new UserLogin(3, "all", "123456", new String[]{"ROLE_ALL"}));
    }

    public String[] getRoleByUser(UserLogin userLogin) {
        for (UserLogin user : userList) {
            if (userLogin.getUsername().equals(userLogin.getUsername())) {
                return user.getRoles();
            }
        }
        return null;
    }

    public boolean checkLogin(UserLogin user) {
        for (UserLogin userExist : userList) {
            if (StringUtils.equals(user.getUsername(), userExist.getUsername()) && StringUtils.equals(user.getPassword(), userExist.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public UserLogin loadUserByUsername(String username) {
        for (UserLogin user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
