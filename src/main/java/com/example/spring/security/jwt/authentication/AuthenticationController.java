package com.example.spring.security.jwt.authentication;

import com.example.spring.security.jwt.constant.MessageConstants;
import com.example.spring.security.jwt.response.JwtResponse;
import com.example.spring.security.jwt.service.JwtService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:8080")
//@RestController
@Controller
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> singIn(@RequestBody UserLogin user) throws JOSEException {
        JwtResponse jwtResponse = new JwtResponse();
        HttpStatus httpStatus = null;
        if (authenticationService.checkLogin(user)) {
            String[] roles = authenticationService.getRoleByUser(user);
            jwtResponse.setRoles(Arrays.asList(roles));
            jwtResponse.setAccessToken(jwtService.generateTokenLogin(user.getUsername()));
            jwtResponse.setExpiredAccessToken(new Date());
            jwtResponse.setType(MessageConstants.TYPE_TOKEN);
//            jwtResponse.setRoles(roles);
            httpStatus = HttpStatus.OK;
        } else {
            jwtResponse.setMessage("Wrong user or password");
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jwtResponse, httpStatus);
    }
}
