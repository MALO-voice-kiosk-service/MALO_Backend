package com.backend.neuru.Service;

import com.backend.neuru.DTO.CityDTO;
import com.backend.neuru.DTO.LocationDTO;
import com.backend.neuru.DTO.PostDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Entity.CityEntity;
import com.backend.neuru.Entity.LocationEntity;
import com.backend.neuru.Entity.PostEntity;
import com.backend.neuru.Repository.CityRepository;
import com.backend.neuru.Repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final CityRepository cityRepository;

    @Transactional
    public ResponseDTO<?> registerCity(CityDTO.cityRegisterDTO cityRegisterDTO) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCityName(cityRegisterDTO.getCityName());
        cityEntity.setCOT_COORD_Y(cityRegisterDTO.getY());
        cityEntity.setCOT_COORD_X(cityRegisterDTO.getX());
        cityRepository.save(cityEntity);
        return ResponseDTO.success("도시(구) 등록 완료", cityEntity);
    }

    @Transactional
    public ResponseDTO<?> getCities() {
        List<CityEntity> cities = cityRepository.findAll();
        return ResponseDTO.success("모든 도시(구) 조회 완료", cities);
    }

    @Transactional
    public ResponseDTO<?> registerLocation(LocationDTO.locationRegisterDTO locationRegisterDTO) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCOT_COORD_X(locationRegisterDTO.getX());
        locationEntity.setCOT_COORD_Y(locationRegisterDTO.getY());
        locationEntity.setIs_ad(locationRegisterDTO.getIs_ad());
        locationEntity.setCategory(locationRegisterDTO.getCategory());
        locationRepository.save(locationEntity);
        return ResponseDTO.success("장소 등록 완료", locationEntity);
    }

    @Transactional
    public ResponseDTO<?> getLocations(int category) {
        List<LocationEntity> locationEntities = locationRepository.findByCategory(category);
        return ResponseDTO.success("카테고리에 해당하는 장소 조회 완료", locationEntities);
    }
}
