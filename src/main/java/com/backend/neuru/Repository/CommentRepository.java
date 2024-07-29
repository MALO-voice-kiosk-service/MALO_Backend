package com.backend.neuru.Repository;

import com.backend.neuru.Entity.CommentEntity;
import com.backend.neuru.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
