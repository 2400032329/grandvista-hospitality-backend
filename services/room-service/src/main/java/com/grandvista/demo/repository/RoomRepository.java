package com.grandvista.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grandvista.demo.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}