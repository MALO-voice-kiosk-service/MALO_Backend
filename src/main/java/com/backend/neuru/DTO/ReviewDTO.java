package com.backend.neuru.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class reviewResponseDTO{
        private Long review_id;
        private String review_content;
    }
}
