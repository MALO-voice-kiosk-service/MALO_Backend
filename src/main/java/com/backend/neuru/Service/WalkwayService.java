package com.backend.neuru.Service;

import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.DTO.WalkwayDTO;
import com.backend.neuru.Entity.CityEntity;
import com.backend.neuru.Entity.WalkwayEntity;
import com.backend.neuru.Entity.WalkwayJSONEntity;
import com.backend.neuru.Repository.CityRepository;
import com.backend.neuru.Repository.WalkwayJSONRepository;
import com.backend.neuru.Repository.WalkwayRepository;
import com.backend.neuru.exception.CustomException;
import com.backend.neuru.exception.ErrorCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalkwayService {
    private final WalkwayJSONRepository walkwayJSONRepository;
    private final WalkwayRepository walkwayRepository;
    private final CityRepository cityRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public ResponseDTO<?> fetchDataAndSave(WalkwayDTO.walkwayFetchDTO walkwayFetchDTO) throws IOException {
        log.info("프론트 입력받은 cityID : ", walkwayFetchDTO.getCityID());
        log.info("프론트 입력받은 KEYWORD : ", walkwayFetchDTO.getKeyword());
        Optional<CityEntity> cityEntity = cityRepository.findById(walkwayFetchDTO.getCityID());
        if (cityEntity.isPresent()) {
            String url1 = String.format("https://map.seoul.go.kr/openapi/v5/KEY80_d205ac47ac50400588ae474e5c0f3e2b/public/themes/contents/ko?page_size=20&page_no=1&coord_x=%s&coord_y=%s&distance=2000&search_type=0&search_name=&theme_id=11102801", cityEntity.get().getCOT_COORD_X(), cityEntity.get().getCOT_COORD_Y());
            String response = restTemplate.getForObject(url1, String.class);
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode bodyArray = rootNode.path("body");

            String response2 = "";
            for (JsonNode node : bodyArray) {
                JsonNode standard = node.get("COT_VALUE_01");
                // 보행로 데이터면 상세정보 정보 날리기
                if (standard != null) {
                    String walkway_id = node.get("COT_CONTS_ID").asText();
                    String url2 = String.format("https://map.seoul.go.kr/openapi/v5/KEY80_d205ac47ac50400588ae474e5c0f3e2b/public/themes/contents/detail?theme_id=11102801&conts_id=%s", walkway_id);
                    response2 = restTemplate.getForObject(url2, String.class);

                    JsonNode wayNode = objectMapper.readTree(response2);
                    JsonNode bodyArray2 = wayNode.path("body");
                    JsonNode realNode = bodyArray2.get(0);

                    WalkwayDTO.WalkwayResponseDTO dto = new WalkwayDTO.WalkwayResponseDTO();

                    JsonNode guNode = realNode.get("COT_GU_NAME");
                    if (guNode != null)
                        dto.setCity_name(guNode.asText());
                    else
                        dto.setCity_name("");

                    dto.setQuality_description(standard.asText());

                    JsonNode inclineNode = realNode.get("COT_VALUE_04");
                    if (inclineNode != null)
                        dto.setInclination(inclineNode.asText());
                    else
                        dto.setInclination("");

                    JsonNode widthNode = realNode.get("COT_VALUE_02");
                    if (widthNode != null)
                        dto.setWidth(widthNode.asText());
                    else
                        dto.setWidth("");

                    JsonNode textureNode = realNode.get("COT_VALUE_05");
                    if (textureNode != null)
                        dto.setTexture(textureNode.asText());
                    else
                        dto.setTexture("");

                    JsonNode geomNode = realNode.get("COT_CONTS_GEOM");
                    if (geomNode != null)
                        dto.setCOT_CONTS_GEOM(geomNode.asText());
                    else
                        dto.setCOT_CONTS_GEOM("");

                    response2 = guNode.asText() + inclineNode.asText() + widthNode.asText() + textureNode.asText() + geomNode.asText();

                    // 구 중심좌표 입력 -> 산책로 리스트 받음 -> 산책로 JSON 테이블에 각각 생성
                    WalkwayJSONEntity entity = new WalkwayJSONEntity();
                    entity.setCity_name(dto.getCity_name());
                    entity.setInclination(dto.getInclination());
                    entity.setQuality_description(dto.getQuality_description());
                    entity.setCOT_CONTS_GEOM(dto.getCOT_CONTS_GEOM());
                    entity.setTexture(dto.getTexture());
                    entity.setWidth(dto.getWidth());

                    walkwayJSONRepository.save(entity);
                    Long id = entity.getId();
                    Optional<WalkwayJSONEntity> walkwayJSONEntity = walkwayJSONRepository.findById(id);

                    // 산책로 JSON Entity 각각에 WalkwayEntity 생성
                    WalkwayEntity walkwayEntity = new WalkwayEntity();
                    if (walkwayJSONEntity.isPresent()) {
                        walkwayEntity.setWalkwayJSON(walkwayJSONEntity.get());
                        walkwayEntity.setWalkway_description(null);
                        walkwayEntity.setCityID(walkwayFetchDTO.getCityID());
                        walkwayEntity.setKeyword(walkwayFetchDTO.getKeyword());
                        walkwayEntity.setLike_count(0);
                        walkwayRepository.save(walkwayEntity);

                    } else {
                        throw new CustomException(ErrorCode.WALKWAY_JSON_NOT_FOUND);
                    }
                } else continue;
            }
            return ResponseDTO.success("서울맵에 산책로 리스트 가져와서 DB 저장 성공", "");

        } else{
            throw new CustomException(ErrorCode.CITY_NOT_FOUND);
        }

    }
}