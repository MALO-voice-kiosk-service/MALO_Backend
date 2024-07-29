package com.backend.neuru.Repository;

import com.backend.neuru.Entity.MyWalkwayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyWalkwayRepository extends JpaRepository<MyWalkwayEntity, Long> {
}
