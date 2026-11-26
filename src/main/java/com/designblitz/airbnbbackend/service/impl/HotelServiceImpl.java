package com.designblitz.airbnbbackend.service.impl;

import com.designblitz.airbnbbackend.dto.HotelDto;
import com.designblitz.airbnbbackend.entity.Hotel;
import com.designblitz.airbnbbackend.entity.Room;
import com.designblitz.airbnbbackend.exception.ResourceNotFoundException;
import com.designblitz.airbnbbackend.repository.HotelRepository;
import com.designblitz.airbnbbackend.repository.RoomRepository;
import com.designblitz.airbnbbackend.service.HotelService;
import com.designblitz.airbnbbackend.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new Hotel with name: {}",hotelDto.getName());
        Hotel hotel= modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with Id: {}",hotelDto.getId());
        return modelMapper.map(savedHotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting Hotel with Id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Hotel not found with Id: "+id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating Hotel with Id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Hotel not found with Id: "+id));
        hotelDto.setId(id);
        hotelDto.setActive(hotel.getActive());
        modelMapper.map(hotelDto,hotel);
        hotel=hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public Boolean deleteHotelById(Long id) {
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Hotel not found with Id: "+id));
        hotelRepository.deleteById(id);
        for (Room room:hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
        }
        return true;
    }

    @Override
    public HotelDto activateHotel(Long id) {
        log.info("Activating Hotel with Id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Hotel not found with Id: "+id));
        hotel.setActive(true);
        List<Room> rooms=roomRepository.findRoomByHotel(hotel);
        for (Room room:rooms){
            log.info("Adding Invetory for room with Id: {}",room.getId());
            inventoryService.initializeRoomForAYear(room);
        }
      return  modelMapper.map(hotelRepository.save(hotel),HotelDto.class);
    }

}
