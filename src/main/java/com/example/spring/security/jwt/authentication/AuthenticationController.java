package com.example.spring.security.jwt.authentication;

import com.example.spring.security.jwt.constant.MessageConstants;
import com.example.spring.security.jwt.response.JwtResponse;
import com.example.spring.security.jwt.service.JwtService;
import com.example.spring.security.jwt.service.RefreshTokenService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;

@CrossOrigin("http://localhost:8080")
//@RestController
@Controller
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> singIn(@RequestBody UserLogin user) throws JOSEException, ParseException {
        JwtResponse jwtResponse = new JwtResponse();
        HttpStatus httpStatus = null;
        if (authenticationService.checkLogin(user)) {
            String[] roles = authenticationService.getRoleByUser(user);
            jwtResponse.setRoles(Arrays.asList(roles));
            String token  = jwtService.generateTokenLogin(user.getUsername());
            jwtResponse.setAccessToken(token);
            jwtResponse.setExpiredAccessToken(jwtService.getExpirationDateFromToken(token));
            jwtResponse.setType(MessageConstants.TYPE_TOKEN);
//            jwtResponse.setRoles(roles);
            jwtResponse.setRefreshToken(refreshTokenService.createRefreshToken());
            httpStatus = HttpStatus.OK;
        } else {
            jwtResponse.setMessage("Wrong user or password");
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(jwtResponse, httpStatus);
    }

//    @PostMapping("/refreshtoken")
//    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
//        String requestRefreshToken = request.getRefreshToken();
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(user -> {
//                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in database!"));
//    }
}
