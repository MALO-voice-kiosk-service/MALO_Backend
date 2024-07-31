package com.backend.neuru.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LikeReviewDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class reviewResponseDTO{
        private Long review_id;
        private String review_content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class likeResponseDTO{
        private Long walkway_id;
        private int like_count;
    }

}
