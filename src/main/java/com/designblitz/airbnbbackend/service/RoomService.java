package com.designblitz.airbnbbackend.service;

import com.designblitz.airbnbbackend.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(long hotelId);

    RoomDto getRoomById(Long Id);

    void deleteRoomById(Long Id);
}
