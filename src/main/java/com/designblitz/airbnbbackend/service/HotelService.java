package com.designblitz.airbnbbackend.service;

import com.designblitz.airbnbbackend.dto.HotelDto;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id,HotelDto hotelDto);

    Boolean deleteHotelById(Long id);

    HotelDto activateHotel(Long id);
}
