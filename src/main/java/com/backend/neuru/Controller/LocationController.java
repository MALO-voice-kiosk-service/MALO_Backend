package com.backend.neuru.Controller;


import com.backend.neuru.DTO.CityDTO;
import com.backend.neuru.DTO.PostDTO;
import com.backend.neuru.DTO.ResponseDTO;
import com.backend.neuru.Service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/location")
@RequiredArgsConstructor
@Validated
public class LocationController {
    @Autowired
    private final LocationService locationService;

    // city 등록 API
    @PostMapping(value = "/city")
    public ResponseDTO<?> registerCity(@RequestBody CityDTO.cityRegisterDTO cityRegisterDTO) {
        return locationService.registerCity(cityRegisterDTO);
    }

    // city 조회 API
    @GetMapping(value = "/city")
    public ResponseDTO<?> getCity() {
        return locationService.getCities();
    }
}
