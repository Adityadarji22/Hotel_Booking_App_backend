package com.designblitz.airbnbbackend.service.impl;

import com.designblitz.airbnbbackend.entity.Inventory;
import com.designblitz.airbnbbackend.entity.Room;
import com.designblitz.airbnbbackend.repository.InventoryRepository;
import com.designblitz.airbnbbackend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate startDate=LocalDate.now();
        LocalDate endDate=startDate.plusYears(1);
        while(!startDate.isAfter(endDate)){
            Inventory inventory=Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .date(startDate)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
            log.info("Adding Inventory for Date: {} ",startDate);
            startDate= startDate.plusDays(1);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        inventoryRepository.deleteByRoom(room);
    }
}
