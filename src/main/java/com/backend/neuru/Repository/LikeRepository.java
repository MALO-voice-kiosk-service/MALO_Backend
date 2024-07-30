package com.backend.neuru.Repository;

import com.backend.neuru.Entity.LikeEntity;
import com.backend.neuru.Entity.WalkwayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByWalkway(WalkwayEntity walkwayEntity);
}
