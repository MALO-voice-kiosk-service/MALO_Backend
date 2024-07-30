package com.backend.neuru.Repository;

import com.backend.neuru.Entity.CommentEntity;
import com.backend.neuru.Entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findByCategory(int category);
}
