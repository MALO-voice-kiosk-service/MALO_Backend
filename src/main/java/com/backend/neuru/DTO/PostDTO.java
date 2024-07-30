package com.backend.neuru.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RegisterPostDTO{
        private String post_title;
        private String post_content;
        private int post_tag; // 프론트측에서 tag 0:친목, 1:홍보
        private String location;
//        private String post_img;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class commentResponseDTO{
        private Long comment_id;
        private Long post_id;
        private String comment_content;
    }
}
