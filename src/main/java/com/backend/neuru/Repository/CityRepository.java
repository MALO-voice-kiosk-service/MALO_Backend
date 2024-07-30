package com.backend.neuru.Repository;

import com.backend.neuru.Entity.CityEntity;
import com.backend.neuru.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
}
