package com.backend.neuru.Controller;

import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.DTO.WalkwayDTO;
import com.backend.neuru.Service.WalkwayService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/api/walkway")
@RequiredArgsConstructor
@Validated
public class WalkwayController {
    @Autowired
    private WalkwayService walkwayService;

    @Autowired
    public WalkwayController(WalkwayService walkwayService){
        this.walkwayService = walkwayService;
    }

    @PostMapping(value = "/fetchWalkwayData")
    public ResponseDTO<?> fetchWalkwayData(@RequestBody WalkwayDTO.walkwayFetchDTO walkwayFetchDTO) {
        try {
            return walkwayService.fetchDataAndSave(walkwayFetchDTO);
        } catch (IOException e) {
            return ResponseDTO.error("서울맵에 산책로 리스트 가져와서 DB 저장 실패");
        }
    }
}