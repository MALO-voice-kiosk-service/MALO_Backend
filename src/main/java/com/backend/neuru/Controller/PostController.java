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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/post")
@RequiredArgsConstructor
@Validated
public class PostController {
    @Autowired
    private final PostService postService;

    // 게시글 등록 API
    @PostMapping(value = "")
    public ResponseDTO<?> registerPost(@RequestBody PostDTO.RegisterPostDTO registerPostDTO) {
        return postService.registerPost(registerPostDTO);
    }

    // 모든 게시글 조회 API
    @GetMapping(value = "")
    public ResponseDTO<?> getAllPost() {
        return postService.getAllPost();
    }

    // 특정 게시글 조회 API
    @GetMapping(value = "/{id}")
    public ResponseDTO<?> getPost(@PathVariable("id") Long id) {
        return postService.getPost(id);
    }

    // 게시글 삭제 API
    @DeleteMapping(value = "/{id}")
    public ResponseDTO<?> deletePost(@PathVariable("id") Long id) {
        return postService.deletePost(id);
    }

    // 댓글 등록 API
    @PostMapping(value = "/comment/{id}")
    public ResponseDTO<?> registerComment(@PathVariable("id") Long id, @RequestBody String commentContent) {
        return postService.registerComment(id, commentContent);
    }

    // 댓글 수정 API
//    @PutMapping(value = "/comment/{id}")
//    public ResponseDTO<?> fixComment(@PathVariable("id") Long id, @RequestBody String commentContent) {
//        return postService.fixComment(id, commentContent);
//    }

    // 댓글 조회 API
    @GetMapping(value = "/comment/{id}")
    public ResponseDTO<?> getComments(@PathVariable("id") Long id){
        return postService.getComments(id);
    }
}
