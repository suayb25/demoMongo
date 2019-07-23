package com.company.suayb.repository;

import com.company.suayb.model.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservationRepository extends PagingAndSortingRepository<Hotel,Date> {
    Iterable<Hotel> findByDateAfter(Date start);
}
