package com.backend.neuru.Service;

import com.backend.neuru.DTO.*;
import com.backend.neuru.Entity.*;
import com.backend.neuru.Repository.CityRepository;
import com.backend.neuru.Repository.LocationRepository;
import com.backend.neuru.Repository.WalkwayJSONRepository;
import com.backend.neuru.Repository.WalkwayRepository;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.stream.Location;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final CityRepository cityRepository;
    private final WalkwayJSONRepository walkwayJSONRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public ResponseDTO<?> getLocations(int category, Long walkway_id) throws IOException {
        if(category == 0){
            fetchToiletAndSave(walkway_id);
        } else if(category == 1){
            fetchChargeAndSave(walkway_id);
        }
        List<LocationEntity> locationEntities = locationRepository.findByCategory(category);
        return ResponseDTO.success("카테고리에 해당하는 장소 조회 완료", locationEntities);
    }

    @Transactional
    public ResponseDTO<?> putLocation(Long id, Boolean is_ad) throws IOException {
        Optional<LocationEntity> locationEntity = locationRepository.findById(id);
        if (locationEntity.isPresent()) {
            LocationEntity locationEntity1 = locationEntity.get();
            locationEntity1.setIs_ad(is_ad);
            locationRepository.save(locationEntity1);
            return ResponseDTO.success("장소 광고 유무 수정 완료", locationEntity1);
        } else{
            throw new CustomException(ErrorCode.LOCATION_NOT_FOUND);
        }

    }


    @Transactional
    public ResponseDTO<?> fetchToiletAndSave(Long walkway_id) throws IOException {
        Optional<WalkwayJSONEntity> walkwayJSONEntity = walkwayJSONRepository.findById(walkway_id);
        WalkwayJSONEntity jsonEntity = walkwayJSONEntity.get();
        String geomRaw = jsonEntity.getCOT_CONTS_GEOM();

        String coordinatesPart = geomRaw.substring("LINESTRING(".length(), geomRaw.length() - 1);
        String[] coordinatesArray = coordinatesPart.split(",");
        List<String> coordinates = new ArrayList<>();
        for (String coordinate : coordinatesArray) {
            coordinates.add(coordinate.trim());
        }

        int midIndex = coordinates.size() / 2;

        String[] midCoordinate = coordinates.get(midIndex).split(" ");
        String midX = midCoordinate[0];
        String midY = midCoordinate[1];

        String url = String.format("https://map.seoul.go.kr/openapi/v5/KEY80_d205ac47ac50400588ae474e5c0f3e2b/public/themes/contents/ko?page_size=20&page_no=1&coord_x=%s&coord_y=%s&distance=500&search_type=0&search_name=&theme_id=1693984068965", midX, midY);
        String response = restTemplate.getForObject(url, String.class);
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode bodyArray = rootNode.path("body");

        for (JsonNode node : bodyArray){
            String coor_x = node.get("COT_COORD_X").asText();
            String coor_y = node.get("COT_COORD_Y").asText();

            LocationEntity entity = new LocationEntity();
            entity.setCategory(0);
            entity.setCOT_COORD_X(coor_x);
            entity.setCOT_COORD_Y(coor_y);
            entity.setIs_ad(false);

            locationRepository.save(entity);

            response = coor_x + coor_y ;

        }

        return ResponseDTO.success("서울맵에 화장실 리스트 가져와서 DB 저장 성공", "");
    }

    @Transactional
    public ResponseDTO<?> fetchChargeAndSave(Long walkway_id) throws IOException {
        Optional<WalkwayJSONEntity> walkwayJSONEntity = walkwayJSONRepository.findById(walkway_id);
        WalkwayJSONEntity jsonEntity = walkwayJSONEntity.get();
        String geomRaw = jsonEntity.getCOT_CONTS_GEOM();

        String coordinatesPart = geomRaw.substring("LINESTRING(".length(), geomRaw.length() - 1);
        String[] coordinatesArray = coordinatesPart.split(",");
        List<String> coordinates = new ArrayList<>();
        for (String coordinate : coordinatesArray) {
            coordinates.add(coordinate.trim());
        }

        int midIndex = coordinates.size() / 2;

        String[] midCoordinate = coordinates.get(midIndex).split(" ");
        String midX = midCoordinate[0];
        String midY = midCoordinate[1];

        String url = String.format("https://map.seoul.go.kr/openapi/v5/KEY80_d205ac47ac50400588ae474e5c0f3e2b/public/themes/contents/ko?page_size=20&page_no=1&coord_x=%s&coord_y=%s&distance=2000&search_type=0&search_name=&theme_id=11103392", midX, midY);
        String response = restTemplate.getForObject(url, String.class);
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode bodyArray = rootNode.path("body");

        for (JsonNode node : bodyArray){
            String coor_x = node.get("COT_COORD_X").asText();
            String coor_y = node.get("COT_COORD_Y").asText();

            LocationEntity entity = new LocationEntity();
            entity.setCategory(1); //충전기
            entity.setCOT_COORD_X(coor_x);
            entity.setCOT_COORD_Y(coor_y);
            entity.setIs_ad(false);

            locationRepository.save(entity);

            response = coor_x + coor_y ;

        }

        return ResponseDTO.success("서울맵에 충전기 리스트 가져와서 DB 저장 성공", "");
    }
}
