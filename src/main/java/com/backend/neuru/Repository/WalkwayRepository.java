package com.backend.neuru.Repository;

import com.backend.neuru.Entity.LocationEntity;
import com.backend.neuru.Entity.WalkwayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkwayRepository extends JpaRepository<WalkwayEntity, Long> {
}
