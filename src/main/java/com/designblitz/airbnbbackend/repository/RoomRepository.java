package com.designblitz.airbnbbackend.repository;

import com.designblitz.airbnbbackend.entity.Hotel;
import com.designblitz.airbnbbackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findRoomByHotel(Hotel hotel);

    Long id(Long id);
}
