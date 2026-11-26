package com.designblitz.airbnbbackend.service;

import com.designblitz.airbnbbackend.entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);
}
