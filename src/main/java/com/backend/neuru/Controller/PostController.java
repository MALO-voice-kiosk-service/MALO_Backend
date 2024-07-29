package com.backend.neuru.Controller;


import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.PostDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.UserEntity;
import com.backend.neuru.Service.PostService;
import com.backend.neuru.Service.UserService;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/post")
@RequiredArgsConstructor
@Validated
public class PostController {
    @Autowired
    private final PostService postService;

    @Transactional
    public ResponseDTO<?> registerPost(PostDTO.RegisterPostDTO registerPostDTO) {
        return postService.registerPost(registerPostDTO);
    }

}
