package com.backend.neuru.Repository;

import com.backend.neuru.Entity.CommentEntity;
import com.backend.neuru.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost(Optional<PostEntity> postEntity);
}
