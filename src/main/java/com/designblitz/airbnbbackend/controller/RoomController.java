package com.designblitz.airbnbbackend.controller;

import com.designblitz.airbnbbackend.dto.RoomDto;
import com.designblitz.airbnbbackend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        return new ResponseEntity<>(roomService.createNewRoom(hotelId,roomDto), HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId){
        return new ResponseEntity<>(roomService.getAllRoomsInHotel(hotelId),HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        return new ResponseEntity<>(roomService.getRoomById(roomId),HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public  ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
        return  ResponseEntity.noContent().build();
    }

}
