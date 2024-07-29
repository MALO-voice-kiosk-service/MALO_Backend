package com.backend.neuru.Controller;

import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.UserEntity;
import com.backend.neuru.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    @Autowired
    private final UserService userService;

    // 회원가입 API
    @PostMapping(value = "/signup")
    public ResponseDTO<?> signup(@RequestBody AuthDTO.SignupDTO signupDTO){
        return userService.signup(signupDTO);
    }

    // 로그인 API
    @PostMapping(value = "/login")
    public ResponseDTO<?> login(@RequestBody AuthDTO.LonginDTO longinDTO) throws Exception {
        return userService.login(longinDTO);
    }

    // 로그아웃 API
    @PostMapping(value = "/logout/{id}")
    public ResponseDTO<?> logout(@PathVariable("id") Long id) throws Exception {
        return userService.logout(id);
    }

    // 회원정보 변경 API
    @PutMapping(value = "/update/{id}")
    public ResponseDTO<?> updateUser(@PathVariable("id") Long id, @RequestBody String nickname){
        return userService.updateUser(id, nickname);
    }

    // 회원정보 조회 API
    @GetMapping(value = "/get/{id}")
    public ResponseDTO<?> getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

}
