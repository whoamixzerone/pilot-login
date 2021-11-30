package com.example.pilotlogin.controller;

import com.example.pilotlogin.dto.ErrorResponse;
import com.example.pilotlogin.dto.User;
import com.example.pilotlogin.exception.AccessDeniedException;
import com.example.pilotlogin.exception.UnauthorizedException;
import com.example.pilotlogin.exception.UserNotFoundException;
import com.example.pilotlogin.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
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
    public ResponseEntity<String> userInfo(@Valid @RequestHeader String authorization) throws UnsupportedEncodingException {
        log.info("authorization : {}", authorization);

        String result = "";
        String[] token = authorization.split("Bearer ");
        if(token.length == 2) {
            result = jwtToken.verifyToken(jwtKey, token[1]);
            log.info("reuslt : {}", result);

            if(result == null || result == "")
                throw new AccessDeniedException();
        } else {
            throw new UnauthorizedException();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("account", "foo");
        jsonObject.put("name", "반려생활");
        jsonObject.put("level", 1);

        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
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

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseEntity missingRequestHeaderException(MissingRequestHeaderException e) {
        log.info(">>>> missingRequestHeaderException()");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400", "헤더에 토큰이 없습니다."));
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity unAuthorizedException() {
        log.info(">>>>> unAuthorizedException()");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("401", "로그인이 필요합니다."));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity accessDeniedException() {
        log.info(">>>>> accessDeniedException()");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("403", "접근할 수 없습니다."));
    }
}
