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

    @PostMapping(value = "/review")
    public ResponseDTO<?> registerReview(@RequestBody String reviewContents) throws Exception {
        return likeReviewService.registerReview(reviewContents);
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
