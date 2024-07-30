package com.backend.neuru.Service;

import com.backend.neuru.DTO.AuthDTO;
import com.backend.neuru.DTO.PostDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.CommentEntity;
import com.backend.neuru.Entity.PostEntity;
import com.backend.neuru.Entity.UserEntity;
import com.backend.neuru.Repository.CommentRepository;
import com.backend.neuru.Repository.PostRepository;
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
        PostEntity postEntity = new PostEntity();
        postEntity.setPost_content(registerPostDTO.getPost_content());
        postEntity.setPost_title(registerPostDTO.getPost_title());
        postEntity.setPost_tag(registerPostDTO.getPost_tag());
        postEntity.setLocation(registerPostDTO.getLocation());
        postRepository.save(postEntity);
        return ResponseDTO.success("게시글 등록 완료", registerPostDTO);
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
    public ResponseDTO<?> registerComment(Long id, String commentContent) {
        // DTO를 엔티티로 변환
        CommentEntity comment = new CommentEntity();
        comment.setComment_content(commentContent);
        Optional<PostEntity> postEntity = postRepository.findById(id);
        comment.setPost(postEntity.get());
        commentRepository.save(comment);
        return ResponseDTO.success("댓글 등록 완료", comment);
    }

//    @Transactional
//    public ResponseDTO<?> fixComment(Long id, String commentContent) {
//        Optional<PostEntity> postEntity = postRepository.findById(id);
//        if(postEntity.isPresent()) {
//            Optional<CommentEntity> commentEntity = commentRepository.findByPost(postEntity);
//            commentEntity.get().setComment_content(commentContent);
//            commentRepository.save(commentEntity.get());
//            return ResponseDTO.success("댓글 수정 완료", commentEntity);
//        } else{
//            throw new CustomException(ErrorCode.POST_NOT_FOUND);
//        }
//    }

    @Transactional
    public ResponseDTO<?> getComments(Long id){
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isPresent()) {
            List<CommentEntity> commentEntities = commentRepository.findByPost(postEntity);
            return ResponseDTO.success("모든 댓글 조회 완료", commentEntities);
        } else{
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
    }
}
