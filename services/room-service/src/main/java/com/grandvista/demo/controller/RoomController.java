package com.grandvista.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grandvista.demo.entity.Room;
import com.grandvista.demo.repository.RoomRepository;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;

    @Value("${server.port}")
    private String serverPort;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Get all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Add a new room
    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    // Update room details
    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id,
                           @RequestBody Room updatedRoom) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Room not found"));

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setRoomType(updatedRoom.getRoomType());
        room.setPrice(updatedRoom.getPrice());
        room.setAvailable(updatedRoom.isAvailable());

        return roomRepository.save(room);
    }

    // Update room availability
    @PutMapping("/{id}/availability")
    public Room updateAvailability(@PathVariable Long id,
                                   @RequestBody boolean available) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Room not found"));

        room.setAvailable(available);

        return roomRepository.save(room);
    }

    // Delete a room
    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id) {

        if (!roomRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Room not found");
        }

        roomRepository.deleteById(id);

        return "Room deleted successfully";
    }

    // Identify which Room Service instance handles the request
    @GetMapping("/instance")
    public String getInstance() {
        return "Room Service instance running on port: " + serverPort;
    }
}