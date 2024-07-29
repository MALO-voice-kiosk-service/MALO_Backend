package com.backend.neuru.Service;

import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.PostDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.PostEntity;
import com.backend.neuru.Entity.UserEntity;
import com.backend.neuru.Repository.PostRepository;
import com.backend.neuru.Repository.UserRepository;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public ResponseDTO<?> registerPost(PostDTO.RegisterPostDTO registerPostDTO) {
        postRepository.save(toEntity(registerPostDTO));
        return ResponseDTO.success("게시글 등록 완료", registerPostDTO);
    }

    private PostEntity toEntity(PostDTO.RegisterPostDTO registerPostDTO) {

        return PostEntity.builder()
                .post_title(registerPostDTO.getPost_title())
                .post_tag(registerPostDTO.getPost_tag())
                .post_content(registerPostDTO.getPost_content())
                .location(registerPostDTO.getLocation())
                .build();

    }
}
