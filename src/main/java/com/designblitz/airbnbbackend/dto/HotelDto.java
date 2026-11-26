package com.designblitz.airbnbbackend.dto;

import com.designblitz.airbnbbackend.entity.HotelContactInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private String name;;
    private String city;
    private String[] photos;
    private String[] amenities;
    private String description;
    private HotelContactInfo contactInfo;
    private Boolean active;

}
