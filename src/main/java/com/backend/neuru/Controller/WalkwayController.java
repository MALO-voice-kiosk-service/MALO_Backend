package com.backend.neuru.Controller;

import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.DTO.WalkwayDTO;
import com.backend.neuru.Entity.WalkwayEntity;
import com.backend.neuru.Service.WalkwayService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/walkway")
@RequiredArgsConstructor
@Validated
public class WalkwayController {
    @Autowired
    private final WalkwayService walkwayService;

    @PostMapping(value = "/fetchWalkwayData")
    public ResponseDTO<?> fetchWalkwayData(@RequestBody WalkwayDTO.walkwayFetchDTO walkwayFetchDTO) {
        try {
            return walkwayService.fetchDataAndSave(walkwayFetchDTO);
        } catch (IOException e) {
            return ResponseDTO.error("서울맵에 산책로 리스트 가져와서 DB 저장 실패");
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseDTO<?> getOneWalkway(@PathVariable Long id) {
        try {
            return walkwayService.getOneWalkway(id);
        } catch (IOException e) {
            return ResponseDTO.error("산책로 상세 정보 조회 실패");
        }
    }

    @PostMapping(value = "/summaryWalkway/{id}")
    public ResponseDTO<?> summaryWalkway(@PathVariable("id") Long id) {
        try {
            return walkwayService.getSummary(id);
        }catch (IOException e){
            return ResponseDTO.error("네이버 클로바에 요약 요청 실패");
        }

    }

    @PostMapping(value = "/getWalkwayList/{cityID}/{keyword}")
    public List<WalkwayEntity> getWalkwayList(@PathVariable("cityID") Long cityID, @PathVariable("keyword") int keyword) {
        try{
            return walkwayService.getWalkwayList(cityID, keyword);
        }catch (IOException e){
            List<WalkwayEntity> empty = new ArrayList<>();
            return empty;
        }
    }
}

//    @GetMapping(value = "/{id}")
//    public ResponseDTO<?> getWalkwayInfo(@PathVariable("id") Long id) {}
