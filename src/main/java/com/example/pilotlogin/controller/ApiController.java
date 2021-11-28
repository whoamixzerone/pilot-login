package com.example.pilotlogin.controller;

import com.example.pilotlogin.dto.ErrorResponse;
import com.example.pilotlogin.dto.User;
import com.example.pilotlogin.exception.UserNotFoundException;
import com.example.pilotlogin.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/")
@Slf4j
public class ApiController {

    private final String jwtKey = "ZerOne1234";
    JwtToken jwtToken = new JwtToken();

    @PostMapping("/auth/login")
    public String login(@Valid @RequestBody User user) {
        log.info("user : {}", user);

        JSONObject jsonObject = new JSONObject();
        if(user.getAccount().equals("devbadak") && user.getPassword().equals("1234")) {
            String token = jwtToken.createToken(jwtKey);
            jsonObject.put("accessToken", token);
        } else {
            throw new UserNotFoundException();
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(">>>> MethodArgumentNotValidException() ");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400", "입력값을 확인해주세요."));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity userNotFoundException() {
        log.info(">>>> userNotFoundException()");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("400", "아이디 혹은 비밀번호가 옳바르지 않습니다."));
    }
}
