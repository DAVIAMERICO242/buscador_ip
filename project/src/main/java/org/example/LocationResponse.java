package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationResponse {
    String region_code;
    String city;
    Double latitude;
    Double longitude;
}
