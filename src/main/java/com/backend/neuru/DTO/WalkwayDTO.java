package com.backend.neuru.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WalkwayDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class WalkwayResponseDTO{
        private String city_name;
        private String quality_description;
        private String width;
        private String inclination;
        private String texture;
        private String COT_CONTS_GEOM;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class walkwayFetchDTO{
        private Long cityID;
        private int keyword;
    }
}

