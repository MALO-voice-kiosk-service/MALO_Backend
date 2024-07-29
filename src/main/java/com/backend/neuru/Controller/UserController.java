package com.backend.neuru.Controller;

import com.backend.neuru.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    @Autowired
    private final UserService userService;

    // 회원가입 API
//    @PostMapping(value = "/signup")
//    public ResponseDTO<?> signup(@Valid @RequestBody AuthDTO authDTO){
//        return authService.signup(authDTO);
//    }

}
