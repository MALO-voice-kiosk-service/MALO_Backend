package com.backend.neuru.DTO;

import jakarta.validation.constraints.Size;
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class filteredWalkwayResponseDTO{
        private Long walkway_id;

        @Size(max = 100000)
        private String COT_CONTS_GEOM;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OneWalkwayResponseDTO{
        private Long walkwayID;
        private Long cityID;
        private int likeCount;
        private String walkway_description;
    }
}

