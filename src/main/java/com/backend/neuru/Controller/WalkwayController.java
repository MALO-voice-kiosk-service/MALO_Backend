package com.backend.neuru.Controller;

import com.backend.neuru.Service.WalkwayService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/fetchWalkwayData")
    public String fetchWalkwayData(@RequestParam String coor_x, @RequestParam String coor_y) {
        try {
            return walkwayService.fetchDataAndSave(coor_x, coor_y);
        } catch (IOException e) {
            return "Failed to fetch data: " + e.getMessage();
        }
    }
}