package com.backend.neuru.Repository;

import com.backend.neuru.Entity.CityEntity;
import com.backend.neuru.Entity.WalkwayJSONEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalkwayJSONRepository extends JpaRepository<WalkwayJSONEntity, Long> {
}
