package com.backend.neuru.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CityDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class cityRegisterDTO{
        private String cityName;
        private String x;
        private String y;
    }
}
