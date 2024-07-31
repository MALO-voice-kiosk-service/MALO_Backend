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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        log.info("프론트 입력받은 cityID : " + walkwayFetchDTO.getCityID());
        log.info("프론트 입력받은 KEYWORD : " + walkwayFetchDTO.getKeyword());
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
            List<WalkwayEntity> filteredWalkwayList = getWalkwayList(walkwayFetchDTO.getCityID(), walkwayFetchDTO.getKeyword());

            // filtering된 산책로 리스트 DTO로 전달
            List<WalkwayDTO.filteredWalkwayResponseDTO> filteredWalkwayResponseDTOList = new ArrayList<>();
            for (WalkwayEntity walkwayEntity : filteredWalkwayList) {
                WalkwayDTO.filteredWalkwayResponseDTO responseDTO = new WalkwayDTO.filteredWalkwayResponseDTO();
                responseDTO.setWalkway_id(walkwayEntity.getId());
                responseDTO.setCOT_CONTS_GEOM(walkwayEntity.getWalkwayJSON().getCOT_CONTS_GEOM());
                filteredWalkwayResponseDTOList.add(responseDTO);
            }
            return ResponseDTO.success("서울맵에 산책로 리스트 가져와서 DB 저장 성공", filteredWalkwayResponseDTOList);

        } else{
            throw new CustomException(ErrorCode.CITY_NOT_FOUND);
        }

    }

    @Transactional
    public ResponseDTO<?> getSummary(Long walkway_id) throws IOException
    {
        Optional<WalkwayEntity> walkwayEntity0 = walkwayRepository.findById(walkway_id);
        WalkwayEntity walkwayEntity = walkwayEntity0.get();
        WalkwayJSONEntity jsonEntity = walkwayEntity.getWalkwayJSON();

        String incline = jsonEntity.getInclination();
        String width = jsonEntity.getWidth();
        String texture = jsonEntity.getTexture();
        String standard = jsonEntity.getQuality_description();

        String sendData = incline + standard + texture + width;

        String url = "https://clovastudio.apigw.ntruss.com/testapp/v1/tasks/4ce8i8fw/completions";

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", "NTA0MjU2MWZlZTcxNDJiY8ZPMjWp2NG7Bwakg0rISBmhJbtWHS1JGai9AQ1qbRu5");
        headers.set("X-NCP-APIGW-API-KEY", "6bVMu6hHIDtbrpCEHcCzMtwk0TwMI64Y1DtV0B82");
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", "6a261f99-279a-4ddf-8102-1012466a7a65");
        headers.set("Content-Type", "application/json");

        String jsonBody = "{\n" +
                "  \"text\" : \"" + sendData + "\",\n" +
                "  \"start\" : \"\",\n" +
                "  \"restart\" : \"\",\n" +
                "  \"includeTokens\" : false,\n" +
                "  \"topP\" : 0.8,\n" +
                "  \"topK\" : 4,\n" +
                "  \"maxTokens\" : 300,\n" +
                "  \"temperature\" : 0.85,\n" +
                "  \"repeatPenalty\" : 5.0,\n" +
                "  \"stopBefore\" : [ \"<|endoftext|>\" ],\n" +
                "  \"includeProbs\" : false,\n" +
                "  \"includeAiFilters\" : true\n" +
                "}";

        // Create HttpEntity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        String res = "";
        try {
            // Send POST request
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            String responseBody = response.getBody();

            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = rootNode.path("result");
            // 산책로 설명문
            String text = resultNode.path("text").asText();

//            res = text;

            walkwayEntity.setWalkway_description(text);
            walkwayRepository.save(walkwayEntity);

        } catch (HttpClientErrorException e) {
            System.out.println("HTTP Error: " + e.getStatusCode());
            System.out.println("Response Body: " + e.getResponseBodyAsString());
        }

        return ResponseDTO.success("Naver Clova Studio 산책로 설명 생성 및 산책로 설명 DB 저장 완료", "");
    }

    @Transactional
    public List<WalkwayEntity> getWalkwayList(Long city_id, int keyword) throws IOException
    {
        List<WalkwayEntity> walkwayEntityList = walkwayRepository.findAllByCityIDAndKeyword(city_id, keyword);

        List<WalkwayEntity> returnList = new ArrayList<>();
        int cnt = 0;
        for(WalkwayEntity walkwayEntity : walkwayEntityList)
        {
            WalkwayJSONEntity jsonEntity = walkwayEntity.getWalkwayJSON();

            String incline = jsonEntity.getInclination();
            String width = jsonEntity.getWidth();

            // 추천 리스트 10개 넘으면 그만
            if(cnt > 10)
                break;

            if(keyword == 0) // 혼자
            {
                if(incline.contains("평지")){
                    returnList.add(walkwayEntity);
                    cnt++;
                }
            }
            else if(keyword == 1) // 반려견
            {
                if(width.contains("1.8m이상"))
                {
                    returnList.add(walkwayEntity);
                    cnt++;
                }
            }
            else // 동반자
            {
                returnList.add(walkwayEntity);
                cnt++;
            }

        }
        return returnList;
    }
}