package dev.anzala.bicyclelocator.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private String bicycleId;
    private Double latitude;
    private Double longitude;
}
