package com.designblitz.airbnbbackend.controller;

import com.designblitz.airbnbbackend.dto.HotelDto;
import com.designblitz.airbnbbackend.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        log.info("Attempting to Create a new Hotel with name: {}",hotelDto.getName());
        return new ResponseEntity<>(hotelService.createNewHotel(hotelDto), HttpStatus.CREATED);
    }

    @GetMapping("/{hotelID}")
    public ResponseEntity<HotelDto> getHotelByID(@PathVariable Long hotelID){
        log.info("Try to get Hotel with Id: {}",hotelID);
        return new ResponseEntity<>(hotelService.getHotelById(hotelID),HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Boolean> deleteHotelById(@PathVariable Long hotelId){
        log.info("Attempting to Delete Hotel with Id: {}",hotelId);
        return new ResponseEntity<>(hotelService.deleteHotelById(hotelId),HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId,@RequestBody HotelDto hotelDto){
        log.info("Trying to Update Hotel with Id: {}",hotelId);
        return new ResponseEntity<>(hotelService.updateHotelById(hotelId,hotelDto),HttpStatus.CREATED);
    }

    @PatchMapping("/activate/{hotelId}")
    public ResponseEntity<HotelDto> activateHotel(@PathVariable Long hotelId){
        return new ResponseEntity<>(hotelService.activateHotel(hotelId),HttpStatus.CREATED);
    }
}
