package com.company.suayb.service;

import com.company.suayb.model.Hotel;
import com.company.suayb.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ReservationRepository reservationRepository;
    @Override
    public Iterable<Hotel> findAll() {

        return reservationRepository.findAll();
    }

    @Override
    public void delete(String name) {

    }

    @Override
    public void save(Hotel hotel) {

    }

    @Override
    public Hotel get(String name) {
        return null;
    }

    @Override
    public Iterable<Hotel> findAvailable(Date date) {
        return reservationRepository.findByDateAfter(date);
    }
}
