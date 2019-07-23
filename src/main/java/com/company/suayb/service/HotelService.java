package com.company.suayb.service;

import com.company.suayb.model.Hotel;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface HotelService {
    Iterable<Hotel> findAll();

    void delete(String name);

    void save(Hotel hotel);

    Hotel get(String name);


    Iterable<Hotel> findAvailable(Date date1);
}
