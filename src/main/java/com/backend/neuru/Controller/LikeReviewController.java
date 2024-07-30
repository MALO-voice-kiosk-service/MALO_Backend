package com.backend.neuru.Controller;


import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Service.LikeReviewService;
import com.backend.neuru.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Validated
public class LikeReviewController {
    @Autowired
    private final LikeReviewService likeReviewService;

    // 산책로 리뷰 등록 API
    @PostMapping(value = "/review/{id}")
    public ResponseDTO<?> registerReview(@PathVariable("id") Long id, @RequestBody String reviewContents) throws Exception {
        return likeReviewService.registerReview(id, reviewContents);
    }

    // 산책로 리뷰 수정 API
    @PutMapping(value = "/review/{id}")
    public ResponseDTO<?> fixReview(@PathVariable("id") Long id, @RequestBody String commentContent) {
        return likeReviewService.fixReview(id, commentContent);
    }

    @PostMapping(value = "/like/{id}")
    public ResponseDTO<?> registerLike(@PathVariable("id") Long id) throws Exception {
        return likeReviewService.registerLike(id);
    }

    @DeleteMapping(value = "/like/{id}")
    public ResponseDTO<?> deleteLike(@PathVariable("id") Long id) throws Exception {
        return likeReviewService.deleteLike(id);
    }
}
