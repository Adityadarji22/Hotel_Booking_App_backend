package com.designblitz.airbnbbackend.service.impl;

import com.designblitz.airbnbbackend.dto.RoomDto;
import com.designblitz.airbnbbackend.entity.Hotel;
import com.designblitz.airbnbbackend.entity.Room;
import com.designblitz.airbnbbackend.exception.ResourceNotFoundException;
import com.designblitz.airbnbbackend.repository.HotelRepository;
import com.designblitz.airbnbbackend.repository.RoomRepository;
import com.designblitz.airbnbbackend.service.InventoryService;
import com.designblitz.airbnbbackend.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        log.info("Creating a New Room in Hotel with Id: {}",hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(() ->  new ResourceNotFoundException("Hotel not found with Id: "+hotelId));
        Room room=modelMapper.map(roomDto,Room.class);
        room.setHotel(hotel);
        room=roomRepository.save(room);
        if (hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(long hotelId) {
        log.info("Trying to get All Rooms with hotel Id: {}",hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(() ->  new ResourceNotFoundException("Hotel not found with Id: "+hotelId));
        List<Room> rooms=roomRepository.findRoomByHotel(hotel);
        List<RoomDto> roomDtos=rooms.stream()
                .map(e -> modelMapper.map(e,RoomDto.class))
                .toList();
        return roomDtos;
    }

    @Override
    public RoomDto getRoomById(Long Id) {
        log.info("Getting the Room with Id: {}",Id);
        Room room=roomRepository
                .findById(Id)
                .orElseThrow(() ->  new ResourceNotFoundException("Room not found with Id: "+Id));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long Id) {
        log.info("Deleteing Room with Id: {}",Id);
        Room room=roomRepository
                .findById(Id)
                .orElseThrow(() ->  new ResourceNotFoundException("Room not found with Id: "+Id));
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(Id);
    }
}
