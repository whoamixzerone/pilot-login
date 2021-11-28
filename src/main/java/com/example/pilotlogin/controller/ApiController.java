package com.example.pilotlogin.controller;

import com.example.pilotlogin.dto.User;
import com.example.pilotlogin.exception.ApiException;
import com.example.pilotlogin.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class ApiController {

    private final String jwtKey = "ZerOne1234";
    JwtToken jwtToken = new JwtToken();

    @PostMapping("/auth/login")
    public String login(@RequestBody User user) {
        log.info("user : {}", user);

        JSONObject jsonObject = new JSONObject();
        if(user.getAccount().equals("devbadak") && user.getPassword().equals("1234")) {
            String token = jwtToken.createToken(jwtKey);
            jsonObject.put("accessToken", token);
        } else {

        }

        return jsonObject.toString();
    }

    @GetMapping("/v1/users/me")
    public String userInfo(@RequestHeader String authorization) throws UnsupportedEncodingException {
        log.info("authorization : {}", authorization);
        String[] token = authorization.split("Bearer ");
        String result = jwtToken.verifyToken(jwtKey, token[1]);
        log.info("reuslt : {}", result);

//        return ResponseEntity.status(200).body(result);
        return "";
    }
}
