package com.backend.neuru.Service;

import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.PostDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.CommentEntity;
import com.backend.neuru.Entity.PostEntity;
import com.backend.neuru.Entity.UserEntity;
import com.backend.neuru.Repository.CommentRepository;
import com.backend.neuru.Repository.PostRepository;
import com.backend.neuru.Repository.UserRepository;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    @Transactional
    public ResponseDTO<?> getAllPost() {
        List<PostEntity> postEntities = postRepository.findAll();
        return ResponseDTO.success("모든 게시글 조회 완료", postEntities);
    }

    @Transactional
    public ResponseDTO<?> getPost(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        return ResponseDTO.success("특정 게시글 조회 완료", postEntity);
    }

    @Transactional
    public ResponseDTO<?> deletePost(Long id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        postRepository.deleteById(id);
        return ResponseDTO.success("게시글 삭제 완료", postEntity);
    }

    @Transactional
    public ResponseDTO<?> registerComment(PostDTO.RegisterCommentDTO registerCommentDTO) {
        // DTO를 엔티티로 변환
        CommentEntity comment = new CommentEntity();
        comment.setComment_content(registerCommentDTO.getComment());

        commentRepository.save(comment);
        return ResponseDTO.success("댓글 등록 완료", comment);
    }
}
