package dev.anzala.bicyclelocator.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Location extends BaseModel{
    private double latitude;
    private double longitude;
}
