package com.backend.neuru.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocationDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class locationRegisterDTO{
        private String x;
        private String y;
        private int category; // 0:화장실, 1:충전기, 2:영화관
        private Boolean is_ad;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class getLocationDTO{
        private String x;
        private String y;
    }
}
