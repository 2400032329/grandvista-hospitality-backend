package com.grandvista.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "room-service")
public interface RoomClient {

    @PutMapping("/rooms/{id}/availability")
    void updateAvailability(@PathVariable("id") Long id,
                            @RequestBody boolean available);
}