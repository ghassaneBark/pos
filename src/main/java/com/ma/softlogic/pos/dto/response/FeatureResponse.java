package com.ma.softlogic.pos.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureResponse {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String category;
}
