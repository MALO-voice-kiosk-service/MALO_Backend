package com.backend.neuru.Repository;

import com.backend.neuru.Entity.PostEntity;
import com.backend.neuru.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
